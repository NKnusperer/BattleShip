package gui;

import game.GameManager;
import game.GameProperties;
import game.Ship;

import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 * Das Fenster fuer die Spieleinstellungen, von hier wird das Spiel gestartet
 * @author Hendrik Mueller
 *
 */
public class PropertiesWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4171994972923649350L;
	private JLabel x, xCount, y, yCount, ships, shipsCount;
	private JSlider valueX, valueY, valueShips;
	private JButton confirm;
	private GameWindow gameWindow;
	private JPanel content;
	
	public PropertiesWindow(GameWindow gameWindow) {
		setTitle("Einstellungen");
		this.gameWindow = gameWindow;
		initComponents();
		init();
	}
	
	private void initComponents() {
		this.x = new JLabel("Anzahl Spalten (X)");
		this.xCount = new JLabel("10");
		this.valueX = new JSlider(1, 20);
		this.valueX.setValue(10);
		this.valueX.setMinorTickSpacing(1);
		this.valueX.setMajorTickSpacing(0);
		this.valueX.setPaintTicks(true);
		this.valueX.setPaintLabels(true);
		this.valueX.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				xCount.setText(String.valueOf(valueX.getValue()));
			}
		});

		
		this.y = new JLabel("Anzahl Zeilen (Y)");
		this.yCount = new JLabel("10");
		this.valueY = new JSlider(1, 20);
		this.valueY.setValue(10);
		this.valueY.setMinorTickSpacing(1);
		this.valueY.setMajorTickSpacing(0);
		this.valueY.setPaintTicks(true);
		this.valueY.setPaintLabels(true);
		this.valueY.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				yCount.setText(String.valueOf(valueY.getValue()));
			}
		});
		
		this.ships = new JLabel("Anzahl Schiffe");
		this.shipsCount = new JLabel("3");
		this.valueShips = new JSlider(1,6);
		this.valueShips.setValue(3);
		this.valueShips.setMinorTickSpacing(1);
		this.valueShips.setMajorTickSpacing(0);
		this.valueShips.setPaintTicks(true);
		this.valueShips.setPaintLabels(true);
		this.valueShips.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent arg0) {
				shipsCount.setText(String.valueOf(valueShips.getValue()));
			}
		});
		
		this.confirm = new JButton(new ConfirmAction());
		
		this.content = new JPanel();
		this.content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		
	}

	private void init() {
		//X
		JPanel xPanel = new JPanel();
		xPanel.add(x);
		xPanel.add(valueX);
		xPanel.add(xCount);
		
		//Y
		JPanel yPanel = new JPanel();
		yPanel.add(y);
		yPanel.add(valueY);
		yPanel.add(yCount);
		
		//Ship
		JPanel shipPanel = new JPanel();
		shipPanel.add(ships);
		shipPanel.add(valueShips);
		shipPanel.add(shipsCount);
		
		content.add(xPanel);
		content.add(yPanel);
		content.add(shipPanel);
		content.add(confirm);
		content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setResizable(false);
		add(content);
		pack();
		setVisible(true);
	}
	
	class ConfirmAction extends AbstractAction{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1698446596715997651L;

		public ConfirmAction() {
			super("Spiel starten");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Vector<Ship> ships = new Vector<Ship>();
			for(int i = 0; i < valueShips.getValue(); i++){
				Ship s = new Ship("BlackPearl " + String.valueOf(i + 1), GameManager.getInstance().random(1, 6));
				ships.add(s);
			}
			GameProperties gp = new GameProperties(valueY.getValue(), valueX.getValue(), ships);
			GameManager.getInstance().startGame(gameWindow, gp);
			dispose();
		}
		
	}
}
