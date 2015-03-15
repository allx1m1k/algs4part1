/**
 * dima
 */
public class Solver {
    private Stack<Board> path = new Stack<Board>();
    private Node next, twinNext;
    private boolean solvable;
    private int moves;
    
    private class Node implements Comparable<Node> {
        private Board board;
        private int moves;
        private Node father;
        
        public Node(Board board, int moves, Node father) {
            this.board = board;
            this.moves = moves;
            this.father = father;
        }
        
        public Board getBoard() {
            return board;
        }
       
        public int getMoves() {
            return moves;
        }
        
        public Node getFather() {
            return father;
        }
        
        @Override
        public int compareTo(Node that) {
            int thisMan = this.board.manhattan();
            int thatMan = that.board.manhattan();
            if      (thisMan + this.moves > thatMan + that.moves)    return 1;
            else if (thisMan + this.moves == thatMan + that.moves)   return 0;
            else                                                     return -1;
        }
    }

    /**
     *
     * @param initial
     */
    public Solver(Board initial) {
        // find a solution to the initial board (using the A* algorithm)
        
        MinPQ<Node> leaves = new MinPQ<Node>();
        MinPQ<Node> twinLeaves = new MinPQ<Node>();
        
        next = new Node(initial, 0, null);
        twinNext = new Node(initial.twin(), 0, null);
        
        while (!next.getBoard().isGoal() && !twinNext.getBoard().isGoal()) {
                        
            for (Board b : next.getBoard().neighbors()) {
                if (next.getFather() == null 
                        || !b.equals(next.getFather().getBoard())) {
                
                    leaves.insert(
                            new Node(b, next.getMoves() + 1, next));
                }
            }
            for (Board b : twinNext.getBoard().neighbors()) {
                
                if (twinNext.getFather() == null 
                        || !b.equals(twinNext.getFather().getBoard())) {
                    
                    twinLeaves.insert(
                            new Node(b, twinNext.getMoves() + 1, twinNext));
                }
            }
            
            next = leaves.delMin();
            twinNext = twinLeaves.delMin();
        }
        
        if (next.getBoard().isGoal()) {
            solvable = true;
            moves = next.getMoves();
            while (next != null) {
                path.push(next.getBoard());
                next = next.getFather();
            }
        }
    }
        
    public boolean isSolvable() {
        // is the initial board solvable?
        return solvable;
    }
    
    public int moves() {
        // min number of moves to solve initial board; -1 if no solution
        if (isSolvable())   return moves;
        else                return -1;  
    }
    
    public Iterable<Board> solution() {
        // sequence of boards in a shortest solution; null if no solution
        if (isSolvable())       return path;
        else                    return null;  
    }
    
    
    public static void main(String[] args) {
    }

}
