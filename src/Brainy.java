
public class Brainy extends Player {
	
	private PlayerCharacteristic playerCharacteristic;

	public Brainy() {
		super();
	}
	
	public Brainy(PlayerCharacteristic playerCharacteristic, int playerID, String playerName, int smurfCoin, Location coordinate) {
		super(playerCharacteristic, playerID, playerName, "Brainy", smurfCoin, coordinate);
		this.playerCharacteristic = playerCharacteristic;
	}

}
