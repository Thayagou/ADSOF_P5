package decisiontrees;

import java.util.function.Predicate;

import dataset.Dataset;
import exceptions.DuplicateNodeException;
import exceptions.NodeNotFoundException;
import exceptions.StagnantValueException;

import java.util.*;

public class DecisionTree<T>{
	private Node root;
	private Map<String, Node> nodes = new HashMap<>();
	
	public class Node {
		private String name;
		private Predicate<T> predicate;
		private List<Node> children = new ArrayList<>();
		private Node otherwise;
		
		private Predicate<T> DF_TRUE_STATENMENT = p->true;
		
		private Node (String name, Predicate<T> predicate) {
			this.name = name;
			this.predicate = predicate;
		}
		
		private Node (String name) {
			this.name = name;
			this.predicate = null;
		}
		
		public Node withCondition(String name, Predicate<T> predicate) throws DuplicateNodeException {
			if (nodes.containsKey(name)) throw new DuplicateNodeException(name);
			Predicate<T> pred = new P
			
			Node newNode = new Node(name, predicate);
			children.add(newNode);
			
			if (otherwise != null) otherwise.predicate = otherwise.predicate.and(predicate.negate());
			
			nodes.put(name, newNode);
			
			return this;
		}
		
		public Node otherwise(String name) throws DuplicateNodeException {
			if (nodes.containsKey(name)) throw new DuplicateNodeException(name);
			Predicate<T> otherwisePredicate = p->true;
			
			for (Node child: children) {
				otherwisePredicate.and(child.predicate.negate());
			}
			
			otherwise = new Node(name, otherwisePredicate);
			
			children.add(otherwise);
			
			nodes.put(name, otherwise);
			
			return this;
		}
		
		public boolean test(T value) {
			return predicate.test(value);
		}
		
		public boolean isLeafNode() {
			return (children.isEmpty() && (otherwise == null));
		}
		
		public Node nextPredict (T value) throws StagnantValueException {
			if (isLeafNode()) return this;
			
			for (Node n: children) {
				//System.out.println("Prueba: " + n.name + "\n");
				if (n.test(value)) return n;
			}
			
			if (otherwise != null) return otherwise;
			
			throw new StagnantValueException(value);
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
				for (Node child: children) {
					sb.append(child.name + " ");
				}
				
				if (otherwise != null) sb.append("\nOtherwise: " + otherwise.name + "\n");
				sb.append("\n\n");
				
				for (Node child: children) {
					sb.append(child.toString());
				}
			}
			
			return sb.toString();
		}
	}
	
	public Node node(String nodeName) throws NodeNotFoundException {
		if (nodes.containsKey(nodeName)) return nodes.get(nodeName);
		if (!nodes.isEmpty()) throw new NodeNotFoundException(nodeName);
		
		root = new Node(nodeName, p->true);
		nodes.put(nodeName, root);
		
		return root;
	}
	
	public Map<String, List<T>> predict (List<T> values) {
		Node curr, next = null;
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
			
			endValues.computeIfAbsent(curr.name, k-> new ArrayList<T>()).add(value);
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
		Stack<Node> st = new Stack<>();
		List<Node> camino = new ArrayList<>();
		
		if (root == null) throw new NodeNotFoundException(nodeName);
		if (nodes.containsKey(nodeName) == false) throw new NodeNotFoundException(nodeName);
		
		st.add(root);
		if (dfsRec(nodeName, st, camino) == false) throw new NodeNotFoundException(nodeName);
		System.out.println(camino.stream().map(p->p.name).toList());
		Predicate<T> result = p->true;
		for (Node node: camino) {
			result = result.and(node.predicate);
		}
		
		return result;
	}
	
	private boolean dfsRec(String nodeName, Stack<Node> st, List<Node> camino) {
		boolean found = false;
		
		while ((found == false) && (st.isEmpty() == false)) {
			Node explore = st.pop();
			camino.add(explore);	
			
			if (explore.name.equals(nodeName)) {
				return true;
			}
			
			for (Node child: explore.children) {
				st.add(child);
				if (dfsRec(nodeName, st, camino) == true) return true;
			}
			
			camino.remove(explore);
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return root.toString();
	}
}
