package dataset;

public class Feature<T extends Comparable<T>> {
	private String fName;
	private T value;
	
	public Feature(String fName, T value) {
		this.fName = fName;
		this.value = value;
	}

	public String getfName() {
		return fName;
	}

	public T getValue() {
		return value;
	}
	
}
