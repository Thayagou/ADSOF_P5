package decisiontrees;

import java.util.function.Predicate;
import java.util.*;

import dataset.*;
import features.*;
import labels.*;
import strategies.Strategy;

public class GreedyTreeLearner<T extends Comparable<T>, L> {
	private Strategy strategy;
	/*public DecisionTree<String> learn(LabeledDataset<T, L> dataset, Feature<?>...availableFeatures){
		boolean allLabelsEqual = true;
		if(!dataset.getLabels().isEmpty()) {
			L primerElemento = dataset.getLabels().get(0);
			for(L l : dataset.getLabels()) {
				if(l != primerElemento) {
					allLabelsEqual = false;
					break;
				}
			}
		} else {
			return null;
		}
		
		if(allLabelsEqual) return new Node(dataset.getLabels().get(0).getName());
		
	}*/
	
	public Node<T> learnRec(List<Feature<?>> table, List<Feature<?>> features) {
		Feature<?> best = strategy.getBestFeature(features);
		
		TreeMap<?, List<Integer>> dist = best.distributionPositions();
		
		for (: dist) {
			
		}
	}
	
	public DecisionTree<T> learn(LabeledDataset<T, L> dataset) {
		List<Feature<?>> table = dataset.getTable();
		
		
	}
	
	private Predicate<T> inRange(T min, T max) {
		return n-> (n.compareTo(min) >= 0 && n.compareTo(max) < 0);
	}
}
