import java.awt.Color;
import java.util.ArrayList;
import java.util.Hashtable;

public class Enemy extends Character {

	private int enemyID;

	private String enemyName;

	private String enemyType;

	private Color tilesColor;

	private EnemyCharacteristic enemyCharacteristic;

	private Location startingPoint;


	public Enemy(EnemyCharacteristic enemyCharacteristic, int enemyID, String enemyName, Color tilesColor, String enemyType,
				 Location coordinate) {
		super(enemyID, enemyName, enemyType, coordinate);
		this.enemyCharacteristic = enemyCharacteristic;
		this.enemyID = enemyID;
		this.enemyName = enemyName;
		this.enemyType = enemyType;
		this.tilesColor = tilesColor;
	}

	@Override
	public ArrayList<Location> closestDijkstra(int[][] map, Location coordinate) {
		ArrayList<Location> coordPoints = new ArrayList<Location>();

		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 13; j++) {
				if (this.getCoordinate().getX() == i && this.getCoordinate().getY() == j) {
					this.getCoordinate().setValue(0);
					coordPoints.add(this.getCoordinate());
				} else {
					Location coordPoint = new Location(i, j);
					coordPoint.setValue(Integer.MAX_VALUE);
					coordPoints.add(coordPoint);
				}

			}
		}

		ArrayList<Location> arrList = new ArrayList<Location>();
		Hashtable<Location, Location> table = new Hashtable<Location, Location>();

		arrList.add(this.getCoordinate());

		Location firstLocation = this.getCoordinate();


		int firstCoordPoint = 0;
		for (int i = 1; i < coordPoints.size(); i++) {
			ArrayList<Location> neighborPoints = findingNeighbor(map, coordinate, coordPoints, firstLocation);
			for (Location neigh : neighborPoints)
				if (!arrList.contains(neigh)) {
					int newValue = firstCoordPoint + 1;
					if (newValue < neigh.getValue()) {
						neigh.setValue(newValue);
						table.put(neigh, firstLocation);
					}
				}
			Location newLocation = null;
			int newLocationVal = Integer.MAX_VALUE;
			for (Location l : coordPoints)
				if (!arrList.contains(l)) {
					int piV = l.getValue();
					if (piV < newLocationVal) {
						newLocation = l;
						newLocationVal = piV;
					}
				}
			if (newLocation == null)
				break;
			firstLocation = newLocation;
			firstCoordPoint = newLocationVal;
			arrList.add(firstLocation);

		}

		ArrayList<Location> path = new ArrayList<Location>();
		Location location = null;

		for (Location l : coordPoints) {
			if (l.getX() == coordinate.getX() && l.getY() == coordinate.getY()) {
				location = l;
				break;
			}
		}

		while (location != null) {
			path.add(location);
			location = table.get(location);
		}
		return path;
	}

	private ArrayList<Location> findingNeighbor(int[][] map, Location playerLocation, ArrayList<Location> coordinates,
												Location current) {

		ArrayList<Location> neighborDP = new ArrayList<>();

		if (current.getX() - 1 > 0) {
			for (Location coord : coordinates) {
				if (map[current.getX() - 1][current.getY()] == 1 && coord.getX() == current.getX() - 1
						&& coord.getY() == current.getY()) {
					neighborDP.add(coord);
					break;
				}
			}
		}

		if (current.getX() + 1 < 11) {
			for (Location location : coordinates) {
				if (map[current.getX() + 1][current.getY()] == 1 && location.getX() == current.getX() + 1
						&& location.getY() == current.getY()) {
					neighborDP.add(location);
					break;
				}
			}
		}

		if (current.getY() - 1 > 0) {
			for (Location location : coordinates) {
				if (map[current.getX()][current.getY() - 1] == 1 && location.getX() == current.getX()
						&& location.getY() == current.getY() - 1) {
					neighborDP.add(location);
					break;
				}
			}
		}

		if (current.getY() + 1 < 13) {
			for (Location location : coordinates) {
				if (map[current.getX()][current.getY() + 1] == 1 && location.getX() == current.getX()
						&& location.getY() == current.getY() + 1) {
					neighborDP.add(location);
					break;
				}
			}
		}

		return neighborDP;
	}



	public int getEnemyID() {
		return enemyID;
	}
	public void setEnemyID(int enemyID) {
		this.enemyID = enemyID;
	}

	public String getEnemyType() {
		return enemyType;
	}
	public void setEnemyType(String enemyType) {
		this.enemyType = enemyType;
	}

	public String getEnemyName() {
		return enemyName;
	}
	public void setEnemyName(String enemyName) {
		this.enemyName = enemyName;
	}

	public Color getColor() {
		return tilesColor;
	}
	public void setColor(Color color) {
		this.tilesColor = color;
	}

	public Location getStartingPoint() {
		return startingPoint;
	}
	public void setStartingPoint(Location startingPoint) {
		this.startingPoint = startingPoint;
	}

	public EnemyCharacteristic getEnemyCharacteristic() {
		return enemyCharacteristic;
	}
	public void setEnemyCharacteristic(EnemyCharacteristic enemyCharacteristic) {
		this.enemyCharacteristic = enemyCharacteristic;
	}


}
