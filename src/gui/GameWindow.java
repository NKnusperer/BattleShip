package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 * Fenster fuer das Spiel
 * @author Hendrik Mueller
 *
 */
public class GameWindow extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5426526632779286920L;
	private JMenuBar menuBar;
	private JLabel shots;
	private JLabel valueShots;
	private JLabel info;
	private JLabel valueInfo;
	private JLabel sunken;
	private JLabel valueSunken;
	private JPanel content;
	
	public GameWindow() {
		init();
	}

	private void init() {
		setIconImage(new ImageIcon(getClass().getResource("/miss_25x25.gif")).getImage());
		setTitle("Schiffeversenken");
		setSize(600, 600);
		setMinimumSize(new Dimension(400, 400));
		generateMenu();
		setLayout(new BorderLayout());
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		
		JPanel shotPanel = new JPanel();
		shots = new JLabel("Versuche:");
		valueShots = new JLabel("0");
		shotPanel.add(shots);
		shotPanel.add(valueShots);
		
		JPanel infoPanel = new JPanel();
		info = new JLabel("Info:");
		valueInfo = new JLabel("-");
		infoPanel.add(info);
		infoPanel.add(valueInfo);
		
		JPanel sunkenPanel = new JPanel();
		sunken = new JLabel("Versenkt:");
		valueSunken = new JLabel("0/0");
		sunkenPanel.add(sunken);
		sunkenPanel.add(valueSunken);
		
		JPanel shotInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,10,0));
		shotInfoPanel.add(shotPanel);
		shotInfoPanel.add(infoPanel);
		
		topPanel.add(shotInfoPanel);
		topPanel.add(sunkenPanel);
		
		add(topPanel, BorderLayout.NORTH);
		setVisible(true);
	}
	
	public void setCenterPanel(JPanel content){
		if(this.content != null){
			remove(this.content);
		}
		this.content = content;
		content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		add(content, BorderLayout.CENTER);
		content.updateUI();
	}

	private void generateMenu() {
		this.menuBar = new JMenuBar();
		
		//Game
		JMenu gameItem = new JMenu("Spiel");
		//New Game
		JMenuItem newGame = new JMenuItem(new NewGameAction());
		gameItem.add(newGame);
		
		//End
		JMenuItem endGame = new JMenuItem(new EndGameAction());
		gameItem.add(endGame);
		
		this.menuBar.add(gameItem);
		
		setJMenuBar(menuBar);
	}
	
	private class NewGameAction extends AbstractAction{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public NewGameAction() {
			super("Neues Spiel");
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			new PropertiesWindow(GameWindow.this);
		}
		
	}
	
	private class EndGameAction extends AbstractAction{
		/**
		 * 
		 */
		private static final long serialVersionUID = -4715493648492996806L;

		public EndGameAction() {
			super("Beenden");
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}
	}

	/**
	 * @return the valueShots
	 */
	public JLabel getValueShots() {
		return valueShots;
	}

	/**
	 * @param valueShots the valueShots to set
	 */
	public void setValueShots(JLabel valueShots) {
		this.valueShots = valueShots;
	}

	/**
	 * @return the valueInfo
	 */
	public JLabel getValueInfo() {
		return valueInfo;
	}

	/**
	 * @param valueInfo the valueInfo to set
	 */
	public void setValueInfo(JLabel valueInfo) {
		this.valueInfo = valueInfo;
	}

	/**
	 * @return the valueSunken
	 */
	public JLabel getValueSunken() {
		return valueSunken;
	}

	/**
	 * @param valueSunken the valueSunken to set
	 */
	public void setValueSunken(JLabel valueSunken) {
		this.valueSunken = valueSunken;
	}
}
