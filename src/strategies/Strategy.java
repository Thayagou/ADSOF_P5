package strategies;

import dataset.*;

/**
 * Tipo: Interface Strategy.que define el mejor feature pro el que dividir el dataset en un determinado momento
 * @author Tiago Oselka y Juan Ibáñez
 */
public interface Strategy {
	
	/**
	 * Obtiene el mejor Feature para hacer el splic siguiendo un criterio implementado
	 *
	 * @param <T> parámetro genérico del objeto almacenado en el dataset
	 * @param <L> parámetro genérico del label que se les da a los objetos del dataset
	 * @param dataset Dataset a partir del cual se obtiene el mejor Feature
	 * @return tag del mejor feature
	 */
	public <T,L> String getBestFeature(LabeledDataset<T, L> dataset);
}
