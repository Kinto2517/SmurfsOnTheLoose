import java.awt.Color;

public class Gargamel extends Enemy {
	
	private EnemyCharacteristic enemyCharacteristic;

	public Gargamel(EnemyCharacteristic enemyCharacteristic, int enemyID, String enemyName, Color rgb, Location coordinate) {
		super(enemyCharacteristic, enemyID, enemyName, new Color(0xCE2443), "Gargamel", coordinate);
		this.enemyCharacteristic = enemyCharacteristic;
	}


}
