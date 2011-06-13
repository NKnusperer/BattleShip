package gui;

import game.ShipPosition;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 * Grafische darstellung einer Gridposition
 * @author Hendrik Mueller
 *
 */
public class GridPosition extends JLabel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8344304631826952383L;
	private ShipPosition shipPosition;
	
	public GridPosition() {
		init();
	}
	
	public GridPosition(ShipPosition shipPosition) {
		this.shipPosition = shipPosition;
		init();
	}
	
	public boolean isPositionFree(){
		return shipPosition == null;
	}

	private void init() {
		setVerticalTextPosition(JLabel.CENTER);
		setHorizontalTextPosition(JLabel.CENTER);
		setVerticalAlignment(JLabel.CENTER);
		setHorizontalAlignment(JLabel.CENTER);
		setBorder(BorderFactory.createLineBorder(Color.black));
	}

	/**
	 * @return the shipPosition
	 */
	public ShipPosition getShipPosition() {
		return shipPosition;
	}

	/**
	 * @param shipPosition the shipPosition to set
	 */
	public void setShipPosition(ShipPosition shipPosition) {
		this.shipPosition = shipPosition;
	}
}
