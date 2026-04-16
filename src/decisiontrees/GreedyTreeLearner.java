package decisiontrees;

import java.util.function.Predicate;
import java.util.*;

import dataset.*;
import features.*;
import labels.*;
import strategies.Strategy;

public class GreedyTreeLearner<T extends Comparable<T>, L> {
	private Strategy strategy;
	/*
	 * public DecisionTree<String> learn(LabeledDataset<T, L> dataset,
	 * Feature<?>...availableFeatures){ boolean allLabelsEqual = true;
	 * if(!dataset.getLabels().isEmpty()) { L primerElemento =
	 * dataset.getLabels().get(0); for(L l : dataset.getLabels()) { if(l !=
	 * primerElemento) { allLabelsEqual = false; break; } } } else { return null; }
	 * 
	 * if(allLabelsEqual) return new Node(dataset.getLabels().get(0).getName());
	 * 
	 * }
	 */

	// @SuppressWarnings("unchecked")
	public Node<T> learnRec(Node<T> parentNode, LabeledDataset<T, L> dataset, List<Feature<?>> availableFeatures) {
		//Misma etiqueta en todos
		List<L> labels = dataset.getLabels();
		if (labels.stream().distinct().count() == 1) {
			return new Node<T>(labels.getFirst().toString(), p -> true);
		}
		
		// Elegir mejor feature
		Feature<?> bestFeature = strategy.getBestFeature(dataset);
		List<Feature<?>> remaining = new ArrayList<>(availableFeatures);
        remaining.remove(bestFeature);
		
        TreeMap<?, List<Integer>> dist = bestFeature.distributionPositions();
        
		for(List<Integer> value : dist.values()) {
			LabeledDataset<T, L> subset = dataset.getLabeledSubset(value);
			parentNode.addChild(learnRec());
		}
		
//		Feature<?> best = strategy.getBestFeature(features);
//		
//		TreeMap<?, List<Integer>> dist = best.distributionPositions();
//		
//		features.remove(best);
//		
//		for (Map.Entry<?, List<Integer>> entry: dist.entrySet()) {
//			List<Feature<?>> subTree = new ArrayList<>(features);
//			
//			
//		}
	}

	public DecisionTree<T> learn(LabeledDataset<T, L> dataset) {
		DecisionTree<T> dTree = new DecisionTree<>();
		List<Feature<?>> table = dataset.getTable();

		learnRec();

		return dTree;
	}

	private Predicate<T> inRange(T min, T max) {
		return n -> (n.compareTo(min) >= 0 && n.compareTo(max) < 0);
	}
}
