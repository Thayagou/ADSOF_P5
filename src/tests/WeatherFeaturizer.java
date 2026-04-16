package tests;

import java.util.*;

import features.Feature;
import features.Featurizer;

public class WeatherFeaturizer implements Featurizer<Weather> {

	@Override
	public List<Feature<?>> getFeatureList() {
		List<Feature<?>> features = new ArrayList<>();
		return null;
	}

	@Override
	public <V extends Comparable<V>> V getValue(Weather elem, String featureTag) {
		// TODO Auto-generated method stub
		return null;
	}

}
