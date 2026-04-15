package labels;

public interface LabelProvider<T, L extends Label> {
	public L getLabel(T elem);
}
