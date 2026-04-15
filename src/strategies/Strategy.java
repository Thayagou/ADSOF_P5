package strategies;

import features.*;
import java.util.*;

public interface Strategy {
	Feature<?> getBestFeature(List<Feature<?>> features);
}
