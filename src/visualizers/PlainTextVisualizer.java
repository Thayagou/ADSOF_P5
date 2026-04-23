package visualizers;

import decisiontrees.DecisionTree;
import visitorPattern.Visitor;

/**
 * Tipo: Class PlainTextVisualizer:  Implementa Visitor pattern y permite imprimir en formato de texto
 * @author Tiago Oselka y Juan Ibáñez
 */
public class PlainTextVisualizer implements Visitor{
	
	/** Constante DF_INDENTATION: nivel de indentación que se desea */
	private static final String DF_INDENTATION = "   ";
	
	/**
	 * Instancia un nuevo Objeto PlainTextVisualizer.
	 */
	public PlainTextVisualizer() {}
	
	/**
	 * Visita un árbol y lo imprime formateado
	 *
	 * @param tree Árbol a imprimir
	 */
	@Override
	public void visit(DecisionTree<?> tree) {
		prettyPrintRec(tree, "");
	}
	
	/**
	 * Imprime formateado el árbol
	 *
	 * @param tree Nodo del árbol a imprimir
	 * @param indentation Indentación con la que se imprime el nodo actual
	 */
	private void prettyPrintRec(DecisionTree<?> tree, String indentation) {
		System.out.println(indentation + tree.getName().replace("\n", "\t"));
		
		for (DecisionTree<?> child: tree.getChildren()) {
			prettyPrintRec(child, indentation + DF_INDENTATION);
		}
		
		DecisionTree<?> otherwise = tree.getOtherwise();
		if (otherwise != null) prettyPrintRec(otherwise, indentation + DF_INDENTATION);
	}

}
