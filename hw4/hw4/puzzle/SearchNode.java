package hw4.puzzle;

public class SearchNode implements Comparable<SearchNode>{
    private WorldState worldState;
    private SearchNode prev;
    private int moveSeq;
//    private boolean marked;
    /*
    * worldState 接口包含了邻居、距离和是否是终点方法
    * prev指指向该对象的对象
    * moveSeq指实际移动的步数
    * */
    public SearchNode(WorldState state) {
        worldState = state;
        prev = null;
        moveSeq = 0;
    }
    public SearchNode(WorldState state, SearchNode node, int moveSeq) {
        worldState = state;
        prev = node;
        this.moveSeq = moveSeq;
    }

    @Override
    public int compareTo(SearchNode o) {
        return this.moveSeq + worldState.estimatedDistanceToGoal() -
                o.moveSeq - o.worldState.estimatedDistanceToGoal();
    }

    public WorldState getWorldState() {
        return worldState;
    }
    public SearchNode getPrev() {
        return prev;
    }
    public int getMoveSeq() {
        return moveSeq;
    }
//    public boolean isMarked() {
//        return marked;
//    }
//    public void setMarked() {
//        marked = true;
//    }
}
