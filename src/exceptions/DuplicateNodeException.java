package exceptions;

/**
 * Tipo: Class DuplicateNodeException.
 */
public class DuplicateNodeException extends Exception{
	
	private static final long serialVersionUID = 1L;

	/** Campo duplicateName. */
	private String duplicateName;
	
	/** Mensaje que definimos como el predefinido de la excepción */
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
	 * @param nodeName nombre del nodo duplicado
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
