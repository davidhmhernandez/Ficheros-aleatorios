package es.torvall;

import java.io.IOException;

public class Main {
	final static String csvFile = "./resources/librosl.csv";
	final static String randomFile = "./resources/librosl.bin";

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		LibraryManager lm = new LibraryManager();
		
		lm.writeRandomFile(randomFile,lm.readCSV(csvFile));
		lm.leerBin(randomFile);

	}

}
