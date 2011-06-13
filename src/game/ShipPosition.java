package game;
/**
 * Position eines Schiffteils im Koordinatensystem
 * @author Hendrik Mueller
 *
 */
public class ShipPosition {
	private int position;
	private Ship ship;
	
	public ShipPosition(Ship ship, int position) {
		this.position = position;
		this.ship = ship;
	}

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * @return the ship
	 */
	public Ship getShip() {
		return ship;
	}

	/**
	 * @param ship the ship to set
	 */
	public void setShip(Ship ship) {
		this.ship = ship;
	}
}
