import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    // private final WeightedQuickUnionUF uf;
    private final Wqupc uf;
    private int numOpen;
    private int[] stat;

    private boolean[] bottomColOpen;
    private boolean percolates;
    public boolean[] printed;
    
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Invalid input in constructor");
        }

        this.n = n;
        this.numOpen = 0;
        this.stat = new int[this.n*this.n+2];

        //Imaginary Objs
        this.stat[this.n*this.n] = 1;
        this.stat[this.n*this.n+1] = 1;

        // this.uf = new WeightedQuickUnionUF(n*n+2);
        this.uf = new Wqupc(n*n+2);
        

        this.bottomColOpen = new boolean[n];
        this.percolates = false;
        this.printed = new boolean[n*n];
    }

    // opens the site (row, col) if it is not open already
    public boolean open(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n) {
            throw new IllegalArgumentException("Invalid input, too big or too small");
        }

        if (this.stat[this.n*(row-1) + (col-1)] == 0) {

            this.stat[this.n*(row-1) + (col-1)] = 1;

            // left
            if (col > 1) {
                if (isOpen(row, col-1)) {
                    uf.union(this.n*(row-1) + (col-1), this.n*(row-1) + (col-2));
                }
            }
            // right
            if (col < this.n) {
                if (isOpen(row, col+1)) {
                    uf.union(this.n*(row-1) + (col-1), this.n*(row-1) + (col));
                }
            }
            // top
            if (row > 1) {
                if (isOpen(row-1, col)) {
                    uf.union(this.n*(row-1) + (col-1), this.n*(row-2) + (col-1));
                }
            } else {
                // Connects to imaginary obj on top of the highest layer
                uf.union(this.n*(row-1) + (col-1), this.n * this.n);
            }
            // bottom
            if (row < this.n) {
                if (isOpen(row+1, col)) {
                    uf.union(this.n*(row-1) + (col-1), this.n*(row) + (col-1));
                }
            } else {
                // Connects to imaginary obj at the bottom below the lowest layer
                // uf.union(this.n*(row-1) + (col-1), this.n*this.n+1);
                bottomColOpen[col-1] = true;
            }

            if (isFull(row, col) && !percolates) {
                percCheck();
            }

            this.numOpen++;
            return true;
        }

        return false;
    }

    private void percCheck() {
        for (int i = 0; i < n; i++) {
            if (bottomColOpen[i]) {
                if (isFull(n, i+1)) {
                    percolates = true;
                    return;
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n) {
            throw new IllegalArgumentException("Invalid input, too big or too small");
        }

        return (this.stat[this.n*(row-1) + (col-1)] == 1);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n) {
            throw new IllegalArgumentException("Invalid input, too big or too small");
        }

        return (uf.find(this.n*(row-1) + (col-1)) == uf.find(this.n*this.n));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.numOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        // return (uf.find(this.n * this.n) == uf.find(this.n * this.n + 1));
        return this.percolates;
    }
}
