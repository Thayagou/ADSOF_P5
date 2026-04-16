package decisiontrees;

import java.util.function.Predicate;
import java.util.*;

import dataset.*;
import exceptions.NodeNotFoundException;
import features.*;
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
		Feature<K> bestFeature = (Feature<K>) dataset.removeFeature(tagBestFeature);
		
		
        TreeMap<K, List<Integer>> dist = bestFeature.distributionPositions();
        
        Featurizer<T> featurizer = dataset.getFeaturizer();
		for(K key : dist.keySet()) {
			LabeledDataset<T, L> subDataset = dataset.getLabeledSubset(dist.get(key));

			Predicate<T> childPredicate = null;
			Node<T> child = null;
			
			/* Si el valor es el mayor se le asigna un otherwise para evitar stagnant values
			 * Si es un valor intermedio, le asigna el intervalo (lowerBound - curKey]
			 * Si es el menor se le asigna el rango menor que (-inf - curkey]
			 */
			if (dist.higherKey(key) == null) {
				child = currNode.otherwise(subDataset.toString());
			} else if ( dist.lowerKey(key) == null) {
				childPredicate = p-> ((K) featurizer.getValue(p, tagBestFeature)).compareTo(key) < 0 ;
			} else {
				K lowerBound = dist.lowerKey(key);
				childPredicate = p-> ((K) featurizer.getValue(p, tagBestFeature)).compareTo(key) <= 0 
						&& ((K) featurizer.getValue(p, tagBestFeature)).compareTo(lowerBound) > 0;
			}
			
			if (child == null) {
				child = new Node<>(tagBestFeature + key.toString(), childPredicate);
				currNode.addChild(child);
			}
			
			learnRec(subDataset, child);
		}
	}

	public DecisionTree<T> learn(LabeledDataset<T, L> dataset) throws NodeNotFoundException {
		DecisionTree<T> dTree = new DecisionTree<>("root");
		
		learnRec(new LabeledDataset<T, L>(dataset), dTree.getRoot());


		return dTree;
	}
}
