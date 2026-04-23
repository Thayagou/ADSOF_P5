package labels;

import tests.Temperature;
import tests.Weather;
import tests.WeatherCondition;

/**
 * Tipo: Class ShouldIPlayTennisToday: Asigna labels de tipo Boolean a objetos de tipo Weather
 * 
 * @author Tiago Oselka y Juan Ibáñez
 */
public class ShouldIPlayTennisToday implements LabelProvider<Weather, Boolean>{

	/**
	 * Obtiene una instancia de ShouldIPlayTennisToday
	 */
	public ShouldIPlayTennisToday() {}
	
	/**
	 * Obtiene la Label asignada a elem
	 *
	 * @param elem Objeto Weather a asignar
	 * @return valor de la Label
	 */
	@Override
	public Boolean getLabel(Weather elem) {
		if (elem.getCond() == WeatherCondition.SUNNY && elem.getTemp() == Temperature.HOT) return true;
		
		return false;
	}
	
}
