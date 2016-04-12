package hw4.puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;

public class Solver {


    private class Move implements Comparable<Move> {
        private final Move previous;
        private final Board board;
        private final int numMoves;
        private final int priority;

        public Move(Board board) {
            this.numMoves = 0;
            this.previous = null;
            this.board = board;
            this.priority = 0;
        }

        public Move(Board initial, Move prev, int moves) {
            board = initial;
            previous = prev;
            numMoves = moves;
            priority = board.manhattan() + numMoves;
        }

        public int compareTo(Move move) {
            int e = (this.board.manhattan() - move.board.manhattan()) 
            + (this.numMoves - move.numMoves);
            return e;
        }

        public Board board() {
            return this.board;
        }

        public Move prev() {
            return this.previous;
        }
    }

    private int totalMoves;
    private Move search;


    public Solver(Board initial) {

        totalMoves = 0;
        MinPQ<Move> pq = new MinPQ<Move>();

        pq.insert(new Move(initial, null, totalMoves));


        while (true) {
            Move n = pq.delMin();

            if (n.board().isGoal()) {
                search = n;
                totalMoves = n.numMoves;
                break;
            }
            checkNeighbours(n, pq);
        }
    }

    private void checkNeighbours(Move n, MinPQ<Move> mp) {
        for (Board neighbor : BoardUtils.neighbors(n.board())) {
            if (n.prev() == null || !neighbor.equals(n.prev().board())) {
                mp.insert(new Move(neighbor, n, n.numMoves + 1));
            }
        }
    }

    public int moves() {
        return totalMoves;
    }

    public Iterable<Board> solution() {
        LinkedList<Board> sol = new LinkedList<Board>();
        Move a = search;

        while (a != null) {
            sol.addFirst(a.board);
            a = a.prev();
        }
        return sol;
    }


    // DO NOT MODIFY MAIN METHOD
    // Uncomment this method once your Solver and Board classes are ready.
    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution()) {
            StdOut.println(board);
        }
    }

}
