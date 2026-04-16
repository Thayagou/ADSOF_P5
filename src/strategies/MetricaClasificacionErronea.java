package strategies;

import dataset.*;
import features.*;
import java.util.*;

public class MetricaClasificacionErronea implements Strategy {

	@Override
	public Feature<?> getBestFeature(LabeledDataset<?, ?> dataset) {
		List<Feature<?>> features = dataset.getTabla();
		Featurizer featurizer = dataset.getFeaturizer();
		List<?> values = dataset.getValues();
		List<?> labels = dataset.getLabels();
		
		for(Feature<?> f : features) {
			TreeMap<?, List<Integer>> dist = f.distributionPositions();
			
			for(Object k : dist.keySet()) {
				for(int i : dist.get(k)) {
					
				}
			}
		}
	}
	
}
