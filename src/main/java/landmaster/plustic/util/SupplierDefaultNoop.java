package landmaster.plustic.util;

import java.util.function.Supplier;

public interface SupplierDefaultNoop<T> extends Supplier<T> {
    @Override
    default T get() {
        return null;
    }
}
