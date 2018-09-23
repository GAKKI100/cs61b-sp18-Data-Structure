package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState{
    private int[][] tiles2D;
    private int[][] temp;
    private int[] tileD;
    private int[][] goal2D;
    private int[] goalD;
    int BLANK =  0;

    public Board(int[][] tiles){
        tiles2D = new int[tiles.length][tiles.length];
        tileD = new int[size() * size()];
        goal2D = new int[size()][size()];
        goalD = new int[size() * size()];

        for(int i = 0; i < size();i++){
            for(int j = 0; j < size(); j++){
                tiles2D[i][j] = tiles[i][j];
                tileD[toOneD(i,j)] = tiles2D[i][j];
                goal2D[i][j] = toOneD(i,j)+1;
                goalD[toOneD(i,j)] = toOneD(i,j) + 1;
            }
        }
        goal2D[size() - 1][size() - 1] = BLANK;
        goalD[size() * size() - 1] = BLANK;
    }

    private int[] toTwoD(int n){
        int[] ij = new int[2];
        ij[0] = n / size();
        ij[1] = n % size();
        return ij;
    }

    private int toOneD(int i, int j){
        return i * size() + j;
    }


    public int tileAt(int i, int j){
        if(i < 0 || i >= size() || j < 0 || j >= size()){
            throw new IndexOutOfBoundsException();
        }
        if(tiles2D[i][j] == BLANK){
           return 0;
       }
       return tiles2D[i][j];
    }

    public int size(){
        return tiles2D.length;
    }

    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }


    public int hamming(){
        int count = 0;
        for (int i = 0; i < size() * size(); i++){
            if(goalD[i] != BLANK && tileD[i] != goalD[i]){
                count++;
            }
        }
        return count;
    }

    public int manhattan(){
        int[] goal_ij;
        int[] tile_ij;
        int sum = 0;

        for (int i = 0; i < size() * size(); i++){
            for (int j = 0; j < size() * size(); j++){
                if(goalD[i] != BLANK && goalD[i] == tileD[j]){
                    goal_ij = toTwoD(i);
                    tile_ij = toTwoD(j);
                    sum = sum + countDistance(goal_ij[0],goal_ij[1],
                            tile_ij[0],tile_ij[1]);
                }
            }
        }
        return sum;
    }


    private int countDistance(int x1, int y1, int x2, int y2){
        return Math.abs(x1 -x2) + Math.abs(y1 - y2);
    }

    public int estimatedDistanceToGoal(){
        return manhattan();
    }
    public boolean equals(Object y){
        return (this.tiles2D == ((Board)y).tiles2D);
    }

    /** Returns the string representation of the board. 
      * Uncomment this method. */
   public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
