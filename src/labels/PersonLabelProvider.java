package labels;

import features.Person;

/**
 * Tipo: Class PersonLabelProvider: Asigna labels de tipo String a objetos de tipo Person
 * @author Tiago Oselka y Juan Ibáñez
 */
public class PersonLabelProvider implements LabelProvider<Person, String>{

	/**
	 * Obtiene la Label asignada a elem
	 *
	 * @param elem Objeto Person a asignar
	 * @return valor de la Label
	 */
	@Override
	public String getLabel(Person elem) {
		if (!elem.isMale()) return "female";
		
		if (elem.getAge() > 65) return "old male";
		if (elem.getAge() > 34) return "middle male";
		else return "young male";
	}

}
