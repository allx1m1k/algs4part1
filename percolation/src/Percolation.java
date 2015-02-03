/**
 * Created by dima on 1/25/2015.
 */
public class Percolation {
    private int[][] grid;
    private WeightedQuickUnionUF uf;

    private final int BLOCKED = -1; //site is Blocked
    private final int OPEN = 0; //site is Opened
    private final int FULL = 1; //site is Full
    private int count;   // number of components

    /**
     *
     * @param N
     */
    public Percolation(int N)               // create N-by-N grid, with all sites blocked
    {
        /* DONE */
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < N; j++) {
                grid[i][j] = BLOCKED;
            }
        }

    }

    /**
     *
     * @param i
     * @param j
     */
    public void open(int i, int j)          // open site (row i, column j) if it is not open already
    {
        /* TODO */

    }

    /**
     *
     * @param i
     * @param j
     * @return
     */
    public boolean isOpen(int i, int j)     // is site (row i, column j) open?
    {
        /* DONE */
        if (grid[i][j] == OPEN) {
            return true;
        } else return false;
    }

    /**
     *
     * @param i
     * @param j
     * @return <tt>true</tt> if the sites <tt>i</tt> and <tt>j</tt> is full, and <tt>false</tt> otherwise
     * @throws
     */
    public boolean isFull(int i, int j)     // is site (row i, column j) full?
    {
        /* DONE */
        if (grid[i][j] == FULL) {
            return true;
        } else return false;
    }

    public boolean percolates()             // does the system percolate?
    {
        /* TODO */
        return true;
    }



    public static void main(String[] args)   // test client (optional)
    {
        System.out.println("Hello algs4part1");
        int N = StdIn.readInt();
        Percolation pf = new Percolation(N);
        //QuickUnionUF uf = new QuickUnionUF(N);

    }

}
