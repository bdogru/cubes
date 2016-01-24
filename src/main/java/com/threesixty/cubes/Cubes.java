package com.threesixty.cubes;

import java.util.List;

import com.threesixty.cubes.domain.Board;
import com.threesixty.cubes.domain.Piece;
import com.threesixty.cubes.service.InputReaderService;

/**
 * Hello world!
 *
 */
public class Cubes {
	public static void main(String[] args) {
		InputReaderService irs = new InputReaderService(args[0]);
		List<Piece> pieces = irs.getPiecesFromFile();
		Board b = new Board(pieces.size());
		b.testInput(pieces);
		b.printBoard();
	}
}
