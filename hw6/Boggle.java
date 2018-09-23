import java.io.File;
import java.util.*;

public class Boggle {

    // File path of dictionary file
    static String dictPath = "words.txt";

    /**
     * Solves a Boggle puzzle.
     * 
     * @param k The maximum number of words to return.
     * @param boardFilePath The file path to Boggle board file.
     * @return a list of words found in given Boggle board.
     *         The Strings are sorted in descending order of length.
     *         If multiple words have the same length,
     *         have them in ascending alphabetical order.
     */
    public static List<String> solve(int k, String boardFilePath) {
        // YOUR CODE HERE
        In in = new In(dictPath);
        trie = new TieSet();
        while(in.hasNextLine()){
            trie.put(in.readLine());
        }

        In in1 = new In(boardFilePath);
        String[] s = in1.readAllLines();
        int y = s.length;
        int x = s[0].length();
        char[][] board = new char[y][x];
        char[] boardOneD = new char[x * y];
        for(int j = 0; j < y; j++){
            for(int i = 0; i < x; i++){
                board[j][i] = s[j].charAt(i);
                boardOneD[s[0].length() * j + i] = s[j].charAt(i);
            }
        }

        List<Map<Integer, Character>> myMap  = new ArrayList<>();
        adjacencyMap(boardOneD,myMap,y,x);

        toReturn = new ArrayList<>();
        stack =new Stack<>();
        for(int i = 0; i < x * y; i++){
            passed = new ArrayList<>();
            char startChar = boardOneD[i];
            p = trie.root;
            stack.push(p);
            if(!p.links.containsKey(startChar)){
                continue;
            }
            passed.add(i);
            p = p.links.get(startChar);
            depthFirstSearching(i, myMap,boardOneD);
        }
        return sortList(toReturn,k);
    }

    private static TieSet trie;

    private static Stack<TieSet.Node> stack;

    private static TieSet.Node p;

    private static List<Integer> passed;

    private static List<String> toReturn;

    private static void adjacencyMap(char[] M, List<Map<Integer, Character>> myMap, int y, int x){
        for(int row = 0; row < y; row++){
            for(int col  = 0; col < x; col++){
                Map<Integer, Character> map = new HashMap<>();
                int loc = row * x + col;
                int ul = loc - 1 - x;
                int ur = loc + 1 - x;
                int ll = loc - 1 + x;
                int lr = loc + 1 + x;
                int above = loc - x;
                int below = loc + x;

                if(loc + 1 < x * y && col != (x - 1)){
                    map.put(loc + 1,M[loc + 1]);
                }
                if(loc - 1 >= 0 && col != 0){
                    map.put(loc - 1,M[loc - 1]);
                }
                if(above >= 0){
                    map.put(above,M[above]);
                }
                if(below < x * y){
                    map.put(below,M[below]);
                }
                if(ul >= 0 && col != 0 && row != 0){
                    map.put(ul,M[ul]);
                }
                if(ur >= 0 && col != (x - 1) && row != 0){
                    map.put(ur,M[ur]);
                }
                if(ll < x * y && col != 0 && row != (y - 1)){
                    map.put(ll,M[ll]);
                }
                if(lr < x * y && col != (x - 1) && row != (y - 1)){
                    map.put(lr,M[lr]);
                }
                myMap.add(map);
            }
        }
    }

    private static void depthFirstSearching(int start, List<Map<Integer, Character>> myMap, char[] M){
        if(p.exist){
            String a = pathToString(M);
            if(!toReturn.contains(a)){
                toReturn.add(a);
            }
        }
        Map<Integer, Character> adjMap =  myMap.get(start);
        stack.push(p);
        for(int next : adjMap.keySet()){
            if(passed.contains(next)){
                continue;
            }
            if(p.links.isEmpty()){
                continue;
            }
            char nextChar = M[next];
            if(!p.links.containsKey(nextChar)){
                continue;
            }
            p = p.links.get(nextChar);
            passed.add(next);
            depthFirstSearching(next,myMap,M);

        }
        passed.remove(passed.size() - 1);
        stack.pop();
        p = stack.peek();
    }

    private static String pathToString(char[] M){
        Iterator<Integer> i = passed.iterator();
        StringBuilder sb = new StringBuilder();
        while(i.hasNext()){
            sb.append(M[i.next()]);
        }
        return sb.toString();
    }

    private static List<String> sortList(List<String> myList, int k){
        Comparator<String> x = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2)
            {
                if(o1.length() > o2.length())
                    return -1;

                if(o2.length() > o1.length())
                    return 1;
                if(o2.length() == o1.length()){
                    return o1.compareTo(o2);
                }
                return 0;
            }

        };

        List<String> sortList = new ArrayList<>();
        Collections.sort(myList,  x);
        for(int i = 0; i < k; i++){
            if(i > myList.size() - 1){
                break;
            }
            sortList.add(myList.get(i));
        }
        return sortList;
    }

    public static void main(String[] args){
        File tmpDir = new File(args[1]);
        if(Integer.valueOf(args[0]) <= 0 || !tmpDir.exists()){
            throw new IllegalArgumentException();
        }

        List<String> solveList = solve(Integer.valueOf(args[0]) ,args[1]);
        Iterator<String> i = solveList.iterator();
        while(i.hasNext()){
            System.out.println(i.next());
        }
    }
}
