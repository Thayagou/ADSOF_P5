package exceptions;

/**
 * Excepcion StagnantValueException: se lanza en caso de que el objeto introducido en el árbol no haya conseguido llegar a una hoja del árbol
 * @author Tiago Oselka y Juan Ibáñez
 */
public class StagnantValueException extends Exception {
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instancia un nuevo Objeto StagnantValueException.
	 *
	 * @param value Objeto que no logró llegar al final del árbol
	 */
	public StagnantValueException(Object value) {
		super("El objeto no logró alcanzar una hoja del árbol: " + value.toString());
	}
}
