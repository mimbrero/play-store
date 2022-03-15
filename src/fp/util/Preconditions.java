package fp.util;

public class Preconditions {

    /**
     * Comprueba que la expresión pasada como argumento se evalúa a {@code true}. En caso contrario, lanzará una
     * {@link IllegalArgumentException} con el mensaje propuesto.
     *
     * @param expression expresión a evaluar
     * @param exceptionMessage mensaje que tendrá la excepción
     */
    public static void checkArgument(boolean expression, String exceptionMessage) {
        if (!expression)
            throw new IllegalArgumentException(exceptionMessage);
    }

    /**
     * Comprueba que la expresión pasada como argumento se evalúa a {@code true}. En caso contrario, lanzará una
     * {@link IllegalStateException} con el mensaje propuesto.
     *
     * @param expression expresión a evaluar
     * @param exceptionMessage mensaje que tendrá la excepción
     */
    public static void checkState(boolean expression, String exceptionMessage) {
        if (!expression)
            throw new IllegalStateException(exceptionMessage);
    }

    /**
     * Comprueba que el objeto pasado como argumento no es {@code null}. En caso contrario, lanzará una
     * {@link NullPointerException} con el mensaje propuesto.
     *
     * @param toCheck objeto a comprobar
     * @param exceptionMessage mensaje que tendrá la excepción
     * @return el objeto pasado como argumento
     */
    public static <T> T checkNotNull(T toCheck, String exceptionMessage) {
        if (toCheck == null)
            throw new NullPointerException(exceptionMessage);
        return toCheck;
    }

    /**
     * Comprueba que el objeto pasado como argumento es {@code null}. En caso contrario, lanzará una
     * {@link IllegalStateException} con el mensaje propuesto.
     *
     * @param toCheck objeto a comprobar
     * @param exceptionMessage mensaje que tendrá la excepción
     */
    public static void checkNull(Object toCheck, String exceptionMessage) {
        checkState(toCheck == null, exceptionMessage);
    }
}