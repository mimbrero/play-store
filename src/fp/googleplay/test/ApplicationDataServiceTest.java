package fp.googleplay.test;

import fp.googleplay.ApplicationCategory;
import fp.googleplay.ApplicationData;
import fp.googleplay.factory.ApplicationDataFactory;
import fp.googleplay.factory.ApplicationDataFactoryImpl;
import fp.googleplay.analyzer.ApplicationDataService;
import fp.googleplay.analyzer.LoopApplicationDataService;
import fp.util.test.Test;
import fp.util.test.UnitTest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static fp.util.test.Assertions.assertEquals;
import static fp.util.test.Assertions.assertThat;

public class ApplicationDataServiceTest extends UnitTest {

    private final ApplicationDataService service;
    private final List<ApplicationData> mockData;

    public ApplicationDataServiceTest(ApplicationDataFactory applicationDataFactory, ApplicationDataService service) throws IOException {
        this.service = service;
        this.mockData = applicationDataFactory.parseCsv("data/mock-data.csv");
        service.add(this.mockData);
    }

    @Test
    public void existsAnAppWithHigherRatingForTheSameCategory_FirstItem_true() {
        ApplicationData app = this.mockData.get(0);
        assertEquals(app.getName(), "Photo Editor & Candy Camera & Grid & ScrapBook");

        Boolean exists = service.existsAnAppWithHigherRatingForTheSameCategory(this.mockData.get(0));
        assertThat(exists, "there should be an app with higher rating than " + app.getName());

        if (exists)
            print("Existe una aplicación con mayor puntuación que " + app.getName() + " de su misma categoría");
    }

    @Test(1)
    public void calculateAverageRating_COMMUNICATION() {
        Float averageRating = service.calculateAverageRating(ApplicationCategory.COMMUNICATION);
        print("La media de las valoraciones de las aplicaciones de tipo COMMUNICATION es " + averageRating);
    }

    @Test(2)
    public void filter_AllParameters_17Elements() {
        Collection<ApplicationData> filtered = service.filter(
                ApplicationCategory.COMMUNICATION, 3.4F, 500, 100_000,
                LocalDateTime.of(2021, 2, 10, 0, 0), false
        );

        assertEquals(filtered.size(), 17);

        print("Hay " + filtered.size() + " aplicaciones de comunicación que tengan al menos 3.4 puntos de valoración, " +
              "500 valoraciones, 100 000 instalaciones y que se actualizaron después del 10/2/2021. Las 5 primeras son:");
        filtered.stream().limit(5).forEachOrdered(x -> System.out.println("- " + x));
    }

    @Test(3)
    public void groupByCategory_COMMUNICATIONHas17Elements() {
        Map<ApplicationCategory, Collection<ApplicationData>> grouped = service.groupByCategory(
                3.4F, 500, 100_000,
                LocalDateTime.of(2021, 2, 10, 0, 0), false
        );

        assertEquals(grouped.get(ApplicationCategory.COMMUNICATION).size(), 17);
        assertEquals(grouped.get(ApplicationCategory.ENTERTAINMENT).size(), 30);

        printGroupByCategory(grouped, ApplicationCategory.COMMUNICATION);
        printGroupByCategory(grouped, ApplicationCategory.ENTERTAINMENT);
    }

    private void printGroupByCategory(Map<ApplicationCategory, Collection<ApplicationData>> grouped, ApplicationCategory category) {
        Collection<ApplicationData> data = grouped.get(category);

        print("Hay " + data.size() + " aplicaciones de " + category + " que tengan al menos 3.4 puntos de valoración, " +
              "500 valoraciones, 100 000 instalaciones y que se actualizaron después del 10/2/2021. Las 5 primeras son:");
        data.stream().limit(5).forEachOrdered(x -> System.out.println("- " + x));
    }

    @Test(4)
    public void getInstallsByCategory() {
        Map<ApplicationCategory, Long> installs = service.getInstallsByCategory();

        assertEquals(installs.get(ApplicationCategory.COMMUNICATION), 22367710000L);
        assertEquals(installs.get(ApplicationCategory.ENTERTAINMENT), 2839060000L);

        print("Las aplicaciones de tipo comunicación suman " + installs.get(ApplicationCategory.COMMUNICATION) + " instalaciones.");
        print("Las aplicaciones de tipo entretenimiento suman " + installs.get(ApplicationCategory.ENTERTAINMENT) + " instalaciones.");
    }

    public static void main(String[] args) throws IOException {
        ApplicationDataFactory factory = new ApplicationDataFactoryImpl();
        ApplicationDataService analyzer = new LoopApplicationDataService();

        new ApplicationDataServiceTest(factory, analyzer).init();
    }
}
