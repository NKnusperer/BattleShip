package game;

import java.util.Vector;

/**
 * beinhaltet die Einstellungen des Spiels
 * @author Hendrik Mueller
 *
 */
public class GameProperties {
	private int gridRows;
	private int gridCols;
	private Vector<Ship> ships;
	
	public GameProperties(){
		
	}
	
	/**
	 * @param gridRows
	 * @param gridCols
	 * @param ships
	 */
	public GameProperties(int gridRows, int gridCols, Vector<Ship> ships) {
		super();
		this.gridRows = gridRows;
		this.gridCols = gridCols;
		this.ships = ships;
	}
	
	/**
	 * @return the gridRows
	 */
	public int getGridRows() {
		return gridRows;
	}
	/**
	 * @param gridRows the gridRows to set
	 */
	public void setGridRows(int gridRows) {
		this.gridRows = gridRows;
	}
	/**
	 * @return the gridCols
	 */
	public int getGridCols() {
		return gridCols;
	}
	/**
	 * @param gridCols the gridCols to set
	 */
	public void setGridCols(int gridCols) {
		this.gridCols = gridCols;
	}
	/**
	 * @return the ships
	 */
	public Vector<Ship> getShips() {
		return ships;
	}
	/**
	 * @param ships the ships to set
	 */
	public void setShips(Vector<Ship> ships) {
		this.ships = ships;
	}
}
