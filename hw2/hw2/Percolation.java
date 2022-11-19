package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.ArrayList;
import java.util.List;

public class Percolation {
    private int N;
    private WeightedQuickUnionUF UF;
    private int topNode;
    private int bottomNode;
    private boolean[][] open;
    private int open_nums;
    public Percolation(int N) {
        if (N<=0) {
            throw new IndexOutOfBoundsException("N need to be number > 0");
        }
        this.N = N;
        UF = new WeightedQuickUnionUF(N*N+2);
        topNode = N*N;
        bottomNode = N*N + 1;
        open = new boolean[N][N];
        for (boolean[] boo1 : open) {
            for (boolean boo2 : boo1) {
                boo2 = false;
            }
        }
        open_nums = 0;
    }
    public void open(int row, int col) {
        int index = getIndex(row,col);
        if (!isLegal(row, col)) {
            throw new IndexOutOfBoundsException("you need to reassign row and col");
        }
        open_nums += 1;
        if (row == 0 ) {
            if (!UF.connected(index,topNode)){
                UF.union(index,topNode);
            }
        }else if (row == N-1) {
            if (!UF.connected(index,bottomNode)){
                UF.union(index,bottomNode);
            }
        }
        open[row][col] = true;
        List<int[]> aroundList = getAround(row,col);
        for (int[] pos:aroundList) {
            int around_index = getIndex(pos[0], pos[1]);
            if (!isLegal(pos[0],pos[1])) {
                continue;
            }
            if (open[pos[0]][pos[1]] == true && !UF.connected(index,around_index)) {
                UF.union(index,around_index);
            }
        }
    }
    public boolean isOpen(int row, int col) {
        if (!isLegal(row, col)) {
            throw new IndexOutOfBoundsException("you should give a legal coordinate");
        }
        return open[row][col];
    }
    private int getIndex(int row, int col) {
        int index = row * N + col;
        return index;
    }
    public boolean isFull(int row, int col) {
        int index = getIndex(row, col);
        if (UF.connected(index,topNode) && UF.connected(index, bottomNode)) {
            return true;
        }
        return false;
    }
    public int numberOfOpenSites() {
        return open_nums;
    }
    public boolean percolates() {
        return UF.connected(topNode,bottomNode);
    }
    private List<int[]> getAround(int row, int col) {
        int[] down = new int[]{row-1,col};
        int[] up = {row+1, col};
        int[] right = {row, col+1};
        int[] left = {row,col-1};
        List<int[]> around = new ArrayList<>();

        around.add(down);
        around.add(up);
        around.add(right);
        around.add(left);
        return around;
    }

    private boolean isLegal(int row, int col) {
        if (row<0 || row>N-1 || col<0 || col>N-1) {
            return false;
        }
        return true;
    }
    public static void main(String args[]) {
        Percolation p = new Percolation(2);
        p.open(0,0);
        p.open(1,0);
        System.out.println(p.percolates());
        System.out.println(p.isFull(0,1));
    }
    public int getN() {
        return N;
    }
    public int getOpen_nums() {
        return open_nums;
    }

}
