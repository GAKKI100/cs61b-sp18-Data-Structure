package lab11.graphs;

import edu.princeton.cs.algs4.Stack;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private Stack<Integer> stack;
    private int start;
    private int end;

    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        s = maze.xyTo1D(1, 1);
        t = maze.xyTo1D(maze.N(), maze.N());
        distTo[s] = 0;
        edgeTo[s] = s;
        stack = new Stack<>();
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        dfs(s);
        if(start != 0){
            pop(start);
        }
    }

    private void dfs(int v){
        marked[v] = true;
        announce();
        stack.push(v);
        if (v == t) {
            targetFound = true;
        }
        if (targetFound) {
            return;
        }

        for (int w : maze.adj(v)){
            if (!marked[w]){
                distTo[w] = distTo[v] + 1;
                stack.push(w);
                dfs(w);
                if (targetFound) {
                    return;
                }

            }else{
                if(distTo[w] != distTo[v] - 1){
                    start = w;
                    targetFound = true;
                    return;
                }
            }
        }

    }

    private void pop(int start){
        end = stack.peek();
        while(stack.peek() != start){
            edgeTo[stack.pop()] = stack.peek();
            announce();
        }
        edgeTo[start] = end;
        announce();
    }
}

