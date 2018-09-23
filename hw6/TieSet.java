import edu.princeton.cs.algs4.MinPQ;

import java.util.*;

public class TieSet {
    public class Node{
        boolean exist;
        Map<Character, Node> links;

        public Node(){
            exist = false;
            links = new TreeMap<>();
        }
    }

    public Node root = new Node();

    public void put(String s){
        put(root, s, 0);
    }

    private Node put(Node x, String s, int d){
        if(x == null){
            x = new Node();
        }
        if(d == s.length()){
            x.exist = true;
            return x;
        }
        char c = s.charAt(d);
        x.links.put(c,put(x.links.get(c),s,d+1));
        return x;
    }



}
