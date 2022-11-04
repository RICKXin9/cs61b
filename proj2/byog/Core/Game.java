package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;
import java.awt.Font;
import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.*;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 50;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        drawStartUI();
        char c = getStartChar();
        switch (c) {
            case 'n':
                newGame();
            case 'l':
                loadGame();
            case 'q':
                System.exit(0);
        }

    }

    private char getStartChar() {
        char startChar;
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                startChar = Character.toLowerCase(StdDraw.nextKeyTyped());
                if (startChar=='n'||startChar=='l'||startChar=='q') {
                    break;
                } else {
                    continue;
                }
            } else {
                continue;
            }
        }
        return startChar;
    }
    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        input = toLower(input);
        TETile[][] finalWorldFrame = null;
        char firstChar = input.charAt(0);
        if (firstChar == 'n') {
//            newGame 用来新建游戏
            finalWorldFrame = newGame(input);
        } else if (firstChar == 'l') {
//            loadGame用来导入游戏
            finalWorldFrame = loadGame(input);
        } else if (firstChar == 'q') {
            System.exit(0);
        }
        return finalWorldFrame;
    }
    /*以下是play with input String方法*/
    private TETile[][] newGame(String input) {
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        ter.initialize(WIDTH, HEIGHT);
        int indexS = input.indexOf('s');
        long seed = convertSeed(input.substring(1, indexS));
        finalWorldFrame = generateWorld(finalWorldFrame,seed);

//        System.out.println("开始位置"+Player.getPos().getX()+"  "+Player.getPos().getY());

//        ter.renderFrame(finalWorldFrame);
        drawText(finalWorldFrame);
//        play(finalWorldFrame, input.substring(indexS + 1));
        // 判断input字符串s后面是否还有wsad操作
//        System.out.println("indeS的位置为" + indexS);
        playerMove(finalWorldFrame,input,indexS);
        if(isSaveGame(input,indexS)) {
            saveGame(finalWorldFrame);
        }

//        System.out.println("结束位置"+Player.getPos().getX()+"  "+Player.getPos().getY());


        return finalWorldFrame;
    }

    private TETile[][] loadGame(String input) {
        TETile[][] finalWorldFrame;
        finalWorldFrame = getSavedGame();
//        play(finalWorldFrame, input.substring(1));
        int indexS = 1;
        playerMove(finalWorldFrame,input,indexS);
        if (isSaveGame(input,indexS)) {
            saveGame(finalWorldFrame);
        }
        return finalWorldFrame;
    }
    /*play with input String方法结束*/
    /*以下是play with keyboard方法*/
    private void newGame() {
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        ter.initialize(WIDTH, HEIGHT);
        String seedString = getSeed();
        if (seedString.equals("")) {
            System.exit(0);
        }
        long seed = convertSeed(seedString);
        finalWorldFrame = generateWorld(finalWorldFrame,seed);
        StdDraw.clear();
        ter.renderFrame(finalWorldFrame);
        play(finalWorldFrame);

    }
    private void loadGame() {
        TETile[][] finalWorldFrame;
        finalWorldFrame = getSavedGame();
//        play(finalWorldFrame, input.substring(1));
        ter.renderFrame(finalWorldFrame);
        play(finalWorldFrame);
    }
    private void play(TETile[][] finalWorldFrame) {
        while (true) {
            if (StdDraw.hasNextKeyTyped()){
                char direction = StdDraw.nextKeyTyped();
                switch (direction) {
                    case 'w':
                        Player.walkUp(finalWorldFrame);
                        ter.renderFrame(finalWorldFrame);
                        break;
                    case 's':
                        Player.walkDown(finalWorldFrame);
                        ter.renderFrame(finalWorldFrame);
                        break;
                    case 'a':
                        Player.walkLeft(finalWorldFrame);
                        ter.renderFrame(finalWorldFrame);
                        break;
                    case 'd':
                        Player.walkRight(finalWorldFrame);
                        ter.renderFrame(finalWorldFrame);
                        break;
                    case ':':
                        while (true) {
                            if (!StdDraw.hasNextKeyTyped()) {
                                continue;
                            }
                            if (Character.toLowerCase(StdDraw.nextKeyTyped()) == 'q') {
                                saveGame(finalWorldFrame);
                                System.exit(0);
                            } else {
                                break;
                            }
                        }
                        break;
                    default:
                }
            }
            drawText(finalWorldFrame);
        }
    }
    private void drawStartUI() {
        initializeCanvas();

        Font font = new Font("Monaco", Font.PLAIN, 60);
        StdDraw.setFont(font);
        StdDraw.text(WIDTH / 2, 3 * HEIGHT / 4, "CS61B: THE GAME");

        Font smallFont = new Font("Monaco", Font.PLAIN, 30);
        StdDraw.setFont(smallFont);
        StdDraw.text(WIDTH / 2, HEIGHT / 4 + 2, "New Game (N)");
        StdDraw.text(WIDTH / 2, HEIGHT / 4, "Load Game (L)");
        StdDraw.text(WIDTH / 2, HEIGHT / 4 - 2, "Quit (Q)");
        StdDraw.show();
    }

    private void initializeCanvas() {
        StdDraw.setCanvasSize(WIDTH * 16, HEIGHT * 16);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(Color.BLACK);
//        StdDraw.enableDoubleBuffering();
        StdDraw.setPenColor(Color.WHITE);
    }
    private String getSeed() {
        StdDraw.enableDoubleBuffering();
        String seedString = "";
        drawInputSeed();
//        StdDraw.pause(100);
        while(true) {

            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char digit;
            digit = StdDraw.nextKeyTyped();
            if (Character.toLowerCase(digit)!= 's') {
                if (!Character.isDigit(digit)) {
                    continue;
                }
                seedString += digit;
                StdDraw.clear();
                drawInputSeed();
                StdDraw.text(WIDTH/2,HEIGHT/2,seedString);
                StdDraw.show();

            }else{
                break;
            }
        }
        return seedString;
    }
    private void drawInputSeed() {
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(new Font("Monaco",Font.BOLD,30));
        StdDraw.text(WIDTH/2, HEIGHT*4/5, "Please set a random seed");
        StdDraw.show();
    }
    private void saveGame(TETile[][] finalWorldFrame){
        try{
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("savefile.txt"));
            out.writeObject(finalWorldFrame);
            out.writeObject(Player.getPos());
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    private TETile[][] getSavedGame() {
        TETile[][] finalWorldFrame = null;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("savefile.txt"));
            finalWorldFrame = (TETile[][]) in.readObject();
            Player.setPos((Position) in.readObject());
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return finalWorldFrame;
    }
    private long convertSeed(String seedString) {
        return Long.valueOf(seedString.toString());
    }
    private String toLower(String input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (Character.isUpperCase(ch)) {
                sb.append(Character.toLowerCase(ch));
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }
    private boolean isSaveGame(String input, int indexS) {
        //找到冒号索引
        for (int i = indexS+1; i< input.length(); i = i + 1) {
            if(input.charAt(i)==':') {
                // 防止数组越界
                if (i==input.length()-1) {
                    break;
                }
                if(input.charAt(i+1)=='q') {
                    return true;
                }
            }
        }
        return false;
    }
    // indexS 为wsad移动键的起始索引,input输入为给定的所有字符串
    private void playerMove(TETile[][] finalWorldFrame, String input, int indexS) {
        if (input.length()==indexS+1) {
            "".isEmpty();//do nothing
        }else {
//            System.out.println("playmove函数被执行");
            for (int i = indexS+1; i< input.length(); i = i + 1) {
                char direction = input.charAt(i);
                switch (direction) {
                    case 'w':
                        Player.walkUp(finalWorldFrame);
                        break;
                    case 's':
                        Player.walkDown(finalWorldFrame);
                        break;
                    case 'a':
                        Player.walkLeft(finalWorldFrame);
                        break;
                    case 'd':
                        Player.walkRight(finalWorldFrame);
                        break;

                    default:
                }
            }
        }
    }
    private TETile[][] generateWorld(TETile[][] world,Long SEED){
        Random RANDOM = new Random(SEED);
//        TERenderer ter = new TERenderer();
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        //生成一个随机的房间
        int b_x = RandomUtils.uniform(RANDOM,10,WIDTH/2);
        int b_y = RandomUtils.uniform(RANDOM,10,HEIGHT/2);
        int room_wid = RandomUtils.uniform(RANDOM,3,Room.getRoomMaxWidth());
        int room_hi = RandomUtils.uniform(RANDOM,3,Room.getRoomMinHeight());
        int up_X = b_x+room_wid;
        int up_Y = b_y+room_hi;
        Position p1 = new Position(b_x,b_y);
        Position p2 = new Position(up_X,up_Y);
        Room initRoom = new Room(p1,p2);
        initRoom.addRoom(world);
        //得到该房间的随机连接点
        Connention connention = initRoom.getRandCons(RANDOM);

        List<Connention> con = connention.generateConn(world,RANDOM);
        List<Connention> longLists = new ArrayList<>();
        longLists.addAll(con);
        List<Position> cornerList = new ArrayList<>();
        Position[] init_corner = initRoom.getCornerPosition();
//        System.out.println("初始房间"+init_corner.length);
        for (Position p:init_corner) {
            cornerList.add(p);
        }
        int initTimes = longLists.size();
        int roomSize=1;
        int cornerSize=cornerList.size();

        while (longLists.size()!=0 && initTimes<=70) {
            Connention c = longLists.remove(0);
            roomSize++;
            List<Connention> new_con = c.generateConn(world,RANDOM);
            Position[] corner = c.getRoom().getCornerPosition();
            for (Position p:corner) {
                cornerList.add(p);
                cornerSize++;
            }
            longLists.addAll(new_con);
            initTimes = initTimes + new_con.size();
        }
        /*当initTimes的次数过多时，longLists里面仍然存有connection，此时也需要将里面所有room的corner去除掉*/
        if (longLists.size()!=0){
            for (Connention co:longLists) {
                Position[] ps = co.getRoom().getCornerPosition();
                for (Position p:ps ) {
                    cornerList.add(p);
                }
            }
        }
        //得到该区域所有wall位置和room位置
        Map<String,List> roomWalls = getWallRoomPos(world);
        List<Position> rooms = roomWalls.get("Room");
        List<Position> walls = roomWalls.get("Wall");
//        System.out.println("原先walllist长度"+walls.size());
//        System.out.println("增加的room的数量"+roomSize);
//        System.out.println("增加的corner位置的数量"+cornerSize);
        // 去除所有的corner的位置
//        Set wallSet = new HashSet(walls);
//        List<Position> result = new ArrayList<>(wallSet);
//        assert result.size() == walls.size();
        for (Position cor:cornerList) {
            for (int i=0; i<walls.size(); i++) {
                if (cor.equals(walls.get(i))) {
                    walls.remove(i);
                    break;
                }
            }
        }
        int roomNums = rooms.size();
        int wallNums = walls.size();
//        System.out.println("现在walllist长度"+wallNums);
        int roomNumber = RandomUtils.uniform(RANDOM,0,roomNums);
        int wallNumber = RandomUtils.uniform(RANDOM,0,wallNums);
        Position door = walls.get(wallNumber);
        Position gamer = rooms.get(roomNumber);
        for (Position cor:cornerList) {
            if (cor.equals(door)) {
                System.out.println("生成的door属于角落，不可到达");
            }
        }
        world[door.getX()][door.getY()] = Tileset.MY_DOOR;
        world[gamer.getX()][gamer.getY()] = Tileset.PLAYER;
        Player.setPos(new Position(gamer.getX(), gamer.getY()));
        return world;
    }
    private static Map<String,List> getWallRoomPos(TETile[][] world) {
        List<Position> roomList = new ArrayList<>();
        List<Position> wallList = new ArrayList<>();
        Map<String,List> wallRoom = new HashMap<>();
        int height = world[0].length;
        int width = world.length;
        Position p;
        for (int i = 0; i<width; i++) {
            for (int j = 0; j < height; j++) {
                if (world[i][j].equals(Tileset.WALL)) {
                    p = new Position(i,j);
                    wallList.add(p);
                }
                else if (world[i][j].equals(Tileset.FLOOR)) {
                    p = new Position(i,j);
                    roomList.add(p);
                }
            }
        }
        wallRoom.put("Room",roomList);
        wallRoom.put("Wall",wallList);
        return wallRoom;
    }
    private void drawText(TETile[][] world) {

        if(StdDraw.isMousePressed()) {
            double posx = StdDraw.mouseX();
            double posy = StdDraw.mouseY();
            String s = world[(int) posx][(int) posy].description();
            StdDraw.clear();
            ter.renderFrame(world);

            StdDraw.setFont(new Font("Monaco", Font.BOLD, 20));
            StdDraw.setPenColor(Color.white);
            StdDraw.text(4.0,Double.valueOf(HEIGHT-2),s);
            StdDraw.show();

        }
    }

    public static void main(String[] args) {
//        Game myGame = new Game();
//        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
//        myGame.ter.initialize(WIDTH, HEIGHT);
//        finalWorldFrame = myGame.generateWorld(finalWorldFrame,27398178947198247l);
        Game myGame = new Game();
        myGame.playWithKeyboard();

    }
}
