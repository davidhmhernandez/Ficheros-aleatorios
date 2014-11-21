package es.torvall;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class LibraryManager {
	final static int TAM_NAME = 20;

	/**
	 * @author David y Carlos
	 * @param csvFile
	 *            metodo para leer el archivo CSV
	 */
	public ArrayList<Book> readCSV(String csvFile) {
		ArrayList<Book> libros = new ArrayList<Book>();
		BufferedReader streamIn = null;
		String line;
		StringTokenizer st;
		StringBuffer sb;
		Book b;

		try {
			streamIn = new BufferedReader(new FileReader(csvFile));
			while ((line = streamIn.readLine()) != null) {// Leemos el Archivo
				// System.out.println(line);
				try { // Hasta el Final
					st = new StringTokenizer(line, ";");
					while (st.hasMoreTokens()) {// se lee las lineas mientras
												// haya campos
						int id = Integer.parseInt(st.nextToken());

						sb = new StringBuffer(st.nextToken());
						sb.setLength(TAM_NAME); // fijo a tam max 10
						String title = sb.toString();

						int fk_author = Integer.parseInt(st.nextToken());

						int fk_publisher = Integer.parseInt(st.nextToken());

						int year = Integer.parseInt(st.nextToken());

						int stock = Integer.parseInt(st.nextToken());
						// se crea el objeto libro
						b = new Book(id, title, fk_author, fk_publisher, year,
								stock);
						libros.add(b);

					}

				} catch (NumberFormatException nfe) {// excepcion para la
														// lectura de la linea
														// que no se pueda
														// convertir

				}
			}
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		} finally {
			if (streamIn != null) {
				try {
					streamIn.close();// cerramos el flujo
				} catch (IOException e) {

				}
			}
		}

		return libros;
	}

	/**
	 * @author David y Carlos
	 * @param randomFile
	 *            metodo para escribir un fichero aleatorio, atraves de un
	 *            objeto libro
	 */
	public void writeRandomFile(String randomFile, ArrayList<Book> libros) {
		RandomAccessFile streamOut = null;
		StringBuffer sb;

		try {
			streamOut = new RandomAccessFile(randomFile, "rw");
			for (Book b : libros) {

				int id = b.getBook_id();
				streamOut.writeInt(id);

				sb = new StringBuffer(b.getTitle());
				sb.setLength(TAM_NAME); // fijo a tam max 20
				String title = sb.toString();
				streamOut.writeChars(title);

				int fk_author = b.getFk_author();
				streamOut.writeInt(fk_author);

				int fk_publisher = b.getFk_publisher();
				streamOut.writeInt(fk_publisher);

				int year = b.getYear();
				streamOut.writeInt(year);

				int stock = b.getStock();
				streamOut.writeInt(stock);

			}

		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				streamOut.close();// se cierra el flujo
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	/**
	 * @author Carlos y David
	 * Metodo que lee el archivo BIN y lo muestra por pantalla
	 * @param randomFile
	 */

	public void leerBin(String randomFile) {

		RandomAccessFile file = null;
		try {
			file = new RandomAccessFile(randomFile, "r");
		

		int book_id, fk_author, year, fk_publisher, stock;

		char title[] = new char[TAM_NAME], aux;

		// System.out.println(file.length());

		do {
			book_id = file.readInt();
			for (int i = 0; i < TAM_NAME; i++) {
				aux = file.readChar();
				if ((int) aux != 0) {
					title[i] = aux;
				} else
					title[i] = ' ';
			}
			String titleS = new String(title);
			fk_author = file.readInt();
			fk_publisher = file.readInt();
			year = file.readInt();
			stock = file.readInt();
			System.out.println("ID: " + book_id + " Title: " + titleS
					+ " Author: " + fk_author + " Publisher: " + fk_publisher
					+ " Year: " + year + " Stock: " + stock);
		} while (file.getFilePointer() < file.length());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try{
				file.close();
			}catch(IOException ioe){
				
			}
		}
		

	}
}
