package strategies;

import dataset.*;

public interface Strategy {
	public <T,L> String getBestFeature(LabeledDataset<T, L> dataset);
}
