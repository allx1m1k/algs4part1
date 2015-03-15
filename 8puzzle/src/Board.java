/**
 * dima
 */
public class Board {
    private int[][] board;
    private int N;
    private int blankRow;
    private int blankCol;
    private int manhattan = 0;
    
    public Board(int[][] blocks) {
        this.N = blocks.length;
        this.board = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int value = blocks[i][j];
                board[i][j] = value;
                if (value == 0) {
                    blankRow = i;
                    blankCol = j;
                }
                else {
                    int x = (value - 1) / N;
                    int y = (value - 1) % N;
                    manhattan += Math.abs(i - x) + Math.abs(j - y);
                }
            }
        }
    }
    
    public int dimension() {
        return N;
    }
        
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int value = board[i][j];
                if (value != 0 && value != i * N + j + 1)   hamming++;
            }
        }
        return hamming;
    }
    
    public int manhattan() {
        return manhattan;
    }
    
    public boolean isGoal() {
        return hamming() == 0;
    }
    
    /* Notice don't swap the blank block */    
    public Board twin() {
        Board twin;
        if (board[0][0] != 0 && board[0][1] != 0) {
            exch(board, 0, 0, 0, 1);
            twin = new Board(board);
            exch(board, 0, 0, 0, 1);
        }
        else {
            exch(board, 1, 0, 1, 1);
            twin = new Board(board);
            exch(board, 1, 0, 1, 1);
        }
        return twin;
    }
    
    public boolean equals(Object y) {
        if (this == y)  return true;
        if (y == null)  return false;
        
        if (this.getClass() != y.getClass())            return false;
        
        /* Forget the checking of dimension is a BUG! */
        Board that = (Board) y;
        if (that.dimension() != this.dimension())       return false;
        
        for (int i = 0; i < N; i++) 
            for (int j = 0; j < N; j++)
                if (board[i][j] != that.board[i][j])    return false; 
        
        return true;
    }
    

    private void addNeighbor(int row, int col, Stack<Board> neighbors) {       
        exch(board, row, col, blankRow, blankCol);
        Board nbr = new Board(board);
        exch(board, row, col, blankRow, blankCol);
        neighbors.push(nbr);
    }
    
    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<Board>();
        
        if (blankRow - 1 > -1)
            addNeighbor(blankRow - 1, blankCol, neighbors);
        
        if (blankRow + 1 < N) 
            addNeighbor(blankRow + 1, blankCol, neighbors);
        
        if (blankCol - 1 > -1)
            addNeighbor(blankRow, blankCol - 1, neighbors);
        
        if (blankCol + 1 < N)
            addNeighbor(blankRow, blankCol + 1, neighbors);
        
        return neighbors;
    }
    
    private void exch(int[][] a, int x1, int y1, int x2, int y2) {
        int temp = a[x1][y1];
        a[x1][y1] = a[x2][y2];
        a[x2][y2] = temp;
    }
    
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    
    
    public static void main(String[] args) {

        /*for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int N = in.readInt();
            int[][] tiles = new int[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            // solve the slider puzzle
            Board initial = new Board(tiles);
            System.out.println(initial.toString());
//            System.out.println(initial.manhattan());
            System.out.println(initial.hamming());
//            System.out.print(initial.twin().toString());

            // test neighbors
            for (Board neiber : initial.neighbors()) {
                System.out.println("II");
                System.out.println(neiber.toString());

            }*/
    }


}
