
public class EnemyCharacteristic {


	private int step;

	private boolean isJumpy;

	public EnemyCharacteristic(int step, boolean isJumpy) {
		super();
		this.step = step;
		this.isJumpy = isJumpy;
	}


	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public void setJumpy(boolean jumpy) {
		this.isJumpy = jumpy;
	}
	public boolean isJumpy() {
		return isJumpy;
	}
	
}
