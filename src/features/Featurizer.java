package features;

import java.util.*;

public interface Featurizer<T> {

	public List<Feature<?>> getFeatures(T data);
}
