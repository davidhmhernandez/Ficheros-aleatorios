package es.torvall;

import java.util.ArrayList;

public class Main {
	final static String csvFile = "./resources/librosl.csv";
	final static String randomFile = "./resources/librosl.bin";

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		LibraryManager lm = new LibraryManager();
		/*
		lm.writeRandomFile(randomFile,lm.readCSV(csvFile));
		lm.imprimir(randomFile);*/
		
		ArrayList<Book>a = lm.buscarLibros(randomFile,67);
		for(Book b : a){
			//System.out.println(b.toString());
			
		}
	}

}
