package tests;

/**
 * Tipo: Class Weather.
 * @author Tiago Oselka y Juan Ibáñez
 */
public class Weather {
	
	/** Campo cond. */
	private WeatherCondition cond;
	
	/** Campo temp. */
	private Temperature temp;
	
	/**
	 * Instancia un nuevo Objeto Weather.
	 *
	 * @param cond parámetro cond
	 * @param temp parámetro temp
	 */
	public Weather(WeatherCondition cond, Temperature temp) {
		this.cond = cond;
		this.temp = temp;
	}

	/**
	 * Obtiene Cond.
	 *
	 * @return valor de Cond
	 */
	public WeatherCondition getCond() {
		return cond;
	}

	/**
	 * Obtiene Temp.
	 *
	 * @return valor de Temp
	 */
	public Temperature getTemp() {
		return temp;
	}

	/**
	 * toString.
	 *
	 * @return valor de tipo String
	 */
	@Override
	public String toString() {
		return "Weather [cond=" + cond + ", temp=" + temp + "]";
	}
	
	
}
