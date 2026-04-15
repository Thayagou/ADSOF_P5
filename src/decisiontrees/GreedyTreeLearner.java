package decisiontrees;

import java.util.function.Predicate;

import dataset.*;
import features.*;
import labels.*;

public class GreedyTreeLearner<T extends Comparable<T>, L> {
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
	
	private Predicate<T> inRange(T min, T max) {
		return n-> (n.compareTo(min) >= 0 && n.compareTo(max) < 0);
	}
}
