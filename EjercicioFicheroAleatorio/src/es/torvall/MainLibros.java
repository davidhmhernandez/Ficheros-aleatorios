package es.torvall;

import java.awt.EventQueue;

import javax.swing.JFrame;


import javax.swing.JButton;

public class MainLibros {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainLibros window = new MainLibros();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainLibros() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton CSVtoBin = new JButton("Pasar CSV a BIN");
		CSVtoBin.setBounds(10, 11, 116, 23);
		frame.getContentPane().add(CSVtoBin);
		
		JButton addBook = new JButton("A\u00F1adir Libro");
		addBook.setBounds(10, 45, 116, 23);
		frame.getContentPane().add(addBook);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(10, 79, 116, 23);
		frame.getContentPane().add(btnNewButton);
	}
}
