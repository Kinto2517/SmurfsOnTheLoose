import java.util.ArrayList;

public class Player extends Character {

	private int playerID;

	private String playerName;

	private String playerType;

	private int smurfCoin;

	private PlayerCharacteristic playerCharacteristic;

	public Player() {
		super();
	}


	public Player(PlayerCharacteristic playerCharacteristic, int playerID,
				  String playerName, String playerType, int smurfCoin, Location coordinate) {
		super(playerID, playerName, playerType, coordinate);
		this.playerCharacteristic = playerCharacteristic;
		this.playerID = playerID;
		this.playerName = playerName;
		this.playerType = playerType;
		this.smurfCoin = smurfCoin;
	}


	public int showScore() {
		return smurfCoin;
	}

	@Override
	public ArrayList<Location> closestDijkstra(int[][] map, Location coordinate) {return null;}


	public int getPlayerID() {
		return playerID;
	}
	public String getPlayerName() {
		return playerName;
	}
	public String getPlayerType() {
		return playerType;
	}
	public int getSmurfCoin() {
		return smurfCoin;
	}
	public PlayerCharacteristic getPlayerCharacteristic() {
		return playerCharacteristic;
	}


	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public void setPlayerType(String playerType) {
		this.playerType = playerType;
	}
	public void setSmurfCoin(int smurfCoin) {
		this.smurfCoin = smurfCoin;
	}
	public void setPlayerCharacteristic(PlayerCharacteristic playerCharacteristic) {
		this.playerCharacteristic = playerCharacteristic;
	}

}


