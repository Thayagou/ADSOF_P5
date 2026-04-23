package tests;

import dataset.Dataset;
import decisiontrees.DecisionTree;
import exceptions.DuplicateNodeException;
import exceptions.NodeNotFoundException;
import features.Person;
import features.PersonFeaturizer;

public class EjemploDeUsoApartado2 {

	public static void main(String[] args) throws DuplicateNodeException, NodeNotFoundException {
		Dataset <Person> dataSet = buildDataSet(); // like in previous listing
		
		DecisionTree<Person> dt = buildPersonDecisionTree();
		//System.out.println(dt);
		System.out.println(dt.predict(dataSet));
		System.out.println(dt.predict(new Person("Miguel", 86, 72, 165, true), new Person("Clara", 42, 59, 162, false)));
		System.out.println(dt);
		System.out.println(dt.getPredicate("old male"));
		dt.getPredicate("female");
	}
	
	public static DecisionTree<Person> buildPersonDecisionTree() throws DuplicateNodeException, NodeNotFoundException {
		DecisionTree<Person> dt = new DecisionTree<>("root");
		
		dt.node("root") // nodo raíz, al ser el primero que se añade
		.withCondition ("male", p -> p.isMale())
		.otherwise ("female");
		
		dt.node("male"). // como el nodo ya existe, se añaden condiciones sobre el
		withCondition("old male", p -> p.getAge() > 65)
		.withCondition("middle male", p -> p.getAge() <= 65 && p.getAge() > 34) .otherwise ("young male");

		return dt;
	}
		
	public static Dataset<Person> buildDataSet() {
		Person people [] = { new Person("Pedro", 66, 75, 180, true), new Person("Ana", 47, 54, 158, false), new Person("Luis", 34, 75, 176, true), new Person("Rosa", 47, 54, 158, false)
		};
		
		Dataset<Person> dataSet = new Dataset<>(new PersonFeaturizer()); // A Featurizer for Person objects 
		dataSet.addAll(people);
		return dataSet;
	}

}
