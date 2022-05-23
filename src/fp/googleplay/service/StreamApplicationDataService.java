package fp.googleplay.service;

import fp.googleplay.ApplicationCategory;
import fp.googleplay.ApplicationData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implemented with streams.
 */
public class StreamApplicationDataService extends AbstractApplicationDataService {

    /**
     * Creates an instance with an empty {@link ArrayList} of {@link ApplicationData}.
     */
    public StreamApplicationDataService() {
        super();
    }

    /**
     * Creates an instance with the given data.
     * @param data the data to work with
     */
    public StreamApplicationDataService(Collection<ApplicationData> data) {
        super(data);
    }

    /**
     * Creates an instance with the given data.
     * @param data the data to work with
     */
    public StreamApplicationDataService(Stream<ApplicationData> data) {
        super(data);
    }

    @Override
    public Boolean existsAnAppWithHigherRatingForTheSameCategory(ApplicationData applicationData) {
        return this.data.stream().anyMatch(data -> data.getRating() > applicationData.getRating());
    }

    @Override
    public Double calculateAverageRating(ApplicationCategory category) {
        return this.data.stream()
                .filter(data -> data.getCategory().equals(category))
                .mapToDouble(ApplicationData::getRating)
                .average()
                .orElse(Double.NaN);
    }

    @Override
    public Collection<ApplicationData> filter(ApplicationCategory category, float minRating, int minReviews, int minInstalls,
                                              LocalDateTime minLastUpdated, boolean multideviceNeeded) {
        return this.data.stream()
                .filter(app -> app.getCategory().equals(category))
                .filter(app -> this.matches(app, minRating, minReviews, minInstalls, minLastUpdated, multideviceNeeded))
                .collect(Collectors.toList());
    }

    @Override
    public Map<ApplicationCategory, List<ApplicationData>> groupByCategory(float minRating, int minReviews, int minInstalls, LocalDateTime minLastUpdated, boolean multideviceNeeded) {
        return this.data.stream()
                .filter(app -> this.matches(app, minRating, minReviews, minInstalls, minLastUpdated, multideviceNeeded))
                .collect(Collectors.groupingBy(ApplicationData::getCategory));
    }

    @Override
    public Map<ApplicationCategory, Long> getInstallsByCategory() {
        return this.data.stream()
                .collect(Collectors.groupingBy(
                        ApplicationData::getCategory,
                        Collectors.summingLong(ApplicationData::getInstalls)
                ));
    }
}
