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

	/**
	 * Obtiene el tag del feature
	 *
	 * @return nombre del tag del feature
	 */
	public String getTag() {
		return tag;
	}
	
}
