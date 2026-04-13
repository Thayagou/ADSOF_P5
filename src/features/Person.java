package features;

public class Person {
	private String name;
	private int age;
	private double weight;
	private double height;
	private boolean male;
	
	public Person(String name, int age, double weight, double height, boolean male) {
		this.name = name;
		this.age = age;
		this.weight = weight;
		this.height = height;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public double getWeight() {
		return weight;
	}

	public double getHeight() {
		return height;
	}

	public boolean isMale() {
		return male;
	}
	
	
}
