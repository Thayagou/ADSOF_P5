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
	public LabeledDataset<T, L> getLabeledSubset(List<Integer> index) {
		LabeledDataset<T, L> dataset = new LabeledDataset<T, L>(featurizer, labelProvider);

		for (Integer i : index) {
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

	@Override
	public void removeDuplicates() {
		List<T> newCollection = new ArrayList<>();
		List<L> newLabels = new ArrayList<>();
		Set<T> seen = new HashSet<>();

		for (int i = 0; i < collection.size(); i++) {
			T elem = collection.get(i);
			if (seen.add(elem)) {
				newCollection.add(elem);
				newLabels.add(labels.get(i));
			}
		}

		collection.clear();
		labels.clear();
		features.values().forEach(List::clear);

		for (int i = 0; i < newCollection.size(); i++) {
			add(newCollection.get(i));
		}
	}

	public List<L> getLabels() {
		return labels;
	}
}