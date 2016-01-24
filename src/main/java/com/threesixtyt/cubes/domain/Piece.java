package com.threesixtyt.cubes.domain;

public class Piece {
	private boolean[][] edges;
	private int rotateCount;

	public int getRotateCount() {
		return rotateCount;
	}

	public Piece() {
		edges = new boolean[4][5];
		rotateCount = 0;
	}

	public Piece(Piece pieceToCopy) {
		edges = new boolean[4][5];
		rotateCount = pieceToCopy.getRotateCount();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 5; j++) {
				edges[i][j] = pieceToCopy.getEdge(i + pieceToCopy.getRotateCount())[j];
			}
		}
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

	public void rotate() {
		rotateCount++;
		rotateCount %= 4;
	}

	public void printRow(int rowNr) {
		switch (rowNr) {
		case 0:
			for (Boolean b : edges[(4 - rotateCount) % 4]) {
				if (b) {
					System.out.print("o");
				} else {
					System.out.print(" ");
				}
			}
			break;
		case 4:
			for (int i = 4; i >= 0; i--) {
				if (edges[(6 - rotateCount) % 4][i]) {
					System.out.print("o");
				} else {
					System.out.print(" ");
				}
			}
			break;
		case 2:
			if (edges[(7 - rotateCount) % 4][4 - rowNr]) {
				System.out.print("o");
			} else {
				System.out.print(" ");
			}
			System.out.print("o");
			System.out.print(rotateCount);
			System.out.print("o");
			if (edges[(5 - rotateCount) % 4][rowNr]) {
				System.out.print("o");
			} else {
				System.out.print(" ");
			}
			break;
		default:
			if (edges[(7 - rotateCount) % 4][4 - rowNr]) {
				System.out.print("o");
			} else {
				System.out.print(" ");
			}
			System.out.print("ooo");
			if (edges[(5 - rotateCount) % 4][rowNr]) {
				System.out.print("o");
			} else {
				System.out.print(" ");
			}
			break;
		}
	}

	public boolean[] getEdge(int edgeNr) {
		return edges[(edgeNr - rotateCount + 4) % 4];
	}

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
}
