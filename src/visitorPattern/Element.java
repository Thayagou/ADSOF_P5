package visitorPattern;

/**
 * Tipo: Interface Element del patrón de diseño Visitor
 */
public interface Element {
	
	/**
	 * accept.
	 *
	 * @param visitor parámetro visitor
	 */
	public void accept(Visitor visitor);
}
