package decisiontrees;

import java.util.function.Predicate;

import dataset.Dataset;
import exceptions.*;
import visitorPattern.Element;
import visitorPattern.Visitor;

import java.util.*;

/**
 * Tipo: Class DecisionTree: Árbol cuyos nodos contienen predicados que condicionan acceso y permiten exploración
 *
 * @param <T> parámetro genérico
 */
public class DecisionTree<T> implements Element{
	
	/** Nombre del nodo */
	private String name = null;
	
	/** Predicado que determina los objetos que acceden al nodo */
	private Predicate<T> predicate = null;
	
	/** Lista de hijos del nodo */
	private List<DecisionTree<T>> children = new ArrayList<>();
	
	/** Nodo Otherwise */
	private DecisionTree<T> otherwise = null;

	/**
	 * Instancia un nuevo Objeto DecisionTree.
	 *
	 * @param rootName nombre del root
	 */
	public DecisionTree(String rootName) {
		this.name = rootName;
		this.predicate = p->true;
	}
	
	/**
	 * Instancia un nuevo Objeto DecisionTree.
	 */
	public DecisionTree() { 
		this.predicate = p->true;
	}
	
	/**
	 * Instancia un nuevo Objeto DecisionTree.
	 *
	 * @param nodeName parámetro nodeName
	 * @param predicate parámetro predicate
	 */
	public DecisionTree(String nodeName, Predicate<T> predicate) {
		this.name = nodeName;
		this.predicate = predicate;
	}

	/**
	 * Si no había raíz, establece su nombre a nodeName, en caso contrario busca el nodo  con dicho nombre
	 *
	 * @param nodeName nombre del nodo buscado
	 * @return DecisionTree que representa el nodo buscado
	 * @throws NodeNotFoundException se lanza en caso de que el nodo buscado no se encuentre
	 */
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
	 *
	 * @param value Valor que tratamos de encontrar hoja
	 * @return valor de tipo DecisionTree
	 * @throws StagnantValueException excepción de tipo StagnantValueException
	 */
	private DecisionTree<T> nextPredict (T value) throws StagnantValueException {
		if (isLeafNode()) return this;
		
		for (DecisionTree<T> n: children) {
			//System.out.println("Prueba: " + n.name + "\n");
			if (n.test(value)) return n;
		}
		
		if (otherwise != null) return otherwise;
		
		throw new StagnantValueException(value);
	}

	/**
	 * Obtiene etiquetas resultado para la entrada proporcionada
	 *
	 * @param values Lista de valores a obtener etiquetas
	 * @return Mapa con etiquetas y valores
	 */
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

	/**
	 * Obtiene etiquetas resultado para los objetos almacenados en el dataset
	 *
	 * @param dataset Dataset al que se desea proporcionar etiquetas
	 * @return Mapa con etiquetas y valores
	 */
	public Map<String, List<T>> predict(Dataset<T> dataset) {
		return predict(dataset.getValues());
	}

	/**
	 * Obtiene etiquetas resultado para la entrada proporcionada
	 *
	 * @param values Array de valores a obtener etiquetas
	 * @return Mapa con etiquetas y valores
	 */
	@SuppressWarnings("unchecked")
	public Map<String, List<T>> predict(T... values) {
		return predict(List.of(values));
	}

	/**
	 * Obtiene el predicado resultante de pasar por todos los nodos del camino hasta el nodo buscado
	 *
	 * @param nodeName Nombre del nodo buscado
	 * @return Predicado resultante
	 * @throws NodeNotFoundException Se lanza en caso de que no se encuentre el nodo
	 */
	public Predicate<T> getPredicate(String nodeName) throws NodeNotFoundException {
		Predicate<T> result = predicateRec(this, nodeName, t -> true);

		if (result == null)
			throw new NodeNotFoundException(nodeName);
		return result;
	}

	/**
	 * Versión recursiva de getPredicate
	 *
	 * @param node Nodo actual siendo explorado
	 * @param target Nombre del nodo buscado
	 * @param predAcum Predicado obtenido recorriendo el camino hasta el nodo actual
	 * @return Predicado resultante
	 */
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

	/**
	 * Obtiene el nodo con el nombre buscado a partir de un DFS
	 *
	 * @param nodeName Nombre del nodo buscado
	 * @return Nodo representado por un DecisionTree
	 * @throws NodeNotFoundException Se lanza en el caso de que no se encuentre el nodo buscado
	 */
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
	 * Añade un nodo como hijo.
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
	 * Añade un hijo al nodo con el nombre y la condicion especificados.
	 *
	 * @param name Nombre del nodo hijo
	 * @param predicate Predicado del nodo hijo
	 * @return Nodo al que se le añade el hijo
	 */
	public DecisionTree<T> withCondition(String name, Predicate<T> predicate) {	
		return addChild(new DecisionTree<>(name, predicate));
	}
	
	/**
	 * Añade el nodo otherwise a este nodo.
	 *
	 * @param name Nombre del nodo othrwise
	 * @return Nodo al que se añade el otherwise
	 */
	public DecisionTree<T> otherwise(String name) {
		return otherwise(new DecisionTree<>(name));
	}
	
	/**
	 * Añade el DecisionTree al nodo Otherwise
	 *
	 * @param otherwise DecisionTree a añadir
	 * @return DecisionTree añadido
	 */
	public DecisionTree<T> otherwise(DecisionTree<T> otherwise) {
		Predicate<T> otherwisePredicate = p->true;
		
		for (DecisionTree<T> child: children) {
			otherwisePredicate = otherwisePredicate.and(child.predicate.negate());
		}
		
		otherwise.predicate = otherwisePredicate;
		
		this.otherwise = otherwise;
		
		return this;
	}

	/**
	 * Realiza test del predicado del nodo
	 *
	 * @param value valor a testear
	 * @return resultado del predicado
	 */
	public boolean test(T value) {
		return predicate.test(value);
	}
	
	/**
	 * Comprueba si el nodo es una hoja
	 *
	 * @return true si es una hoja, falso en caso contrario
	 */
	public boolean isLeafNode() {
		return (children.isEmpty() && (otherwise == null));
	}
	
	/**
	 * Obtiene Name.
	 *
	 * @return valor de Name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Establece Name.
	 *
	 * @param name nuevo valor
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Obtiene Children.
	 *
	 * @return valor de Children
	 */
	public List<DecisionTree<T>> getChildren() {
		return Collections.unmodifiableList(children);
	}
	
	/**
	 * Obtiene Otherwise.
	 *
	 * @return valor de Otherwise
	 */
	public DecisionTree<T> getOtherwise() {
		return otherwise;
	}
	
	/**
	 * Obtiene Predicate.
	 *
	 * @return valor de Predicate
	 */
	public Predicate<T> getPredicate() {
		return predicate;
	}
	
	/**
	 * toString.
	 *
	 * @return valor de tipo String
	 */
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

	/**
	 * Método accept del patrón Visitor que implementa el double dispatch
	 *
	 * @param visitor Visitante del árbol
	 */
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
		
	}

}

	

