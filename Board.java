package hw4.puzzle;

public class Board {

    private final int blank = 0;
    private int[][] tiles;
    private int sum = 0;

    public Board(int[][] tiles) {
        this.tiles = copy(tiles);
    }

    public int[][] copy(int[][] tiles1) {
        int[][] copy = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                copy[i][j] = tiles[i][j];
            }
        }
        return copy;
    }

    public int tileAt(int i, int j) {
        int N = tiles.length;
        if (i >= 0 && i <= N * N - 1 && j >= 0 && j <= N * N - 1) {
            return tiles[i][j];
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public int size() {
        return tiles.length;
    }

    public int hamming() {
        int count = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tileAt(i, j) == 0) {
                    continue;
                } else if (tilesNotInSpace(i, j)) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean tilesNotInSpace(int i, int j) {
        int tile = tileAt(i, j);

        return !isBlank(tile) && tile != goalTile(i, j);
    }

    private boolean isBlank(int tile) {
        return tile == blank;
    }

    private int goalTile(int i, int j) {
        return i * size() + j + 1;
    }


    public int manhattan() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                sum += calculateDistance(i, j);
            }
        }
        return sum;
    }

    private int calculateDistance(int i, int j) {
        int tile = tileAt(i, j);

        return (isBlank(tile)) ? 0 : Math.abs(i - row(tile)) + Math.abs(j - col(tile));
    }

    private int row(int tile) {
        return (tile - 1) / size();
    }

    private int col(int tile) {
        return (tile - 1) % size();
    }

    public boolean isGoal() {
        //  for(int i = 0; i < tiles.length; i++){
        //      for(int j = 0; j < tiles.length; j++){
        //          if(tilesNotInSpace(i, j)) return false;
        //      }
        // }
        // return true;
        return manhattan() == 0;
    }


    public boolean equals(Object y) {
        if (y == this) {
            return false;
        }
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        Board other = (Board) y;
        if (this.toString() != other.toString()) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return 0;
    }


    /**
     * Returns the string representation of the board.
     * Uncomment this method.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}

