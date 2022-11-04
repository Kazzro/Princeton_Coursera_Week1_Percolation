import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final boolean[][] grid;
    private final int size;
    private final int top;
    private final int bottom;
    private final WeightedQuickUnionUF wquf;
    private final WeightedQuickUnionUF backwash;
    private int counter;

    public Percolation(int n) {

        if (n <= 0) {
            throw new IllegalArgumentException("n has to be positive.");
        }

        this.size = n;
        this.grid = new boolean[n][n];
        this.bottom = n * n + 1;

        this.top = n * n;
        this.wquf = new WeightedQuickUnionUF(top + 2);
        this.backwash = new WeightedQuickUnionUF(top + 1);
        this.counter = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
            }
        }
        for (int i = 0; i < size; i++) {
            wquf.union(top, i);
            backwash.union(top, i);
            wquf.union(bottom, size * (size - 1) + i);
        }

    }

    public void open(int row, int col) {
        row = row - 1;
        col = col - 1;

        errorHandling(row, col, "open()");

        int gridSlot = row * size + col;

        if (!isOpen(row + 1, col + 1)) {
            grid[row][col] = true;
            counter++;
        } else return;

        if (row > 0 && grid[row - 1][col]) { // up 1
            wquf.union(gridSlot, gridSlot - size);
            backwash.union(gridSlot, gridSlot - size);
        }
        if (col > 0 && grid[row][col - 1]) { // left 1
            wquf.union(gridSlot, gridSlot - 1);
            backwash.union(gridSlot, gridSlot - 1);
        }
        if (row < (size - 1) && grid[row + 1][col]) { // down 1
            wquf.union(gridSlot, gridSlot + size);
            backwash.union(gridSlot, gridSlot + size);
        }
        if (col < (size - 1) && grid[row][col + 1]) { // right 1
            wquf.union(gridSlot, gridSlot + 1);
            backwash.union(gridSlot, gridSlot + 1);
        }
    }

    public boolean isOpen(int row, int col) {
        row = row - 1;
        col = col - 1;

        errorHandling(row, col, "isOpen()");

        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        row = row - 1;
        col = col - 1;

        errorHandling(row, col, "isFull()");
        if (grid[row][col]) {
            return backwash.find(row * size + col) == backwash.find(top);
        }
        return false;
    }

    public int numberOfOpenSites() {
        return counter;
    }

    public boolean percolates() {
        if (size > 1) {
            return wquf.find(top) == wquf.find(bottom);
        } else return grid[0][0];
    }


    private void errorHandling(int row, int col, String task) {
        if (row >= size) {
            throw new IllegalArgumentException("Rowinput:   " + row + "  is greater than:   " + size + " due to gridsize:    " + grid.length + " in task " + task);
        }
        if (col >= size) {
            throw new IllegalArgumentException("Colinput:   " + col + "  is greater than:   " + size + " due to gridsize:     " + grid.length + "     in task      " + task);
        }
        if (row < 0) {
            throw new IllegalArgumentException("Rowinput:   " + row + "  is lower than:     1   in gridsize:    " + grid.length + "     in task      " + task);
        }
        if (col < 0) {
            throw new IllegalArgumentException("Colinput:   " + col + "  is lower than:     1   in gridsize:    " + grid.length + "     in task      " + task);
        }
    }
}
