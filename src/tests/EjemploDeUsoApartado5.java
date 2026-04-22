package tests;

import dataset.LabeledDataset;
import decisiontrees.DecisionTree;
import decisiontrees.GreedyTreeLearner;
import labels.ShouldIPlayTennisToday;
import strategies.MetricaClasificacionErronea;
import visualizers.GraphvizVisualizer;
import visualizers.PlainTextVisualizer;

public class EjemploDeUsoApartado5 {
	public static void main(String[] args) {
		DecisionTree<Weather> trih = learnTree();
		
		PlainTextVisualizer viz = new PlainTextVisualizer();
		GraphvizVisualizer graphViz = new GraphvizVisualizer("graph", "TestGraph");
		viz.visit(trih);
		graphViz.visit(trih);
		//System.out.println(trih);
		//System.out.println(trih.predict(buildDataSet()));
	}
	
	public static DecisionTree<Weather> learnTree() {
		LabeledDataset<Weather, Boolean> dataSet = buildDataSet();
		GreedyTreeLearner<Weather, Boolean> learner = new GreedyTreeLearner<>(new MetricaClasificacionErronea()); 
		DecisionTree<Weather> tree = learner.learn(dataSet); return tree;
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
}
