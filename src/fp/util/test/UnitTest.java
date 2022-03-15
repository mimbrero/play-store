package fp.util.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public abstract class UnitTest {

    public void init() {
        TestResults results = new TestResults();

        Arrays.stream(this.getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(Test.class))
                .forEachOrdered(method -> this.testMethod(method, results));

        print("\n\n");
        printSeparator();
        print("Tests terminados. " + results.getSuccessful() + " correctamente y " + results.getExceptions() + " excepciones capturadas.");
        printSeparator();
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
        print("\n\n");
        printSeparator();
        print("Test #" + method.getName() + ":");
        printSeparator();

        try {
            method.invoke(this);
            results.incrementSuccessful();
        } catch (InvocationTargetException t) {
            print("Capturada " + t.getTargetException() + ".");
            results.incrementExceptions();
        } catch (IllegalAccessException ignored) {
        }
    }
}
