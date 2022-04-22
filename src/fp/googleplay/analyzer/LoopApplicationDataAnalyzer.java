package fp.googleplay.analyzer;

import fp.googleplay.ApplicationCategory;
import fp.googleplay.ApplicationData;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Implemented with classic loops.
 */
public class LoopApplicationDataAnalyzer extends AbstractApplicationDataAnalyzer implements ApplicationDataAnalyzer {

    public LoopApplicationDataAnalyzer() {
        super();
    }

    public LoopApplicationDataAnalyzer(Collection<ApplicationData> data) {
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
