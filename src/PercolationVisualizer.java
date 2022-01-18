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

    public static double[] ratio;
    public static boolean realTimeFlow;
    public static boolean[] isDrawn;

    public static Percolation p;

    public static void main(String[] args) throws Exception {
        setup();
        loop();
    }

    public static void setup() {
        StdOut.print("Sample size and trials: ");
        n = StdIn.readInt();
        t = StdIn.readInt();
        
        buffer = 0.01 / n;
        siteHalfLength = (1d / (double) (2*n)) - buffer;
        StdDraw.setFont(fontItalic);

        ratio = new double[t];
        realTimeFlow = true;
    }

    public static void loop() throws Exception {
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

    public static boolean paintSite(int row, int col, String type) throws Exception {
        type = type.toLowerCase();

        switch(type) {
            case "open": StdDraw.setPenColor(StdDraw.WHITE); break;
            case "fill": StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE); break;
            case "close": StdDraw.setPenColor(StdDraw.BLACK); break;
            default: throw new Exception("INVALID TYPE. VALID: [open, fill, close]");
        }

        StdDraw.filledRectangle((1d / (double) n) * col + (1d / (double) (2*n)) , (1d / (double) n) * (n-1 - row) + (1d / (double) (2*n)) , siteHalfLength, siteHalfLength);
        return true;
    }

    public static void flow(int row, int col) throws Exception {
        if (row < 0 || row >= n || col < 0 || col >= n) {
            return;
        }

        if (p.isFull(row, col)) {
            paintSite(row, col, "fill");
            isDrawn[row*n+col] = true;
            
            if (row > 0) {
                if (!isDrawn(row-1, col))
                    flow(row-1, col);
            }

            if (col > 0) {
                if (!isDrawn(row, col-1))
                    flow(row, col-1);
            }

            if (col < n-1) {
                if (!isDrawn(row, col+1))
                    flow(row, col+1);
            }


            if (row < n-1) {
                if (!isDrawn(row+1, col))
                    flow(row+1, col);
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
            ratio[k] = p.numberOfOpenSites() / (double) (n*n);
            StdOut.println("Sites open = " + (int) (ratio[k] * 100000) / 1000d + "%");

            // Display percolation results on canvas
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledRectangle(0.5, 1-0.1, 0.2, 0.075);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.rectangle(0.5, 1-0.1, 0.2, 0.075);
            
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.text(0.5, 1-0.1, "Sites open = " + (int) (ratio[k] * 10000) / 100d + "%");
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
        StdDraw.filledRectangle(0.5, 0.5, 0.3, 0.3);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.rectangle(0.5, 0.5, 0.3, 0.3);

        StdDraw.text(0.5, 1 - 0.3, "RESULTS");
        StdDraw.text(0.5, 1 - 0.4, "Mean sites open = " + Math.round(mean*10000)/100d + "%");
        StdDraw.text(0.5, 1 - 0.45, "For a " + n + " by " + n + " grid, that is " + (int) (n*n * (sum / t)) + " sites.");
        StdDraw.text(0.5, 1 - 0.5, "Standard Deviation = " + Math.round(stdDev*1000)/1000d);

        StdOut.println("Mean sites open    = " + sum / t * 100d + "%");
        StdOut.println("Standard Deviation = " + stdDev);
    }
}
