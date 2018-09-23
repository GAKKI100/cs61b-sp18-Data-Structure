package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Comparator;


public class Solver {
    private MinPQ<Node> pq;
    private Comparator<Node> cmp;
    private int moves;
    private Node result;
    private ArrayList<WorldState> arr = new ArrayList<>(100);

    public Solver(WorldState initial){
        cmp = new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                if ((n1.edtg + n1.time) <
                        (n2.edtg + n2.time)) {
                    return -1;
                } else if ((n1.edtg + n1.time) ==
                        (n2.edtg + n2.time)) {
                    return 0;
                } else {
                    return 1;
                }
            }
        };
        pq = new MinPQ<>(cmp);
        pq.insert(new Node(initial,0,null));
        Node temp = pq.delMin();
        while(!temp.ws.isGoal()){
            Iterable<WorldState> i = temp.ws.neighbors();
            moves = count(temp) + 1;
            for (WorldState ws :i){
                if(temp.prev != null){
                    if(temp.prev.ws.equals(ws)){
                        continue;
                    }
                }
                pq.insert(new Node(ws,moves,temp));
            }
            temp = pq.delMin();
        }
        result = temp;
        addResult(result);
        arr.add(result.ws);

    }

    public int moves(){
        return arr.size() - 1;
    }

    public Iterable<WorldState> solution(){
        return arr;
    }

    private Node addResult(Node p){
        if(p.prev == null){
            return p;
        }
        Node x = addResult(p.prev);
        arr.add(p.prev.ws);
        return x;
    }

    private int count(Node p){
        Node a = p;
        int count = 0;
        while(a.prev != null){
            a = a.prev;
            count++;
        }
        return count;
    }
}
