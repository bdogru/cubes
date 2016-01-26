package com.threesixtyt.cubes.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.threesixtyt.cubes.domain.Board;
import com.threesixtyt.cubes.domain.Piece;

/**
 * Main processing service class
 * 
 * @author Bekir Dogru
 *
 */
public class ProcessorService {
	/**
	 * Recursive methods that gets the last state of the board and list of the
	 * all free pieces
	 * 
	 * @param board
	 *            last state of the board
	 * @param pieces
	 *            list of the free pieces
	 * @return result board state</br>
	 *         <code>null</code> if all pieces couldn't be but by this variation
	 */
	public Board process(Board board, List<Piece> pieces) {
		if (pieces.isEmpty()) {
			return board;
		}
		Iterator<Piece> it = pieces.iterator();
		while (it.hasNext()) {
			Piece next = it.next();
			List<Board> boards = findPlace(board, next);
			for (Board b : boards) {
				List<Piece> nextPieces = new LinkedList<Piece>(pieces);
				nextPieces.remove(next);
				if ((b = process(b, nextPieces)) != null) {
					return b;
				}
			}
		}
		return null;
	}

	/**
	 * Tries to find a matching place for one piece
	 * 
	 * @param board
	 *            last state of the board
	 * @param piece
	 *            piece to look for matching place
	 * @return all possible board states by placing piece
	 */
	private List<Board> findPlace(Board board, Piece piece) {
		List<Board> boards = new ArrayList<Board>();
		for (int i = 0; i < board.getBoardLength(); i++) {
			/**
			 * Checks if position is null or not, if it is not null then tries
			 * to put piece to the four surrounding places
			 */
			for (int j = 0; j < board.getBoardLength(); j++) {
				if (board.getPiece(i, j) == null) {
					continue;
				}
				boards.addAll(check(board, i, j + 1, piece));
				boards.addAll(check(board, i + 1, j, piece));
				boards.addAll(check(board, i, j - 1, piece));
				boards.addAll(check(board, i - 1, j, piece));
			}
		}
		return boards;
	}

	/**
	 * Checks if the piece is matching to the specified location by rotating
	 * four times, and returns all possible board states
	 * 
	 * @param board
	 *            last state of the board
	 * @param i
	 *            location line
	 * @param j
	 *            location column
	 * @param piece
	 *            piece to check if match to the specified location
	 * @return all possible board states by placing the piece
	 */
	private List<Board> check(Board board, int i, int j, Piece piece) {
		List<Board> result = new ArrayList<Board>();
		if (board.getPiece(i, j) != null) {
			return result;
		}
		Piece pieceBottom = board.getPiece(i + 1, j);
		Piece pieceLeftBottom = board.getPiece(i + 1, j - 1);
		Piece pieceLeft = board.getPiece(i, j - 1);
		Piece pieceLeftTop = board.getPiece(i - 1, j - 1);
		Piece pieceTop = board.getPiece(i - 1, j);
		Piece pieceRightTop = board.getPiece(i - 1, j + 1);
		Piece pieceRight = board.getPiece(i, j + 1);
		Piece pieceRightBottom = board.getPiece(i + 1, j + 1);

		for (int rotateCount = 0; rotateCount < 4; rotateCount++) {
			boolean isTotallyMatch = isMatch(pieceLeft, piece, 1) && isMatch(pieceRight, piece, 3)
					&& isMatch(pieceTop, piece, 2) && isMatch(pieceBottom, piece, 0)
					&& isMatchCorner(pieceLeftTop, pieceTop, pieceLeft, piece)
					&& isMatchCorner(pieceLeft, piece, pieceLeftBottom, pieceBottom)
					&& isMatchCorner(pieceTop, pieceRightTop, piece, pieceRight)
					&& isMatchCorner(piece, pieceRight, pieceBottom, pieceRightBottom);
			if (isTotallyMatch) {
				Board tmp = new Board(board);
				tmp.putPiece(piece, i, j);
				result.add(tmp);
			}
			piece.rotate();
		}
		return result;
	}

	/**
	 * Checks if two pieces are match by the specified edge of the p1 differs by
	 * {@link Piece#isMatch(Piece, int)} by <code>null</code> check
	 * 
	 * @param p1
	 * @param p2
	 * @param edge
	 *            edge number of the p1
	 * @return <code>false</code> if not matching</br>
	 *         <code>true</code>if either of the pieces is null or matching each
	 *         other
	 */
	private boolean isMatch(Piece p1, Piece p2, int edge) {
		if (p1 == null || p2 == null) {
			return true;
		}
		return p1.isMatch(p2, edge);
	}

	/**
	 * checks if corners of four pieces are match for each other
	 * 
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param p4
	 * @return
	 */
	private boolean isMatchCorner(Piece p1, Piece p2, Piece p3, Piece p4) {
		boolean isAllFourFilled = false;
		if (p1 != null && p2 != null && p3 != null && p4 != null) {
			isAllFourFilled = true;
		}
		int trueCount = 0;
		if (p1 != null && p1.getEdge(2)[0]) {
			trueCount++;
		}
		if (p2 != null && p2.getEdge(3)[0]) {
			trueCount++;
		}
		if (p3 != null && p3.getEdge(1)[0]) {
			trueCount++;
		}
		if (p4 != null && p4.getEdge(0)[0]) {
			trueCount++;
		}
		boolean result = false;
		if (isAllFourFilled) {
			if (trueCount == 1) {
				result = true;
			}
		} else if (trueCount <= 1) {
			result = true;
		}
		return result;
	}
}
