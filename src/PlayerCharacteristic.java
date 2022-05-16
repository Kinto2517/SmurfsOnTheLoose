
public class PlayerCharacteristic {
	
	private int step;

	private int azraelDMG;

	private int gargamelDMG;

	public PlayerCharacteristic() {
		
	}

	public PlayerCharacteristic(int step, int azraelDMG, int gargamelDMG) {
		super();
		this.step = step;
		this.azraelDMG = azraelDMG;
		this.gargamelDMG = gargamelDMG;
	}

	public int getStep() {
		return step;
	}

	public int getAzraelDMG() {
		return azraelDMG;
	}

	public int getGargamelDMG() {
		return gargamelDMG;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public void setAzraelDMG(int azraelDMG) {
		this.azraelDMG = azraelDMG;
	}

	public void setGargamelDMG(int gargamelDMG) {
		this.gargamelDMG = gargamelDMG;
	}
	
	
	
	
}
