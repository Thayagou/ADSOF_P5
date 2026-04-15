package dataset;

import labels.*;
import features.*;
import java.util.*;

public class LabeledDataset<T, L> extends Dataset<T> {
	private LabelProvider<T, L> labelProvider;
	private Map<L, ArrayList<T>> asignedLabels = new LinkedHashMap<>();

	public LabeledDataset(Featurizer<T> featurizer, LabelProvider<T, L> labelProvider) {
		super(featurizer);
		this.labelProvider = labelProvider;
	}

	@Override
	public void add(T elem) {
		super.add(elem);
		L label = labelProvider.getLabel(elem);
		asignedLabels.computeIfAbsent(label, l->new ArrayList<>()).add(elem);
	}

	public List<L> getLabels() {
		return asignedLabels.keySet().stream().toList();
	}
}