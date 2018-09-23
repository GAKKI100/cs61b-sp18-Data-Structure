package hw4.puzzle;

public class Node{
    public WorldState ws;
    public int time;
    public Node prev;
    public int edtg;



    public Node(WorldState ws,int time,Node prev){
        this.ws = ws;
        this.time = time;
        this.prev = prev;
        this.edtg = ws.estimatedDistanceToGoal();
    }
}
