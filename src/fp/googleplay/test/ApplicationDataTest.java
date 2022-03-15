package fp.googleplay.test;

import fp.googleplay.AppCategory;
import fp.googleplay.AppType;
import fp.googleplay.ApplicationData;
import fp.util.test.Test;
import fp.util.test.UnitTest;

import java.time.Duration;
import java.time.LocalDateTime;

import static fp.util.Preconditions.assertThrows;
import static fp.util.Preconditions.checkState;

public class ApplicationDataTest extends UnitTest {

    private final ApplicationData MOCK; // Mock de un ApplicationData para usar en los tests de métodos

    public ApplicationDataTest() {
        MOCK = new ApplicationData(
                "Discord", AppCategory.COMMUNICATION, 4.7f, 900_000, "200M", 1_000_000_000,
                0, LocalDateTime.of(2021, 12, 29, 0, 0), "v2.6",
                "8.0", false
        );
    }

    //
    // Tests constructor 1
    //

    @Test
    public void constructor1_AllFine() {
        ApplicationData data = new ApplicationData(
                "Discord", AppCategory.COMMUNICATION, 4.7f, 900_000, "200M", 1_000_000_000,
                0, LocalDateTime.of(2021, 12, 29, 0, 0), "v2.6",
                "8.0", false
        );

        print(data);
    }

    @Test(1)
    public void constructor1_EmptyName_ThenIllegalArgumentException() {
        String name = "";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new ApplicationData(name, AppCategory.COMMUNICATION, 4.7f, 900_000, "200M",
                        1_000_000_000, 0, LocalDateTime.now(), "v2.6", "8.0",
                        false)
        );

        print("Se ha lanzado la excepción " + exception);
    }

    @Test(2)
    public void constructor1_FutureUpdate_ThenIllegalArgumentException() {
        LocalDateTime dateTime = LocalDateTime.of(3000, 1, 1, 0, 0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new ApplicationData("Discord", AppCategory.COMMUNICATION, 4.7f, 900_000,
                        "200M", 1_000_000_000, 0, dateTime, "v2.6", "8.0",
                        false)
        );

        print("Se ha lanzado la excepción " + exception);
    }

    @Test(3)
    public void constructor1_NoInstallsButThereAreReviews_ThenIllegalArgumentException() {
        int installs = 0;
        int reviews = 500;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new ApplicationData("Discord", AppCategory.COMMUNICATION, 4.7f, reviews, "200M",
                        installs, 0, LocalDateTime.now(), "v2.6", "8.0", false)
        );

        print("Se ha lanzado la excepción " + exception);
    }

    //
    // Test constructor 2
    //

    @Test(4)
    public void constructor2_AllFine() {
        ApplicationData data = new ApplicationData("Discord", AppCategory.COMMUNICATION, "200M", 0, "v2.6", "8.0", false);

        checkState(data.rating() == 0);
        checkState(data.reviews() == 0);
        checkState(data.installs() == 0);

        print(data);
    }

    //
    // Tests de métodos
    //

    @Test(5)
    public void type_PriceIsZero_FREE() {
        ApplicationData data = MOCK;
        checkState(data.type() == AppType.FREE);

        print("Tipo = " + data.type() + " (el precio es " + data.price() + ").");
    }

    @Test(6)
    public void timeSinceLastUpdate_OneDayAfter_OneDay() {
        ApplicationData data = MOCK;
        LocalDateTime oneDayAfter = LocalDateTime.of(2021, 12, 30, 0, 0);

        checkState(data.timeSinceLastUpdate(oneDayAfter).equals(Duration.ofDays(1)));
        print("Tiempo desde la última actualización = " + data.timeSinceLastUpdate(oneDayAfter).toDays() + " días");
    }

    public static void main(String[] args) {
        new ApplicationDataTest().init();
    }
}
