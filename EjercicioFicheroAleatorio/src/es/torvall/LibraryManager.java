package es.torvall;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.StringTokenizer;

public class LibraryManager {
	final static int TAM_NAME = 20;
	Book b = null;
	StringBuffer sb;
	private RandomAccessFile streamOut;

	/**
	 * @author David y Carlos
	 * @param csvFile
	 * metodo para leer el archivo CSV
	 */
	public void readCSV(String csvFile) {
		BufferedReader streamIn = null;
		String line;
		StringTokenizer st;

		try {
			streamIn = new BufferedReader(new FileReader(csvFile));
			while ((line = streamIn.readLine()) != null) {// Leemos el Archivo
				System.out.println(line);
				try { // Hasta el Final
					st = new StringTokenizer(line, ";");
					while (st.hasMoreTokens()) {// se lee las lineas mientras haya campos
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
					}
				} catch (NumberFormatException nfe) {// excepcion para la lectura de la linea que no se pueda convertir
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

	}

	/**
	 * @author David y Carlos
	 * @param randomFile
	 * metodo para escribir un fichero aleatorio, atraves de un objeto libro
	 */
	public void writeRandomFile(String randomFile) {
		
		try {
			streamOut = new RandomAccessFile(randomFile, "rw");
			int id = b.getBook_id();
			streamOut.write(id);

			sb = new StringBuffer(b.getTitle());
			sb.setLength(TAM_NAME); // fijo a tam max 20
			String title = sb.toString();
			streamOut.writeChars(title);

			int fk_author = b.getFk_author();
			streamOut.write(fk_author);

			int year = b.getYear();
			streamOut.write(year);

			int fk_publisher = b.getFk_publisher();
			streamOut.write(fk_publisher);

			int stock = b.getStock();
			streamOut.write(stock);

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
	public boolean deleteId(int id,String randomFile){
		int posicion=(id-1)*60;
		boolean realizado=false;
		RandomAccessFile file=null;
		
		try {
			file=new RandomAccessFile(randomFile,"rw");
			file.seek(posicion);
			
			if(file.read()==id){
				file.write(0);//id
				file.skipBytes(44);
				file.write(0);//fk_autor
				file.skipBytes(4);
				file.write(0);//autor
				file.skipBytes(4);
				file.write(0);//editor
				file.skipBytes(4);
				file.write(0);//año
				file.skipBytes(4);
				file.write(0);//stock
				
				realizado=true;
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				file.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return realizado;
		
	}
	
	

}


