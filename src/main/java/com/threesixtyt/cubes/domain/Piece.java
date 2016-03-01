package com.threesixtyt.cubes.domain;

/**
 * Puzzle Piece
 * 
 * @author Bekir Dogru
 *
 */
public class Piece {
	/**
	 * four edges of a piece, every edge consists of 5 booleans which indicates
	 * if this part is blank or not. </br>
	 * edges and booleans are ordered clockwise </br>
	 * <code>false</code> if blank </br>
	 * <code>true</code> if not blank
	 */
	private boolean[][] edges;
	/**
	 * keeps the number of rotation applied on the piece
	 */
	private int rotateCount;

	/**
	 * 
	 * @return {@link Piece#rotateCount}
	 */
	public int getRotateCount() {
		return rotateCount;
	}

	public boolean isReflected = false;
	private int nr;

	public Piece() {
		edges = new boolean[4][5];
		rotateCount = 0;
	}

	/**
	 * copy constuctor
	 * 
	 * @param pieceToCopy
	 */
	public Piece(Piece pieceToCopy) {
		edges = new boolean[4][5];
		rotateCount = pieceToCopy.getRotateCount();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 5; j++) {
				edges[i][j] = pieceToCopy.getPureEdge(i)[j];
			}
		}
		this.nr = pieceToCopy.getNr();
		this.isReflected = pieceToCopy.isReflected();
	}

	private void initializeRow(int rowNr, boolean[] rowContent) {
		switch (rowNr) {
		case 0:
			edges[0] = rowContent;
			edges[1][0] = rowContent[4];
			edges[3][4] = rowContent[0];
			break;
		case 4:
			for (int i = 0; i < 5; i++) {
				edges[2][i] = rowContent[4 - i];
			}
			edges[1][4] = rowContent[4];
			edges[3][0] = rowContent[0];
			break;
		default:
			edges[1][rowNr] = rowContent[4];
			edges[3][4 - rowNr] = rowContent[0];
			break;
		}
	}

	/**
	 * initializes the row by the data read by the input file
	 * 
	 * @param rowNr
	 *            row number of the piece that data indicates
	 * @param rowContent
	 *            data from the input file
	 */
	public void initializeRow(int rowNr, char[] rowContent) {
		if (rowContent.length != 5 || rowNr > 5) {
			throw new IllegalArgumentException(
					"Row Number should be between 0-5 and Row Content should have 5 elements.");
		}
		boolean[] boolRowContent = new boolean[5];
		for (int i = 0; i < 5; i++) {
			if (rowContent[i] == ' ') {
				boolRowContent[i] = false;
			} else {
				boolRowContent[i] = true;
			}
		}
		initializeRow(rowNr, boolRowContent);
	}

	/**
	 * Rotates the piece by 90 degrees
	 */
	public void rotate() {
		if (isReflected) {
			rotateCount += 3;
			rotateCount %= 4;
		} else {
			rotateCount++;
			rotateCount %= 4;
		}
	}

	/**
	 * Prints the specified row of the piece to the {@link System#out}
	 * 
	 * @param rowNr
	 *            row to be printed
	 */
	public void printRow(int rowNr) {
		switch (rowNr) {
		case 0:
			for (Boolean b : getEdge(0)) {
				if (b) {
					System.out.print("o");
				} else {
					System.out.print(" ");
				}
			}
			break;
		case 4:
			for (int i = 4; i >= 0; i--) {
				if (getEdge(2)[i]) {
					System.out.print("o");
				} else {
					System.out.print(" ");
				}
			}
			break;
		default:
			if (getEdge(3)[4 - rowNr]) {
				System.out.print("o");
			} else {
				System.out.print(" ");
			}
			System.out.print("ooo");
			if (getEdge(1)[rowNr]) {
				System.out.print("o");
			} else {
				System.out.print(" ");
			}
			break;
		}
	}

	/**
	 * 
	 * @param edgeNr
	 *            which edge to be returned
	 * @return desired edge
	 */
	public boolean[] getEdge(int edgeNr) {
		if (!isReflected) {
			return edges[(edgeNr - rotateCount + 4) % 4];
		}
		if(edgeNr == 0 || edgeNr == 2) {
			boolean[] result = new boolean[5];
			for(int i = 0; i<5; i++) {
				result[i] = edges[(edgeNr - rotateCount + 4) % 4][4-i];
			}
			return result;
		}
		boolean[] result = new boolean[5];
		for(int i = 0; i<5; i++) {
			result[i] = edges[(edgeNr - rotateCount + 6) % 4][4-i];
		}
		return result;
		
	}
	
	public boolean[] getPureEdge(int edgeNr) {
		return edges[edgeNr];
	}

	/**
	 * Checks if this piece is matching with another piece by the specified edge
	 * 
	 * @param piece
	 *            second piece to check if match
	 * @param edgeNr
	 *            edge to be checked
	 * @return
	 */
	public boolean isMatch(Piece piece, int edgeNr) {
		boolean[] edge = getEdge(edgeNr);
		boolean[] edge2 = piece.getEdge((edgeNr + 2) % 4);
		boolean result = true;
		result = (!(edge[0] && edge2[4])) && (!(edge[4] && edge2[0]));
		for (int i = 1; i < 4; i++) {
			if (!result) {
				break;
			}
			result = result && (edge[i] ^ edge2[4 - i]);

		}
		return result;
	}

	public boolean isMatch(Piece piece, int edgeNr, int edgeNr2) {
		boolean[] edge = getEdge(edgeNr);
		boolean[] edge2 = piece.getEdge(edgeNr2);
		boolean result = true;
		result = (!(edge[0] && edge2[4])) && (!(edge[4] && edge2[0]));
		for (int i = 1; i < 4; i++) {
			if (!result) {
				break;
			}
			result = result && (edge[i] ^ edge2[4 - i]);

		}
		return result;
	}

	public int getNr() {
		return nr;
	}

	public void setNr(int nr) {
		this.nr = nr;
	}

	public boolean isReflected() {
		return isReflected;
	}

	public void reflect() {
		this.isReflected = !this.isReflected;
	}

}
