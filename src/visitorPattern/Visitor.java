package visitorPattern;

import decisiontrees.DecisionTree;

public interface Visitor {
	public void visit(DecisionTree<?> tree);
}
