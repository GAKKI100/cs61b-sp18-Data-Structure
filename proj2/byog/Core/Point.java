package byog.Core;

import java.io.*;

public class Point implements java.io.Serializable{
    public int x;
    public int y;
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
    public boolean equals(Object p){
        if (this == p) {
            return true;
        }
        if (p == null) {
            return false;
        }
        if (this.getClass() != p.getClass()) {
            return false;
        }
        Point that = (Point) p;
        return ((this.x == that.x) && (this.y == that.y));
    }
}
