package fp.googleplay.service;

import fp.googleplay.ApplicationCategory;
import fp.googleplay.ApplicationData;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
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
     *
     * @param data the data to work with
     */
    public StreamApplicationDataService(Collection<ApplicationData> data) {
        super(data);
    }

    /**
     * Creates an instance with the given data.
     *
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
                .orElse(0);
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

    @Override
    public ApplicationData getMostPopularApplication(ApplicationCategory category, float minRating, int minReviews,
                                                     LocalDateTime minLastUpdated, boolean multideviceNeeded) {
        return this.data.stream()
                .filter(app -> this.matches(app, minRating, minReviews, -1, minLastUpdated, multideviceNeeded))
                .max(Comparator.comparingInt(ApplicationData::getInstalls))
                .orElse(null);
    }

    @Override
    public Collection<ApplicationData> filterAndSortByRating(ApplicationCategory category, int minReviews) {
        return this.data.stream()
                .filter(app -> app.getCategory().equals(category))
                .filter(app -> app.getReviews() >= minReviews)
                .sorted(Comparator.comparingDouble(ApplicationData::getRating).reversed())
                .toList();
    }

    @Override
    public Map<ApplicationCategory, ApplicationData> getLastUpdatedApplicationsByCategory() {
        return this.data.stream()
                .collect(Collectors.toMap(
                        ApplicationData::getCategory,
                        Function.identity(),
                        BinaryOperator.maxBy(Comparator.comparing(ApplicationData::getLastUpdated))
                ));
    }

    @Override
    public SortedMap<ApplicationCategory, List<ApplicationData>> getMostPopularApplicationsByCategory(int n) {
        return this.data.stream()
                .sorted(Comparator.comparingInt(ApplicationData::getInstalls))
                .collect(Collectors.groupingBy(
                        ApplicationData::getCategory,
                        TreeMap::new,
                        Collectors.collectingAndThen(Collectors.toList(), list -> list.size() > n ? list.subList(0, n) : list)
                ));
    }
}
