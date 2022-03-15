package fp.googleplay.test;

import fp.googleplay.AppCategory;
import fp.googleplay.AppType;
import fp.googleplay.ApplicationData;
import fp.util.test.Test;
import fp.util.test.UnitTest;

import java.time.Duration;
import java.time.LocalDateTime;

import static fp.util.test.Assertions.*;

public class ApplicationDataTest extends UnitTest {

    //
    // Tests constructor 1
    //

    // Test correcto del constructor 1
    @Test
    public void constructor1_AllFine() {
        ApplicationData data = new ApplicationData(
                "Discord", AppCategory.COMMUNICATION, 4.7f, 900_000, "200M", 1_000_000_000,
                0, LocalDateTime.of(2021, 12, 29, 0, 0), "v2.6",
                "8.0", false
        );

        print(data);
    }

    // Prueba con name vacío
    @Test(1)
    public void constructor1_EmptyName_ThenIllegalArgumentException() {
        String name = "";

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new ApplicationData(name, AppCategory.COMMUNICATION, 4.7f, 900_000, "200M",
                        1_000_000_000, 0, LocalDateTime.now(), "v2.6", "8.0",
                        false),
                "el constructor debería lanzar un IllegalArgumentException porque name está vacío"
        );

        print("Se ha lanzado la excepción " + exception);
    }

    // Prueba con el parámetro lastUpdated en el futuro (1/1/3000)
    @Test(2)
    public void constructor1_FutureUpdate_ThenIllegalArgumentException() {
        LocalDateTime dateTime = LocalDateTime.of(3000, 1, 1, 0, 0);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new ApplicationData("Discord", AppCategory.COMMUNICATION, 4.7f, 900_000,
                        "200M", 1_000_000_000, 0, dateTime, "v2.6", "8.0",
                        false),
                "el constructor debería lanzar un IllegalArgumentException porque lastUpdated es en el futuro"
        );

        print("Se ha lanzado la excepción " + exception);
    }

    // Prueba con 0 instalaciones y 500 valoraciones
    @Test(3)
    public void constructor1_NoInstallsButThereAreReviews_ThenIllegalArgumentException() {
        int installs = 0;
        int reviews = 500;

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new ApplicationData("Discord", AppCategory.COMMUNICATION, 4.7f, reviews, "200M",
                        installs, 0, LocalDateTime.now(), "v2.6", "8.0", false),
                "el constructor debería lanzar un IllegalArgumentException porque no puede haber valoraciones si tiene 0 descargas"
        );

        print("Se ha lanzado la excepción " + exception);
    }

    //
    // Test constructor 2
    //

    @Test(4)
    public void constructor2_AllFine() {
        ApplicationData data = new ApplicationData("Discord", AppCategory.COMMUNICATION, "200M", 0, "v2.6", "8.0", false);

        assertThat(data.getRating() == 0, "el constructor 2 debería poner rating en 0");
        assertThat(data.getReviews() == 0, "el constructor 2 debería poner reviews en 0");
        assertThat(data.getInstalls() == 0, "el constructor 2 debería poner installs en 0");

        print(data);
    }

    //
    // Tests de métodos
    //

    // Mock de un ApplicationData para usar en los tests de métodos
    private final ApplicationData MOCK = new ApplicationData(
            "Discord", AppCategory.COMMUNICATION, 4.7f, 900_000, "200M", 1_000_000_000,
            0, LocalDateTime.of(2021, 12, 29, 0, 0), "v2.6",
            "8.0", false
    );

    // Prueba del método #type(). Saldrá FREE, porque MOCK tiene precio 0
    @Test(5)
    public void getType_PriceIsZero_FREE() {
        ApplicationData data = MOCK;
        assertThat(data.getType() == AppType.FREE, "#getType() debería devolver FREE porque el precio es 0");
        print("Tipo = " + data.getType() + " (el precio es " + data.getPrice() + ").");
    }

    // Prueba del método #timeSinceLastUpdate(Temporal)
    @Test(6)
    public void getTimeSinceLastUpdate_OneDayAfter_OneDay() {
        ApplicationData data = MOCK;
        LocalDateTime oneDayAfter = LocalDateTime.of(2021, 12, 30, 0, 0);

        assertThat(data.getTimeSinceLastUpdate(oneDayAfter).equals(Duration.ofDays(1)), "el tiempo desde la última actualización debería de ser de 1 día");
        print("Tiempo desde la última actualización = " + data.getTimeSinceLastUpdate(oneDayAfter).toDays() + " días");
    }

    // Prueba del método #compareTo()
    @Test(7)
    public void compareTo_SecondWithMoreRating_LessThan0() {
        ApplicationData app1 = MOCK.clone();
        ApplicationData app2 = MOCK.clone();

        app1.setRating(4);
        app2.setRating(4.5f);

        assertThat(app1.compareTo(app2) < 0, "app2 debería ser mayor que app1");
        print("app2 (4.5 de valoración) es mayor que app1 (4 de valoración)");
    }

    // Prueba del método #compareTo() con misma valoración y número de valoraciones pero distinto número de instalaciones
    @Test(8)
    public void compareTo_SameRatingAndReviewsButSecondWithMoreInstalls_GreaterThan0() {
        ApplicationData app1 = MOCK.clone();
        ApplicationData app2 = MOCK.clone();

        app1.setRating(4);
        app2.setRating(4);

        app1.setReviews(900);
        app2.setReviews(900);

        app1.setInstalls(200_000_000);
        app2.setInstalls(100_000_000);

        assertThat(app1.compareTo(app2) > 0, "app1 debería ser mayor que app2");
        print("app1 (200 000 000 instalaciones) es mayor que app2 (100 000 000 instalaciones)");
    }

    public static void main(String[] args) {
        new ApplicationDataTest().init();
    }
}
