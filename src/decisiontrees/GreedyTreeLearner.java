package decisiontrees;

import java.util.function.Predicate;
import java.util.*;

import dataset.*;
import exceptions.InexistantFeatureException;
import features.*;
import strategies.Strategy;

/**
 * Tipo: Class GreedyTreeLearner.
 *
 * @param <T> parámetro genérico que representa los objetos almacenados en el dataset
 * @param <L> parámetro genérico que representa la clase de las labels asignadas de los objetos de tipo T
 * 
 * @author Tiago Oselka y Juan Ibáñez
 */
public class GreedyTreeLearner<T, L> {
	
	/** Estrategia que se sigue para elegir el mejor Feature en cada paso del algoritmo */
	private Strategy strategy;
	
	/** Contador para los nodos de un árbol para asignar siempre nombres únicos*/
	private int count = 0;
	
	/**
	 * Instancia un nuevo Objeto GreedyTreeLearner.
	 *
	 * @param strat parámetro strat
	 */
	public GreedyTreeLearner(Strategy strat) {
		this.strategy = strat;
	}
	
	

	/*
	// @SuppressWarnings("unchecked")
	@SuppressWarnings("unchecked")
	private <K extends Comparable<? super K>> void learnRec(LabeledDataset<T, L> dataset, DecisionTree<T> currNode) throws InexistantFeatureException {
		System.out.println(dataset);
		//Misma etiqueta en todos
		List<L> labels = dataset.getLabels();
		if (labels.stream().distinct().count() == 1) {
			System.out.println("Nuevo nodo!");
			currNode.setName(currNode.getName() + ". Label:  " + labels.getFirst());
			return;
		}
		
		// Elegir mejor feature
		String tagBestFeature = strategy.getBestFeature(dataset);
		System.out.println(tagBestFeature);
		Feature<K> bestFeature = (Feature<K>) dataset.removeFeature(tagBestFeature);
		//System.out.println(dataset);
		
        TreeMap<K, List<Integer>> dist = bestFeature.distributionPositions();
        System.out.println(dist + "\n\n");
        Featurizer<? super T> featurizer = dataset.getFeaturizer();
        
		for(K key : dist.keySet()) {
			LabeledDataset<T, L> subDataset = dataset.getLabeledSubset(dist.get(key));

			Predicate<T> childPredicate = null;
			DecisionTree<T> child = null;
			System.out.println(key);
			if (dist.higherKey(key) == null) {
				System.out.println("Entra " + key.toString());
				currNode.otherwise((count++) + "- " + key.toString());
				child = currNode.getOtherwise();
				
			} else if ( dist.lowerKey(key) == null) {
				childPredicate = p-> ((K) featurizer.getValue(p, tagBestFeature)).compareTo(key) < 0 ;
				
			} else {
				K lowerBound = dist.lowerKey(key);
				childPredicate = p-> ((K) featurizer.getValue(p, tagBestFeature)).compareTo(key) <= 0 
						&& ((K) featurizer.getValue(p, tagBestFeature)).compareTo(lowerBound) > 0;
			}
			
			if (child == null) {
				child = new DecisionTree<>((count++) + "- " + key.toString(), childPredicate);
				currNode.addChild(child);
			}
			
			learnRec(subDataset, child);
		}
	}*/
	
	/**
	 * Construye un árbol de decisión a partir de un dataset siguiendo un algoritmo greedy implementado en la verión recursiva
	 *
	 * @param <K> parámetro genérico comparable
	 * @param dataset Dataset del cual se desea "aprender" y generar un árbol
	 * @param predicate Predicado que se le va a asignar al nodo actual del árbol
	 * @param name Nombre que se le va a asignar al nodo actual del árbol
	 * @return Un DecisionTree resultante de las decisiones de la estrategia definida
	 * @throws InexistantFeatureException Se lanza en el caso de que la estrategia elegida devuelva algún feature que no sea válido
	 */
	@SuppressWarnings("unchecked")
	public <K extends Comparable<? super K>> DecisionTree<T> learnRecV2(LabeledDataset<T, L> dataset, Predicate<T> predicate, String name) throws InexistantFeatureException {
		System.out.println(dataset);
		//Misma etiqueta en todos
		List<L> labels = dataset.getLabels();
		if (labels.stream().distinct().count() == 1) {
			System.out.println("Nuevo nodo!");
			
			return new DecisionTree<T>(/*(count++) + "- " +*/ name + "\nLeaf label: " + labels.getFirst().toString(), predicate);
		}
		
		// Elegir mejor feature
		String tagBestFeature = strategy.getBestFeature(dataset);
		System.out.println(tagBestFeature);
		Feature<K> bestFeature;
		
		bestFeature = (Feature<K>) dataset.removeFeature(tagBestFeature);
		
		//System.out.println(dataset);
		
		DecisionTree<T> curr = new DecisionTree<>(/*(count++) + "- " +*/ name + "\nSplit: " + tagBestFeature, predicate);
		
        TreeMap<K, List<Integer>> dist = bestFeature.distributionPositions();
        //System.out.println(dist + "\n\n");
        Featurizer<? super T> featurizer = dataset.getFeaturizer();
        
		for(K key : dist.keySet()) {
			LabeledDataset<T, L> subDataset = dataset.getLabeledSubset(dist.get(key));

			Predicate<T> childPredicate = null;
			System.out.println(key);
			/* Si el valor es el mayor se le asigna un otherwise para evitar stagnant values
			 * Si es un valor intermedio, le asigna el intervalo (lowerBound - curKey]
			 * Si es el menor se le asigna el rango menor que (-inf - curkey]
			 */
			if (dist.higherKey(key) == null) {
				System.out.println("Entra " + key.toString());
				curr.otherwise(learnRecV2(subDataset, p->true, key.toString()));
				
			} else if ( dist.lowerKey(key) == null) {
				childPredicate = p-> ((K) featurizer.getValue(p, tagBestFeature)).compareTo(key) <= 0 ;
				curr.addChild(learnRecV2(subDataset, childPredicate, key.toString()));
				
			} else {
				K lowerBound = dist.lowerKey(key);
				childPredicate = p-> ((K) featurizer.getValue(p, tagBestFeature)).compareTo(key) <= 0 
						&& ((K) featurizer.getValue(p, tagBestFeature)).compareTo(lowerBound) > 0;
				curr.addChild(learnRecV2(subDataset, childPredicate, key.toString()));
			}
		}
		
		return curr;
	}

	/**
	 * Construye un árbol de decisión a partir de un dataset siguiendo un algoritmo greedy implementado en la verión recursiva
	 *
	 * @param dataset Dataset del cual se desea "aprender" y generar un árbol
	 * @return Un DecisionTree resultante de las decisiones de la estrategia definida
	 * @throws InexistantFeatureException Se lanza en el caso de que la estrategia elegida devuelva algún feature que no sea válido
	 */
	public DecisionTree<T> learn(LabeledDataset<T, L> dataset) throws InexistantFeatureException {
		return learnRecV2(new LabeledDataset<T, L>(dataset), p->true, "Root");
	}
}
