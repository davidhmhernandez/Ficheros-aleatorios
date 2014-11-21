package es.torvall;


public class Main {
	final static String csvFile = "./resources/librosl.csv";
	final static String randomFile = "./resources/librosl.bin";

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		LibraryManager lm = new LibraryManager();

		
		lm.writeRandomFile(randomFile, lm.readCSV(csvFile));
		
				
		
		Book b = new Book(1010,"El Señor de las Moscas",1,83,2013,1);
		Book b2 = new Book(1,"El Señor de las casas",1,83,2013,1);
		lm.nuevoLibro(b, randomFile);
		lm.nuevoLibro(b2, randomFile);
		
		lm.leerBin(randomFile);


	}

}
