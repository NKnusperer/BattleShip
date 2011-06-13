package game;
import gui.GameWindow;

import javax.swing.ToolTipManager;


/**
 * 
 * @author Hendrik Mueller
 * Einstiegspunkt
 */
public class BattleShip {
	
	public BattleShip() {
		init();
	}
	
	private void init() {
		new GameWindow();
		ToolTipManager.sharedInstance().setInitialDelay(10);
	}

	public static void main(String[] args){
		new BattleShip();
	}
}
