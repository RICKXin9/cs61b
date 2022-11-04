package byog.Core;
import byog.TileEngine.TETile;

import java.util.*;

public class Connention {
    private Queue<Position> connections;
    private Room room;
    Connention(){
        connections = new LinkedList<>();

    }
    public Queue<Position> getConnections(){
        return connections;
    }
    public void setRoom(Room room){
        this.room = room;
    }
    public Room getRoom() {
        return room;
    }
    public List<Connention> generateConn(TETile[][] world, Random random){
        /*根据现有的connection可以生成多个connection*/
        Room new_room;
        List<Room> rooms = new ArrayList<>();
        List<Connention> cons = new ArrayList<>();
        for (Position p:connections){
            new_room = room.addRandomRoom(world,p,random);
            if (new_room!=null){
                rooms.add(new_room);
            }
        }
        for (Room room:rooms){
            // 对于新生成的room，进而生成对应的connection
            Connention con = room.getRandCons(random);
            cons.add(con);
        }
        return cons;
    }
}
