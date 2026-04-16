package strategies;

import features.*;

import java.util.List;

import dataset.*;

public interface Strategy {
	Feature<?> getBestFeature(LabeledDataset<?,?> dataset, List<Feature<?>> availableFeatures);	
}
