package strategies;

import dataset.*;
import features.*;
import java.util.*;

/**
 * Tipo: Class MetricaClasificacionErronea.
 * @author Tiago Oselka y Juan Ibáñez
 */
public class MetricaClasificacionErronea implements Strategy {
	
	/**
	 * Instancia un nuevo Objeto MetricaClasificacionErronea.
	 */
	public MetricaClasificacionErronea() { }
	
	/**
	 * Obtiene el mejor Feature siguiendo el criterio del enunciado
	 *
	 * @param <T> parámetro genérico del objeto almacenado en el dataset
	 * @param <L> parámetro genérico del label que se les da a los objetos del dataset
	 * @param dataset Dataset a partir del cual se obtiene el mejor Feature
	 * @return tag del mejor feature
	 */
	@Override
	public <T, L> String getBestFeature(LabeledDataset<T, L> dataset) {
		List<Feature<?>> features = dataset.getTable();
	
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
		
		return bestFeature.getTag();
	}
	
}
