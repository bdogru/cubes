package com.threesixty.cubes.domain;

public class Board {
	private Piece[][] board;

	public Board(int pieceCount) {
		if (pieceCount < 1) {
			throw new IllegalArgumentException("There has to be some pieces");
		}
		board = new Piece[pieceCount][pieceCount];
	}

	public Board(Board b) {
		int pieceCount = b.getBoardLength();
		board = new Piece[pieceCount][pieceCount];
		for (int i = 0; i < pieceCount; i++) {
			for (int j = 0; j < pieceCount; j++) {
				board[i][j] = b.getBoard(i, j);
			}
		}
	}

	public void printBoard() {
		for (Piece[] line : board) {
			for (Piece piece : line) {
				for (int rowNr = 0; rowNr < 5; rowNr++) {
					if (piece == null) {
						System.out.print("     ");
					} else {
						piece.printRow(rowNr);
					}
				}
			}
			System.out.print(System.lineSeparator());
		}
	}

	public Piece getBoard(int x, int y) {
		if (board == null) {
			throw new IllegalAccessError("Board is not initialized!");
		}
		return board[x][y];
	}

	public int getBoardLength() {
		return board.length;
	}

	public void shiftDown() {
		for (int i = getBoardLength() - 2; i >= 0; i--) {
			for (int j = 0; j < getBoardLength(); j++) {
				board[i+1][j] = board[i][j];
			}
		}
	}

	public void shiftRight() {
		for (int i = getBoardLength() - 2; i >= 0; i--) {
			for (int j = 0; j < getBoardLength(); j++) {
				board[j][i+1] = board[j][i];
			}
		}
	}
}
