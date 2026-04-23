package tests;

import dataset.LabeledDataset;
import decisiontrees.DecisionTree;
import decisiontrees.GreedyTreeLearner;
import features.Person;
import features.PersonFeaturizer;
import labels.PersonLabelProvider;
import labels.ShouldIPlayTennisToday;
import strategies.MetricaClasificacionErronea;
import visualizers.GraphvizVisualizer;
import visualizers.PlainTextVisualizer;

public class EjemploDeUsoApartado5 {
	public static void main(String[] args) {
		DecisionTree<Weather> trih = learnTree();
		DecisionTree<Person> trihPerson = learnTreePerson();
		
		PlainTextVisualizer viz = new PlainTextVisualizer();
		GraphvizVisualizer graphViz = new GraphvizVisualizer("graph", "TestGraph");
		viz.visit(trih);
		viz.visit(trihPerson);
		GraphvizVisualizer graphVizPerson = new GraphvizVisualizer("graphPerson", "TestGraph");
		graphViz.visit(trih);
		graphVizPerson.visit(trihPerson);
		//System.out.println(trih);
		//System.out.println(trih.predict(buildDataSet()));
	}
	
	public static DecisionTree<Weather> learnTree() {
		LabeledDataset<Weather, Boolean> dataSet = buildDataSet();
		GreedyTreeLearner<Weather, Boolean> learner = new GreedyTreeLearner<>(new MetricaClasificacionErronea()); 
		DecisionTree<Weather> tree = learner.learn(dataSet); 
		return tree;
	}

	private static LabeledDataset<Weather, Boolean> buildDataSet() { 
		Weather conditions [] = {
				new Weather (WeatherCondition. RAINY, Temperature.COLD),
				new Weather (WeatherCondition. RAINY, Temperature.HOT),
				new Weather (WeatherCondition. SUNNY, Temperature.COLD),
				new Weather (WeatherCondition. SUNNY, Temperature.HOT)
		};
		
		LabeledDataset <Weather, Boolean> ds = new LabeledDataset<>(new WeatherFeaturizer(), new ShouldIPlayTennisToday());
		ds.addAll(conditions);
		return ds;
	}
	
	public static DecisionTree<Person> learnTreePerson() {
		LabeledDataset<Person, String> dataSet = buildDataSetPerson();
		GreedyTreeLearner<Person, String> learner = new GreedyTreeLearner<>(new MetricaClasificacionErronea()); 
		DecisionTree<Person> tree = learner.learn(dataSet); 
		return tree;
	}
	
	private static LabeledDataset<Person, String> buildDataSetPerson() { 
		Person people [] = { 
				new Person("Pedro", 66, 75, 180, true), 
				new Person("Ana", 47, 54, 158, false),
				new Person("Luis", 35, 70, 176, true), 
				new Person("Raul", 44, 67, 176, true), 
				new Person("Juan", 34, 75, 176, true), 
				new Person("Rosa", 47, 54, 158, false)
		};
			
			
		LabeledDataset <Person, String> ds = new LabeledDataset<>(new PersonFeaturizer(), new PersonLabelProvider());
		ds.addAll(people);
		return ds;
	}
}
