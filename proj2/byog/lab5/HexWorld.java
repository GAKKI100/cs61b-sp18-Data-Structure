package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 30;
    private static final int HEIGHT = 30;

    public static class Position{
        private int x;
        private int y;
        public Position(int Px, int Py){
            x = Px;
            y = Py;
        }
    }
    private static int addHexagonHelper1(int s, int y){
        int number;
        if (y < s){
            number = s-1-y;
        }else{
            number = y-s;
        }
        return number;
    }
    private static int addHexagonHelper2(int s, int y){
        int number;
        if (y < s){
            number = y*2+s;
        }else{
            number = s+(s-1)*2-(y-s)*2;
        }
        return number;
    }

    public static void addHexagon(TETile[][] world, Position p, int s, TETile t){
        int rowHexagon;
        int rowTri;
        for (int y = 0; y < 2 * s; y++){
            rowTri = addHexagonHelper1(s,y);
            rowHexagon = addHexagonHelper2(s,y);
            for (int x = rowTri; x < rowTri + rowHexagon;x++){
                world[x+p.x][y+p.y] = t;
            }
        }
    }

    public static void main(String[] args) {

        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        int s = 3;
        Position[][] p = new Position[5][9];
        for (int y = 0; y < 9; y++){
            if (y == 0 || y == 8){
                int x = 2;
                p[x][y] = new Position((2*s-1)*x, y * s);
            }else if (y%2==0){
                for (int x = 0; x <5; x = x + 2)
                {
                    p[x][y] = new Position((2*s-1)*x, y * s);
                }
            }else{
                for (int x = 1; x <4; x = x + 2){
                    p[x][y] = new Position((2*s-1)*x, y * s);
                }
            }
            //0,2;1,13;2,024;3,13;4,024;5,13;6,024;7,13;8,2
        }
        TETile t = Tileset.GRASS;
        addHexagon(world,p[0][2],s,t);
        addHexagon(world,p[0][4],s,t);
        addHexagon(world,p[1][7],s,t);
        t = Tileset.FLOWER;
        addHexagon(world,p[3][7],s,t);
        addHexagon(world,p[4][6],s,t);
        addHexagon(world,p[1][1],s,t);
        t = Tileset.TREE;
        addHexagon(world,p[2][8],s,t);
        addHexagon(world,p[3][3],s,t);
        addHexagon(world,p[4][4],s,t);
        t = Tileset.MOUNTAIN;
        addHexagon(world,p[0][6],s,t);
        addHexagon(world,p[1][3],s,t);
        addHexagon(world,p[1][5],s,t);
        addHexagon(world,p[2][0],s,t);
        addHexagon(world,p[2][2],s,t);
        addHexagon(world,p[2][4],s,t);
        addHexagon(world,p[2][6],s,t);
        addHexagon(world,p[3][1],s,t);
        t = Tileset.SAND;
        addHexagon(world,p[3][5],s,t);
        addHexagon(world,p[4][2],s,t);

        ter.renderFrame(world);
    }


}
