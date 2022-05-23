package fp.googleplay.service;

import fp.googleplay.ApplicationCategory;
import fp.googleplay.ApplicationData;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

/**
 * Implemented with classic loops.
 */
public class LoopApplicationDataService extends AbstractApplicationDataService implements ApplicationDataService {

    /**
     * Creates an instance with an empty {@link ArrayList} of {@link ApplicationData}.
     */
    public LoopApplicationDataService() {
        super();
    }

    /**
     * Creates an instance with the given data.
     * @param data the data to work with
     */
    public LoopApplicationDataService(Collection<ApplicationData> data) {
        super(data);
    }

    /**
     * Creates an instance with the given data.
     * @param data the data to work with
     */
    public LoopApplicationDataService(Stream<ApplicationData> data) {
        super(data);
    }

    @Override
    public Boolean existsAnAppWithHigherRatingForTheSameCategory(ApplicationData reference) {
        for (ApplicationData app : this.data) {
            if (app.getCategory().equals(reference.getCategory()) && app.getRating() > reference.getRating())
                return true;
        }
        return false;
    }

    @Override
    public Float calculateAverageRating(ApplicationCategory category) {
        float sum = 0;
        int size = 0;

        for (ApplicationData app : this.data) {
            if (app.getCategory().equals(category)) {
                sum += app.getRating();
                size++;
            }
        }

        return sum / size;
    }

    @Override
    public Collection<ApplicationData> filter(ApplicationCategory category, float minRating, int minReviews, int minInstalls,
                                              LocalDateTime minLastUpdated, boolean multideviceNeeded) {
        Collection<ApplicationData> data = new ArrayList<>();

        for (ApplicationData app : this.data) {
            if (app.getCategory().equals(category) && this.matches(app, minRating, minReviews, minInstalls, minLastUpdated, multideviceNeeded))
                data.add(app);
        }
        return data;
    }

    @Override
    public Map<ApplicationCategory, Collection<ApplicationData>> groupByCategory(float minRating, int minReviews, int minInstalls,
                                                                                 LocalDateTime minLastUpdated, boolean multideviceNeeded) {
        Map<ApplicationCategory, Collection<ApplicationData>> map = new HashMap<>();

        for (ApplicationData app : this.data) {
            if (!this.matches(app, minRating, minReviews, minInstalls, minLastUpdated, multideviceNeeded))
                continue;

            ApplicationCategory category = app.getCategory();
            if (!map.containsKey(category))
                map.put(category, new ArrayList<>());
            map.get(category).add(app);
        }
        return map;
    }

    @Override
    public Map<ApplicationCategory, Long> getInstallsByCategory() {
        Map<ApplicationCategory, Long> map = new HashMap<>();

        for (ApplicationData app : this.data) {
            ApplicationCategory category = app.getCategory();
            map.put(category, map.getOrDefault(category, 0L) + app.getInstalls());
        }
        return map;
    }
}
