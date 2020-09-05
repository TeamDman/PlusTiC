package landmaster.plustic.modules;

import java.util.LinkedHashSet;
import java.util.Set;

public interface IModule {
    public static final Set<IModule> modules = new LinkedHashSet<>();

    default void init() {
    }

    default void init2() {
    }

    default void init3() {
    }
}
