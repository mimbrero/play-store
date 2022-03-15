package fp.util.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class UnitTest {

    protected void printSeparator() {
        print("=====================================");
    }

    protected void print(String s) {
        System.out.println(s);
    }

    protected void print(Object o) {
        System.out.println(o);
    }

    public void init() {
        int successful = 0;
        int exceptions = 0;

        for (Method method : this.getClass().getMethods()) {
            if (!method.isAnnotationPresent(Test.class))
                continue;

            print("\n\n");
            printSeparator();
            print("Test #" + method.getName() + ":");
            printSeparator();

            try {
                method.invoke(this);
                successful++;
            } catch (InvocationTargetException t) {
                print("Catched " + t.getTargetException());
                exceptions++;
            } catch (IllegalAccessException ignored) {
            }
        }

        print("\n\n");
        printSeparator();
        print("Finished running tests. " + successful + " successful tests and " + exceptions + " exceptions catched.");
        printSeparator();
    }
}
