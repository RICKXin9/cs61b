package byog.Core;
import org.junit.Test;

import java.sql.PseudoColumnUsage;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class RoomTest {
    Position p1 = new Position(0,0);
    Position p2 = new Position(2,2);
    Room room = new Room(p1,p2);
    List<Position> legalRoom = room.getFindWall();

    @Test
    public void testGetFindWall(){
        Position m1 = new Position(1,0);
        Position m2 = new Position(0,1);
        Position m3 = new Position(2,1);
        Position m4 = new Position(1,2);
        List<Position> wantList = new LinkedList<>();
        wantList.add(m1);
        wantList.add(m2);
        wantList.add(m3);
        wantList.add(m4);

    }
}
