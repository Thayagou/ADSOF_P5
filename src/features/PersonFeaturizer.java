package features;

import java.util.*;

/**
 * Tipo: Class PersonFeaturizer: Obtiene los features de un objeto Person
 */
public class PersonFeaturizer implements Featurizer<Person>{
	
	/** Constante AGE_ST_TAG. */
	//private static final String NAME_ST_TAG = "name";
	private static final String AGE_ST_TAG = "age";
	
	/** Constante HEIGHT_ST_TAG. */
	private static final String HEIGHT_ST_TAG = "height";
	
	/** Constante WEIGHT_ST_TAG. */
	private static final String WEIGHT_ST_TAG = "weight";
	
	/** Constante GENDER_ST_TAG. */
	private static final String GENDER_ST_TAG = "gender";

	/**
	 * Obtiene la lista de Features.
	 *
	 * @return lista de Features de las personas
	 */
	@Override
	public List<Feature<?>> getFeatureList() {
		List<Feature<?>> list = new ArrayList<Feature<?>>();
		//list.add(new Feature<String>(NAME_ST_TAG));
		list.add(new Feature<Integer>(AGE_ST_TAG));
		list.add(new Feature<Double>(HEIGHT_ST_TAG));
		list.add(new Feature<Double>(WEIGHT_ST_TAG));
		list.add(new Feature<String>(GENDER_ST_TAG));
		return list;
	}
	
 	
	
	/**
	 * Obtiene el valor de elem especificado por la featureTag 
	 *
	 * @param <V> valor genérico comparable
	 * @param elem parámetro del que se extrae el valor
	 * @param featureTag Nombre del valor del que se desea obtener el valor
	 * @return valor del Value
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <V extends Comparable<? super V>> V getValue(Person elem, String featureTag) {

		switch(featureTag) {
		/*case NAME_ST_TAG:
			return (V) elem.getName();*/
		case AGE_ST_TAG:
			return (V) (Integer) elem.getAge();
		case HEIGHT_ST_TAG:
			return (V) (Double) elem.getHeight();
		case WEIGHT_ST_TAG:
			return (V) (Double) elem.getWeight();
		case GENDER_ST_TAG:
			return (V) (String) (elem.isMale() ? "MALE" : "FEMALE");
		default:
			return null;
		}
	}

}
