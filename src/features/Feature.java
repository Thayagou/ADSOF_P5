package features;

import java.util.*;

/**
 * Tipo: Class Feature.
 *
 * @param <T> parámetro genérico
 */
public class Feature<T extends Comparable<T>> extends ArrayList<T> {
	
	private static final long serialVersionUID = 1L;

	/** Etiqueta del feature (su nombre). */
	private String tag;
	
	/**
	 * Instancia un nuevo Objeto Feature.
	 *
	 * @param fName parámetro fName
	 */
	public Feature(String tag) {
		this.tag = tag;
	}
	
	public Feature(Feature<T> feature) {
		this(feature.tag);
		
		this.addAll(feature);
	}

	/**
	 * Obtiene el tag del feature
	 *
	 * @return nombre del tag del feature
	 */
	public String getTag() {
		return tag;
	}
	
	public T min() {
		if (isEmpty()) return null;
		return Collections.min(this);
	}
	
	public T max() {
		if (isEmpty()) return null;
		return Collections.max(this);
	}
	
	/**
	 * Obtiene la distribución de valores para un Feature determinado
	 * 
	 * @return mapa con la distribución
	 */
	public Map<T, Long> distribution() {
		Map<T, Long> dist = new HashMap<>();
		
		forEach(p->dist.merge(p, 1L, (a,b)->Long.sum(a, b)));

		return dist;
	}
	
	public TreeMap<T, List<Integer>> distributionPositions() {
		TreeMap<T, List<Integer>> dist = new TreeMap<>();
		
		int size = this.size();
		
		for (int i = 0; i < size; i++) {
			dist.computeIfAbsent(get(i), t->new ArrayList<>()).add(i);
		}
		
		return dist;
	}
	
}
