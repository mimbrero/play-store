package fp.util.test;

public class Assertions {

    private Assertions() {
        // Non instantiable static class
    }

    /**
     * Comprueba que la expresión pasada como argumento se evalúa a {@code true}.
     *
     * @param expression expresión a evaluar
     */
    public static void assertThat(boolean expression) {
        assertThat(expression, null);
    }

    /**
     * Comprueba que la expresión pasada como argumento se evalúa a {@code true}.
     *
     * @param expression       expresión a evaluar
     * @param exceptionMessage mensaje que tendrá la excepción
     */
    public static void assertThat(boolean expression, String exceptionMessage) {
        if (!expression)
            throw new AssertionError(exceptionMessage);
    }

    /**
     * Comprueba que el {@link Runnable} pasado como argumento lanza una excepción del tipo especificado.
     * Esto hace que la excepción no se lance.
     */
    public static <T extends Throwable> T assertThrows(Class<T> throwableClass, Runnable runnable) {
        return assertThrows(throwableClass, runnable, "no se ha lanzado ninguna excepción del tipo dado");
    }

    /**
     * Comprueba que el {@link Runnable} pasado como argumento lanza una excepción del tipo especificado.
     * Esto hace que la excepción no se lance.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Throwable> T assertThrows(Class<T> throwableClass, Runnable runnable, String exceptionMessage) {
        try {
            runnable.run();
        } catch (Throwable throwable) {
            if (throwableClass.isInstance(throwable)) {
                return (T) throwable;
            }
            throw throwable;
        }
        throw new AssertionError(exceptionMessage);
    }
}
