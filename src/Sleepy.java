
public class Sleepy extends Player {
	
	private PlayerCharacteristic playerCharacteristic;

	public Sleepy() {
		super();
	}
	
	public Sleepy(PlayerCharacteristic playerCharacteristic, int playerID,
				  String playerName, int smurfCoin, Location coordinate) {
		super(playerCharacteristic, playerID, playerName, "Sleepy", smurfCoin, coordinate);
		this.playerCharacteristic = playerCharacteristic;
	}
}
