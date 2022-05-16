
public class Score extends Player {

	
	private Player player;

	private Enemy enemy;

	public Score(Player player, Enemy enemy) {
		this.player = player;
		this.enemy = enemy;
	}


	@Override
	public int showScore() {
		if (enemy.getEnemyType() == "Gargamel") {
			player.setSmurfCoin(player.getSmurfCoin() - player.getPlayerCharacteristic().getGargamelDMG());

		} else if (enemy.getEnemyType() == "Azrael") {
			player.setSmurfCoin(player.getSmurfCoin() - player.getPlayerCharacteristic().getAzraelDMG());
		}
		return player.getSmurfCoin();
	}
	
}
