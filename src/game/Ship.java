package game;
/**
 * Stellt ein Schiff dar
 * @author Hendrik Mueller
 *
 */
public class Ship {
	private String name;
	private int length;
	private int hits;
	
	/**
	 * @param name
	 * @param length
	 */
	public Ship(String name, int length) {
		super();
		this.name = name;
		this.length = length;
	}
	
	public boolean isDestroyed(){
		return hits == length;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
}
