package exceptions;

// TODO: Auto-generated Javadoc
/**
 * Se lanza en caso de que el feature buscado no exista.
 */
public class InexistantFeatureException extends Exception{

	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instancia un nuevo Objeto UnexistantFeatureException.
	 *
	 * @param featureName Nombre del feature que se buscó
	 */
	public InexistantFeatureException(String featureName) {
		super("Searched feature \"" + featureName + "\" could not be found");
	}

}
