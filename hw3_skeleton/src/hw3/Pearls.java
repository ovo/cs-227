package hw3;

import api.*;

import java.util.ArrayList;

/**
 * Basic game state and operations for a the puzzle game "Pearls", which
 * is a simplified version of "Quell".
 *
 * @author smkautz
 */
public class Pearls {
    /**
     * Two-dimensional array of Cell objects representing the
     * grid on which the game is played.
     */
    private Cell[][] grid;

    /**
     * Instance of PearlUtil to be used with this game.
     */
    private PearlUtil util;

    /**
     * Boolean representing if the player has won the game
     */
    private boolean gameWon;

    /**
     * Integer representing the number of moves
     */
    private int moves;

    /**
     * Integer representing score
     */
    private int score;


    /**
     * Constructs a game from the given string description.  The conventions
     * for representing cell states as characters can be found in
     * <code>StringUtil</code>.
     *
     * @param init      string array describing initial cell states
     * @param givenUtil PearlUtil instance to use in the <code>move</code> method
     */
    public Pearls(String[] init, PearlUtil givenUtil) {
        grid = StringUtil.createFromStringArray(init);
        util = givenUtil;
        moves = 0;
        score = 0;
    }

    /**
     * Returns the number of columns in the grid.
     *
     * @return width of the grid
     */
    public int getColumns() {
        return grid[0].length;
    }

    /**
     * Returns the number of rows in the grid.
     *
     * @return height of the grid
     */
    public int getRows() {
        return grid.length;
    }

    /**
     * Returns the cell at the given row and column.
     *
     * @param row row index for the cell
     * @param col column index for the cell
     * @return cell at given row and column
     */
    public Cell getCell(int row, int col) {
        return grid[row][col];
    }

    /**
     * Returns true if the game is over, false otherwise.  The game ends when all pearls
     * are removed from the grid or when the player lands on a cell with spikes.
     *
     * @return true if the game is over, false otherwise
     */
    public boolean isOver() {
        for (Cell[] cells: grid) {
            for (Cell cell: cells) {
                if (cell.getState() == State.PEARL)
                    return false;
                if (cell.isPlayerPresent() && State.isSpikes(cell.getState()))
                    return true;
            }
        }
        return true;
    }

    /**
     * Performs a move along a state sequence in the given direction, updating the score,
     * the move count, and all affected cells in the grid.  The method returns an
     * array of MoveRecord objects representing the states in original state sequence before
     * modification, with their <code>movedTo</code> and <code>disappeared</code>
     * status set to indicate the cell states' new locations after modification.
     *
     * @param dir direction of the move
     * @return array of MoveRecord objects describing modified cells
     */
    public MoveRecord[] move(Direction dir) {
        State[] sequence = getStateSequence(dir);
        MoveRecord[] record = new MoveRecord[sequence.length];
        moves += 1;
        for (int i = 0; i < sequence.length; i++) {
            record[i] = new MoveRecord(sequence[i], i);

            if (sequence[i] == State.PEARL)
                score += 1;
        }
        util.moveBlocks(sequence, record);
        int playerPos = util.movePlayer(sequence, record, dir);
        setStateSequence(sequence, dir, playerPos);

        return record;
    }

    /**
     * Returns true if the game has been won
     *
     * @return true if the game is won, false otherwise
     */
    public boolean won() {
        for (Cell[] cells: grid) {
            for (Cell cell: cells) {
                if (cell.getState() == State.PEARL)
                    return false;
                if (cell.isPlayerPresent() && State.isSpikes(cell.getState()))
                    return false;
            }
        }
        return isOver();
    }

    /**
     * Returns the current row of the player
     *
     * @return int the row of the player
     */
    public int getCurrentRow() {
        for (int i = 0; i < grid.length; i++) {
            for (int x = 0; x < grid[i].length; x++) {
                if (grid[i][x].isPlayerPresent())
                    return i;
            }
        }
        return 0;
    }

    /**
     * Returns the current column of the player
     *
     * @return int the column of the player
     */
    public int getCurrentColumn() {
        for (Cell[] cells : grid) {
            for (int x = 0; x < cells.length; x++) {
                if (cells[x].isPlayerPresent())
                    return x;
            }
        }
        return 0;
    }

    /**
     * Returns the next row of the player
     *
     * @param row
     *  the current row of the player
     * @param col
     *  the current column of the
     * @param dir
     *  the direction of the player
     * @param doPortalJump
     *  true if player should portal jump, false otherwise
     * @return
     *  int the next row of the player
     */
    public int getNextRow(int row, int col, Direction dir, boolean doPortalJump) {
        if (dir == Direction.UP) {
            if (doPortalJump) {
                int nextCell = grid[row][col].getRowOffset();
                return row + nextCell;
            }
            if (row == 0) {
                return getRows() - 1;
            }
            return row - 1;
        }
        if (dir == Direction.DOWN) {
            if (doPortalJump) {
                int nextCell = grid[row][col].getRowOffset();
                return row + nextCell;
            }
            if (row == getRows() - 1) {
                return 0;
            }
            return row + 1;
        }
        if (dir == Direction.LEFT) {
            if (doPortalJump) {
                int nextCell = grid[row][col].getRowOffset();
                return row + nextCell;
            }
            return row;
        }
        if (dir == Direction.RIGHT) {
            if (doPortalJump) {
                int nextCell = grid[row][col].getRowOffset();
                return row + nextCell;
            }
            return row;
        }
        return 0;
    }

    /**
     * Returns the next column of the player
     *
     * @param row
     *  the current row of the player
     * @param col
     *  the current column of the
     * @param dir
     *  the direction of the player
     * @param doPortalJump
     *  true if player should portal jump, false otherwise
     * @return
     *  int the next column of the player
     */
    public int getNextColumn(int row, int col, Direction dir, boolean doPortalJump) {
        if (dir == Direction.UP) {
            if (doPortalJump) {
                int nextCell = grid[row][col].getColumnOffset();
                return col + nextCell;
            }
            return col;
        }
        if (dir == Direction.DOWN) {
            if (doPortalJump) {
                int nextCell = grid[row][col].getColumnOffset();
                return col + nextCell;
            }
            return col;
        }
        if (dir == Direction.LEFT) {
            if (doPortalJump) {
                int nextCell = grid[row][col].getColumnOffset();
                return col + nextCell;
            }
            if (col == 0) {
                return getColumns() - 1;
            }
            return col - 1;
        }
        if (dir == Direction.RIGHT) {
            if (doPortalJump) {
                int nextCell = grid[row][col].getColumnOffset();
                return col + nextCell;
            }
            if (col == getColumns() - 1) {
                return 0;
            }
            return col + 1;
        }
        return col;
    }


    /**
     * Generates a state sequence for the player move
     * @param dir
     *  The direction of the player
     * @return
     *  State[] sequence of the player move
     */
    public State[] getStateSequence(Direction dir) {
        ArrayList<State> temp = new ArrayList<State>();
        int playerX = getCurrentRow();
        int playerY = getCurrentColumn();
        boolean portalClosed = true;
        boolean containsMovable = false;

        for (Cell[] cells : grid) {
            for (Cell cell : cells) {
                if (State.isMovable(cell.getState())) {
                    containsMovable = true;
                    break;
                }
            }
        }

        temp.add(grid[playerX][playerY].getState());

        while (!State.isBoundary(grid[playerX][playerY].getState(), false)) {
            boolean doJump = false;

            if (grid[playerX][playerY].getState() == State.PORTAL) {
                if (!portalClosed) {
                    portalClosed = true;
                } else {
                    doJump = true;
                    portalClosed = false;
                }
            }

            playerX = getNextRow(playerX, playerY, dir, doJump);
            playerY = getNextColumn(playerX, playerY, dir, doJump);
            temp.add(grid[playerX][playerY].getState());
        }

        return temp.toArray(new State[0]);
    }

    public void setStateSequence(State[] states, Direction dir, int playerIndex) {
        int playerX = getCurrentRow();
        int playerY = getCurrentColumn();
        int index = 0;
        boolean portalClosed = true;

        grid[playerX][playerY].setPlayerPresent(false);

        while (!State.isBoundary(grid[playerX][playerY].getState(), false)) {
            boolean doJump = false;

            if (grid[playerX][playerY].getState() == State.PORTAL) {
                if (!portalClosed) {
                    portalClosed = true;
                } else {
                    doJump = true;
                    portalClosed = false;
                }
            }

            if (index == playerIndex)
                grid[playerX][playerY].setPlayerPresent(true);
            grid[playerX][playerY].setState(states[index]);

            playerX = getNextRow(playerX, playerY, dir, doJump);
            playerY = getNextColumn(playerX, playerY, dir, doJump);
            index++;
        }

    }

    /**
     * Returns the score of the game
     *
     * @return
     *  int score of the game
     */
    public int getScore() {
        return score;
    }

    /**
     * Returns moves player has made
     *
     * @return
     *  int moves the player has made
     */
    public int getMoves() {
        return moves;
    }

    /**
     * Returns the total pearls left in the grid
     *
     * @return
     *  int the number of pearls
     */
    public int countPearls() {
        int pearls = 0;
        for (Cell[] cells : grid) {
            for (Cell cell : cells) {
                if (cell.getState() == State.PEARL)
                    pearls++;
            }
        }
        return pearls;
    }

}
