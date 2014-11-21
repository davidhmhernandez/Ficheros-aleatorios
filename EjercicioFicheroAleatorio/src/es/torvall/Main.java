package es.torvall;

public class Main {
	final static String csvFile = "./resources/librosl.csv";
	final static String randomFile = "./resources/librosl.bin";

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		LibraryManager lm = new LibraryManager();
		lm.readCSV(csvFile);
		lm.writeRandomFile(randomFile);
		
		
		

	}

}
