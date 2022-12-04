package lab11.graphs;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;

    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        maze = m;
        s = maze.xyTo1D(sourceX,sourceY);
        t = maze.xyTo1D(targetX,targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        announce();
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        Queue<Integer> queue = new LinkedList<>();

        queue.add(s);
        while (!queue.isEmpty()) {
            int re = queue.remove();
            marked[re] = true;
            announce();
            if (re == t) {
                break;
            }
            for (int around:maze.adj(re)) {
                if (!marked[around]) {
                    edgeTo[around] = re;
                    distTo[around] = distTo[re] + 1;
                    announce();
                    queue.add(around);
                }

            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

