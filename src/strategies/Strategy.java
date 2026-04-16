package strategies;

import features.*;

import java.util.List;

import dataset.*;

public interface Strategy {
	public <T,L> String getBestFeature(LabeledDataset<T, L> dataset);
}
