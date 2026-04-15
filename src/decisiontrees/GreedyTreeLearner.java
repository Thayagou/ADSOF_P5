package decisiontrees;

import dataset.*;
import features.*;
import labels.*;

public class GreedyTreeLearner<T, L extends Label> {
	public DecisionTree<String> learn(LabeledDataset<T, L> dataset, Feature<?>...availableFeatures){
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
		
	}
}
