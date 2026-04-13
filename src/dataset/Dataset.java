package dataset;

import features.*;
import java.util.*;

/**
 * Tipo: Class Dataset.
 *
 * @param <T> parámetro genérico
 */
public class Dataset<T>{
	
	/** Campo featurizer. */
	private Featurizer<T> featurizer;
	
	/** Features del dataset. */
	private Map<String, Feature<?>> features = new HashMap<String, Feature<?>>();
	
	/** Colección de objetos que forman el dataset. */
	private List<T> collection = new ArrayList<T>();
	
	
	/**
	 * Instancia un nuevo Objeto Dataset.
	 *
	 * @param featurizer parámetro featurizer
	 */
	public Dataset(Featurizer<T> featurizer) {
		this.featurizer = featurizer;
		for(Feature<?> f : featurizer.getFeatureList()) {
			this.features.put(f.getTag(), f);
		}
	}
	
	/**
	 * Añade los valores de un nuevo elemento
	 *
	 * @param elem Elemento a añadir
	 */
	public void add(T elem) {
		for(Feature<?> f : features.values()) {
			f.add(featurizer.getValue(elem, f.getTag()));
		}
		collection.add(elem);
	}
	
	/**
	 * Obtiene el feature con el tag especificado si existe.
	 *
	 * @param tag parámetro tag
	 * @return feature con la tag
	 */
	public Feature<?> feature(String tag) {
		return features.get(tag);
	}
}
