package byog.Core;

public class Room {
     public int x1;
     public int x2;
     public int y1;
     public int y2;

     public int w;
     public int h;

     public Point center;

     public Room(int x, int y, int w, int h){
         this.x1 = x;
         this.x2 = x + w;
         this.y1 = y;
         this.y2 = y + h;
         this.center = new Point((int)Math.floor((x1 + x2) / 2),
                 (int)Math.floor((y1 + y2) / 2));
     }
}
