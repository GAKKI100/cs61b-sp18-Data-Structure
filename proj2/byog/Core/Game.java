package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;
import java.awt.Font;

import java.awt.*;
import java.io.*;



public class Game{
    /* Feel free to change the width and height. */
    public static final int WIDTH = 72;
    public static final int HEIGHT = 30;
    public TERenderer ter = new TERenderer();
    private static int MouseX;
    private static int MouseY;
    public Point player;
    public Point door;
    public boolean win = false;
    public life LIFE;
    public static TETile[][] finalWorldFrame;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        GUI_Initial();
        drawGUI();
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                break;
            }
        }
        char c = StdDraw.nextKeyTyped();
        if (c == 'N' || c == 'n'){
            newGame();
            System.exit(0);
        } else if (c == 'L' || c == 'l'){
           TETile[][] object1 = null;
           Point player1 = null;
           Point door1 = null;
           life LIFE1 = null;
           String filename = "file.ser";
           String filename1 = "file1.ser";
           String filename2 = "file2.ser";
           String filename3 = "file3.ser";
            // Deserialization
            try
            {
                // Reading the object from a file
                FileInputStream file = new FileInputStream(filename);
                ObjectInputStream in = new ObjectInputStream(file);
                FileInputStream file1 = new FileInputStream(filename1);
                ObjectInputStream in1 = new ObjectInputStream(file1);
                FileInputStream file2 = new FileInputStream(filename2);
                ObjectInputStream in2 = new ObjectInputStream(file2);
                FileInputStream file3 = new FileInputStream(filename3);
                ObjectInputStream in3 = new ObjectInputStream(file3);

                // Method for deserialization of object
                object1 = (TETile[][]) in.readObject();
                finalWorldFrame = object1;
                player1 = (Point) in1.readObject();
                player = player1;
                door1 = (Point) in2.readObject();
                door = door1;
                LIFE1 = (life) in3.readObject();
                LIFE = LIFE1;

                in.close();
                file.close();
                in1.close();
                file1.close();
                in2.close();
                file2.close();
                in3.close();
                file3.close();
                ter.renderFrame(finalWorldFrame);
                System.out.println("Object has been deserialized ");
                operation();
                System.exit(0);
            } catch(IOException ex) {
                System.out.println("IOException is caught");
            } catch(ClassNotFoundException ex)
            {
                System.out.println("ClassNotFoundException is caught");
            }

        }else if (c == 'Q' || c == 'q'){
            System.exit(0);
        }

    }

    public void GUI_Initial(){
        StdDraw.setCanvasSize(this.WIDTH * 16, this.HEIGHT * 16);
        StdDraw.setXscale(0, this.WIDTH);
        StdDraw.setYscale(0, this.HEIGHT);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
    }

    public void drawGUI() {
        StdDraw.clear();
        StdDraw.clear(Color.black);

        Font bigFont = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setFont(bigFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(((double)WIDTH)/2, ((double)HEIGHT)*3/4, "CS 61B: THE GAME");
        Font smallFont = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(smallFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(((double)WIDTH)/2, 12.0, "New Game(N)");
        StdDraw.text(((double)WIDTH)/2, 9.0, "Load Game(L)");
        StdDraw.text(((double)WIDTH)/2, 6.0, "Quit Game(Q)");
        StdDraw.show();
    }

    public void drawSeed(String s){
        Font smallFont = new Font("Monaco", Font.CENTER_BASELINE, 20);
        StdDraw.setFont(smallFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(((double)WIDTH)/2, 3.0, "SEED: "+s);
        StdDraw.show();
    }

    public void newGame(){
        ter.initialize(WIDTH, HEIGHT);
        finalWorldFrame = new TETile[WIDTH][HEIGHT];
        String input = "";
        drawSeed(input);
        while (true){
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char c = StdDraw.nextKeyTyped();
            if (c == 'S'){
                break;
            }
            input += String.valueOf(c);
            drawGUI();
            drawSeed(input);
        }
        Long seed = toDigit(input);

        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                finalWorldFrame[x][y] = Tileset.NOTHING;
            }
        }
        placeRooms PLACEROOMS = new placeRooms(seed);
        TETile t = Tileset.FLOOR;
        PLACEROOMS.addMaze(finalWorldFrame,t);
        t = Tileset.WALL;
        PLACEROOMS.addWall(finalWorldFrame,t);
        player = PLACEROOMS.player1;
        door = PLACEROOMS.door;
        LIFE = new life(5);
        finalWorldFrame = drawLife(finalWorldFrame, LIFE.x);
        ter.renderFrame(finalWorldFrame);
        operation();
    }

    public void operation(){
        while (true){
            if(StdDraw.hasNextKeyTyped()){
                char c = StdDraw.nextKeyTyped();
                if (c  == 'Q'){
                    String filename = "file.ser";
                    String filename1 = "file1.ser";
                    String filename2 = "file2.ser";
                    String filename3 = "file3.ser";

                    // Serialization
                    try
                    {
                        //Saving of object in a file
                        FileOutputStream file = new FileOutputStream(filename);
                        ObjectOutputStream out = new ObjectOutputStream(file);
                        FileOutputStream file1 = new FileOutputStream(filename1);
                        ObjectOutputStream out1 = new ObjectOutputStream(file1);
                        FileOutputStream file2 = new FileOutputStream(filename2);
                        ObjectOutputStream out2 = new ObjectOutputStream(file2);
                        FileOutputStream file3 = new FileOutputStream(filename3);
                        ObjectOutputStream out3 = new ObjectOutputStream(file3);

                        // Method for serialization of object
                        out.writeObject(finalWorldFrame);
                        out1.writeObject(player);
                        out2.writeObject(door);
                        out3.writeObject(LIFE);

                        out.close();
                        file.close();
                        out1.close();
                        file1.close();
                        out2.close();
                        file2.close();
                        out3.close();
                        file3.close();

                        System.out.println("Object has been serialized");

                    } catch(IOException ex)
                    {
                        System.out.println("IOException is caught");
                    }
                    break;
                }
                finalWorldFrame = playerMove(finalWorldFrame,c);
                finalWorldFrame = drawLife(finalWorldFrame, LIFE.x);
                ter.renderFrame(finalWorldFrame);
                if (win){
                    System.exit(0);
                }else if (LIFE.x == 0){
                    System.exit(0);
                }
                try
                {
                    Thread.sleep(10);
                }
                catch(InterruptedException ex)
                {
                    Thread.currentThread().interrupt();
                }
            }
            if(StdDraw.isMousePressed()){
                MouseX = (int)StdDraw.mouseX();
                MouseY = (int)StdDraw.mouseY();
                String des = finalWorldFrame[MouseX][MouseY].description();
                finalWorldFrame = drawHUD(finalWorldFrame, des);
                ter.renderFrame(finalWorldFrame);
                try
                {
                    Thread.sleep(1000);
                }
                catch(InterruptedException ex)
                {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        finalWorldFrame = new TETile[WIDTH][HEIGHT];
        ter.initialize(WIDTH, HEIGHT);

        Long seed = toDigit(input);

        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                finalWorldFrame[x][y] = Tileset.NOTHING;
            }
        }
        ter.renderFrame(finalWorldFrame);
        placeRooms PLACEROOMS = new placeRooms(seed);
        TETile t = Tileset.FLOOR;
        PLACEROOMS.addMaze(finalWorldFrame,t);
        t = Tileset.WALL;
        PLACEROOMS.addWall(finalWorldFrame,t);
        player = PLACEROOMS.player1;
        door = PLACEROOMS.door;
        LIFE = new life(5);
        finalWorldFrame = drawLife(finalWorldFrame, LIFE.x);
        ter.renderFrame(finalWorldFrame);
        operation();
        return finalWorldFrame;
    }

    public Long toDigit(String input){
        char[] c = input.toCharArray();
        int count = 0;
        for (int i = 0; i < input.length(); i++){
            if (Character.isDigit(c[i])){
                count++;
            }
        }
        int[] arr = new int[count];

        count = 0;
        for (int i = 0; i < input.length(); i++) {
            if (Character.isDigit(c[i])) {
                arr[count] = c[i] - '0';
                count++;
            }
        }
        StringBuilder builder = new StringBuilder();
        for (int i : arr) {
            builder.append(i);
        }
        long requestLong = Long.parseLong(builder.toString());
        return requestLong;
    }

    public TETile[][] drawHUD(TETile[][] finalWorldFrame, String des) {
        for (int i = 0; i < 15; i++){
            finalWorldFrame[i][HEIGHT-1] = Tileset.NOTHING;
        }
        for (int i = 0; i < des.length(); i++){
            finalWorldFrame[i][HEIGHT-1] = new TETile (des.toCharArray()[i], Color.white, Color.black, "nothing");
        }
        return finalWorldFrame;
    }

    public TETile[][] drawLife(TETile[][] finalWorldFrame, int life){
        for (int i = 60; i < 70; i++) {
            finalWorldFrame[i][HEIGHT - 1] = Tileset.NOTHING;
        }
        for (int i = 60; i < 60 + life; i++){
            finalWorldFrame[i][HEIGHT-1] = Tileset.FLOWER;
        }
        return finalWorldFrame;
    }

    public TETile[][] playerMove(TETile[][] finalWorldFrame, char c){
        TETile t = Tileset.WALL;
        if (c == 'w'){
            if (!finalWorldFrame[player.x][player.y+1].equals(t)){
                finalWorldFrame[player.x][player.y] = Tileset.FLOOR;
                finalWorldFrame[player.x][player.y+1] = Tileset.PLAYER;
                player = new Point(player.x, player.y+1);
                if (player.equals(door)){
                    win = true;
                }
            }else{
                LIFE.x--;
            }
            return finalWorldFrame;
        }else if (c == 'a'){
            if (!finalWorldFrame[player.x-1][player.y].equals(t)){
                finalWorldFrame[player.x][player.y] = Tileset.FLOOR;
                finalWorldFrame[player.x-1][player.y] = Tileset.PLAYER;
                player = new Point(player.x-1, player.y);
                if (player.equals(door)){
                    win = true;
                }
            } else{
                LIFE.x--;
            }
            return finalWorldFrame;
        }else if (c == 's'){
            if (!finalWorldFrame[player.x][player.y-1].equals(t)){
                finalWorldFrame[player.x][player.y] = Tileset.FLOOR;
                finalWorldFrame[player.x][player.y-1] = Tileset.PLAYER;
                player = new Point(player.x, player.y-1);
                if (player.equals(door)){
                    win = true;
                }
            }else{
                LIFE.x--;
            }
            return finalWorldFrame;
        }else if (c == 'd'){
            if (!finalWorldFrame[player.x+1][player.y].equals(t)){
                finalWorldFrame[player.x][player.y] = Tileset.FLOOR;
                finalWorldFrame[player.x+1][player.y] = Tileset.PLAYER;
                player = new Point(player.x+1, player.y);
                if (player.equals(door)){
                    win = true;
                }
            }else{
                LIFE.x--;
            }
            return finalWorldFrame;
        }else {
            return finalWorldFrame;
        }
    }
}