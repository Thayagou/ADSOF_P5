package visitorPattern;

/**
 * Tipo: Interface Element del patrón de diseño Visitor
 * @author Tiago Oselka y Juan Ibáñez
 */
public interface Element {
	
	/**
	 * Acepta la visita de un Visitante
	 *
	 * @param visitor Visitante actual
	 */
	public void accept(Visitor visitor);
}
