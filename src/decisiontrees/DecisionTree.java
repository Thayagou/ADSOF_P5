package decisiontrees;

import java.util.function.Predicate;

import dataset.Dataset;
import exceptions.*;

import java.util.*;

public class DecisionTree<T> {
	private String name = null;
	private Predicate<T> predicate = null;
	private List<DecisionTree<T>> children = new ArrayList<>();
	private DecisionTree<T> otherwise = null;

	public DecisionTree(String rootName) {
		this.name = rootName;
		this.predicate = p->true;
	}
	
	public DecisionTree() { 
		this.predicate = p->true;
	}
	
	public DecisionTree(String nodeName, Predicate<T> predicate) {
		this.name = nodeName;
		this.predicate = predicate;
	}

	public DecisionTree<T> node(String nodeName) throws NodeNotFoundException {
		// Caso del nodo raíz
		if (name == null) {
			name = nodeName;			
			return this;
		}

		return getNode(nodeName);
	}
	
	/**
	 * Devuelve el primer nodo hijo que cumple su predicado para el valor
	 * TODO
	 *
	 * @param value
	 * @return
	 * @throws StagnantValueException
	 */
	public DecisionTree<T> nextPredict (T value) throws StagnantValueException {
		if (isLeafNode()) return this;
		
		for (DecisionTree<T> n: children) {
			//System.out.println("Prueba: " + n.name + "\n");
			if (n.test(value)) return n;
		}
		
		if (otherwise != null) return otherwise;
		
		throw new StagnantValueException(value);
	}

	public Map<String, List<T>> predict(List<T> values) {
		DecisionTree<T> curr, next = null;
		Map<String, List<T>> endValues = new LinkedHashMap<>();

		for (T value : values) {
			next = this;
			do {
				curr = next;
				try {
					next = curr.nextPredict(value);
					// System.out.println(next.name + " " + curr.name + "\n");
				} catch (StagnantValueException e) {
					System.out.println(e);
					break;
				}

			} while (!curr.equals(next));

			endValues.computeIfAbsent(curr.getName(), k -> new ArrayList<T>()).add(value);
		}

		return endValues;
	}

	public Map<String, List<T>> predict(Dataset<T> dataset) {
		return predict(dataset.getValues());
	}

	@SuppressWarnings("unchecked")
	public Map<String, List<T>> predict(T... values) {
		return predict(List.of(values));
	}

	public Predicate<T> getPredicate(String nodeName) throws NodeNotFoundException {
		Predicate<T> result = predicateRec(this, nodeName, t -> true);

		if (result == null)
			throw new NodeNotFoundException(nodeName);
		return result;
	}

	private Predicate<T> predicateRec(DecisionTree<T> node, String target, Predicate<T> predAcum) {
		Predicate<T> nextPred = predAcum.and(node.getPredicate());

		if (node.getName().equals(target))
			return nextPred;

		for (DecisionTree<T> child : node.children) {
			Predicate<T> res = predicateRec(child, target, nextPred);
			if (res != null)
				return res;
		}

		return null;
	}

	private DecisionTree<T> getNode(String nodeName) throws NodeNotFoundException {
		Stack<DecisionTree<T>> st = new Stack<>();

		st.add(this);

		while (!st.isEmpty()) {
			DecisionTree<T> cur = st.pop();
			if (cur.getName().equals(nodeName))
				return cur;

			for (DecisionTree<T> child : cur.children) {
				st.add(child);
			}
		}

		throw new NodeNotFoundException(nodeName);
	}
	
	/**
	 * Añade un nodo como hijo
	 *
	 * @param child Nodo hijo que se añade
	 * @return Nodo al qe se le añade el hijo
	 */
	public DecisionTree<T> addChild(DecisionTree<T> child) {
		Predicate<T> predicate = child.getPredicate();
		if (otherwise != null) otherwise.predicate = otherwise.predicate.and(predicate.negate());
		
		children.add(child);
		
		return this;
	}
	
	/**
	 * Añade un hijo al nodo con el nombre y la condicion especificados
	 *
	 * @param name Nombre del nodo hijo
	 * @param predicate Predicado del nodo hijo
	 * @return Nodo al que se le añade el hijo
	 */
	public DecisionTree<T> withCondition(String name, Predicate<T> predicate) {	
		return addChild(new DecisionTree<>(name, predicate));
	}
	
	/**
	 * Añade el nodo otherwise a este nodo
	 *
	 * @param name Nombre del nodo othrwise
	 * @return Nodo al que se añade el otherwise
	 */
	public DecisionTree<T> otherwise(String name) {
		Predicate<T> otherwisePredicate = p->true;
		
		for (DecisionTree<T> child: children) {
			otherwisePredicate = otherwisePredicate.and(child.predicate.negate());
		}
		
		otherwise = new DecisionTree<>(name, otherwisePredicate);
		
		return this;
	}

	public boolean test(T value) {
		return predicate.test(value);
	}
	
	public boolean isLeafNode() {
		return (children.isEmpty() && (otherwise == null));
	}
	
	public String getName() {
		return name;
	}
	
	public List<DecisionTree<T>> getChildren() {
		return Collections.unmodifiableList(children);
	}
	
	public DecisionTree<T> getOtherwise() {
		return otherwise;
	}
	
	public Predicate<T> getPredicate() {
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
			for (DecisionTree<T> child: children) {
				sb.append(child.name + " ");
			}
			
			if (otherwise != null) sb.append("\nOtherwise: " + otherwise.name + "\n");
			sb.append("\n\n");
			
			for (DecisionTree<T> child: children) {
				sb.append(child.toString());
			}
			
			if (otherwise != null) sb.append(otherwise.toString());
		}
		
		return sb.toString();
	}

}

	

