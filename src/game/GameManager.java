package game;
import gui.GameWindow;
import gui.GridPosition;
import gui.PropertiesWindow;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author Hendrik Mueller
 * Klasse die das komplette Spiel regelt
 * Ist ein Singleton
 */
public class GameManager {
	private static GameManager instance;
	private GameWindow gameWindow;
	private Vector<GridPosition> positions;
	private Vector<GridPosition> clickedPositions; //Positionen auf denen schon geballert wurde
	private Vector<ShipPosition> positionsLeft;
	private Vector<Ship> sunkenShips;
	private ImageIcon hitIcon;
	private ImageIcon missIcon;
	private int trys;
	
	
	
	private GameManager(){
		missIcon = new ImageIcon(getClass().getResource("/miss_15x15.gif"));
		hitIcon = new ImageIcon(getClass().getResource("/hit_15x15.gif"));
	};

    /**
     * Statische Methode „getInstance()“ liefert die einzige Instanz der Klasse zurück.
     * Ist synchronisiert und somit thread-sicher.
     */
    public synchronized static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }
	
    /**
     * zum starten des spiels,
     * Panel wird nach cols, rows aufgebaut
     * Schiffe werden automatisch positioniert
     * @param gameWindow
     * @param cols
     * @param rows
     * @param ships
     */
	public void startGame(final GameWindow gameWindow, GameProperties gameProperties){
		this.gameWindow = gameWindow;
		initGame(gameProperties);
		generateGridSlots(gameProperties); //Slots des Grids erstellen
		JPanel gamePanel = generateGridPanel(gameProperties); //Grid erstellen
		placeShips(gameProperties); //Schiffe plazieren
		generateGridListener(gameWindow, gamePanel, gameProperties); //Listener erstellen
		
		gameWindow.setCenterPanel(gamePanel);
	}
	
	/**
	 * erstellt die Vectoren und setzt die Texte zurueck
	 * @param gameProperties
	 */
	private void initGame(GameProperties gameProperties){
		//Init
		positions = new Vector<GridPosition>(gameProperties.getGridCols() * gameProperties.getGridRows());
		clickedPositions = new Vector<GridPosition>();
		positionsLeft = new Vector<ShipPosition>(); //Positionen, die noch nicht getroffen wurden
		sunkenShips = new Vector<Ship>();
		
		this.trys = 0;
		gameWindow.getValueShots().setText("0"); 
		gameWindow.getValueSunken().setText(String.format("0/%s", gameProperties.getShips().size()));
		gameWindow.getValueInfo().setText("-");
	}
	
	/**
	 * generiert und setzt die KlickListener fuer das GamePanel
	 */
	private void generateGridListener(final GameWindow gameWindow, final JPanel gamePanel, final GameProperties gameProperties){
		gamePanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				try{
					GridPosition clickedComp = (GridPosition) gamePanel.getComponentAt(e.getPoint());
					if(!clickedPositions.contains(clickedComp)){
						//Wenn Komponente noch nicht angeklickt wurde
						increaseTrys(gameWindow);
						clickedPositions.add(clickedComp); //Komponente wurde nun angeklickt, also merken
						if(clickedComp.getShipPosition() != null){
							Ship ship =  clickedComp.getShipPosition().getShip();
							ship.setHits(ship.getHits() + 1);
							float percentLeft = (100f / new Float(ship.getLength())) * new Float(ship.getHits());
							if(((int)percentLeft) != 100){
								gameWindow.getValueInfo().setText(String.format("Treffer! (Schiff: %s, Schaden: %d)", ship.getName(), (int)percentLeft));
								//Tooltip erstellen
								clickedComp.setToolTipText(String.format("Schiff: %s, Laenge: %d, Treffer: %d", ship.getName(), ship.getLength(), ship.getHits()));
							}
							else{
								gameWindow.getValueInfo().setText(String.format("Versenkt! (Schiff: %s, Schaden: %d )", ship.getName(), (int)percentLeft));
								sunkenShips.add(ship);
								gameWindow.getValueSunken().setText(String.format("%s/%s", sunkenShips.size(), gameProperties.getShips().size()));
							}
							positionsLeft.remove(clickedComp.getShipPosition()); //Position ist nicht mehr da
							clickedComp.setShipPosition(null); //Position weg machen, da getroffen
							clickedComp.setIcon(hitIcon);
							if(sunkenShips.size() == gameProperties.getShips().size()){
								//Spiel gewonnen
								JOptionPane.showMessageDialog(gameWindow, "Alle Schiffe versenkt");
								gamePanel.setEnabled(false);
								gamePanel.removeMouseListener(gamePanel.getMouseListeners()[0]);
							}
						}
						else{
							//Daneben
							clickedComp.setIcon(missIcon);
						}
					}
				}
				catch (Exception ex) {
					// TODO: handle exception
				}
			}
		});
	}
	
	private void increaseTrys(GameWindow gameWindow){
		trys++;
		gameWindow.getValueShots().setText(String.valueOf(trys));
	}
	
	/**
	 * Generiert die Slot Objekte des Rasters
	 * @param cols
	 * @param rows
	 */
	private void generateGridSlots(GameProperties gameProperties){
		for(int i = 0 ; i < gameProperties.getGridCols() * gameProperties.getGridRows(); i++){
			GridPosition position = new GridPosition();
			positions.add(position);
		}
	}
	
	/**
	 * Generiert das sichtbare Raster
	 * @return
	 */
	private JPanel generateGridPanel(GameProperties gameProperties){
		//Panel generieren
		JPanel gamePanel = new JPanel();
		gamePanel.setLayout(new GridLayout(gameProperties.getGridRows(), gameProperties.getGridCols()));
		for(int i = 0 ; i < gameProperties.getGridCols() * gameProperties.getGridRows(); i++){
			GridPosition position = positions.get(i);
//			position.setText(String.valueOf(i));
			gamePanel.add(position);
		}
		return gamePanel;
	}
	
	/**
	 * plaziert die Schiffe vertikal oder horizontal
	 * nach Zufall
	 * @param ships
	 */
	private void placeShips(GameProperties gameProperties){
		
		//Positionen der Schiffe festlegen
		Vector<Ship> couldNotPlace = new Vector<Ship>(); //Falls keine Positionen gefunden werden koennen
		for(Ship s : gameProperties.getShips()){
			boolean vertical = random(0, 1) > 0;
			if(vertical){
				List<GridPosition> freeVerticalPositions;
				try {
					freeVerticalPositions = findRandomFreeVerticalPositions(s.getLength(), gameProperties.getGridCols(), gameProperties.getGridRows());
					for(int i = 0; i < s.getLength(); i++){
						GridPosition gp = freeVerticalPositions.get(i);
						ShipPosition sp = new ShipPosition(s, 2);
						gp.setShipPosition(sp);
						positionsLeft.add(sp);
					}
//					for(GridPosition freePos : freeVerticalPositions){
////						System.out.println("Found VerticalPosition: " + freePos.getText());
//					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					couldNotPlace.add(s);
					e.printStackTrace();
				}

			}
			else{
				//Schiff horizontal positionieren
				List<GridPosition> freeHorizontalPositions;
				try {
					freeHorizontalPositions = findRandomFreeHorizontalPositions(s.getLength(), gameProperties.getGridCols(), gameProperties.getGridRows());
					for(int i = 0; i < s.getLength(); i++){
						GridPosition gp = freeHorizontalPositions.get(i);
						ShipPosition sp = new ShipPosition(s, 2);
						gp.setShipPosition(sp);
						positionsLeft.add(sp);
					}
//					for(GridPosition freePos : freeHorizontalPositions){
//						System.out.println("Found HorizontalPositions: " + freePos.getText());
//					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					couldNotPlace.add(s);
				}

			}
		}
		if(couldNotPlace.size() > 0){
			StringBuilder error = new StringBuilder("Folgende Schiffe konnten nicht positioniert werden anzahl (" + couldNotPlace.size() +"): ");
			for(Ship s : couldNotPlace){
				error.append("\n" + s.getName());
			}
			error.append("\nStarten Sie das Spiel erneut");
			JOptionPane.showMessageDialog(gameWindow, error.toString());
			initGame(gameProperties);
			new PropertiesWindow(gameWindow);
		}
		
	}
	
	/**
	 * Sucht fuer das Schiff passende freie vertikalte Positionen raus
	 * @param shipSize
	 * @param cols
	 * @param rows
	 * @return
	 * @throws Exception 
	 */
	private List<GridPosition> findRandomFreeVerticalPositions(int shipSize, final int cols, final int rows) throws Exception{
		boolean found = false;
		List<GridPosition> foundPositions = new Vector<GridPosition>();
		
		int trys = 0;
		while(!found){
			if(trys == rows * cols){
				//Maxmiale Versuchsanzahl
				throw new Exception("Could not set Positions");
			}
			foundPositions.clear();
			/*
			 * Zeile aussuchen, die Leange des Schiffs abziehen
			 * da das schiff sonst nicht reinpassen wuerde
			 */
			int row = random(0, rows - shipSize); 

			int col = random(0, cols); //Spalte aussuchen
			//Pruefen, ob noch kein Schiff an Positionen
			boolean partOk = true;
			int startPos = row * cols + col;
			//Horizontaler Startposition darf max so gross sein, dass noch das Schiff rein passt
			if(startPos <= (rows*cols) - shipSize){
//				System.out.println("Vertical startpos: " + startPos);
				for(int i = 0; i < shipSize; i++){
					GridPosition position = positions.get(startPos + (i * cols));
					if(!position.isPositionFree()){
						partOk = false;
					}
					else{
						foundPositions.add(position);
					}
				}
				if(partOk){
					found = true;
				}
			}
			trys++;
		}
		return foundPositions;
	}
	
	/**
	 * Sucht fuer das Schiff passende freie horizontale Positionen raus
	 * @param shipSize
	 * @param cols
	 * @param rows
	 * @return
	 * @throws Exception 
	 */
	private List<GridPosition> findRandomFreeHorizontalPositions(int shipSize,  final int cols, final int rows) throws Exception{
		boolean found = false;
		List<GridPosition> foundPositions = new Vector<GridPosition>();
		
		int trys = 0;
		while(!found){
			if(trys == rows * cols){
				//Maxmiale Versuchsanzahl
				throw new Exception("Could not set Positions");
			}
			foundPositions.clear();
			int row = random(0, rows); //Zeile aussuchen
			/*
			 * Spalte aussuchen, die Leange des Schiffs abziehen
			 * da das schiff sonst nicht reinpassen wuerde
			 */
			int col = random(0, cols - shipSize); 
			//Pruefen, ob noch kein Schiff an Positionen
			boolean partOk = true;
			int startPos = row * cols + col;
			//Horizontaler Startposition darf max so gross sein, dass noch das Schiff rein passt
			if(startPos <= (rows*cols) - shipSize){
				for(int i = 0; i < shipSize; i++){
					GridPosition position = positions.get(startPos + i);
					if(!position.isPositionFree()){
						partOk = false;
					}
					else{
						foundPositions.add(position);
					}
				}
				if(partOk){
					found = true;
				}
			}
			trys++;
		}
		return foundPositions;
	}
	
	public int random(int min, int max){
		return (int) (Math.floor(Math.random() * (max - min + 1)) + min);
	}
}
