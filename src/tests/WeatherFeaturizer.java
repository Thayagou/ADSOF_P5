package tests;

import java.util.*;

import features.Feature;
import features.Featurizer;

public class WeatherFeaturizer implements Featurizer<Weather> {
	private static final String COND_ST_TAG = "condition";
	private static final String TEMP_ST_TAG = "temperature";
	
	@Override
	public List<Feature<?>> getFeatureList() {
		List<Feature<?>> list = new ArrayList<>();
		list.add(new Feature<WeatherCondition>(COND_ST_TAG));
		list.add(new Feature<Temperature>(TEMP_ST_TAG));
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V extends Comparable<V>> V getValue(Weather elem, String featureTag) {
		switch(featureTag) {
		/*case NAME_ST_TAG:
			return (V) elem.getName();*/
		case COND_ST_TAG:
			return (V) (WeatherCondition) elem.getCond();
		case TEMP_ST_TAG:
			return (V) (Temperature) elem.getTemp();
		default:
			return null;
		}
	}

}
