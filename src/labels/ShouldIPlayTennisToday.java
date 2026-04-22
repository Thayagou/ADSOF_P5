package labels;

import tests.Temperature;
import tests.Weather;
import tests.WeatherCondition;

public class ShouldIPlayTennisToday implements LabelProvider<Weather, Boolean>{

	@Override
	public Boolean getLabel(Weather elem) {
		if (elem.getCond() == WeatherCondition.SUNNY && elem.getTemp() == Temperature.HOT) return true;
		
		return false;
	}
	
}
