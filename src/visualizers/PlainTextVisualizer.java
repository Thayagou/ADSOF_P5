package visualizers;

import decisiontrees.DecisionTree;
import visitorPattern.Visitor;

public class PlainTextVisualizer implements Visitor{
	private static final String DF_INDENTATION = "   ";
	
	public PlainTextVisualizer() {}
	@Override
	public void visit(DecisionTree<?> tree) {
		
		prettyPrintRec(tree, "");
	}
	
	private void prettyPrintRec(DecisionTree<?> tree, String indentation) {
		System.out.println(indentation + tree.getName().replace("\n", "\t"));
		
		for (DecisionTree<?> child: tree.getChildren()) {
			prettyPrintRec(child, indentation + DF_INDENTATION);
		}
		
		DecisionTree<?> otherwise = tree.getOtherwise();
		if (otherwise != null) prettyPrintRec(otherwise, indentation + DF_INDENTATION);
	}

}
