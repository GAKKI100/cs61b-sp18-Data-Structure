import java.io.*;
import java.util.*;

public class BinaryTrie implements Serializable{

    private class Node implements Serializable {
        private char ch;
        private double freq;
        private Node left, right;
        public Node(char ch, double freq, Node left, Node right){
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }
    }

    private Node root;

    private Map<Character, BitSequence> returnMap;

    private StringBuilder bitSequeneceOfChar = new StringBuilder();

    public BinaryTrie(Map<Character, Integer> frequencyTable){
        Comparator<Node> cmp = new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                if(o1.freq > o2.freq){
                    return 1;
                }else{
                    return -1;
                }
            }
        };
        PriorityQueue<Node> pq = new PriorityQueue<>(cmp);
        Iterator<Character> i = frequencyTable.keySet().iterator();
        while(i.hasNext()){
            char ch = i.next();
            double freq = frequencyTable.get(ch);
            pq.add(new Node(ch,freq,null,null));
        }

        while(pq.size() > 1){
            Node left = pq.poll();
            Node right = pq.poll();
            Node parent = new Node('\0',left.freq + right.freq, left, right);
            pq.add(parent);
        }
        root = pq.poll();
    }

    private boolean isLeaf(Node x){
        return (x.left == null && x.right == null);
    }

    public Match longestPrefixMatch(BitSequence querySequence){
        Node p = root;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < querySequence.length(); i++){
            int num = querySequence.bitAt(i);
            if(num == 0){
                p = p.left;
            }else{
                p = p.right;
            }
            sb.append(num);
            if(isLeaf(p)){
                break;
            }
        }
        BitSequence bs = new BitSequence(sb.toString());
        return new Match(bs, p.ch);
    }

    public Map<Character, BitSequence> buildLookupTable(){
        returnMap = new HashMap<>();
        bitSequeneceOfChar = new StringBuilder();
        traverse(root);
        return returnMap;
    }

    private void traverse(Node x){
        if(isLeaf(x)){
            BitSequence bs = new BitSequence(bitSequeneceOfChar.toString());
            returnMap.put(x.ch,bs);
            return;
        }
        bitSequeneceOfChar.append(0);
        traverse(x.left);
        bitSequeneceOfChar.deleteCharAt(bitSequeneceOfChar.length() - 1);
        bitSequeneceOfChar.append(1);
        traverse(x.right);
        bitSequeneceOfChar.deleteCharAt(bitSequeneceOfChar.length() - 1);
    }
}