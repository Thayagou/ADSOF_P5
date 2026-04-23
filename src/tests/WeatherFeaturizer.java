package tests;

import java.util.*;

import features.Feature;
import features.Featurizer;

/**
 * Tipo: Class WeatherFeaturizer.
 *  @author Tiago Oselka y Juan Ibáñez
 */
public class WeatherFeaturizer implements Featurizer<Weather> {
	
	/** Constante COND_ST_TAG. */
	private static final String COND_ST_TAG = "condition";
	
	/** Constante TEMP_ST_TAG. */
	private static final String TEMP_ST_TAG = "temperature";
	
	/**
	 * Obtiene FeatureList.
	 *
	 * @return valor de FeatureList
	 */
	@Override
	public List<Feature<?>> getFeatureList() {
		List<Feature<?>> list = new ArrayList<>();
		list.add(new Feature<WeatherCondition>(COND_ST_TAG));
		list.add(new Feature<Temperature>(TEMP_ST_TAG));
		return list;
	}

	/**
	 * Obtiene un dato a partir del elemento y el nombre del dato
	 *
	 * @param <V> valor genérico comparable
	 * @param elem Elemento del que se extraen los valores
	 * @param featureTag Nombre del feature
	 * @return valor obtenido
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <V extends Comparable<? super V>> V getValue(Weather elem, String featureTag) {
		switch(featureTag) {
		case COND_ST_TAG:
			return (V) (WeatherCondition) elem.getCond();
		case TEMP_ST_TAG:
			return (V) (Temperature) elem.getTemp();
		default:
			return null;
		}
	}

}
