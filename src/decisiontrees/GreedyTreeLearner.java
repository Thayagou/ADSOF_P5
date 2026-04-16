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
	@SuppressWarnings("unchecked")
	public <K extends Comparable<K>> void learnRec(LabeledDataset<T, L> dataset, Node<T> currNode) {
		//Misma etiqueta en todos
		List<L> labels = dataset.getLabels();
		if (labels.stream().distinct().count() == 1) {
			return;
		}
		
		// Elegir mejor feature
		String tagBestFeature = strategy.getBestFeature(dataset);
		@SuppressWarnings("unchecked")
		Feature<K> bestFeature = (Feature<K>) dataset.removeFeature(tagBestFeature);
		
		
        TreeMap<K, List<Integer>> dist = bestFeature.distributionPositions();
        
        K lowerBound = null, higherBound, curKey;
        Featurizer<T> featurizer = dataset.getFeaturizer();
		for(Map.Entry<K, List<Integer>> entry : dist.entrySet()) {
			curKey = entry.getKey();
			dist.higherKey(curKey);
			LabeledDataset<T, L> subDataset = dataset.getLabeledSubset(entry.getValue());

			Predicate<T> childPredicate;
			Node<T> child = null;
			
			/* Si el valor es el mayor se le asigna un otherwise para evitar stagnant values
			 * Si es un valor intermedio, le asigna el intervalo lowerBound - curKey
			 * Si es el menor se le asigna el rango menor que
			 */
			if (dist.higherKey(curKey) == null) {
				child = currNode.otherwise(subDataset.toString());
			} else if (lowerBound == null) {
				childPredicate = p-> ((K) featurizer.getValue(p, tagBestFeature)).compareTo(curKey) < 0 ;
			} else {
				childPredicate = p-> ((K) featurizer.getValue(p, tagBestFeature)).compareTo(curKey) <= 0 
						&& ((K) featurizer.getValue(p, tagBestFeature)).compareTo(lowerBound) > 0;
			}
			
			if (child == null) child = new Node<>(tagBestFeature + curKey.toString(), childPredicate);
			
			
			 
			currNode.addChild(child);
			
			learnRec(subDataset, child);
			
			lowerBound = curkey;
		}
	}

	public DecisionTree<T> learn(LabeledDataset<T, L> dataset) {
		DecisionTree<T> dTree = new DecisionTree<>();
		List<Feature<?>> table = dataset.getTable();


		return dTree;
	}

	private Predicate<T> inRange(T min, T max) {
		return n -> (n.compareTo(min) >= 0 && n.compareTo(max) < 0);
	}
}
