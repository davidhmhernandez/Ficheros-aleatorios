package es.torvall;

import java.io.Serializable;

/**
 * JavaBean
 * 
 * @author David Hernandez
 * 
 */

public class Book implements Serializable {

	private static final long serialVersionUID = 1L;

	private int book_id;

	private String title;
	private int fk_author;
	private int year;
	private int fk_publisher;
	private int stock;

	public Book() {
	}

	/**
	 * 
	 * @param book_id
	 * @param title
	 * @param fk_author
	 * @param year
	 * @param fk_publisher
	 * @param stock
	 */
	Book(int book_id, String title, int fk_author, int year, int fk_publisher,
			int stock) {

		setBook_id(book_id);
		this.title = title;
		this.fk_author = fk_author;
		this.year = year;
		this.fk_publisher = fk_publisher;
		this.stock = stock;

	}

	public int getBook_id() {
		return book_id;
	}

	public void setBook_id(int book_id) {
		if (book_id > 1000) {
			this.book_id = 0;
		} else
			this.book_id = book_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getFk_author() {
		return fk_author;
	}

	public void setFk_author(int fk_author) {
		this.fk_author = fk_author;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getFk_publisher() {
		return fk_publisher;
	}

	public void setFk_publisher(int fk_publisher) {
		this.fk_publisher = fk_publisher;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	@Override
	public String toString() {

		return "Libro : ID--" + book_id + " Título-- " + title + " FK Autor: "
				+ fk_author + " Año-- " + year + "FK_Publisher-- "
				+ fk_publisher + " Stock-- " + stock;
	}

}
