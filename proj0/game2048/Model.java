package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author jinyu
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /**
     * Return whether is a valid move from cur_t to target_t
     */
    public boolean isValidMove(Tile cur_t, Tile target_t, boolean isTargetMerged) {
        if (target_t == null) {
            return true;
        } else if (cur_t.value() == target_t.value() && !isTargetMerged) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Return the maximal valid row current tile could move to in the same column
     */
    public int whichRowTileGo(int col, int row, boolean[] merged) {
        int far_valid_row = row;
        for (int target_row = row; target_row < this.size(); target_row += 1) {
            if (isValidMove(this.tile(col, row), this.tile(col, target_row), merged[target_row])) {
                far_valid_row = Math.max(far_valid_row, target_row);
            }
        }
        return far_valid_row;
    }

    /**
     * Tilt one column of the board and increase the score if merged
     */
    public boolean tileOneColumn(int col) {
        boolean[] merged = new boolean[this.size()];
        boolean changed = false;
        // order from front to back
        for (int row = this.size() - 1; row >= 0; row -= 1) {
            // empty tile doesn't need move
            if (this.tile(col, row) != null) {
                int new_row = whichRowTileGo(col, row, merged);
                changed = changed || new_row != row;
                // if merged
                if (this.board.move(col, new_row, this.tile(col, row))) {
                    merged[new_row] = true;
                    this.score += this.tile(col, new_row).value();
                }
            }
        }
        return changed;
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed;
        changed = false;

        this.board.setViewingPerspective(side);
        for (int col = 0; col < this.size(); col += 1) {
            if (tileOneColumn(col)) {
                changed = true;
            }
        }
        this.board.setViewingPerspective(Side.NORTH);
        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.

        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        // TODO: Fill in this function.
        int board_size = b.size();
        for (int row = 0; row < board_size; row += 1) {
            for (int col = 0; col < board_size; col += 1) {
                if (b.tile(col, row) == null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        int board_size = b.size();
        for (int col = 0; col < board_size; col += 1) {
            for (int row = 0; row < board_size; row += 1) {
                Tile t = b.tile(col, row);
                if (t != null && t.value() == MAX_PIECE) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isValidTilePosition(Board b, int col, int row) {
        if (col < 0 || row < 0) {
            return false;
        } else if (col >= b.size() || row >= b.size()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Return true if there are any same adjacent tiles for the tile of the given position.
     */
    public static boolean atLeastOneSameAdjacent(Board b, int col, int row) {
        if (b.tile(col, row) == null) {
            return false;
        }
        // four directions: left, down, up, right
        int[] dx = new int[]{-1, 0, 1, 0};
        int[] dy = new int[]{0, -1, 0, 1};
        int this_tile_value = b.tile(col, row).value();
        for (int i = 0; i < dx.length; i++) {
            int new_col = col + dx[i], new_row = row + dy[i];
            if (isValidTilePosition(b, new_col, new_row)) {
                Tile new_tile = b.tile(new_col, new_row);
                if (new_tile != null && new_tile.value() == this_tile_value) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Return true if there are any same adjacent tile pairs in b.
     * Null tiles are not same as any types of tile even itself
     */
    public static boolean atLeastOnePairTilesSame(Board b) {
        int board_size = b.size();
        for (int col = 0; col < board_size; col += 1) {
            for (int row = 0; row < board_size; row += 1) {
                if (atLeastOneSameAdjacent(b, col, row)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        // TODO: Fill in this function.
        // Check if b has empty spaces
        if (emptySpaceExists(b)) {
            return true;
        } else {
            return atLeastOnePairTilesSame(b);
        }
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
