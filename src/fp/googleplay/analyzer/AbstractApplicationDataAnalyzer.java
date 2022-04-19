package fp.googleplay.analyzer;

import fp.googleplay.ApplicationCategory;
import fp.googleplay.ApplicationData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractApplicationDataAnalyzer implements ApplicationDataAnalyzer {

    protected final Collection<ApplicationData> data;

    /**
     * Creates an instance with an empty {@link ArrayList} of {@link ApplicationData}.
     */
    public AbstractApplicationDataAnalyzer() {
        this(new ArrayList<>());
    }

    public AbstractApplicationDataAnalyzer(Collection<ApplicationData> data) {
        this.data = data;
    }

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

    @Override
    public Collection<ApplicationData> filter(ApplicationCategory category) {
        return this.filter(category, false);
    }

    @Override
    public Collection<ApplicationData> filter(ApplicationCategory category, boolean multideviceNeeded) {
        return this.filter(category, 0, 0, 0, LocalDateTime.MIN, multideviceNeeded);
    }

    @Override
    public Map<ApplicationCategory, Collection<ApplicationData>> groupByCategory() {
        return this.groupByCategory(0, 0, 0, LocalDateTime.MIN, false);
    }

    protected boolean matches(ApplicationData app, float minRating, int minReviews, int minInstalls,
                              LocalDateTime minLastUpdated, boolean multiDeviceNeeded) {
        return app.getRating() >= minRating
               && app.getReviews() >= minReviews
               && app.getInstalls() >= minInstalls
               && (app.getLastUpdated().isAfter(minLastUpdated) || app.getLastUpdated().isEqual(minLastUpdated))
               && (app.getMultiDevice() || !multiDeviceNeeded);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractApplicationDataAnalyzer that = (AbstractApplicationDataAnalyzer) o;
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
