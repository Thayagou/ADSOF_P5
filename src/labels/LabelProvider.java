package labels;

/**
 * Tipo: Interface LabelProvider: Asigna labels de tipo L a objetos de tipo T
 *
 * @param <T> parámetro genérico que representa al tipo de objeto al que se le asigna la label
 * @param <L> parámetro genérico que representa a la clase de la label
 * 
 * @author Tiago Oselka y Juan Ibáñez
 */
public interface LabelProvider<T, L> {
	
	/**
	 * Obtiene la Label asignada a elem
	 *
	 * @param elem Objeto a asignar la label
	 * @return valor de la Label
	 */
	public L getLabel(T elem);
}
