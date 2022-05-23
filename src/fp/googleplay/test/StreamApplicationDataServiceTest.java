package fp.googleplay.test;

import fp.googleplay.ApplicationCategory;
import fp.googleplay.ApplicationData;
import fp.googleplay.factory.ApplicationDataFactory;
import fp.googleplay.factory.ApplicationDataFactoryImpl;
import fp.googleplay.service.ApplicationDataService;
import fp.googleplay.service.StreamApplicationDataService;
import fp.util.test.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import static fp.util.test.Assertions.assertEquals;

public class StreamApplicationDataServiceTest extends ApplicationDataServiceTest {

    public StreamApplicationDataServiceTest(ApplicationDataFactory applicationDataFactory, ApplicationDataService service) throws IOException {
        super(applicationDataFactory, service);
    }

    @Test(5)
    public void getMostPopularApplication_COMMUNICATION_Messenger() {
        ApplicationData app = service.getMostPopularApplication(ApplicationCategory.COMMUNICATION, -1,
                -1, LocalDateTime.MIN, false);

        assertEquals(app.getName(), "Messenger – Text and Video Chat for Free");
        print("La aplicación de comunicación con más descargas es " + app);
    }

    @Test(6)
    public void filterAndSortByRating_COMMUNICATION_M() {
        List<ApplicationData> filtered = service.filterAndSortByRating(ApplicationCategory.COMMUNICATION, 0);

        print("Hay " + filtered.size() + " aplicaciones de comunicación. Las 3 mejor valoradas son:");
        filtered.stream().limit(3).forEachOrdered(x -> System.out.println("- " + x));
    }

    @Test(7)
    public void getLastUpdatedApplications() {
        List<ApplicationData> apps = service.getLastUpdatedApplications(7);

        print("Las últimas 7 aplicaciones actualizadas son:");
        apps.forEach(x -> System.out.println("- " + x));
    }

    @Test(8)
    public void getLastUpdatedApplicationsByCategory() {
        Map<ApplicationCategory, ApplicationData> apps = service.getLastUpdatedApplicationsByCategory();

        print("La aplicación que más recientemente se actualizó de la categoría de arte y diseño es " + apps.get(ApplicationCategory.ART_AND_DESIGN));
    }

    @Test(9)
    public void getMostPopularApplicationsByCategory() {
        SortedMap<ApplicationCategory, List<ApplicationData>> apps = service.getMostPopularApplicationsByCategory(5);

        print("Las 5 aplicaciones con más descargas de la categoría de comunicación son:");
        apps.get(ApplicationCategory.COMMUNICATION).forEach(x -> System.out.println("- " + x));
    }

    @Test(10)
    public void getCategoryWithMostInstallations() {
        print("La categoría con más instalaciones es " + service.getCategoryWithMostInstallations());
    }

    public static void main(String[] args) {
        try {
            new StreamApplicationDataServiceTest(new ApplicationDataFactoryImpl(), new StreamApplicationDataService()).init();
        } catch (IOException e) {
            System.out.println("Ha habido un error relacionado al archivo CSV para hacer pruebas:");
            e.printStackTrace();
        }
    }
}
