package visitorPattern;

import decisiontrees.DecisionTree;

/**
 * Tipo: Interface Visitor del patrón de diseño Visitor
 * @author Tiago Oselka y Juan Ibáñez
 */
public interface Visitor {
	
	/**
	 * Visita un árbol de decisión
	 *
	 * @param tree Árbol a visitar
	 */
	public void visit(DecisionTree<?> tree);
}
