package fp.util.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Clase a extender por todos los tests.
 *
 * Para ejecutar el test, se tiene que instanciar y llamar al método {@link #init()}.
 * Los métodos anotados con {@link Test} serán ejecutados en el orden marcado por {@link Test#value()}.
 */
public abstract class UnitTest {

    public void init() {
        TestResults results = new TestResults();

        Arrays.stream(this.getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(Test.class))
                .sorted(Comparator.comparingInt(method -> method.getAnnotation(Test.class).value()))
                .forEachOrdered(method -> this.testMethod(method, results));

        print("\n\n================================================");
        print("Tests terminados. " + results.getSuccessful() + " correctamente y " + results.getExceptions() + " fallidos.");
    }

    protected void printSeparator() {
        print("=====================================");
    }

    protected void print(String s) {
        System.out.println(s);
    }

    protected void print(Object o) {
        System.out.println(o);
    }

    private void testMethod(Method method, TestResults results) {
        print("\n");
        printSeparator();
        print("Test " + method.getName() + ":");
        printSeparator();

        try {
            method.invoke(this);
            results.incrementSuccessful();
        } catch (InvocationTargetException t) {
            t.getTargetException().printStackTrace();
            results.incrementExceptions();
        } catch (IllegalAccessException ignored) {
        }
    }
}
