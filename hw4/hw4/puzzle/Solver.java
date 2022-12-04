package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Solver {
    private MinPQ<SearchNode> PQ;
    private List<WorldState> worldStateList;
    public int numberOfTotalThings;

    public Solver(WorldState initial) {
        numberOfTotalThings = 0;
        SearchNode initialNode = new SearchNode(initial);
        PQ = new MinPQ<>();
        PQ.insert(initialNode);
        while (true) {
            SearchNode p = PQ.delMin();
            if (p.getWorldState().isGoal()) {
                getAnswer(p);
                return;
            } else {
                Iterable<WorldState> neighs = p.getWorldState().neighbors();
                for (WorldState n : neighs) {
                    if (p.getPrev() == null || !n.equals(p.getPrev().getWorldState())) {
                        PQ.insert(new SearchNode(n, p, p.getMoveSeq() + 1));
                        numberOfTotalThings++;
                    }
                }
            }
        }
    }

    private void getAnswer(SearchNode p) {
        worldStateList = new ArrayList<>();

        while (p.getPrev() != null) {
            worldStateList.add(p.getWorldState());
            p = p.getPrev();
        }
        worldStateList.add(p.getWorldState());
    }
    public int moves() {
        return worldStateList.size()-1;
    }
    public Iterable<WorldState> solution() {
        List<WorldState> worldStates = new ArrayList<>();
        for (int i = worldStateList.size()-1; i>=0; i--) {
            worldStates.add(worldStateList.get(i));
        }
        return worldStates;
    }
}
