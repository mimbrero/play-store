package fp.googleplay.test;

import fp.googleplay.ApplicationCategory;
import fp.googleplay.ApplicationData;
import fp.googleplay.factory.ApplicationDataFactory;
import fp.googleplay.factory.ApplicationDataFactoryImpl;
import fp.util.test.Test;
import fp.util.test.UnitTest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static fp.util.test.Assertions.*;

public class ApplicationDataFactoryTest extends UnitTest {

    private final ApplicationDataFactory factory;

    public ApplicationDataFactoryTest(ApplicationDataFactory factory) {
        this.factory = factory;
    }

    @Test
    public void parse_AllFine() {
        ApplicationData data = factory.parse("Discord,COMMUNICATION,4.7,900000,200M,\"1,000,000,000+\",0,29/12/2021,00:00,v2.6,8.0,false");

        assertEquals(data.getName(), "Discord");
        assertEquals(data.getCategory(), ApplicationCategory.COMMUNICATION);
        assertEquals(data.getRating(), 4.7F);
        assertEquals(data.getReviews(), 900_000);
        assertEquals(data.getSize(), "200M");
        assertEquals(data.getInstalls(), 1_000_000_000);
        assertEquals(data.getPrice(), 0F);
        assertEquals(data.getLastUpdated(), LocalDateTime.of(2021, 12, 29, 0, 0));
        assertEquals(data.getCurrentVersion(), "v2.6");
        assertEquals(data.getAndroidVersion(), "8.0");
        assertEquals(data.getMultiDevice(), false);

        print(data);
    }

    @Test
    public void parse_notEnoughProperties_IllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> factory.parse("Discord,4.7,900000,200M"),
                "the line should be invalid"
        );

        print("Una línea sin los parámetros necesarios da error al parsear.");
    }

    @Test
    public void parse_invalidCategory_IllegalArgumentException() {
        assertThrows(
                IllegalArgumentException.class,
                () -> factory.parse("Discord,INVALID,4.7,900000,200M,\"1,000,000,000+\",0,29/12/2021,00:00,v2.6,8.0,false"),
                "the INVALID ApplicationCategory should not exist"
        );

        print("Una línea con una categoría no parseable da error al parsear.");
    }

    @Test
    public void parseCsv_AllFine() throws IOException {
        List<ApplicationData> data = factory.parseCsv("data/mock-data.csv");

        assertEquals(data.size(), 1000);
        assertEquals(data.get(0).getName(), "Photo Editor & Candy Camera & Grid & ScrapBook");

        print("Hay " + data.size() + " elementos en el CSV. El primero es " + data.get(0));
    }

    public static void main(String[] args) {
        new ApplicationDataFactoryTest(new ApplicationDataFactoryImpl()).init();
    }
}
