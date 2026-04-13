package features;

import java.util.*;

/**
 * Tipo: Interface Featurizer.
 *
 * @param <T> parámetro genérico
 */
public interface Featurizer<T> {
	
	/**
	 * Obtiene Features.
	 *
	 * @param data parámetro data
	 * @return valor de Features
	 */
	public List<Feature<?>> getFeatures(T data);
}
