import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Font;
import java.util.concurrent.TimeUnit;

public class PercolationVisual {
    
    public static void main(String[] args) throws InterruptedException {

        StdOut.print("Sample size & trials: ");
        int n = StdIn.readInt();
        int t = StdIn.readInt();
        
        Font tr = new Font("Trebuchet MSTre", Font.PLAIN, 18);
        double buf = 0.01 * 1 / n;
        double[] ratio = new double[t];

        for (int k = 0; k < t; k++) {

            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.filledRectangle(0.5, 0.5, 0.5, 0.5);

            Percolation p = new Percolation(n);

            while (!p.percolates()) {
                int rowR = StdRandom.uniform(n);
                int colR = StdRandom.uniform(n);

                if (p.open(rowR+1, colR+1)) {
                    int nx = n - rowR - 1;
                    int ny = colR;

                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.filledRectangle((1d / (double) n) *ny + (1d / (double) (2*n)) , (1d / (double) n) *nx + (1d / (double) (2*n)) , (1d / (double) (2*n)) - buf, (1d / (double) (2*n)) - buf);

                    // Draws flow in real time; very slow
                    // /*
                    StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
                    for (int i = 0; i < n*n; i++) {
                        if (p.isFull(i/n+1, i%n+1) && !p.printed[i]) {
                            StdDraw.filledRectangle((1d / (double) n) * (i%n) + (1d / (double) (2*n)) , (1d / (double) n) * (n-1-i/n) + (1d / (double) (2*n)) , (1d / (double) (2*n)) - buf, (1d / (double) (2*n)) - buf);
                        }
                    }
                    // */
                }
            }

            StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
            for (int i = 0; i < n*n; i++) {
                int row = i / n;
                int col = i % n;
                if (p.isFull(row+1, col+1)) {
                    col = n - 1 - i/n;
                    row = i % n;
                    StdDraw.filledRectangle((1d / (double) n) *row + (1d / (double) (2*n)) , (1d / (double) n) *col + (1d / (double) (2*n)) , (1d / (double) (2*n)) -buf, (1d / (double) (2*n)) -buf);
                }
            }

            StdOut.println("Percent of sites open = " + p.numberOfOpenSites() / (double) (n*n) * 100 + "%");

            // Display percolation results on canvas
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledRectangle(0.5, 1-0.1, 0.2, 0.075);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.rectangle(0.5, 1-0.1, 0.2, 0.075);
            
            StdDraw.setFont(tr);
            StdDraw.setPenColor(StdDraw.BLACK);

            ratio[k] = p.numberOfOpenSites() / (double) (n*n);
            StdDraw.text(0.5, 1-0.1, "Sites open = " + (int) (ratio[k] * 10000) / 100d + "%");

            TimeUnit.MILLISECONDS.sleep(1500);
        }

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

        StdOut.println("Mean sites open    = " + sum / t * 100d + "%");
        StdOut.println("Standard Deviation = " + stdDev);

        StdDraw.setPenColor(StdDraw.PINK);
        StdDraw.filledRectangle(0.5, 0.5, 0.3, 0.4);

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.rectangle(0.5, 0.5, 0.3, 0.4);

        StdDraw.text(0.5, 1 - 0.2, "RESULTS");
        StdDraw.text(0.5, 1 - 0.3, "Mean sites open = " + Math.round(mean*100)/100d + "%");
        StdDraw.text(0.5, 1 - 0.35, "For a " + n + " by " + n + " grid, that is " + (int) (n*n * (sum / t)) + " sites.");
        StdDraw.text(0.5, 1 - 0.4, "Standard Deviation = " + Math.round(stdDev*1000)/1000d);
    }
}
