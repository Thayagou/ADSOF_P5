package labels;

public interface LabelProvider<T, L> {
	public L getLabel(T elem);
}
