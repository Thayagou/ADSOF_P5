package tests;

public class EjemploDeUsoApartado5 {
	public static void main(String[] args) {
		
	}
	
	public static DecisionTree<Weather> learnTree() {
		buildDataSet() =
		=
		LabeledDataset<Weather, Boolean> dataSet GreedyTreeLearner<Weather, Boolean> learner new GreedyTreeLearner<>(); Decision Tree<Weather> tree learner.learn (dataSet); return tree;
		=
		}
	private static LabeledDataset<Weather, Boolean> buildDataSet() { Weather conditions [] = {
		};
		new Weather (WeatherCondition. RAINY, Temperature.COLD), new Weather (WeatherCondition. RAINY, Temperature.HOT) // más objetos .
		LabeledDataset <Weather, Boolean> ds
		=
		new LabeledDataset<>(new WeatherFeaturizer(), new ShouldIPlayTennisToday());
		ds.addAll(conditions);
		return ds;
		}
}
