package features;

/**
 * Tipo: Class Person: objeto que representa una persona
 * @author Tiago Oselka y Juan Ibáñez
 */
public class Person {
	
	/** Campo name. */
	private String name;
	
	/** Campo age. */
	private int age;
	
	/** Campo weight. */
	private double weight;
	
	/** Campo height. */
	private double height;
	
	/** Campo male. */
	private boolean male;
	
	/**
	 * Instancia un nuevo Objeto Person.
	 *
	 * @param name parámetro name
	 * @param age parámetro age
	 * @param weight parámetro weight
	 * @param height parámetro height
	 * @param male parámetro male
	 */
	public Person(String name, int age, double weight, double height, boolean male) {
		this.name = name;
		this.age = age;
		this.weight = weight;
		this.height = height;
		this.male = male;
	}

	/**
	 * Obtiene Name.
	 *
	 * @return valor de Name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Obtiene Age.
	 *
	 * @return valor de Age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * Obtiene Weight.
	 *
	 * @return valor de Weight
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * Obtiene Height.
	 *
	 * @return valor de Height
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * Comprueba si es Male.
	 *
	 * @return true si es Male, falso en caso contrario
	 */
	public boolean isMale() {
		return male;
	}
	
	/**
	 * To String de una persona
	 *
	 * @return valor de tipo String
	 */
	@Override
	public String toString() {
		return name + " " + age + " " + " " + weight + " " + height;
	}
	
	
}
