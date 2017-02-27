import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * A window that shows a Sudoku puzzle, 9x9, 16x16 and Samurai variants.
 * 
 * @author  Programació avançada i estructura de dades - PAED <br/>
 * 		    La Salle - Universitat Ramon Llull
 * 
 * @version 1.0 14/12/2015
 */
public class SudokuGUI extends JFrame implements Runnable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Number of rows of the board  */
	private int rows;
	/** Number of columns of the board  */
	private int columns;
	/** Labels for each cell */
	private JLabel[][] cells;
	/** A matrix that indicates, for each cell of the board, if its number
	 *  will be calculated (true) or if it is a initial cell (false).*/
	private boolean[][] toSolve;

	public static final int UNKNOWN_NUMBER = -1;
	public static final String UNKNOWN_CELL = "?";
	public static final String OUT_OF_BOARD_CELL = "";
	public static final Color UNKNOWN_CELL_COLOR = Color.LIGHT_GRAY;
	public static final Color SOLVED_CELL_COLOR = Color.WHITE;
	public static final Color INITIAL_CELL_COLOR = Color.GRAY;
	public static final Color OUT_OF_BOARD_COLOR = Color.WHITE;
	public static final Color LINE_COLOR = Color.DARK_GRAY;
	public static final Dimension DEFAULT_DIMENSION = new Dimension(600, 600);


	/**
	 * Creates a new window that shows the board without any number.
	 * 
	 * @param title Title to be displayed on the bar of the window.
	 * @param width Width of the window in pixels.
	 * @param height Height of the window in pixels.
	 * @param toSolve A matrix that indicates, for each cell of the board, if its number
	 *                will be calculated (true) or if it is a initial cell (false).
	 */
	public SudokuGUI(String title, int width, int height, boolean[][] toSolve) {
		this.rows = toSolve.length;
		this.columns = toSolve[0].length;

		this.toSolve = new boolean[rows][columns];
		for (int row=0; row<rows; row++) {
			for (int column=0; column<columns; column++) {
				this.toSolve[row][column] = toSolve[row][column];
			}
		}

		initializeBoard();

		if (width == 0 || height == 0) {
			setSize(DEFAULT_DIMENSION);
		} else {
			setSize(new Dimension(width, height));
		}
		setTitle(title);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	/**
	 * Updates the board with the given matrix of numbers. A cell with UNKNOWN_NUMBER (-1)
	 * value indicates that the cell has not been calculated yet.
	 * 
	 * @param numbers The matrix with the matrix of numbers to update the board.
	 */
	public void updateBoard(int [][] numbers) {
		if (!isVisible()) setVisible(true);
		for (int row=0; row<rows; row++) {
			for (int column=0; column<columns; column++) {
				updateCell(row, column, numbers[row][column]);
			}
		}
	}

	/** Initialize the board according to the number of rows, columns and content
	 *  of the matrix of booleans "toSolve".
	 */
	private void initializeBoard() {
		GridLayout grid = new GridLayout(rows, columns);

		cells = new JLabel[rows][columns];
		for (int row=0; row<rows; row++) {
			for (int column=0; column<columns; column++) {
				cells[row][column] = new JLabel(UNKNOWN_CELL);
				cells[row][column].setHorizontalAlignment(SwingConstants.CENTER);
				cells[row][column].setFont(this.cells[row][column].getFont().deriveFont(16f));
				cells[row][column].setOpaque(true);
				if (isOutOfBoard(row, column)) {
					cells[row][column].setBorder(BorderFactory.createLineBorder(OUT_OF_BOARD_COLOR));
					cells[row][column].setBackground(OUT_OF_BOARD_COLOR);
					cells[row][column].setText(OUT_OF_BOARD_CELL);
				} else {
					cells[row][column].setBorder(BorderFactory.createLineBorder(LINE_COLOR));
					if (!toSolve[row][column]) {
						cells[row][column].setBackground(INITIAL_CELL_COLOR);
						cells[row][column].setFont(this.cells[row][column].getFont().deriveFont(Font.BOLD));
					}
				}
				add(this.cells[row][column]);
			}
			getContentPane().setLayout(grid);
		}
	}

	/** 
	 * Checks if the given row and column are not inside a Samurai Sudoku board
	 * 
	 * @param row Row to check
	 * @param column Column to check
	 * 
	 * @return If the cell is out of the board
	 */
	private boolean isOutOfBoard(int row, int column) {
		return	rows==21 && columns==21 &&
				((row>=0 && row<=5 && column>=9 && column<=11)
				|| (row>=9 && row<=11 && column>=0 && column<=5)
				|| (row>=15 && row<=20 && column>=9 && column<=11) 
				|| (row>=9 && row<=11 && column>=15 && column<=20));
	}

	/**
	 * Updates the given cell on the screen.
	 * 
	 * @param row Row to update
	 * @param column Column to update
	 * @param number Number to update the row with
	 */
	private void updateCell(int row, int column, int number) {
		if (!isOutOfBoard(row, column)) {
			if (number == UNKNOWN_NUMBER) {
				this.cells[row][column].setBackground(UNKNOWN_CELL_COLOR);
				this.cells[row][column].setText(UNKNOWN_CELL);
			} else if (toSolve[row][column]) {
				this.cells[row][column].setBackground(SOLVED_CELL_COLOR);
				this.cells[row][column].setText(String.valueOf(number));
			} else {
				this.cells[row][column].setText(String.valueOf(number));
			}
		}
	}

	@Override
	public void run() {

	}

}