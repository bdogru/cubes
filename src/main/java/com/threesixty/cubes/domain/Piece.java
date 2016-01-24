package com.threesixty.cubes.domain;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Piece {
	private boolean[][] edges;
	private int rotateCount;
	
	public Piece() {
		edges = new boolean[4][5];
		rotateCount = 0;
	}
	
	private void initializeRow(int rowNr, boolean[] rowContent) {
		switch (rowNr) {
		case 0:
			edges[0] = rowContent;
			edges[1][0] = rowContent[4];
			edges[3][4] = rowContent[0];
			break;
		case 3:
			for(int i = 0; i < 5; i++) {
				edges[2][i] = rowContent[4-i];
			}
			edges[1][4] = rowContent[4];
			edges[3][0] = rowContent[0];
			break;
		default:
			edges[1][rowNr] = rowContent[4];
			edges[3][4-rowNr] = rowContent[0];
			break;
		}
	}
	
	public void initializeRow(int rowNr, char[] rowContent) {
		if(rowContent.length != 5 || rowNr > 3) {
			throw new IllegalArgumentException("Row Number should be between 0-3 and Row Content should have 5 elements.");
		}
		boolean[] boolRowContent = new boolean[5];
		for(int i = 0; i < 5; i++) {
			if(rowContent[i] == ' ') {
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
			for(Boolean b : edges[(0-rotateCount)%4]) {
				if(b) {
					System.out.print("o");
				} else {
					System.out.print(" ");
				}
			}
			break;
		case 4:
			for(int i = 4; i >= 0; i--) {
				if(edges[(2-rotateCount)%4][i]) {
					System.out.print("o");
				} else {
					System.out.print(" ");
				}
			}
			break;
		default:
			if(edges[(3-rotateCount)%4][4-rowNr]) {
				System.out.print("o");
			} else {
				System.out.print(" ");
			}
			System.out.print("ooo");
			if(edges[(1-rotateCount)%4][rowNr]) {
				System.out.print("o");
			} else {
				System.out.print(" ");
			}
			break;
		}
	}
}
