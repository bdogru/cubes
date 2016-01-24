package com.threesixtyt.cubes;

import java.util.List;

import com.threesixtyt.cubes.domain.Board;
import com.threesixtyt.cubes.domain.Piece;
import com.threesixtyt.cubes.service.InputReaderService;
import com.threesixtyt.cubes.service.ProcessorService;


public class Cubes {
	public static void main(String[] args) throws Exception {
		InputReaderService irs = new InputReaderService(args[0]);
		List<Piece> pieces = irs.getPiecesFromFile();
		Board board = new Board(pieces.size());
		Piece firstPiece = pieces.remove(0);
		board.putFirstPieces(firstPiece);
		ProcessorService ps = new ProcessorService();
		board = ps.process(board, pieces);
		if(board == null) {
			throw new Exception("Solution couldn't be found.");
		}
		board.printBoard();
	}
}
