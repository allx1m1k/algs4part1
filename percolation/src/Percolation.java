/**
 *  The <tt>Percolation</tt> class represents a answer for 1 programming assignment.
 *  <p>
 *  This implementation uses <tt>WeightedQuickUnionUF</> data structure.
 *  Initializing a data structure with <em>quadratic</em> time.
 *  Afterwards, <em>union</em>, <em>find</em>, and <em>connected</em> take
 *  time linear time (in the worst case) and <em>count</em> takes constant
 *  time.
 *  <p>
 *  For specification of assignment, see <a href="http://coursera.cs.princeton.edu/algs4/assignments/percolation.html">Percolation: Instructions</a>
 *
 *  @author Dmytro Peltik
 *
  */
public class Percolation {
    private int rowLen; //number of rows
    private int topIndex; //top index
    private int bottomIndex; //bottom index
    private int gridSize; //number of sites
    private boolean[] grid; //matrix of sites
    private boolean percolates; //does grid percolates
    private WeightedQuickUnionUF uf; //union find model

    /**
     * Create N-by-N grid, with all sites blocked
     * @param N the number of sites
     */
    public Percolation(int N)
    {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException(
                    "N must be larger than 0"
            );
        }
        rowLen = N;
        gridSize = N*N;
        uf = new WeightedQuickUnionUF(gridSize + 2);
        grid = new boolean[gridSize];
        topIndex = gridSize;
        bottomIndex = gridSize + 1;
    }

    /**
     * Checks if row and column are valid
     * @param i row number
     * @param j column nubmer
     */
    private void checkInput(int i, int j) {
        if (1 > i || rowLen < i) {
            throw new java.lang.IndexOutOfBoundsException(
                    "i must be between 1 and "+ rowLen
            );
        }
        if (1 > j || rowLen < j) {
            throw new java.lang.IndexOutOfBoundsException(
                    "j must be between 1 and "+ rowLen
            );
        }
    }

    /**
     * Opens the particular site
     * @param iOne row index
     * @param jOne column index
     */
    public void open(int iOne, int jOne) {
        checkInput(iOne, jOne);

        // Change indexes to start at 1, not 0
        int i = iOne - 1;
        int j = jOne - 1;

        int index = getIndex(i, j);

        if (!grid[index]) {
            grid[index] = true;

            // If the spot we just opened has any open neighbors, connect them
            int n; // Neighbor's index
            boolean hasN = false;
            for (int d = 0; d < gridSize; d++) {
                n = getNeighborIndex(i, j, d);
                if (-1 != n && isOpen(n)) {
                    uf.union(index, n);
                    hasN = true;
                }
            }

            // If it is in the top row, connect it with the top node
            if (0 == i) {
                uf.union(index, topIndex);
            }
            if (hasN) {
                // check if this made any of the bottom nodes connected
                // to the top
                for (int b = gridSize-1; b >= gridSize-rowLen; b--) {
                    if (isOpen(b) && uf.connected(topIndex, b)) {
                        uf.union(b, bottomIndex);
                        break;
                    }
                }
            } else if (1 == gridSize) {
                uf.union(index, bottomIndex);
            }
        }
    }

    /**
     * Translate 2D row and column into int
     * @param i row number
     * @param j column number
     * @return index
     */
    private int getIndex(int i, int j) {
        return i*rowLen + j;
    }

    /*
     * Get the index of a neighbor in the specified direction.
     *
     * @param (int) i the index
     * @param (int) j the index
     * @param (int) d the direction of the neighbor:
     *                  0 = UP, 1 = RIGHT, 2 = DOWN, 3 = LEFT
     *
     * @return (int) the index of the neighbor or -1 if it is out of bounds
     *
     */
    private int getNeighborIndex(int i, int j, int d) {
        if (0 > d || gridSize < d) {
            throw new java.lang.IllegalArgumentException(
                    "Direction must be between 0 and gridSize"
            );
        }
        switch (d) {
            case 0:  // UP
                if (0 == i) {
                    return -1;
                }
                return getIndex(i-1, j);
            case 1:  // RIGHT
                if (j+1 == rowLen) {
                    return -1;
                }
                return getIndex(i, j+1);
            case 2:  // DOWN
                if (1+i == rowLen) {
                    return -1;
                }
                return getIndex(i+1, j);
            case 3:  // LEFT
                if (0 == j) {
                    return -1;
                }
                return getIndex(i, j-1);
            default:
        }
        return -1;
    }

    /**
     * Checks if the particular index is open (true), or not (false)
     * @param index site index
     * @return true is open site
     */
    private boolean isOpen(int index) {
        return grid[index];
    }

    /**
     * is site (row i, column j) open?
     * @param i row number
     * @param j column number
     * @return true is open site
     */
    public boolean isOpen(int i, int j) {
        checkInput(i, j);

        // Change indexes to start at 1, not 0
        return isOpen(getIndex(i-1, j-1));
    }

    /**
     * is site (row i, column j) full?
     * @param i row number
     * @param j column number
     * @return true is full site
     */
    public boolean isFull(int i, int j) {
        checkInput(i, j);

        // Change indexes to start at 1, not 0
        return uf.connected(topIndex, getIndex(i-1, j-1));
    }

    /**
     * does the system percolate?
     * @return true is grid percolates
     */
    public boolean percolates() {
        if (!percolates) {
            percolates = uf.connected(topIndex, bottomIndex);
        }
        return percolates;
    }

    /**
     * test client (optional)
     * @param args class arguments
     */
    public static void main(String[] args)
    {
        //System.out.println("Hello algs4part1");
        int N = StdIn.readInt();
        //Percolation pf = new Percolation(3);
        //QuickUnionUF uf = new QuickUnionUF(N);
        Percolation p = new Percolation(N);

        p.open(1, 1);
        p.open(2, 1);
        p.open(2, 2);
        p.open(3, 2);
        p.open(3, 3);
        p.open(4, 3);

        /*p.open(1,3);
        p.open(2,3);
        p.open(3,3);
        p.open(3,1);
        p.open(2,1);
        p.open(1,1);
        System.out.println(p.percolates);
        */
    }

}
