package colonelgerdauf.plustic.util;

import java.util.function.*;

public interface SupplierDefaultNoop<T> extends Supplier<T> {
	@Override
	default T get() {
		return null;
	}
}
