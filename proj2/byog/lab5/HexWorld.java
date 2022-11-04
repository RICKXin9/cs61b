package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Random;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import javax.swing.text.Position;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    public static void addHexagon(TETile[][] world, Position p, int s, TETile t){
        int left = p.x;
        int bottom = p.y;
        TERenderer renderer = new TERenderer();
        for (int i = 0; i < 2*s; i++){
            int[] positionArr = getPosition(i, s, left, bottom);
            int xPos = positionArr[0];
            int yPos = positionArr[1];
            int turns = recurrNums(i,s);
            for (int j=0; j<turns; j++){
                world[xPos+j][yPos] = t;
            }
        }
        renderer.renderFrame(world);
    }
    private static void drawBackground(TETile[][] world){
        TERenderer renderer = new TERenderer();
        int height = world[0].length;
        int width = world.length;
        renderer.initialize(width,height);
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        renderer.renderFrame(world);
    }
//    i 为层数, 0-2s-1
    private static int[] getPosition(int i, int s, int x, int y){
        int[] arr = new int[2];
        if (i < s ){
            arr[0] = x-i;
        }
        else{
            arr[0] = x -(2*s - 1 - i);
        }
        arr[1] = y+i;
        return arr;
    }
    private static int recurrNums(int i , int s){
        if (i < s ){
            return 2*i+s;
        }
        else{
            return 2*(2*s - 1 - i)+s;
        }
    }

    public static class Position{
        private int x ;
        private int y ;
        public Position(int x ,int y){
            this.x = x;
            this.y = y;
        }
    }
    public static Position getTopRightNeighbor(Position old, int s){
        int left = old.x;
        int bottom = old.y;
        int[] extraPosition = getPosition(s,s,left,bottom);
        int xxPos = extraPosition[0];
        int yyPos = extraPosition[1];
        int newBottom = yyPos;
        int newLeft = xxPos+2*s+1;
        Position newPos = new Position(newLeft,newBottom);
        return newPos;
    }
    public static Position getBottomRightNeighbor(Position old ,int s){
        int left = old.x;
        int bottom = old.y;
        int newLeft = left + 2*s-1;
        int newBottom = bottom - s;
        Position newPos = new Position(newLeft,newBottom);
        return newPos;
    }
    public static void drawWorld(TETile[][] world, Position p, int s){
//        drawBackground(world);

            TETile tile = randomTile();
            addHexagon(world,p,s,tile);
            Position p1 = new Position(p.x,p.y-2*s);
            tile = randomTile();
            addHexagon(world,p1,s,tile);
            Position p2 = new Position(p.x,p.y-4*s);
            tile = randomTile();
            addHexagon(world,p2,s,tile);

            p = getTopRightNeighbor(p,s);
            tile = randomTile();
            addHexagon(world,p,s,tile);
            p1 = new Position(p.x,p.y-2*s);
            tile = randomTile();
            addHexagon(world,p1,s,tile);
            p2 = new Position(p.x,p.y-4*s);
            tile = randomTile();
            addHexagon(world,p2,s,tile);
            Position p3 = new Position(p.x,p.y-6*s);
            tile = randomTile();
            addHexagon(world,p3,s,tile);

            p = getTopRightNeighbor(p,s);
            tile = randomTile();
            addHexagon(world,p,s,tile);
            p1 = new Position(p.x,p.y-2*s);
            tile = randomTile();
            addHexagon(world,p1,s,tile);
            p2 = new Position(p.x,p.y-4*s);
            tile = randomTile();
            addHexagon(world,p2,s,tile);
            p3 = new Position(p.x,p.y-6*s);
            tile = randomTile();
            addHexagon(world,p3,s,tile);
            Position p4 = new Position(p.x,p.y-8*s);
            tile = randomTile();
            addHexagon(world,p4,s,tile);

            p = getBottomRightNeighbor(p,s);
            tile = randomTile();
            addHexagon(world,p,s,tile);
            p1 = new Position(p.x,p.y-2*s);
            tile = randomTile();
            addHexagon(world,p1,s,tile);
            p2 = new Position(p.x,p.y-4*s);
            tile = randomTile();
            addHexagon(world,p2,s,tile);
            p3 = new Position(p.x,p.y-6*s);
            tile = randomTile();
            addHexagon(world,p3,s,tile);

            p = getBottomRightNeighbor(p,s);
            tile = randomTile();
            addHexagon(world,p,s,tile);
            p1 = new Position(p.x,p.y-2*s);
            tile = randomTile();
            addHexagon(world,p1,s,tile);
            p2 = new Position(p.x,p.y-4*s);
            tile = randomTile();
            addHexagon(world,p2,s,tile);

    }
    private static TETile randomTile() {
        final long SEED = 2873123;
        final Random RANDOM = new Random(SEED);
        int tileNum = RANDOM.nextInt(4);
        switch (tileNum) {
            case 0: return Tileset.SAND;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.MOUNTAIN;
            default: return Tileset.GRASS;
        }
    }
    public static void main(String args[]){
        TETile[][] world = new TETile[50][50];
        Position p = new Position(4,30);

        int s =3;
        drawBackground(world);
        drawWorld(world,p,s);

    }
}
