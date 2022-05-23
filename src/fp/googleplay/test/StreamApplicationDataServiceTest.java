package fp.googleplay.test;

import fp.googleplay.factory.ApplicationDataFactory;
import fp.googleplay.factory.ApplicationDataFactoryImpl;
import fp.googleplay.service.ApplicationDataService;
import fp.googleplay.service.StreamApplicationDataService;

import java.io.IOException;

public class StreamApplicationDataServiceTest extends ApplicationDataServiceTest {

    public StreamApplicationDataServiceTest(ApplicationDataFactory applicationDataFactory, ApplicationDataService service) throws IOException {
        super(applicationDataFactory, service);
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
