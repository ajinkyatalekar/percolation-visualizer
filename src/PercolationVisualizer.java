import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Font;
import java.util.concurrent.TimeUnit;

public class PercolationVisualizer {
    
    public static int n;
    public static int t;

    public static Font fontItalic = new Font("Sans Serif", Font.ITALIC, 18);
    public static Font fontBold = new Font("Sans Serif", Font.BOLD, 18);
    // Buffer -> Reduce square site size to show border
    public static double buffer;
    public static double siteHalfLength;
    public static boolean fillPink;

    public static double[] ratio;
    public static final boolean realTimeFlow = true;
    public static boolean[] isDrawn;

    public static Percolation p;
    public static boolean isInteractive;

    public static void main(String[] args) throws Exception {
        setup();
        if (isInteractive) {
            interactiveMode();
        } else {
            normalMode();
        }
    }

    public static void setup() throws Exception {
        StdOut.print("'z' for normal, 'x' for interactive trials: ");
        String s = StdIn.readLine();

        if (s.toLowerCase().equals("x")) {
            isInteractive = true;
        } else if (s.toLowerCase().equals("z")) {
            isInteractive = false;
        } else {
            throw new Exception("Invalid Input");
        }

        StdOut.print("Sample size: ");
        n = StdIn.readInt();
        
        buffer = 0.01 / n;
        siteHalfLength = (1d / (double) (2*n));
    }

    public static void normalMode() throws Exception {
        StdOut.print("trials: ");
        t = StdIn.readInt();

        ratio = new double[t];
        fillPink = false;
        StdDraw.setFont(fontItalic);

        for (int k = 0; k < t; k++) {
            StdDraw.clear(StdDraw.BLACK);

            p = new Percolation(n);
            isDrawn = new boolean[n*n];

            while (!p.percolates()) {
                int row = StdRandom.uniform(n);
                int col = StdRandom.uniform(n);

                if (p.open(row, col)) {
                    paintSite(row, col, "open");

                    // Draws all filled sites after every site is opened
                    if (realTimeFlow) {
                        if (p.isFull(row, col))
                            flow(row, col);
                    }
                }
            }

            // Draws all filled sites on percolation
            if (!realTimeFlow) {
                for (int i = 0; i < n*n; i++) {
                    int row = i / n;
                    int col = i % n;
                    if (p.isFull(row, col) && !isDrawn[i]) {
                        paintSite(row, col, "fill");
                    }
                }
            }

            quickStats(k);
            TimeUnit.MILLISECONDS.sleep(1500);
        }

        fullStats();
    }

    public static void interactiveMode() throws Exception {
        p = new Percolation(n);
        isDrawn = new boolean[n*n];
        int dif = (int) Math.sqrt(n) / 5;

        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.PINK);
        StdDraw.setPenRadius();
        StdDraw.setFont(fontItalic);

        buffer = 0;
        fillPink = true;

        // Grid
        if (n <= 50) {
            for (int i = 0; i < n; i++) {
                StdDraw.line(0, siteHalfLength*2*i, 1, siteHalfLength*2*i);
                StdDraw.line(siteHalfLength*2*i, 0, siteHalfLength*2*i, 1);
            }
        }

        boolean dataShown = false;
        while(true) {
            if (StdDraw.isMousePressed()) {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();

                int col = (int) (x * n);
                int row = n - (int) (y * n) - 1;

                if (col >= 0 && col < n && row >=0 && row < n) {
                    if (p.open(row, col)) {
                        paintSite(row, col, "open");
                    }

                    for (int i = 1; i < dif; i++) {
                        if (row+i < n) {
                            if (p.open(row+i, col)) {
                                paintSite(row+i, col, "open");
                            }
                        }

                        if (col+i < n) {
                            if (p.open(row, col+i)) {
                                paintSite(row, col+i, "open");
                            }
                        }

                        if (row-i >= 0) {
                            if (p.open(row-i, col)) {
                                paintSite(row-i, col, "open");
                            }
                        }

                        if (col-i >= 0) {
                            if (p.open(row, col-i)) {
                                paintSite(row, col-i, "open");
                            }
                        }
                    }

                    if (realTimeFlow)
                        flow(row, col);

                    if (p.percolates() && !dataShown) {
                        quickStats(0);
                        dataShown = true;
                    }
                }
            }
        }
    }

    public static boolean paintSite(int row, int col, String type) throws Exception {
        type = type.toLowerCase();

        switch(type) {
            case "open": StdDraw.setPenColor(StdDraw.WHITE); break;
            case "fill": StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE); break;
            case "fillalt": StdDraw.setPenColor(StdDraw.PINK); break;
            case "close": StdDraw.setPenColor(StdDraw.BLACK); break;
            default: throw new Exception("INVALID TYPE. VALID: [open, fill, fillalt, close]");
        }

        StdDraw.filledRectangle((1d / (double) n) * col + (1d / (double) (2*n)) , (1d / (double) n) * (n-1 - row) + (1d / (double) (2*n)) , siteHalfLength-buffer, siteHalfLength-buffer);
        return true;
    }

    public static void flow(int row, int col) throws Exception {
        if (row < 0 || row >= n || col < 0 || col >= n) {
            return;
        }

        String s;
        if (fillPink) {
            s = "fillalt";
        }
        else {
            s = "fill";
        }

        if (p.isFull(row, col)) {
            paintSite(row, col, s);
            isDrawn[row*n+col] = true;

            // Down
            if (row < n-1) {
                if (!isDrawn(row+1, col))
                    flow(row+1, col);
            }

            // Left
            if (col > 0) {
                if (!isDrawn(row, col-1))
                    flow(row, col-1);
            }

            // Right
            if (col < n-1) {
                if (!isDrawn(row, col+1))
                    flow(row, col+1);
            }
            
            // Up
            if (row > 0) {
                if (!isDrawn(row-1, col))
                    flow(row-1, col);
            }
        }
    }

    public static boolean isDrawn(int row, int col) throws Exception {
        if (row < 0 || row >= n || col < 0 || col >= n) {
            throw new Exception("INVALID INPUT");
        }
        return isDrawn[row*n+col];
    }

    public static void quickStats(int k) {
        double mean = p.numberOfOpenSites() / (double) (n*n);

        if (k >= 0 && k < t) {
            ratio[k] = mean;
        }
        

            StdOut.println("Sites open = " + (int) (mean * 100000) / 1000d + "%");

            // Display percolation results on canvas
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledRectangle(0.5, 1-0.1, 0.19, 0.05);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.rectangle(0.5, 1-0.1, 0.19, 0.05);
            
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.text(0.5, 1-0.1, "Sites open = " + (int) (mean * 10000) / 100d + "%");
    }

    public static void fullStats() {
        double sum = 0;
        for (double d : ratio) {
            sum += d;
        }
        double mean = sum / t;

        double stdDev = 0;
        for (double d : ratio) {
            stdDev += (d-mean)*(d-mean);
        }
        stdDev /= (t-1);
        stdDev = Math.sqrt(stdDev);

        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledRectangle(0.5, 0.5, 0.35, 0.3);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.rectangle(0.5, 0.5, 0.35, 0.3);

        StdDraw.text(0.5, 1 - 0.3, "RESULTS");
        StdDraw.text(0.5, 1 - 0.4, "Mean sites open = " + Math.round(mean*10000)/100d + "%");
        StdDraw.text(0.5, 1 - 0.45, "For a " + n + " by " + n + " grid, that is " + (int) (n*n * (sum / t)) + " sites.");
        StdDraw.text(0.5, 1 - 0.5, "Standard Deviation = " + Math.round(stdDev*1000)/1000d);

        StdOut.println("Mean sites open    = " + sum / t * 100d + "%");
        StdOut.println("Standard Deviation = " + stdDev);
    }
}
