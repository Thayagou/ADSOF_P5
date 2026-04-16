package features;

import java.util.*;

/**
 * Tipo: Class PersonFeaturizer.
 */
public class PersonFeaturizer implements Featurizer<Person>{
	
	private static final String NAME_ST_TAG = "name";
	private static final String AGE_ST_TAG = "age";
	private static final String HEIGHT_ST_TAG = "height";
	private static final String WEIGHT_ST_TAG = "weight";

	/**
	 * Obtiene la lista de Features.
	 *
	 * @return lista de Features de las personas
	 */
	@Override
	public List<Feature<?>> getFeatureList() {
		List<Feature<?>> list = new ArrayList<Feature<?>>();
		list.add(new Feature<String>(NAME_ST_TAG));
		list.add(new Feature<Integer>(AGE_ST_TAG));
		list.add(new Feature<Double>(HEIGHT_ST_TAG));
		list.add(new Feature<Double>(WEIGHT_ST_TAG));
		return list;
	}
	
 	/**
	 * Obtiene el valor de un feature de una persona
	 * 
	 * @return Valor del feature especificado
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <V extends Comparable<V>> V getValue(Person elem, String featureTag) {

		switch(featureTag) {
		case NAME_ST_TAG:
			return (V) elem.getName();
		case AGE_ST_TAG:
			return (V) (Integer) elem.getAge();
		case HEIGHT_ST_TAG:
			return (V) (Double) elem.getHeight();
		case WEIGHT_ST_TAG:
			return (V) (Double) elem.getWeight();
		default:
			return null;
		}
	}

}
