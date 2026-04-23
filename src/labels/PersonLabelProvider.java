package labels;

import features.Person;

public class PersonLabelProvider implements LabelProvider<Person, String>{

	@Override
	public String getLabel(Person elem) {
		if (!elem.isMale()) return "female";
		
		if (elem.getAge() > 65) return "old male";
		if (elem.getAge() > 34) return "middle male";
		else return "young male";
	}

}
