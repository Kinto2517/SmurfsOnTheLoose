
public class Objects {
	
	private String objectName;
	
	private int objectScore;
	
    private int maxTime;
	
	private int timeToAppear;
	
	private boolean onOff;
	
	private Location coordinate;

	public Objects() {
		
	}

	public Objects(String objectName, int objectScore, int maxTime, int timeToAppear, boolean onOff, Location coordinate) {
		this.objectName = objectName;
		this.objectScore = objectScore;
		this.maxTime = maxTime;
		this.timeToAppear = timeToAppear;
		this.onOff = onOff;
		this.coordinate = coordinate;
	}

	public String getObjectName() {
		return objectName;
	}

	public int getObjectScore() {
		return objectScore;
	}

	public int getMaxTime() {
		return maxTime;
	}

	public int getTimeToAppear() {
		return timeToAppear;
	}

	public Location getCoordinate() {
		return coordinate;
	}



	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public void setObjectScore(int objectScore) {
		this.objectScore = objectScore;
	}

	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}

	public void setTimeToAppear(int timeToAppear) {
		this.timeToAppear = timeToAppear;
	}

	public void setOnOff(boolean onOff) {
		this.onOff = onOff;
	}

	public void setCoordinate(Location coordinate) {
		this.coordinate = coordinate;
	}


	public boolean isOnOff() {
		return onOff;
	}
	
}
