import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    private double ullon;
    private double lrlon;
    private double w;
    private double h;
    private double ullat;
    private double lrlat;
    private int degree;
    private double LonDPP;
    private int xSize;
    private int ySize;

    public Rasterer() {
        // YOUR CODE HERE
       this.degree = 7;
       this.ySize = 0;
       this.ySize = 0;
    }


    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    private double getLonDPP(int i){
        double lonDistanceOfWholeMap = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) * 288200;
        double lonDistanceOfTile = lonDistanceOfWholeMap / Math.pow(2,i);
        return lonDistanceOfTile / MapServer.TILE_SIZE;
    }

    private int[] getXY(){
        int[] xy = new int[4];
        int N = (int)Math.pow(2,degree);

        double x1 = (ullon - MapServer.ROOT_ULLON) /
                (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON);
        for(int i = 0; i <= N; i++){
            if(x1 <= 0){
                xy[0] = 0;
                break;
            }
            if( (double)i / N >= x1){
                xy[0] = i - 1;
                break;
            }
        }

        double y1 = (ullat - MapServer.ROOT_ULLAT) /
                (MapServer.ROOT_LRLAT - MapServer.ROOT_ULLAT);
        for(int i = 0; i <= N; i++){
            if(y1 <= 0){
                xy[1] = 0;
                break;
            }
            if( (double)i / N >= y1){
                xy[1] = i - 1;
                break;
            }
        }

        double x2 = (lrlon - MapServer.ROOT_ULLON) /
                (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON);
        if (x2 >= 1){
            xy[2] = (int)Math.pow(2,degree) - 1;
        }else{
            for(int i = 0; i <= N; i++){
                if( (double)i / N >= x2){
                    xy[2] = i - 1;
                    if(xy[2] >= Math.pow(2,degree)){
                        xy[2] = (int)Math.pow(2,degree) - 1;
                    }
                    break;
                }
            }
        }

        double y2 = (lrlat - MapServer.ROOT_ULLAT) /
                (MapServer.ROOT_LRLAT - MapServer.ROOT_ULLAT);
        if (y2 >= 1){
            xy[3] = (int)Math.pow(2,degree) - 1;
        }else{
            for(int i = 0; i <= N; i++){
                if( (double)i / N >= y2){
                    xy[3] = i - 1;
                    if(xy[3] >= (int)Math.pow(2,degree)){
                        xy[3] = (int)Math.pow(2,degree) - 1;
                    }
                    break;
                }
            }
        }
        return xy;
    }

    private double[] result(){
        double N = Math.pow(2,degree);
        double[] result = new double[4];
        int[] xy = getXY();
        double x0 = ((double) (xy[0])) / N;
        double x1 = ((double) (xy[0] + this.xSize)) / N;
        double x2 = ((double) (xy[1])) / N;
        double x3 = ((double) (xy[1] + this.ySize)) / N;

        result[0] = x0 * (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) + MapServer.ROOT_ULLON;
        result[1] = x1 * (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) + MapServer.ROOT_ULLON ;
        result[2] = x2 * (MapServer.ROOT_LRLAT - MapServer.ROOT_ULLAT) + MapServer.ROOT_ULLAT;
        result[3] = x3 * (MapServer.ROOT_LRLAT - MapServer.ROOT_ULLAT) + MapServer.ROOT_ULLAT;
        return result;
    }

   public Map<String, Object> getMapRaster(Map<String, Double> params) {
        System.out.println(params);
        int[] xy;

        this.lrlon = params.get("lrlon");
        this.ullon = params.get("ullon");
        this.lrlat = params.get("lrlat");
        this.ullat = params.get("ullat");
        this.w = params.get("w");
        this.h = params.get("h");
       Map<String, Object> results = new HashMap<>();

       if(ullon > lrlon || ullat < lrlat){
           results.put("query_success", false);
           return results;
       }
       if(lrlon < MapServer.ROOT_ULLON || ullon > MapServer.ROOT_LRLON){
           results.put("query_success", false);
           return results;
       }
       if(lrlat > MapServer.ROOT_ULLAT || ullat < MapServer.ROOT_LRLAT){
           results.put("query_success", false);
           return results;
       }
        this.LonDPP = (lrlon -ullon) * 288200 / w;

        for (int i = 0; i < 8; i++){
            if(this.LonDPP >= getLonDPP(i)){
                this.degree = i;
                break;
            }
        }
        xy = getXY();
        this.xSize = xy[2] - xy[0] + 1;
        this.ySize = xy[3] - xy[1] + 1;

        String[][] png = new String[this.ySize][this.xSize];


        for(int y = 0; y < png.length; y++){
            for (int x = 0; x < png[0].length; x++){
                png[y][x] = "d"+ degree + "_x" + (xy[0] + x) + "_y" +  (xy[1] + y)+ ".png";
            }
        }


        double[] result = result();
        results.put("render_grid", png);
        results.put("raster_ul_lon", result[0]);
        results.put("raster_ul_lat", result[2]);
        results.put("raster_lr_lon", result[1]);
        results.put("raster_lr_lat", result[3]);
        results.put("depth", this.degree);
        results.put("query_success", true);
        System.out.println(results);
        return results;
    }


}
