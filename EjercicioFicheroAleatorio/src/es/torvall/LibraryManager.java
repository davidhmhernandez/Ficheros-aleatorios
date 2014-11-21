package es.torvall;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class LibraryManager {
	final static int TAM_NAME = 20;
	Book b = null;
	StringBuffer sb;

	public void imprimir(String fichero) {
		RandomAccessFile streamIn = null;
		Book b; // ¿Dos Book? ////////////////////////////////////////
		try {
			// abrimos el flujo de salida para escribir un fichero
			// de acceso aleatorio
			streamIn = new RandomAccessFile(fichero, "r");
			try {
				do {
					b = readBook(streamIn);
					if (b.getBook_id() != 0)
						System.out.println(b.toString());
				} while (streamIn.getFilePointer() <= streamIn.length());
			} catch (EOFException e) {
			}
		} catch (FileNotFoundException e) {
			System.err.println("Fichero no encontrado " + e.getMessage());
		} catch (IOException e) {
			System.err.println("Error E/S " + e.getMessage());
		} finally {
			if (streamIn != null)
				try {
					streamIn.close();
				} catch (IOException e) {
					System.err.println("Error E/S " + e.getMessage());
				}
		}
	}

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

		try {
			streamIn = new BufferedReader(new FileReader(csvFile));
			while ((line = streamIn.readLine()) != null) {// Leemos el Archivo
				System.out.println(line);
				try { // Hasta el Final
					st = new StringTokenizer(line, ";");
					while (st.hasMoreTokens()) {// se lee las lineas mientras
												// haya campos
						int id = Integer.parseInt(st.nextToken());

						sb = new StringBuffer(st.nextToken());
						sb.setLength(TAM_NAME); // fijo a tam max 10
						String title = sb.toString();

						int fk_author = Integer.parseInt(st.nextToken());

						int year = Integer.parseInt(st.nextToken());

						int fk_publisher = Integer.parseInt(st.nextToken());

						int stock = Integer.parseInt(st.nextToken());
						// se crea el objeto libro
						b = new Book(id, title, fk_author, year, fk_publisher,
								stock);
						libros.add(b);
					}
				} catch (NumberFormatException nfe) {// excepcion para la
														// lectura de la linea
														// que no se pueda
														// convertir
					System.out.println(nfe.getMessage());
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
		try {
			streamOut = new RandomAccessFile(randomFile, "rw");
			for (Book b : libros) {
				escribirBook(streamOut, b);
			}
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				streamOut.close();// se cierra el flujo
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public String readTitle(RandomAccessFile stream) throws IOException {
		StringBuilder sb = new StringBuilder();
		char letra;
		for (int i = 0; i < TAM_NAME; i++) {
			letra = stream.readChar(); // Leemos caracter a caracter

			if ((int) letra != 0)
				sb.append(letra);
			else
				sb.append(' ');

		}

		return sb.toString();
	}

	public void escribirBook(RandomAccessFile stream, Book b)
			throws IOException {

		int id = b.getBook_id();
		stream.write(id);

		sb = new StringBuffer(b.getTitle());
		sb.setLength(TAM_NAME); // fijo a tam max 20
		String title = sb.toString();
		stream.writeChars(title);

		int fk_author = b.getFk_author();
		stream.write(fk_author);

		int year = b.getYear();
		stream.write(year);

		int fk_publisher = b.getFk_publisher();
		stream.write(fk_publisher);

		int stock = b.getStock();
		stream.write(stock);
	}

	public Book readBook(RandomAccessFile stream) throws IOException {

		int book_id = stream.readInt();

		String title = readTitle(stream);

		int fk_author = stream.readInt();
		int year = stream.readInt();
		int fk_publisher = stream.readInt();
		int stock = stream.readInt();

		Book book = new Book(book_id, title, fk_author, year, fk_publisher,
				stock);
		return book;
	}
	
	

	public ArrayList<Book> buscarLibros(String randomFile, int editorial) {
		System.out.println("buscaLibros");
		ArrayList<Book> libros = new ArrayList<Book>(); // ArrayList donde guardaremos los libros de la editorial
		Book book;
		RandomAccessFile streamIn = null;
		/*try {
			streamIn = new RandomAccessFile(randomFile, "r");
			do {
				book = readBook(streamIn);
				if (book.getFk_publisher() == editorial)
					libros.add(book);
			} while (streamIn.getFilePointer() != streamIn.length());
		} catch (FileNotFoundException e) {
			System.err.println("Fichero no encontrado");
		} catch (IOException e) {
			System.err.println("Error e/s");
		} finally {
			if (streamIn != null) {
				try {
					streamIn.close();
				} catch (IOException e) {
					System.err.println("Error e/s al cerrar");
				}
			}
		}*/
		
		try{
			do{
				int posicion = 56;
				streamIn.seek(posicion);
				int publisher = streamIn.readInt();
				if(publisher == editorial){
					streamIn.seek(posicion - 56);
					libros.add(readBook(streamIn));
					streamIn.seek(posicion + 60);
				}else{
					streamIn.seek(posicion + 60);
				}
			}while(streamIn.getFilePointer() != streamIn.length());
		} catch (FileNotFoundException e) {
			System.err.println("Fichero no encontrado");
		} catch (IOException e) {
			System.err.println("Error E/S");
		}finally{
			if (streamIn != null) {
				try {
					streamIn.close();
				} catch (IOException e) {
					System.err.println("Error e/s al cerrar");
				}
			}
		}
		return libros;
	}
}
