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
	public final static int POSICION_EDITORIAL = 48;
	public final static int TAMANO_REGISTRO = 60;

	Book b = null;
	StringBuffer sb;
	private ArrayList<Book> libros = null;

	public void imprimir(String fichero) {
		RandomAccessFile streamIn = null;
		Book b;
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
		StringBuffer sb;
		Book b = null;

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
	 * @author Juan
	 * @param id
	 * @param randomFile
	 * @return Metodo que recibe un id y devuelve un objeto de la clase Book;
	 */

	public Book findBook(int id, String randomFile) {
		int posicion = (id - 1) * 60;
		Book libro = new Book();

		RandomAccessFile file = null;
		try {
			file = new RandomAccessFile(randomFile, "r");
			file.seek(posicion);
			if (file.read() == id) {
				libro = readBook(file);
			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			try {
				file.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return libro;

	}

	/**
	 * @author Cati Método que imprime todo el contenido del fichero “BIBLIO.BIN
	 * @param randomFile
	 */

	public void imprimeBin(String randomFile) {
		RandomAccessFile raf = null;

		int book_id;
		String title;
		int fk_author;
		int year;
		int fk_publisher;
		int stock;
		try {
			raf = new RandomAccessFile(randomFile, "r");

			System.out.println("\n...Lista de ficheros...");
			do {

				book_id = raf.readInt();

				char aTitle[] = new char[TAM_NAME], aux;

				for (int i = 0; i < aTitle.length; i++) {

					aux = raf.readChar();
					if (aux != 0) {
						aTitle[i] = aux;
					} else {
						aTitle[i] = ' ';
					}
				}

				title = new String(aTitle);
				fk_author = raf.readInt();
				fk_publisher = raf.readInt();
				year = raf.readInt();
				stock = raf.readInt();

				System.out.println("ID: " + book_id + " Title: " + title
						+ " Author: " + fk_author + " Publisher: "
						+ fk_publisher + " Year: " + year + " Stock: " + stock);

			} while (raf.getFilePointer() < raf.length());

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				raf.close();
			} catch (IOException ioe) {

			}
		}
	}
	

	/**
	 * @author Carlos y David Metodo que lee el archivo BIN y lo muestra por
	 *         pantalla
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
						+ " Author: " + fk_author + " Publisher: "
						+ fk_publisher + " Year: " + year + " Stock: " + stock);
			} while (file.getFilePointer() < file.length());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				file.close();
			} catch (IOException ioe) {

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

	/**
	 * @author CarlosGonzalezGonzalez
	 * @param randomFile
	 * @param editorial
	 * @return
	 */

	public ArrayList<Book> buscarLibros(String randomFile, int editorial) {
		ArrayList<Book> libros = new ArrayList<Book>(); // ArrayList donde
														// guardaremos los
														// libros de la
														// editorial
		Book book;
		RandomAccessFile streamIn = null;
		int posicion = 0;
		/*
		 * // Forma lenta try { streamIn = new RandomAccessFile(randomFile,
		 * "r"); do { book = readBook(streamIn); if (book.getFk_publisher() ==
		 * editorial) libros.add(book); } while (streamIn.getFilePointer() !=
		 * streamIn.length()); } catch (FileNotFoundException e) {
		 * System.err.println("Fichero no encontrado"); } catch (IOException e)
		 * { System.err.println("Error e/s"); } finally { if (streamIn != null)
		 * { try { streamIn.close(); } catch (IOException e) {
		 * System.err.println("Error e/s al cerrar"); } } }
		 */

		// Forma rapida
		try {
			streamIn = new RandomAccessFile(randomFile, "r"); // Abrimos el
																// flujo
			do {
				posicion += POSICION_EDITORIAL; // Nos posicionamos en la
												// editorial directamente
				streamIn.seek(posicion);
				int publisher = streamIn.readInt(); // Leemos la editorial
				if (publisher == editorial) { // Si son iguales...
					posicion -= POSICION_EDITORIAL; // Nos colocamos al inicio
													// del libro
					streamIn.seek(posicion);
					Book b = readBook(streamIn);
					posicion += TAMANO_REGISTRO;// Nos volvemos a colocar en la
												// siguiente posicion
					libros.add(readBook(streamIn)); // Anadimos el libro al
													// ArrayList
				} else { // Si son distintas...
					posicion += TAMANO_REGISTRO - POSICION_EDITORIAL;
					streamIn.seek(posicion); // Pasamos al siguiente libro
				}
			} while (streamIn.getFilePointer() != streamIn.length());
		} catch (FileNotFoundException e) {
			System.err.println("Fichero no encontrado");
		} catch (IOException e) {
			System.err.println("Error E/S");
		} finally {
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