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
		lm.leerBin(randomFile);
		
		Book b = new Book();
		b = lm.findBook(5, randomFile);
		System.out.println(b.toString());

	}

}
