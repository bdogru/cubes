package com.threesixtyt.cubes.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.threesixtyt.cubes.domain.Piece;

/**
 * Service class that is responsible to read data from the input file
 * 
 * @author Bekir Dogru
 *
 */
public class InputReaderService {
	private File f;

	public InputReaderService(String filePath) {
		f = new File(filePath);
	}

	/**
	 * Reads input file line by line, constructs pieces
	 * 
	 * @return all constructed pieces that is read from the input file
	 */
	public List<Piece> getPiecesFromFile() {
		FileReader fr = null;
		try {
			fr = new FileReader(f);
		} catch (FileNotFoundException fnf) {
			throw new IllegalArgumentException("Input file couldn't be found.", fnf);
		}
		BufferedReader br = new BufferedReader(fr);

		List<Piece> pieces = new LinkedList<Piece>();
		String line;
		List<Piece> linePieces = null;
		int pieceNr = 0;
		try {
			for (int counter = 0; (line = br.readLine()) != null; counter++) {
				if (counter % 5 == 0) {
					linePieces = new ArrayList<Piece>();
				}
				for (int i = 0; i + 4 < line.length(); i += 5) {
					char[] row = new char[5];
					for (int j = 0; j < 5; j++) {
						row[j] = line.charAt(i + j);
					}
					if (counter % 5 == 0) {
						Piece p = new Piece();
						p.setNr(pieceNr);
						pieceNr++;
						linePieces.add(p);
					}
					linePieces.get((i / 5)).initializeRow(counter % 5, row);
				}
				if (counter % 5 == 4) {
					pieces.addAll(linePieces);
					linePieces = null;
				}
			}
		} catch (IOException e) {
			throw new IllegalArgumentException("Input file couldn't be read.", e);
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				throw new IllegalArgumentException("Buffered reader couldn't be closed.", e);
			}
		}
		return pieces;
	}
}
