package fp.googleplay.service;

import fp.googleplay.ApplicationCategory;
import fp.googleplay.ApplicationData;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

public abstract class AbstractApplicationDataService implements ApplicationDataService {

    protected final Collection<ApplicationData> data;

    /**
     * Creates an instance with an empty {@link ArrayList} of {@link ApplicationData}.
     */
    public AbstractApplicationDataService() {
        this(new ArrayList<>());
    }

    /**
     * Creates an instance with the given data.
     * @param data the data to work with
     */
    public AbstractApplicationDataService(Collection<ApplicationData> data) {
        this.data = data;
    }

    /**
     * Creates an instance with the given data.
     * @param data the data to work with
     */
    public AbstractApplicationDataService(Stream<ApplicationData> data) {
        this(data.toList());
    }

    // ----------------------------------------------------------
    // DATA MANAGEMENT
    // ----------------------------------------------------------

    @Override
    public Integer getDataSize() {
        return this.data.size();
    }

    @Override
    public void add(ApplicationData data) {
        this.data.add(data);
    }

    @Override
    public void add(Collection<ApplicationData> data) {
        this.data.addAll(data);
    }

    @Override
    public void remove(ApplicationData data) {
        this.data.remove(data);
    }

    // ----------------------------------------------------------
    // OVERLOAD
    // ----------------------------------------------------------

    @Override
    public Collection<ApplicationData> filter(ApplicationCategory category) {
        return this.filter(category, false);
    }

    @Override
    public Collection<ApplicationData> filter(ApplicationCategory category, boolean multideviceNeeded) {
        return this.filter(category, 0, 0, 0, LocalDateTime.MIN, multideviceNeeded);
    }

    @Override
    public Map<ApplicationCategory, List<ApplicationData>> groupByCategory() {
        return this.groupByCategory(0, 0, 0, LocalDateTime.MIN, false);
    }

    // ----------------------------------------------------------
    // UTIL
    // ----------------------------------------------------------

    protected boolean matches(ApplicationData app, float minRating, int minReviews, int minInstalls,
                              LocalDateTime minLastUpdated, boolean multiDeviceNeeded) {
        return app.getRating() >= minRating
               && app.getReviews() >= minReviews
               && app.getInstalls() >= minInstalls
               && (app.getLastUpdated().isAfter(minLastUpdated) || app.getLastUpdated().isEqual(minLastUpdated))
               && (app.getMultiDevice() || !multiDeviceNeeded);
    }



    @Override
    public ApplicationData getMostPopularApplication(ApplicationCategory category, float minRating, int minReviews, LocalDateTime minLastUpdated, boolean multideviceNeeded) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<ApplicationData> filterAndSortByRating(ApplicationCategory category, int minReviews) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractApplicationDataService that = (AbstractApplicationDataService) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
               "data=" + data +
               '}';
    }
}
