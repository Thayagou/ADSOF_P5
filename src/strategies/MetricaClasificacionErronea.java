package strategies;

import dataset.*;
import features.*;
import java.util.*;

public class MetricaClasificacionErronea implements Strategy {

	@Override
	public <T, L> Feature<?> getBestFeature(LabeledDataset<T, L> dataset) {
		List<Feature<?>> features = dataset.getTable();
		
		List<T> values = dataset.getValues();
		List<L> labels = dataset.getLabels();
		
		int minMismatch = Integer.MAX_VALUE;
		Feature<?> bestFeature = null;
		for(Feature<?> f : features) {
			TreeMap<?, List<Integer>> dist = f.distributionPositions();
			
			int maxTagInGroup = 0, groupSum = 0, misMatchSum = 0;
			for (List<Integer> group: dist.values()) {
				Map<L, Integer> groupDistribution = new HashMap<>();
				
				group.forEach(v->groupDistribution.merge(labels.get(v), 1, (a,b)->Integer.sum(a, b)));
				
				for (Map.Entry<L, Integer> entry: groupDistribution.entrySet()) {
					int elemWithTag = entry.getValue();
					if (elemWithTag > maxTagInGroup) maxTagInGroup = elemWithTag;
					groupSum += elemWithTag;
				}
				
				misMatchSum += groupSum - maxTagInGroup;
			}
			
			if (bestFeature == null || misMatchSum < minMismatch) {
				bestFeature = f;
				minMismatch = misMatchSum;
			}
		}
		
		return bestFeature;
	}
	
}
