package strategies;

import features.*;
import dataset.*;

public interface Strategy {
	Feature<?> getBestFeature(LabeledDataset<?,?> dataset);	
}
