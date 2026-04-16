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
	 * @return valor de Features
	 */
	public List<Feature<?>> getFeatureList();
	
	/**
	 * Obtiene el valor de un cierto feature para un objeto
	 *
	 * @param elem Objeto del que obtiene el feature
	 * @param featureTag Etiqueta del feature del que se quiere el valor
	 * @return Valor de feature en el objeto elem
	 */
	public <V extends Comparable<V>> V getValue(T elem, String featureTag);
}
