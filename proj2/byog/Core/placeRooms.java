package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.Random;

public class placeRooms {
    private static final int minRoomSize = 3;
    private static final int maxRoomSize = 6;
    private static final int MAP_WIDTH = 72;
    private static final int MAP_HEIGHT = 30;
    private static final int maxRooms = 18;
    private static final int minRooms = 14;
    private static int ptr = 0;
    private static long SEED;
    private static Random RANDOM;
    public  Point player1;
    public  Point door;

    public placeRooms(long seed){
        SEED = seed;
        RANDOM = new Random(SEED);
    }

    public void addMaze(TETile[][] world, TETile t){
        Room[] rooms = new Room[maxRooms];
        int roomNumber = minRooms + (int)RANDOM.nextInt(maxRooms - minRooms + 1);
        for (int z = 0; z < roomNumber; z++){
            int w = minRoomSize + (int)RANDOM.nextInt(maxRoomSize - minRoomSize + 1);
            int h = minRoomSize + (int)RANDOM.nextInt(maxRoomSize - minRoomSize + 1);
            int x = (int)RANDOM.nextInt(MAP_WIDTH - w - 2) + 1;
            int y = (int)RANDOM.nextInt(MAP_HEIGHT - h - 3) + 1;
            Room newRoom = new Room(x, y, w, h);

            rooms[ptr] = newRoom;
            for (int i = rooms[ptr].x1; i <= rooms[ptr].x2; i++){
                for (int j = rooms[ptr].y1; j <= rooms[ptr].y2; j++){
                    world[i][j] = t;
                }
            }

            Point newCenter = newRoom.center;

            if (ptr != 0){
                Point prevCenter = rooms[ptr-1].center;
                corridors corridor = new corridors();
                if (RANDOM.nextInt(2) == 1){
                    corridors.hCorridor hc = corridor.new hCorridor(prevCenter.x, newCenter.x, prevCenter.y);
                    corridors.vCorridor vc = corridor.new vCorridor(prevCenter.y, newCenter.y, newCenter.x);
                }else{
                    corridors.vCorridor vc = corridor.new vCorridor(prevCenter.y, newCenter.y, prevCenter.x);
                    corridors.hCorridor hc = corridor.new hCorridor(prevCenter.x, newCenter.x, newCenter.y);
                }
                for (int q = 0; q < MAP_WIDTH; q++){
                    for (int a = 0; a < MAP_HEIGHT; a++){
                        if (corridor.arr[q][a] == 1){
                            world[q][a] = t;
                        }
                    }
                }
            }
            ptr++;
        }
        world[rooms[5].center.x][rooms[5].center.y] = Tileset.PLAYER;
        world[rooms[7].center.x][rooms[7].center.y] = Tileset.LOCKED_DOOR;
        player1 = new Point(rooms[5].center.x, rooms[5].center.y);
        door = new Point(rooms[7].center.x, rooms[7].center.y);
    }
    public void addWall(TETile[][] world, TETile t){
        int count = 0;
        for(int i = 0; i < MAP_WIDTH; i++){
            for (int j = 0; j < MAP_HEIGHT; j++){
                if (world[i][j].equals(Tileset.FLOOR)){
                    count=1;
                }
                if (count == 1 && world[i][j].equals(Tileset.NOTHING)){
                    count = 0;
                    world[i][j] = t;
                }
            }
        }
        for(int j = 0; j < MAP_HEIGHT; j++){
            for (int i = 0; i < MAP_WIDTH; i++){
                if (world[i][j].equals(Tileset.FLOOR)){
                    count=1;
                }
                if (count == 1 && world[i][j].equals(Tileset.NOTHING)){
                    count = 0;
                    world[i][j] = t;
                }
            }
        }
        for(int i = MAP_WIDTH - 1; i >= 0; i--) {
            for (int j = MAP_HEIGHT - 1; j >= 0 ; j--) {
                if (world[i][j].equals(Tileset.FLOOR)){
                    count=1;
                }
                if (count == 1 && world[i][j].equals(Tileset.NOTHING)){
                    count = 0;
                    world[i][j] = t;
                }
            }
        }
        for(int j = MAP_HEIGHT - 1; j >= 0; j--) {
            for (int i = MAP_WIDTH - 1; i >= 0 ; i--) {
                if (world[i][j].equals(Tileset.FLOOR)){
                    count=1;
                }
                if (count == 1 && world[i][j].equals(Tileset.NOTHING)){
                    count = 0;
                    world[i][j] = t;
                }
            }
        }
    }
}
