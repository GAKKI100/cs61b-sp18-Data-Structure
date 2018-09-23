package byog.Core;


public class corridors {
    private static final int MAP_WIDTH = 72;
    private static final int MAP_HEIGHT = 30;
    public static int[][] arr = new int[MAP_WIDTH][MAP_HEIGHT];

    public corridors(){
        for(int x = 0; x < MAP_WIDTH; x++){
            for (int y = 0; y < MAP_HEIGHT; y++){
                arr[x][y] = 0;
            }
        }
    }
    public class hCorridor {
        public hCorridor(int x1, int x2, int y) {
            for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
                arr[x][y] = 1;
            }
        }
    }
    public class vCorridor {
        public vCorridor(int y1, int y2, int x) {
            for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
                arr[x][y] = 1;
            }
        }
    }

}
