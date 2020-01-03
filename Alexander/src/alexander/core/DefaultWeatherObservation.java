package alexander.core;

public class DefaultWeatherObservation implements WeatherObservation {
	
	private double value;
	
	public DefaultWeatherObservation(double value) {
		set(value);
	}

	@Override
	public int year() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int month() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int day() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int hour() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int minute() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int hourOfYear() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int minuteOfYear() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getDouble() {
		return value;
	}

	@Override
	public int getInteger() {
		return (int)value;
	}

	@Override
	public void set(double value) {
		this.value = value;
		
	}

	@Override
	public void set(int value) {
		this.value = value;
		
	}

	@Override
	public boolean isMissing() {
		// TODO Auto-generated method stub
		return false;
	}

}
