package dataset;

import labels.*;
import features.*;
import java.util.*;

/**
 * Class LabeledDataset: define el comportamiento del LabeledDataset partiendo de Dataset
 *
 * @param <T> parámetro genérico que representa la clase de los objetos almacenados
 * @param <L> parámetro genérico que representa la clase Label asignada a los objetos almacenados
 */
public class LabeledDataset<T, L> extends Dataset<T> {
	
	/** Campo labelProvider: asigna una label a cada objeto almacenados */
	private LabelProvider<? super T, ? extends L> labelProvider;
	
	/** Campo labels: lista de las labels almacenados, correspondientes en posición con los objetos */
	private List<L> labels = new ArrayList<>();

	/**
	 * Instancia un nuevo Objeto LabeledDataset.
	 *
	 * @param featurizer parámetro featurizer
	 * @param labelProvider parámetro labelProvider
	 */
	public LabeledDataset(Featurizer<? super T> featurizer, LabelProvider<? super T, ? extends L> labelProvider) {
		super(featurizer);

		this.labelProvider = labelProvider;
	}

	/**
	 * Instancia un nuevo Objeto LabeledDataset.
	 *
	 * @param labeledDataset parámetro labeledDataset
	 */
	public LabeledDataset(LabeledDataset<T, L> labeledDataset) {
		super(labeledDataset);

		this.labelProvider = labeledDataset.labelProvider;
		this.labels.addAll(labeledDataset.labels);
	}

	/**
	 * Devuelve un subset del LabeledDataset a partir de una lista de indices.
	 *
	 * @param indexes Lista con los indices de los elementos que se incluiran
	 * @return Nuevo dataset con los elementos que se indicaron
	 */
	public LabeledDataset<T, L> getLabeledSubset(List<Integer> indexes) {
		LabeledDataset<T, L> dataset = new LabeledDataset<T, L>(featurizer, labelProvider);
		
		dataset.copyFeaturesFrom(this);
		
		for (Integer i : indexes) {
			dataset.add(collection.get(i));
		}

		return dataset;
	}

	/**
	 * Añade un elemento al LabeledDataset
	 *
	 * @param elem parámetro elem
	 */
	@Override
	public void add(T elem) {
		super.add(elem);
		L label = labelProvider.getLabel(elem);
		labels.add(label);
	}

	/**
	 * Elimina una línea específica del dataset.
	 *
	 * @param line Línea especificada
	 */
	@Override
	protected void removeLine(int line) {
		super.removeLine(line);
		
		labels.remove(line);
	}

	/**
	 * Obtiene las labels del dataset que van paralelas a la lista de objetos del dataset
	 *
	 * @return lista de labels
	 */
	public List<L> getLabels() {
		return labels;
	}
	
	/**
	 * toString.
	 *
	 * @return valor de tipo String
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < collection.size(); i++) {
			sb.append(collection.get(i).toString() + ": " + labels.get(i) + "\n");
		}
		return super.toString() + "\n" + sb.toString();
	}
}