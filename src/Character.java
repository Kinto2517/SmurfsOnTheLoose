import java.util.ArrayList;

public abstract class Character {
	
	private int ID;
	
	private String name;
	
	private String charType;
	
	private Location coordinate;
	
	public Character() {}
	
	public Character(int ID, String name, String charType, Location coordinate) {
		super();
		this.ID = ID;
		this.name = name;
		this.charType = charType;
		this.coordinate = coordinate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCharType() {
		return charType;
	}

	public void setCharType(String charType) {
		this.charType = charType;
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public Location getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Location coordinate) {
		this.coordinate = coordinate;
	}



	public abstract ArrayList<Location> closestDijkstra(int [][] map, Location coordinate);
}
