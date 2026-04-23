package features;

import java.util.*;

// TODO: Auto-generated Javadoc
/**
 * Tipo: Class Feature: almacena objetos de tipo <T> y contiene un tag identificador.
 *
 * @param <T> parámetro genérico del que esta compuesto el Feature
 * @author Tiago Oselka y Juan Ibáñez
 */
public class Feature<T extends Comparable<? super T>> extends ArrayList<T> {
	
	/** Número de serialización del feature. */
	private static final long serialVersionUID = 1L;

	/** Etiqueta del feature (su nombre). */
	private String tag;
	
	/**
	 * Instancia un nuevo Objeto Feature.
	 *
	 * @param tag parámetro tag
	 */
	public Feature(String tag) {
		this.tag = tag;
	}
	
	/**
	 * Instancia un nuevo Objeto Feature.
	 *
	 * @param source parámetro source
	 */
	public Feature(Feature<T> source) {
		this(source.tag);
		
		this.addAll(source);
	}

	/**
	 * Obtiene el tag del feature.
	 *
	 * @return nombre del tag del feature
	 */
	public String getTag() {
		return tag;
	}
	
	/**
	 * min.
	 *
	 * @return valor de tipo T
	 */
	public T min() {
		if (isEmpty()) return null;
		return Collections.min(this);
	}
	
	/**
	 * max.
	 *
	 * @return valor de tipo T
	 */
	public T max() {
		if (isEmpty()) return null;
		return Collections.max(this);
	}
	
	/**
	 * Obtiene la distribución de valores para un Feature determinado.
	 *
	 * @return mapa con la distribución
	 */
	public Map<T, Long> distribution() {
		Map<T, Long> dist = new HashMap<>();
		
		forEach(p->dist.merge(p, 1L, (a,b)->Long.sum(a, b)));

		return dist;
	}
	
	/**
	 * distributionPositions.
	 *
	 * @return valor de tipo TreeMap
	 */
	public TreeMap<T, List<Integer>> distributionPositions() {
		TreeMap<T, List<Integer>> dist = new TreeMap<>();
		
		int size = this.size();
		
		for (int i = 0; i < size; i++) {
			dist.computeIfAbsent(get(i), t->new ArrayList<>()).add(i);
		}
		
		return dist;
	}
	
}
