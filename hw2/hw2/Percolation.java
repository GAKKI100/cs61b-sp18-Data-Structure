package hw2;


import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.WeakHashMap;

public class Percolation {
    // create N-by-N grid, with all sites initially blocked
    private boolean[][] arr;
    private int[][] arr1;
    private WeightedQuickUnionUF QU;
    private int count = 0;

    public Percolation(int N){
        arr = new boolean[N][N];
        arr1 = new int[N][N];
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                arr[i][j] = false;
                arr1[i][j] = helper(i,j);
            }
        }
        QU = new WeightedQuickUnionUF(N * N + 2);
        for (int j = 0; j < N; j++){
            QU.union(arr1[0][j],N * N +1);
            QU.union(arr1[N-1][j],N * N );
        }
    }


    // open the site (row, col) if it is not open already
    public void open(int row, int col){
        if (!isOpen(row,col)){
            arr[row][col] = true;
            count++;
            if (isOpen(row-1,col)){
                QU.union(helper(row-1,col),arr1[row][col]);
            }
            if (isOpen(row+1,col)){
                QU.union(helper(row+1,col),arr1[row][col]);
            }
            if (isOpen(row,col-1)){
                QU.union(helper(row,col-1),arr1[row][col]);
            }
            if (isOpen(row,col+1)){
                QU.union(helper(row,col+1),arr1[row][col]);
            }
        }
    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        if (row < 0 || row >= arr.length || col < 0 || col >= arr.length){
            return false;
        }
        return arr[row][col];
    }
    // is the site (row, col) full?

    public boolean isFull(int row, int col) {
        if (!isOpen(row,col)){
            return false;
        }
        return QU.connected(arr1[row][col], arr.length * arr.length + 1);
    }
    // number of open sites
    public int numberOfOpenSites(){
        return count;
    }
    // does the system percolate?
    public boolean percolates(){
        return QU.connected(arr.length * arr.length + 1,arr.length * arr.length);
    }
    // use for unit testing (not required)
    public int helper(int row, int col){
        return (row * arr.length) + col;
    }
}
