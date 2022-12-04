package lab11.graphs;
import edu.princeton.cs.algs4.LinkedStack;
import edu.princeton.cs.algs4.Stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private boolean hasCycle = false;
    private List<Integer> Cycle;
    public MazeCycles(Maze m) {
        super(m);
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        Random random = new Random();
        Stack<Integer> stack = new Stack<>();
        int s = random.nextInt(maze.V());
        stack.push(s);
        Cycle = new ArrayList<>();
        while (!stack.isEmpty()) {
            int pop = stack.pop();
            marked[pop] = true;
            announce();
            for (int w: maze.adj(pop)) {
                if (!marked[w]) {
                    stack.push(w);
                    edgeTo[w] = pop;

                    announce();
                }else {
                    if (edgeTo[pop] != w) {
                        hasCycle = true;
                        edgeTo[w] = pop;
                        announce();
                        Cycle.add(pop);
                    }
                }
            }
        }

    }


    // Helper methods go here
}

