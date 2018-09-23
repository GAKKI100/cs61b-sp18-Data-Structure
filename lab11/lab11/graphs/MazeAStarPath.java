package lab11.graphs;

import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private MinPQ<Integer> pq;
    private Comparator<Integer> cmp;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        cmp = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if(h(o1) + distTo[o1] < h(o2) + distTo[o2]){
                    return -1;
                }else if (h(o1) + distTo[o1] == h(o2) + distTo[o2]){
                    return 0;
                }else {
                    return 1;
                }
            }
        };
        pq = new MinPQ<>(cmp);

    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return Math.abs(maze.toX(s) - maze.toX(t)) + Math.abs(maze.toY(s) - maze.toY(t));
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return pq.delMin();
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        // TODO
        pq.insert(s);
        marked[s] = true;
        announce();
        while(!pq.isEmpty()){
            int v = findMinimumUnmarked();
            for (int w : maze.adj(v)){
                if (!marked[w]){
                    pq.insert(w);
                    edgeTo[w] = v;
                    announce();
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    announce();
                    if (w == t) {
                        targetFound = true;
                    }
                    if (targetFound) {
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

