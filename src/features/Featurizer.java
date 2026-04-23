package features;

import java.util.*;

/**
 * Tipo: Interface Featurizer: permite extraer los datos que se deseen de un objeto
 *
 * @param <T> parámetro genérico
 * @author Tiago Oselka y Juan Ibáñez
 */
public interface Featurizer<T> {
	
	/**
	 * Obtiene los features del tipo genérico T
	 *
	 * @return valor de Features
	 */
	public List<Feature<?>> getFeatureList();
	
	/**
	 * Obtiene el valor de un cierto feature para un objeto.
	 *
	 * @param <V> valor genérico
	 * @param elem Objeto del que obtiene el feature
	 * @param featureTag Etiqueta del feature del que se quiere el valor
	 * @return Valor de feature en el objeto elem
	 */
	public <V extends Comparable<? super V>> V getValue(T elem, String featureTag);
}
