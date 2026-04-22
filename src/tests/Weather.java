package tests;

public class Weather {
	private WeatherCondition cond;
	private Temperature temp;
	
	public Weather(WeatherCondition cond, Temperature temp) {
		this.cond = cond;
		this.temp = temp;
	}

	public WeatherCondition getCond() {
		return cond;
	}

	public Temperature getTemp() {
		return temp;
	}
}
