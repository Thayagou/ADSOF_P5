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
	
	/** Campo que almacena los features por su nombre */
	private Map<String, Feature<?>> features = new HashMap<>();
	
	/**
	 * Instancia un nuevo Objeto Dataset.
	 *
	 * @param featurizer parámetro featurizer
	 */
	public Dataset(Featurizer<T> featurizer) {
		this.featurizer = featurizer;
	}
}
