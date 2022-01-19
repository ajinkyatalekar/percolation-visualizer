public class Percolation {
    private final int n;
    private final Wqupc uf;
    private int numOpen;
    private boolean[] isOpen;

    private boolean percolates;
    public boolean[] printed;
    
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Invalid input in constructor");
        }

        this.n = n;
        this.numOpen = 0;
        this.isOpen = new boolean[this.n*this.n+1];

        //Imaginary Objs
        this.isOpen[this.n*this.n] = true;

        this.uf = new Wqupc(n*n+1);
        

        this.percolates = false;
        this.printed = new boolean[n*n];
    }

    // opens the site (row, col) if it is not open already
    public boolean open(int row, int col) {
        if (row < 0 || row >= n || col < 0 || col >= n) {
            throw new IllegalArgumentException("Invalid input, too big or too small");
        }

        if (!this.isOpen[this.n*(row) + (col)]) {

            this.isOpen[this.n*(row) + (col)] = true;

            // left
            if (col > 0) {
                if (isOpen(row, col-1)) {
                    uf.union(this.n*(row) + (col), this.n*(row) + (col-1));
                }
            }
            // right
            if (col < this.n-1) {
                if (isOpen(row, col+1)) {
                    uf.union(this.n*(row) + (col), this.n*(row) + (col+1));
                }
            }
            // top
            if (row > 0) {
                if (isOpen(row-1, col)) {
                    uf.union(this.n*(row) + (col), this.n*(row-1) + (col));
                }
            } else {
                // Connects to imaginary obj on top of the highest layer
                uf.union(this.n*(row) + (col), this.n * this.n);
            }
            // bottom
            if (row < this.n-1) {
                if (isOpen(row+1, col)) {
                    uf.union(this.n*(row) + (col), this.n*(row+1) + (col));
                }
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
        if (isOpen[n*(n-1)]) {
            if (isFull(n-1, 0)) {
                percolates = true;
                return;
            }
        }

        for (int i = 1; i < n; i++) {
            if (isOpen[n*(n-1)+i] && !isOpen[n*(n-1)+i -1]) {
                if (isFull(n-1, i)) {
                    percolates = true;
                    return;
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        row++; col++;
        if (row <= 0 || row > n || col <= 0 || col > n) {
            throw new IllegalArgumentException("Invalid input, too big or too small");
        }

        return (this.isOpen[this.n*(row-1) + (col-1)]);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        row++;
        col++;
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
