package decisiontrees;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import exceptions.StagnantValueException;

public class Node<T> {
	private String name;
	private Predicate<T> predicate;
	private List<Node<T>> children = new ArrayList<>();
	private Node<T> otherwise;
	
	private Predicate<T> DF_TRUE_STATENMENT = p->true;
	
	public Node (String name, Predicate<T> predicate) {
		this.name = name;
		this.predicate = predicate;
	}
	
	public Node (String name) {
		this.name = name;
		this.predicate = null;
	}
	
	/**
	 * Añade un nodo como hijo
	 * TODO
	 *
	 * @param child
	 */
	public Node<T> addChild(Node<T> child) {
		Predicate<T> predicate = child.getPredicate();
		if (otherwise != null) otherwise.predicate = otherwise.predicate.and(predicate.negate());
		
		children.add(child);
		
		return this;
	}
	
	/**
	 * Añade un hijo al nodo con el nombre y la condicion especificados
	 * TODO
	 *
	 * @param name
	 * @param predicate
	 * @return
	 */
	public Node<T> withCondition(String name, Predicate<T> predicate) {	
		return addChild(new Node<>(name, predicate));
	}
	
	/**
	 * Añade el nodo otherwise a este nodo
	 * TODO
	 *
	 * @param name
	 * @return
	 */
	public Node<T> otherwise(String name) {
		Predicate<T> otherwisePredicate = p->true;
		
		for (Node<T> child: children) {
			otherwisePredicate.and(child.predicate.negate());
		}
		
		otherwise = new Node<>(name, otherwisePredicate);
		
		children.add(otherwise);
		
		return this;
	}
	
	/**
	 * Devuelve el valor del predicado del nodo para cierto valor de entrada
	 * TODO
	 *
	 * @param value
	 * @return
	 */
	public boolean test(T value) {
		return predicate.test(value);
	}
	
	public boolean isLeafNode() {
		return (children.isEmpty() && (otherwise == null));
	}
	
	/**
	 * Devuelve el primer nodo hijo que cumple su predicado para el valor
	 * TODO
	 *
	 * @param value
	 * @return
	 * @throws StagnantValueException
	 */
	public Node<T> nextPredict (T value) throws StagnantValueException {
		if (isLeafNode()) return this;
		
		for (Node<T> n: children) {
			//System.out.println("Prueba: " + n.name + "\n");
			if (n.test(value)) return n;
		}
		
		if (otherwise != null) return otherwise;
		
		throw new StagnantValueException(value);
	}
	
	protected String getName() {
		return name;
	}
	
	protected List<Node<T>> getChildren() {
		return children;
	}
	
	protected Node<T> getOtherwise() {
		return otherwise;
	}
	
	protected Predicate<T> getPredicate() {
		return predicate;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Name: " + name + "\n");
		
		if (isLeafNode()) {
			sb.append("Leaf node\n");
			sb.append("\n\n");
		} else {
			sb.append("Children: \n");
			for (Node<T> child: children) {
				sb.append(child.name + " ");
			}
			
			if (otherwise != null) sb.append("\nOtherwise: " + otherwise.name + "\n");
			sb.append("\n\n");
			
			for (Node<T> child: children) {
				sb.append(child.toString());
			}
		}
		
		return sb.toString();
	}
}
