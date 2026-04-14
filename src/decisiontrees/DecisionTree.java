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
			
			Node newNode = new Node(name, predicate);
			children.add(newNode);
			
			nodes.put(name, newNode);
			
			return newNode;
		}
		
		public Node otherwise(String name) throws DuplicateNodeException {
			if (nodes.containsKey(name)) throw new DuplicateNodeException(name);
			otherwise = new Node(name);
			
			nodes.put(name, otherwise);
			
			return otherwise;
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
				if (n.test(value)) return n;
			}
			
			if (otherwise != null) return otherwise;
			
			throw new StagnantValueException(value);
		}
	}
	
	public Node node(String nodeName) throws NodeNotFoundException {
		if (nodes.containsKey(nodeName)) return nodes.get(nodeName);
		if (!nodes.isEmpty()) throw new NodeNotFoundException(nodeName);
		
		root = new Node(nodeName);
		nodes.put(nodeName, root);
		
		return root;
	}
	
	/* Si no se puede guardar en Map
	public Node node(String nodeName) throws NodeNotFoundException {
		Stack<Node> dfs = new Stack<>();
		
		if (root == null) throw new NodeNotFoundException(nodeName);
		dfs.add(root);
		
		while (dfs.isEmpty() == false) {
			Node explore = dfs.pop();
			if (explore.name.equals(nodeName)) return explore;
			
			for (Node n: explore.children) {
				dfs.add(n);
			}
		}
		
		throw new NodeNotFoundException(nodeName);
	}*/
	
	public Map<String, List<T>> predict (List<T> values) {
		Node curr = root, next = null;
		Map<String, List<T>> endValues = new HashMap<>();
		
		for (T value: values) {
			
			do {
				try {
					next = curr.nextPredict(value);
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
	
	public Map<String, List<T>> predict (T...values) {
		return predict(List.of(values));
	}
}
