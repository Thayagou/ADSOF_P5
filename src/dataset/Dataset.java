package dataset;

import features.Featurizer;

/**
 * Tipo: Class Dataset.
 *
 * @param <T> parámetro genérico
 */
public class Dataset<T>{
	
	/** Campo featurizer. */
	private Featurizer<T> featurizer;
	
	
	/**
	 * Instancia un nuevo Objeto Dataset.
	 *
	 * @param featurizer parámetro featurizer
	 */
	public Dataset(Featurizer<T> featurizer) {
		this.featurizer = featurizer;
	}
}
