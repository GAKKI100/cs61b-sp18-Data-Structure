import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private Picture p;
    private int width;
    private int height;
    private boolean horizontal = false;
    private int realWidth;
    private int realHeight;

    public SeamCarver(Picture picture){
        p = picture;
        this.width = p.width();
        this.height = p.height();
        this.realHeight = p.height();
        this.realWidth = p.width();
    }

    // current picture
    public Picture picture(){
        return this.p;
    }

    // width of current picture
    public int width(){
        return width;
    }

    // height of current picture
    public int height(){
        return height;
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y)
    {
        double deltaRx  = picture().get((x + 1 + width())% width(),y).getRed() -
                picture().get((x -1 + width() )% width(),y).getRed();
        double deltaGx  = picture().get((x + 1 + width())% width(),y).getGreen() -
                picture().get((x -1 + width())% width(),y).getGreen();
        double deltaBx = picture().get((x + 1 + width())% width(),y).getBlue() -
                picture().get((x -1 + width())% width(),y).getBlue();
        double deltaXSqrt = deltaBx * deltaBx + deltaGx * deltaGx + deltaRx * deltaRx;

        double deltaRy  = picture().get(x,(y + 1 + height()) % height()).getRed() -
                picture().get(x,(y - 1 + height()) % height()).getRed();
        double deltaGy  = picture().get(x,(y + 1 + height()) % height()).getGreen() -
                picture().get(x,(y - 1 + height()) % height()).getGreen();
        double deltaBy = picture().get(x,(y + 1 + height()) % height()).getBlue() -
                picture().get(x,(y - 1 + height()) % height()).getBlue();
        double deltaYSqrt = deltaBy * deltaBy + deltaGy * deltaGy + deltaRy * deltaRy;
        return deltaXSqrt + deltaYSqrt;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam(){
      this.horizontal = true;
      int[] returnArr = findVerticalSeam();
      this.width = realWidth;
      this.height = realHeight;
      this.horizontal = false;
      return returnArr;
    }


    // sequence of indices for vertical seam
    public int[] findVerticalSeam(){
        double[][] M = findM();
        double min = Double.MAX_VALUE;
        int start = 0;
        for(int i = 0; i < width(); i++){
            if(M[height()-1][i] < min){
                min = M[height()-1][i];
                start = i;
            }
        }
        return helper(M,start);
    }

    private double [][] findM(){
        double e[][] = new double[height()][width()];
        for(int j = 0; j < height(); j++){
            for(int i = 0; i < width(); i++){
                e[j][i] = energy(i, j);
            }
        }
        double[][] e1;
        if (this.horizontal){
            this.width = realHeight;
            this.height = realWidth;
            e1 = new double[height()][width()];
            for(int j = 0; j < height(); j++){
                for(int i = 0; i < width(); i++){
                    e1[j][i] = e[i][j];
                }
            }
        }else{
            e1 = e;
        }

        double M[][] = new double[height()][width()];
        for(int i = 0; i < width(); i++){
            M[0][i] = e1[0][i];
        }

        for(int j = 1; j < height(); j++){
            for(int i = 0; i < width(); i++){
                if(i == 0){
                    M[j][i] = e1[j][i] + Math.min(M[j-1][i], M[j-1][i + 1]);
                }else if(i == width() - 1){
                    M[j][i] = e1[j][i] + Math.min(M[j-1][i], M[j-1][i - 1]);
                }else{
                    M[j][i] = e1[j][i] + Math.min(Math.min(M[j-1][i], M[j-1][i - 1]),
                            Math.min(M[j-1][i], M[j-1][i + 1]));
                }

            }
        }
        return M;
    }

    private int[] helper(double[][] M, int i){
        double next;
        int[] path = new int[height()];
        path[height()-1] = i;

        for(int j = height() - 1; j >= 1; j--){
            if(i == 0){
                next = Math.min(M[j - 1][i], M[j - 1][i + 1]);
                if(next == M[j - 1][i]){
                    path[j - 1] = i;
                }else{
                    path[j - 1] = i + 1;
                }
            }else if(i == width() - 1){
                next = Math.min(M[j - 1][i], M[j - 1][i - 1]);
                if(next == M[j - 1][i]){
                    path[j - 1] = i;
                }else{
                    path[j - 1] = i - 1;
                }
            } else{
                next = Math.min(Math.min(M[j - 1][i], M[j - 1][i - 1]),
                        Math.min(M[j - 1][i], M[j - 1][i + 1]));
                if(next == M[j - 1][i]){
                    path[j - 1] = i;
                }else if (next == M[j - 1][i - 1]){
                    path[j - 1] = i - 1;
                }else{
                    path[j - 1] = i + 1;
                }
            }
            i = path[j - 1];
        }
        return path;
    }


    // remove horizontal seam from picture
    public  void removeHorizontalSeam(int[] seam){

    }

    // remove vertical seam from picture
    public  void removeVerticalSeam(int[] seam){

    }
}