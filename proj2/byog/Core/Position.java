package byog.Core;
import byog.lab5.HexWorld;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class Position implements Serializable{
    private int x;
    private int y;
    public Position(){}
    public Position(int left, int up){
        x = left;
        y = up;
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public List<Position> getAround(int width, int height, boolean corner){
        List<Position> lists = new ArrayList<>();
        Position p1 = new Position(x-1,y+1);
        Position p2 = new Position(x,y+1);
        Position p3 = new Position(x+1,y+1);
        Position p4 = new Position(x-1,y);
        Position p5 = new Position(x+1,y);
        Position p6 = new Position(x-1,y-1);
        Position p7 = new Position(x,y-1);
        Position p8 = new Position(x+1,y-1);
        if(!p4.isEdge(width,height)){
            lists.add(p4);
        }
        if(!p2.isEdge(width,height)){
            lists.add(p2);
        }
        if(!p5.isEdge(width,height)){
            lists.add(p5);
        }
        if(!p7.isEdge(width,height)){
            lists.add(p7);
        }
        if (corner){
            if(!p1.isEdge(width,height)) {
                lists.add(p1);
            }
            if(!p3.isEdge(width,height)) {
                lists.add(p3);
            }
            if(!p6.isEdge(width,height)) {
                lists.add(p6);
            }
            if(!p8.isEdge(width,height)) {
                lists.add(p8);
            }
        }
        return lists;

    }
    public boolean isEdge(int wid, int hei){
        if (x>=1 && x<wid-1){
            if (y>=1 && y<hei-1){
                return false;
            }
            return true;
        }
        return true;
    }

    @Override
    public boolean equals(Object p) {
        if (this == p) {
            return true;
        }
        if (getClass() != p.getClass()){
            return false;
        }
        Position w = (Position) p;
        if (this.x == w.getX() && this.y == w.getY()) {
            return true;
        } else {
            return false;
        }
    }
}
