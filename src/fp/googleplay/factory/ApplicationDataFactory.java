package fp.googleplay.factory;

import fp.googleplay.ApplicationCategory;
import fp.googleplay.ApplicationData;
import fp.googleplay.service.ApplicationDataService;
import fp.util.LocalDateTimeParser;

import java.io.IOException;
import java.util.List;

public interface ApplicationDataFactory {

    enum Implementation {
        LOOP, STREAM
    }

    /**
     * Parses the given line into a {@link ApplicationData} instance.
     * The line must match the {@code String app,ApplicationCategory category,Float rating,Integer reviews,String size,
     * Integer installs,Float price,String lastUpdatedDate,String lastUpdatedTime,String version,String androidVersion,
     * Boolean multidevice} pattern.
     *
     * @param line the line to parse
     * @return an {@link ApplicationData} instance parsed from the given line
     * @throws IllegalArgumentException if the line does not match the pattern above
     * @throws IllegalArgumentException if the {@code category} property cannot be parsed as an {@link ApplicationCategory}
     * @throws NumberFormatException    if any number property cannot be parsed as one
     * @see LocalDateTimeParser#parse(String, String)
     */
    ApplicationData parse(String line) throws IllegalArgumentException;

    /**
     * Returns a list of {@link ApplicationData} for the given CSV file. They will be parsed according to {@link #parse(String)}.
     *
     * @param filePath the path of the CSV file
     * @return a list of {@link ApplicationData} for the given CSV file
     * @throws IOException if an I/O exception related to the file is thrown
     * @see #parse(String)
     */
    List<ApplicationData> parseCsv(String filePath) throws IOException;

    /**
     * Returns an {@link ApplicationDataService} for the given CSV file. They will be parsed according to {@link #parse(String)}.
     *
     * @param filePath the path of the CSV file
     * @param implementation implementation of {@link ApplicationDataService} to instantiate
     * @return a list of {@link ApplicationData} for the given CSV file
     * @throws IOException if an I/O exception related to the file is thrown
     * @see #parse(String)
     */
    ApplicationDataService parseCsv(String filePath, Implementation implementation) throws IOException;
}
