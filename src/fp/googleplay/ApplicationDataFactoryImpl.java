package fp.googleplay;

import fp.util.LocalDateTimeParser;
import fp.util.Preconditions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ApplicationDataFactoryImpl implements ApplicationDataFactory {

    @Override
    public ApplicationData parse(String line) throws IllegalArgumentException {
        List<String> parts = Arrays.stream(line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)")) // https://www.baeldung.com/java-split-string-commas
                .map(s -> s.replace("\"", ""))
                .map(String::trim)
                .toList();

        Preconditions.checkArgument(parts.size() == 12, "the given line does not match the needed pattern");

        String name = parts.get(0);
        ApplicationCategory category = ApplicationCategory.valueOf(parts.get(1));
        Float rating = Float.parseFloat(parts.get(2));
        Integer reviews = Integer.parseInt(parts.get(3));
        String size = parts.get(4);
        Integer installs = Integer.parseInt(parts.get(5).replaceAll("[,+]", "")); // removes ',' and '+' characters
        Float price = Float.parseFloat(parts.get(6));
        LocalDateTime lastUpdated = LocalDateTimeParser.parse(parts.get(7), parts.get(8));
        String version = parts.get(9);
        String androidVersion = parts.get(10);
        Boolean multidevice = Boolean.parseBoolean(parts.get(11));

        return new ApplicationData(name, category, rating, reviews, size, installs, price, lastUpdated, version, androidVersion, multidevice);
    }

    @Override
    public List<ApplicationData> parseCsv(String filePath) throws IOException {
        Path path = Path.of(filePath);

        return Files.lines(path)
                .skip(1) // skip the first line, the header
                .map(this::parse)
                .toList();
    }
}
