package byog.Core;
import java.util.ArrayList;
import byog.TileEngine.TETile;
import byog.TileEngine.TERenderer;
import byog.TileEngine.Tileset;


import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Room {
    private Position bottomLeft;
    private Position upRight;
    private static int roomMaxNum = 20;
    private static int roomMaxWidth = 6;
    private static int roomMaxHeight = 5;
    private static int maxConnection = 8;
    private static int roomMinWidth = 3;
    private static int roomMinHeight = 3;

    public Room(Position p1, Position p2){
        bottomLeft = p1;
        upRight = p2;
    }
    public int getWidth() {
        return upRight.getX()-bottomLeft.getX()+1;
    }
    public int getHeight(){
        return upRight.getY()-bottomLeft.getY()+1;
    }
    public static int getRoomMaxWidth(){
        return roomMaxWidth;
    }
    public static int getRoomMaxNum(){
        return roomMaxNum;
    }
    public static int getRoomMinHeight(){
        return roomMaxHeight;
    }
    public static int getMaxConnection(){
        return maxConnection;
    }
    public void addRoom(TETile[][] world){
        List<Position> wallLists = getRoomWall();
        for (Position wall:wallLists){
            world[wall.getX()][wall.getY()] = Tileset.WALL;
        }
        for (int i = bottomLeft.getX()+1;i <= upRight.getX()-1;i++){
            for (int j=bottomLeft.getY()+1;j<= upRight.getY()-1;j++){

                world[i][j] = Tileset.FLOOR;
            }
        }
    }
    /*
    * 根据链接点的位置构造出房间
    * */
    public Room addRandomRoom(TETile[][] world, Position p, Random random){
        int tryTimes = 1;
        int height = world[0].length;
        int width = world.length;
        Room new_room;
        // 获取当前room关于链接点p的新room的坐标
        Position[] positions = getRandomPosWithRoom(p,random);
        new_room = new Room(positions[0],positions[1]);
//        System.out.println(new_room.isLegal(world));
        if (new_room.getBottomLeft().getX()<0 || new_room.getBottomLeft().getY()<0
        || new_room.getUpRight().getX()>width-1 || new_room.getUpRight().getY() > height-1) {
            return null;
        }
        while (tryTimes<50 && !new_room.isLegal(world)){
            positions = getRandomPosWithRoom(p,random);
            new_room = new Room(positions[0],positions[1]);
            tryTimes++;
        }
        if (!new_room.isLegal(world)){return null;}
        new_room.addRoom(world);
        world[p.getX()][p.getY()] = Tileset.FLOOR;
        /* Add new connections */
        return new_room;

    }
    public Connention getRandCons(Random random) {
        Connention conn = new Connention();
        conn.setRoom(this);
        int conNum = RandomUtils.uniform(random,4,8);
//        System.out.println("生成的连接点个数为"+conNum);
        for (int i=0; i<conNum; i++) {
            Position p = getConnention(random);
            if (!conn.getConnections().contains(p)){
                conn.getConnections().offer(p);
            }

        }
        return conn;
    }


    public Position getConnention(Random random) {

        double select = RandomUtils.uniform(random);
        Position gp = new Position();

        if (select < 0.25) {
            gp.setX(RandomUtils.uniform(random, bottomLeft.getX() + 1, upRight.getX()));

            gp.setY(bottomLeft.getY());
        } else if (select < 0.5) {
            gp.setX(RandomUtils.uniform(random,bottomLeft.getX()+1, upRight.getX()));
            gp.setY(upRight.getY());


        } else if (select < 0.75) {
            gp.setY(RandomUtils.uniform(random,bottomLeft.getY()+1,upRight.getY()));
            gp.setX(bottomLeft.getX());

        } else {
            gp.setY(RandomUtils.uniform(random,bottomLeft.getY()+1,upRight.getY()));
            gp.setX(upRight.getX());

        }

        return gp;
    }

    public Position[] getRandomPosWithRoom(Position p, Random random){
        // p1 作为连接点而存在
        Directions direction = findDirection(p);
        int width = RandomUtils.uniform(random,3,roomMaxWidth);
        int height = RandomUtils.uniform(random,3,roomMaxHeight);
        Position p1;
        Position p2;
        Position[] pos = new Position[2];
        switch (direction){
            case UP:
                int bottom_x = RandomUtils.uniform(random,p.getX()+1-width,p.getX());
                int bottom_y = p.getY();
                p1 = new Position(bottom_x,bottom_y);
                p2 = new Position(bottom_x+width,bottom_y+height);
                pos[0] = p1;
                pos[1] = p2;
                return pos;
            case DOWN:
                int up_x = RandomUtils.uniform(random,p.getX()+1,p.getX()-1+width);
                int up_y = p.getY();
                bottom_x = up_x - width;
                bottom_y = up_y - height;
                p1 = new Position(bottom_x,bottom_y);
                p2 = new Position(bottom_x+width,bottom_y+height);
                pos[0] = p1;
                pos[1] = p2;
                return pos;
            case LEFT:
                up_y = RandomUtils.uniform(random,p.getY()+1,p.getY()-1+height);
                up_x = p.getX();
                bottom_x = up_x - width;
                bottom_y = up_y - height;
                p1 = new Position(bottom_x,bottom_y);
                p2 = new Position(up_x,up_y);
                pos[0] = p1;
                pos[1] = p2;
                return pos;
            case RIGHT:
                bottom_y = RandomUtils.uniform(random,p.getY()-height+2,p.getY());
                bottom_x = p.getX();
                p1 = new Position(bottom_x,bottom_y);
                p2 = new Position(bottom_x+width,bottom_y+height);
                pos[0] = p1;
                pos[1] = p2;
                return pos;
            default:
                return null;
        }

    }

    public boolean isLegal(TETile[][] world){
        List<Position> allpos = getRoomTile();


        for (Position pos: allpos){
//            System.out.println(world[pos.getX()][pos.getY()].description());
//            System.out.println(world[pos.getX()][pos.getY()].getClass());
//            System.out.println(world[pos.getX()][pos.getY()].equals(Tileset.WALL));
            try {
                if (world[pos.getX()][pos.getY()].equals(Tileset.WALL) ||
                        world[pos.getX()][pos.getY()].equals(Tileset.FLOOR)) {
                    return false;
                }
            }catch (Exception e) {
                System.out.println("x坐标"+pos.getX()+"y坐标"+pos.getY());
            }
        }
        return true;
    }

    public List<Position> getRoomWall(){
        List<Position> list = new ArrayList<>();
        int posx = bottomLeft.getX();
        int posy = bottomLeft.getY();
        for (int i=0;i<getWidth();i++){
            for (int j=0;j<getHeight();j++){
                if (j==0 || j==getHeight()-1 || i==0 || i==getWidth()-1){
                    list.add(new Position(posx+i,posy+j));
                }
            }
        }
        return list;
    }

    public List<Position> getAllTile(){
        List<Position> list = new ArrayList<>();
        int posx = bottomLeft.getX();
        int posy = bottomLeft.getY();
        for (int i=0;i<getWidth();i++){
            for (int j=0;j<getHeight();j++){

                list.add(new Position(posx+i,posy+j));

            }
        }
        return list;
    }

    public List<Position> getRoomTile(){

        List<Position> list = new ArrayList<>();
        int posx = bottomLeft.getX();
        int posy = bottomLeft.getY();
        for (int i=1;i<getWidth()-1;i++){
            for (int j=1;j<getHeight()-1;j++){

                list.add(new Position(posx+i,posy+j));

            }
        }
        return list;
    }


    public Position[] getCornerPosition(){
        Position[] posList = new Position[4];
        int minX = bottomLeft.getX();
        int minY = bottomLeft.getY();
        int width = getWidth();
        int height = getHeight();
        // 1,3,2,4
        Position p2 = new Position(minX,minY+height-1);
        Position p3 = new Position(minX+width-1,minY);
        posList[0] = bottomLeft;
        posList[1] = p3;
        posList[2] = p2;
        posList[3] = upRight;
        return posList;

    }
    public List<Position> getFindWall() {
        List<Position> legalWall;
        List<Position> roomTile = getRoomWall();
        Position[] cornerTile = getCornerPosition();
        for (Position p:cornerTile) {
            for (int i = 0; i < roomTile.size(); i++) {
                if (p.equals(roomTile.get(i))) {
                    System.out.println("相等！");
                    roomTile.remove(i);
                    break;
                }
            }
        }
        legalWall = roomTile;
        return legalWall;
    }

    public Directions findDirection(Position con){
        if (con.getY()==upRight.getY()){
            return Directions.UP;
        }
        if (con.getY() == bottomLeft.getY()){
            return Directions.DOWN;
        }
        if (con.getX()==bottomLeft.getX()){
            return Directions.LEFT;
        }
        if (con.getX() == upRight.getX()){
            return Directions.RIGHT;
        }

        return null;
    }
    public Position getBottomLeft(){
        return bottomLeft;
    }
    public Position getUpRight(){
        return upRight;
    }



}
