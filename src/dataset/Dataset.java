package dataset;

import features.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Tipo: Class Dataset.
 *
 * @param <T> parámetro genérico
 */
public class Dataset<T>{
	
	/** Campo featurizer. */
	protected Featurizer<T> featurizer;
	
	/** Features del dataset. */
	protected LinkedHashMap<String, Feature<?>> features = new LinkedHashMap<String, Feature<?>>();
	
	/** Colección de objetos que forman el dataset. */
	protected List<T> collection = new ArrayList<T>();
	
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
	 * Añade todos los valores de un array de nuevos elementos
	 *
	 * @param elems Elementos a añadir
	 */
	public void addAll(T...elems) {
		for (T elem: elems) add(elem);
	}
	
	public void removeDuplicates() {
		List<T> noDuplicates = collection.stream().distinct().collect(Collectors.toList());
		features = new LinkedHashMap<String, Feature<?>>();
		
		for(Feature<?> f : featurizer.getFeatureList()) {
			this.features.put(f.getTag(), f);
		}
		
		
		
		
		
	}
	
	private void removeLine(int line) {
		for (Feature<?> col: features.values()) {
			col.remove(line);
		}
	}
	
	private boolean equalLine(int i, int j) {
		for (Feature<?> col: features.values()) {
			if (compFeature(col, i, j) != 0) return false;
		}
		
		return true;
	}
	
	private <F extends Comparable<F>> int compFeature(Feature<F> col, int i, int j) {
		return col.get(i).compareTo(col.get(j));
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
	
	/**
	 * Obtiene los valores almacenados en el DataSet
	 * 
	 * @return lista de valores
	 */
	public List<T> getValues() {
		return Collections.unmodifiableList(collection);
	}
	
	public List<Feature<?>> getTable() {
		return new ArrayList<>(features.values());
	}
}
