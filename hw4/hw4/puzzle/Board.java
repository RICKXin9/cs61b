package hw4.puzzle;
import java.lang.IndexOutOfBoundsException;

import edu.princeton.cs.algs4.Queue;
public class Board implements WorldState {

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    private final int[][] tiles;
    private int size;
    private static final int BLANK = 0;
    public Board(int[][] tiles) {
        size = tiles[0].length;
        this.tiles = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    public int tileAt(int i, int j) {
        if (i>size-1 || i<0 || j>size-1 || j<0) {
            throw new IndexOutOfBoundsException();
        }
        return tiles[i][j];
    }
    public int size() {
        return size;
    }
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }

    private int[] numberToXy(int num) {
        if (num < 0 || num > size*size-1) {
            System.out.println(num);
            throw new IllegalArgumentException();
        }
        num = num-1;
        int y = num % size;
        int x = num/size;
        int[] numToXy = new int[2];
        numToXy[0] = x;
        numToXy[1] = y;
        return numToXy;
    }

    public int hamming(){
        int count = 0;
        for (int i=0; i<size(); i++) {
            for (int j=0; j<size(); j++) {
                int number = tileAt(i,j);
                if (number == 0) {
                    continue;
                }
                int[] xy = numberToXy(number);
                if (i != xy[0] || j != xy[1]) {
                    count++;
                }
            }
        }
        return count;
    }
    public int manhattan(){
        int count = 0;
        for (int i=0; i<size(); i++) {
            for (int j=0; j<size(); j++) {
                int number = tileAt(i,j);
                if (number == 0) {
                    continue;
                }
                int[] xy = numberToXy(number);
                count += Math.abs(xy[0]-i) + Math.abs(xy[1] - j);
            }
        }
        return count;
    }
    public int estimatedDistanceToGoal(){
        return manhattan();
    }
    public boolean equals(Object y){
        if (!y.getClass().equals(this.getClass())) {
            throw new IllegalArgumentException();
        }
        Board other = (Board) y;
        for (int i=0; i<size(); i++) {
            for (int j=0; j<size(); j++) {
                if (this.tiles[i][j] != ((Board) y).tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;

    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
