package landmaster.plustic.util;

public interface RunnableDefaultNoop extends Runnable {
    @Override
    default void run() {
    }
}
