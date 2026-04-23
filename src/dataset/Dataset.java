package dataset;

import features.*;
import java.util.*;

import exceptions.InexistantFeatureException;

// TODO: Auto-generated Javadoc
/**
 * Tipo: Class Dataset.
 *
 * @param <T> parámetro genérico
 */
public class Dataset<T> {

	/** Campo featurizer: obtiene los Features de los objetos que almacenamos para construir nuestra tabla*/
	protected Featurizer<? super T> featurizer;

	/** Features del dataset: mapa que representa las columnas de la tabla, siendo el String el tag y Feature una lista con los valores*/
	protected LinkedHashMap<String, Feature<?>> features = new LinkedHashMap<String, Feature<?>>();

	/** Colección de objetos que forman el dataset. */
	protected List<T> collection = new ArrayList<T>();

	/**
	 * Instancia un nuevo Objeto Dataset.
	 *
	 * @param featurizer parámetro featurizer que forma la tabla
	 */
	public Dataset(Featurizer<? super T> featurizer) {
		this.featurizer = featurizer;
		for (Feature<?> f : featurizer.getFeatureList()) {
			this.features.put(f.getTag(), f);
		}
	}

	/**
	 * Instancia un nuevo Dataset a partir de otro, creando una copia exacta de este último.
	 *
	 * @param source Dataset del cual se realiza la copia
	 */
	public Dataset(Dataset<T> source) {
		this.featurizer = source.featurizer;
		this.collection = new ArrayList<>(source.collection);

		for (String key : source.features.keySet()) {
			Feature<?> orig = source.features.get(key);
			Feature<?> f = new Feature<>(orig);

			this.features.put(key, f);
		}
	}
	
	/**
	 * Divide un Dataset respecto a un feature devolviendo los subdatasets obtenidos
	 * @param <K> Parámetro genérico que representa un objeto almacenado en feature
	 * @param tag Tag del feature por el que deseamos partir del dataset
	 * @return un mapa con pares (clave, Subdataset de los elementos con esa clave)
	 * @throws InexistantFeatureException Se lanza en caso de que el Feature a partir del cual se desea dividir el dataset no exista
	 */
	@SuppressWarnings("unchecked")
	public <K extends Comparable<? super K>> Map<K, Dataset<T>> splitDataset(String tag) throws InexistantFeatureException {
		Feature<K> splitter = (Feature<K>) this.removeFeature(tag);
		
		Map<K, List<Integer>> distribution = splitter.distributionPositions();
		Map<K, Dataset<T>> dataSplit = new TreeMap<>();
		
		for (K value: distribution.keySet()) {
			dataSplit.put(value, this.getSubset(distribution.get(value)));
		}
		
		return dataSplit;
	}

	/**
	 * Devuelve un subset del dataset a partir de una lista de indices.
	 *
	 * @param indexes Lista con los indices de los elementos que se incluiran
	 * @return Nuevo dataset con los elementos que se indicaron
	 */
	public Dataset<T> getSubset(List<Integer> indexes) {
		Dataset<T> dataset = new Dataset<T>(featurizer);

		dataset.copyFeaturesFrom(this);
		
		for (Integer i : indexes) {
			dataset.add(collection.get(i));
		}

		return dataset;
	}
	
	/**
	 * Elimina los features actuales y reemplaza las columnas por unas de un otro dataset
	 *
	 * @param source Dataset del cual se van a copiar los datos
	 */
	protected void copyFeaturesFrom(Dataset<T> source) {
		features.clear();
		
		for (String feat: source.features.keySet()) {
			features.put(feat, new Feature<>(feat));
		}
	}

	/**
	 * Añade los valores de un nuevo elemento al dataset
	 *
	 * @param elem Elemento a añadir
	 */
	public void add(T elem) {
		for (Feature<?> f : features.values()) {
			f.add(featurizer.getValue(elem, f.getTag()));
		}
		collection.add(elem);
	}

	/**
	 * Añade todos los valores de un array de nuevos elementos.
	 *
	 * @param elems Elementos a añadir
	 */
	@SuppressWarnings("unchecked")
	public void addAll(T... elems) {
		for (T elem : elems)
			add(elem);
	}
	
	/**
	 * removeDuplicates.
	 */
	public void removeDuplicates() {
		int size;
		size = collection.size();
		
		for (int i = 0; i < size; i++) {
			for (int j = i + 1; j < size; j++) {
				if (equalLine(i, j)) {
					removeLine(j);
					size--;
					j--;
				}
			}
		}
	}

	/**
	 * removeLine.
	 *
	 * @param line parámetro line
	 */
	protected void removeLine(int line) {
		for (Feature<?> col : features.values()) {
			col.remove(line);
		}
		
		collection.remove(line);
	}

	/**
	 * equalLine.
	 *
	 * @param i parámetro i
	 * @param j parámetro j
	 * @return true si la operación fue correcta, falso en caso contrario
	 */
	private boolean equalLine(int i, int j) {
		for (Feature<?> col : features.values()) {
			if (compFeature(col, i, j) != 0)
				return false;
		}

		return true;
	}

	/**
	 * Compara dos valores dentro de un array de Feature
	 *
	 * @param <F> parámetro genérico que representa el tipo del Feature
	 * @param col Columna del dataset representada por un Feature
	 * @param i Posición i-ésima del Feature
	 * @param j Posición j-ésima del Feature
	 * @return valor de la comparación entre los valores i y j del Feature
	 */
	private <K extends Comparable<? super K>> int compFeature(Feature<K> col, int i, int j) {
		return col.get(i).compareTo(col.get(j));
	}

	/**
	 * Obtiene el feature con el tag especificado si existe.
	 *
	 * @param <V> valor genérico comparable
	 * @param tag parámetro tag
	 * @return feature con la tag
	 * @throws InexistantFeatureException se lanza en caso de que el feature buscado no exista
	 */
	@SuppressWarnings("unchecked")
	public <K extends Comparable<? super K>> Feature<K> feature(String tag) throws InexistantFeatureException {
		Feature<K> feature = (Feature<K>) features.get(tag);
		if (feature == null) throw new InexistantFeatureException(tag);
		
		return feature;
	}

	/**
	 * Elimina un feature del dataset a partir de su tag y lo devuelve
	 *
	 * @param tag parámetro tag
	 * @return Feature eliminado del Dataset
	 * @throws InexistantFeatureException Se lanza en caso de que el feature a eliminar no exista
	 */
	@SuppressWarnings("unchecked")
	public <K extends Comparable<? super K>> Feature<K> removeFeature(String tag) throws InexistantFeatureException {
		Feature<K> feature = (Feature<K>) features.remove(tag);
		if (feature == null) throw new InexistantFeatureException(tag);
		
		return feature;
		 
	}

	/**
	 * Getter del Featurizer
	 *
	 * @return el featurizer
	 */
	public Featurizer<? super T> getFeaturizer() {
		return featurizer;
	}

	/**
	 * Obtiene los valores almacenados en el DataSet.
	 *
	 * @return lista de valores
	 */
	public List<T> getValues() {
		return Collections.unmodifiableList(collection);
	}

	/**
	 * Getter de la tabla entera del dataset en formato de lista de Features.
	 *
	 * @return tabla del dataset
	 */
	public List<Feature<?>> getTable() {
		return new ArrayList<>(features.values());
	}
	
	/**
	 * toString.
	 *
	 * @return valor de tipo String
	 */
	@Override
	public String toString() {
		return features.toString();
	}
}
