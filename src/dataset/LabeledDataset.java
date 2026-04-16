package dataset;

import labels.*;
import features.*;
import java.util.*;

public class LabeledDataset<T, L> extends Dataset<T> {
	private LabelProvider<T, L> labelProvider;
	private List<L> labels = new ArrayList<>();

	public LabeledDataset(Featurizer<T> featurizer, LabelProvider<T, L> labelProvider) {
		super(featurizer);

		this.labelProvider = labelProvider;
	}
	
	public LabeledDataset(LabeledDataset<T, L> labeledDataset) {
		super(labeledDataset);
		
		this.labelProvider = labeledDataset.labelProvider;
		this.labels.addAll(labeledDataset.labels);
	}
	
	/**
	 * Devuelve un subset del LabeledDataset a partir de una lista de indices
	 *
	 * @param index Lista con los indices de los elementos que se incluiran
	 * @return Nuevo dataset con los elementos que se indicaron
	 */
	public LabeledDataset<T, L> getLabeledSubset(List<Integer> index){
		LabeledDataset<T, L> dataset = new LabeledDataset<T, L>(featurizer, labelProvider);
		
		for(Integer i : index) {
			dataset.add(collection.get(i));
		}
		
		return dataset;
	}

	@Override
	public void add(T elem) {
		super.add(elem);
		L label = labelProvider.getLabel(elem);
		labels.add(label);
	}

	public List<L> getLabels() {
		return labels;
	}
}