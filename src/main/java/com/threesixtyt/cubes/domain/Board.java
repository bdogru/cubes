package com.threesixtyt.cubes.domain;

import java.util.List;

/**
 * Puzzle board
 * 
 * @author Bekir Dogru
 *
 */
public class Board {
	/**
	 * puzzle board
	 */
	private Piece[][] board;

	/**
	 * Construct the board with proper size
	 * 
	 * @param pieceCount
	 *            number of pieces read from the input file
	 */
	public Board(int pieceCount) {
		if (pieceCount < 1) {
			throw new IllegalArgumentException("There has to be some pieces");
		}
		board = new Piece[pieceCount][pieceCount];
	}

	/**
	 * Copy constructor of the {@link Board} class to be used by the recursive
	 * method without ruining the previous board
	 * 
	 * @param b
	 */
	public Board(Board b) {
		int pieceCount = b.getBoardLength();
		board = new Piece[pieceCount][pieceCount];
		for (int i = 0; i < pieceCount; i++) {
			for (int j = 0; j < pieceCount; j++) {
				board[i][j] = b.getPiece(i, j);
			}
		}
	}

	/**
	 * Method to print board to the {@link System#out}
	 */
	public void printBoard() {
		for (Piece[] line : board) {
			for (int rowNr = 0; rowNr < 5; rowNr++) {
				for (Piece piece : line) {
					if (piece == null) {
						System.out.print("     ");
					} else {
						piece.printRow(rowNr);
					}
				}
				System.out.print(System.lineSeparator());
			}
		}
	}

	/**
	 * Returns {@link Piece} from the specified location
	 * 
	 * @param x
	 *            line
	 * @param y
	 *            column
	 * @return piece that located by the parameters, </br>
	 *         <code>null</code> if location is blank or specified location is
	 *         out of border
	 */
	public Piece getPiece(int x, int y) {
		if (board == null) {
			throw new IllegalAccessError("Board is not initialized!");
		}
		if (x < 0 || x >= getBoardLength() || y < 0 || y >= getBoardLength()) {
			return null;
		}
		return board[x][y];
	}

	public int getBoardLength() {
		return board.length;
	}

	/**
	 * Moves down pieces in the board one line
	 */
	private void shiftDown() {
		for (int i = getBoardLength() - 2; i >= 0; i--) {
			for (int j = 0; j < getBoardLength(); j++) {
				board[i + 1][j] = board[i][j];
			}
		}
		for (int i = 0; i < getBoardLength(); i++) {
			board[0][i] = null;
		}
	}

	/**
	 * Moves the pieces in the board to the right one column
	 */
	private void shiftRight() {
		for (int i = getBoardLength() - 2; i >= 0; i--) {
			for (int j = 0; j < getBoardLength(); j++) {
				board[j][i + 1] = board[j][i];
			}
		}
		for (int i = 0; i < getBoardLength(); i++) {
			board[i][0] = null;
		}
	}

	public void testInput(List<Piece> pieces) {
		for (int i = 0; i < pieces.size(); i++) {
			board[i][i] = pieces.get(i);
		}
	}

	/**
	 * Puts first {@link Piece} read from the input file to the top-left location of the
	 * board
	 * 
	 * @param p
	 *            first {@link Piece} that read from the input file
	 */
	public void putFirstPieces(Piece p) {
		if (board == null) {
			throw new IllegalAccessError("Board is not initialized!");
		}
		board[0][0] = p;
	}

	/**
	 * Puts the {@link Piece} to the specified location
	 * 
	 * @param p
	 *            {@link Piece} to put to the board
	 * @param i
	 *            location line
	 * @param j
	 *            location column
	 */
	public void putPiece(Piece p, int i, int j) {
		if (i < 0) {
			shiftDown();
			i++;
		}
		if (j < 0) {
			shiftRight();
			j++;
		}
		board[i][j] = new Piece(p);
	}
}
