package exceptions;

/**
 * Excepcion DuplicateNodeException: se lanza en caso de que el nodo que se intenta introducir ya se encuentre en el árbol
 * @author Tiago Oselka y Juan Ibáñez
 */
public class DuplicateNodeException extends Exception{
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Campo duplicateName, nombre duplicado */
	private String duplicateName;
	
	/** Mensaje que definimos como el predefinido de la excepción. */
	private static String DF_DUPLICATE_MESSAGE = "El siguiente nodo ya se encuentra en el árbol: ";
	
	/**
	 * Instancia un nuevo Objeto DuplicateNodeException.
	 *
	 * @param message parámetro message
	 * @param nodeName parámetro nodeName
	 */
	public DuplicateNodeException(String message, String nodeName) {
		super(message);
		duplicateName = nodeName;
	}
	
	/**
	 * Instancia un nuevo Objeto DuplicateNodeException.
	 *
	 * @param nodeName Nombre del nodo duplicado
	 */
	public DuplicateNodeException(String nodeName) {
		super(DF_DUPLICATE_MESSAGE);
		duplicateName = nodeName;
	}
	
	/**
	 * Obtiene el mensaje.
	 *
	 * @return valor de Message
	 */
	@Override
	public String getMessage() {
		return super.getMessage() + duplicateName;
	}
}
