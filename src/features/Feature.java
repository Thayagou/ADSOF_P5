package features;

import java.util.*;

/**
 * Tipo: Class Feature.
 *
 * @param <T> parámetro genérico
 */
public class Feature<T extends Comparable<T>> extends ArrayList<T> {
	
	private static final long serialVersionUID = 1L;

	/** Campo name. */
	private String fName;
	
	/**
	 * Instancia un nuevo Objeto Feature.
	 *
	 * @param fName parámetro fName
	 */
	public Feature(String fName) {
		this.fName = fName;
	}

	/**
	 * Obtiene el nombre del feature
	 *
	 * @return nombre del feature
	 */
	public String getfName() {
		return fName;
	}
	
}
