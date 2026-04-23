package exceptions;

// TODO: Auto-generated Javadoc
/**
 * Tipo: Class NodeNotFoundException.
 */
public class NodeNotFoundException extends Exception{
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Campo nodeName. Nombre del nodo no encontrado*/
	private String nodeName;
	
	/** Mensaje que definimos como el predefinido de la excepción. */
	private static String DF_NOT_FOUND_MESSAGE = "No se enconttró el nodo con nombre ";
	
	/**
	 * Instancia un nuevo Objeto NodeNotFoundException.
	 *
	 * @param message Mensaje a enviar
	 * @param nodeName nombre del nodo que no fue encontrado
	 */
	public NodeNotFoundException(String message, String nodeName) {
		super(message);
		this.nodeName = nodeName;
	}
	
	/**
	 * Instancia un nuevo Objeto NodeNotFoundException.
	 *
	 * @param nodeName parámetro nodeName
	 */
	public NodeNotFoundException(String nodeName) {
		super(DF_NOT_FOUND_MESSAGE);
		this.nodeName = nodeName;
	}
	
	/**
	 * Obtiene el mensaje.
	 *
	 * @return valor de Message
	 */
	@Override
	public String getMessage() {
		return super.getMessage() + nodeName;
	}
}
