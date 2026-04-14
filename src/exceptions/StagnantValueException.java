package exceptions;

public class StagnantValueException extends Exception {
	private static final long serialVersionUID = 1L;

	private Object value;
	
	private static String DF_STAGNANT_MESSAGE = "El objeto no logró alcanzar una hoja del árbol: ";
	
	public StagnantValueException(Object value) {
		super(DF_STAGNANT_MESSAGE);
		this.value = value;
	}
	
	@Override
	public String getMessage() {
		return super.getMessage() + value.toString();
	}
}
