package com.threesixtyt.cubes.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.threesixtyt.cubes.domain.Board;
import com.threesixtyt.cubes.domain.Piece;

public class ProcessorService {
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

	private List<Board> findPlace(Board board, Piece piece) {
		List<Board> boards = new ArrayList<Board>();
		for (int i = 0; i < board.getBoardLength(); i++) {
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

	private boolean isMatch(Piece p1, Piece p2, int edge) {
		if (p1 == null || p2 == null) {
			return true;
		}
		return p1.isMatch(p2, edge);
	}

	private boolean isMatchCorner(Piece p1, Piece p2, Piece p3, Piece p4) {
		if (p1 != null && p2 != null && p3 != null && p4 != null) {
			return p1.getEdge(2)[0] ^ p2.getEdge(3)[0] ^ p3.getEdge(1)[0] ^ p4.getEdge(0)[0];
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
		if (trueCount > 1) {
			return false;
		}
		return true;
	}
}
