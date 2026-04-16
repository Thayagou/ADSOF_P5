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