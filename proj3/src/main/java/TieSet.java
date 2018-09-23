import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TieSet {
    private static final int R = 128;
    public StringBuilder suffix = new StringBuilder("");

    private class Node{
        boolean exist;
        Node[] links;

        public Node(){
            links = new Node[R];
            exist = false;
        }
    }

    private Node root = new Node();

    public void put(String key){
        put(root,key,0);
    }
    private Node put(Node x, String key, int d){
        if(x == null){
            x = new Node();
        }
        if(d == key.length()){
            x.exist = true;
            return x;
        }
        char c = key.charAt(d);
        x.links[c] = put(x.links[c],key,d+1);
        return x;
    }
    public void find(String key){
        Node p = root;
        for(int i = 0; i < key.length(); i++){
            if(p.links[key.charAt(i)] == null){
                return;
            }
            p = p.links[key.charAt(i)];
        }
        dfs(p);
    }

    private void dfs(Node p){
        if(p.exist){
            this.suffix.append("-");
            return;
        }

        for(int i = 0; i < R; i++){
            Node w = p.links[i];
            if(w == null){
                continue;
            }
            this.suffix.append((char)i);
            dfs(w);
        }
    }

}
