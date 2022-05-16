import java.awt.Color;

public class Azrael extends Enemy {

	private EnemyCharacteristic enemyCharacteristic;

	public Azrael(EnemyCharacteristic enemyCharacteristic, int enemyID, String enemyName, Color rgb, Location coordinate) {
		super(enemyCharacteristic, enemyID, enemyName,new Color(0xB37730),"Azrael", coordinate);
		this.enemyCharacteristic = enemyCharacteristic;
	}
}
