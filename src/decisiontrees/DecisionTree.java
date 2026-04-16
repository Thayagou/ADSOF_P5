package decisiontrees;

import java.util.function.Predicate;

import dataset.Dataset;
import exceptions.DuplicateNodeException;
import exceptions.NodeNotFoundException;
import exceptions.StagnantValueException;

import java.util.*;

public class DecisionTree<T>{
	private Node<T> root;
	
	public DecisionTree(String rootName) {
		root = new Node<>(rootName, p->true);
	}
	
	public Node<T> getRoot() {
		return root;
	}
	
	public Node<T> node(String nodeName) throws NodeNotFoundException {
		if (root == null) {
			root = new Node<>(nodeName, p->true);
			return root;
		}
		
		return getNode(nodeName);
	}
	
	public Map<String, List<T>> predict (List<T> values) {
		Node<T> curr, next = null;
		Map<String, List<T>> endValues = new LinkedHashMap<>();
		
		for (T value: values) {
			next = root;
			do {
				curr = next;
				try {
					next = curr.nextPredict(value);
					//System.out.println(next.name + " " + curr.name + "\n");
				} catch (StagnantValueException e) {
					System.out.println(e);
					break;
				}
				
			} while (!curr.equals(next));
			
			endValues.computeIfAbsent(curr.getName(), k-> new ArrayList<T>()).add(value);
		}
		
		return endValues;
	}
	
	public Map<String, List<T>> predict (Dataset<T> dataset) {
		return predict(dataset.getValues());
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, List<T>> predict (T...values) {
		return predict(List.of(values));
	}
	
	
	public Predicate<T> getPredicate(String nodeName) throws NodeNotFoundException {
		Stack<Node<T>> st = new Stack<>();
		List<Node<T>> camino = new ArrayList<>();
		
		if (root == null) throw new NodeNotFoundException(nodeName);
		
		st.add(root);
		if (dfsRec(nodeName, st, camino) == false) throw new NodeNotFoundException(nodeName);
		Predicate<T> result = p->true;
		for (Node<T> node: camino) {
			result = result.and(node.getPredicate());
		}
		
		return result;
	}
	
	private boolean dfsRec(String nodeName, Stack<Node<T>> st, List<Node<T>> camino) {
		boolean found = false;
		
		while ((found == false) && (st.isEmpty() == false)) {
			Node<T> explore = st.pop();
			camino.add(explore);	
			
			if (explore.getName().equals(nodeName)) {
				return true;
			}
			
			for (Node<T> child: explore.getChildren()) {
				st.add(child);
				if (dfsRec(nodeName, st, camino) == true) return true;
			}
			
			camino.remove(explore);
		}
		
		return false;
	}
	
	private Node<T> getNode(String nodeName) throws NodeNotFoundException {
		Stack<Node<T>> st = new Stack<>();
		
		st.add(root);
		
		while (!st.isEmpty()) {
			Node<T> cur = st.pop();
			if (cur.getName().equals(nodeName)) return cur;
			
			for (Node<T> child: cur.getChildren()) {
				st.add(child);
			}
		}
		
		throw new NodeNotFoundException(nodeName);
	}
	
	@Override
	public String toString() {
		return root.toString();
	}
}
