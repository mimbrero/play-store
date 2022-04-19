package fp.googleplay.analyzer;

import fp.googleplay.ApplicationCategory;
import fp.googleplay.ApplicationData;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

// "analyzer" and not service as it stores the data to work with.
public interface ApplicationDataAnalyzer {

    Integer getDataSize();

    void add(ApplicationData data);

    void add(Collection<ApplicationData> data);

    void remove(ApplicationData data);

    //
    // METHOD 1
    //

    /**
     * @param applicationData app to check if there is another one of the same category and a higher rating
     * @return if there is an app of the same category and a higher rating than the given one in the collection
     */
    Boolean existsAnAppWithHigherRatingForTheSameCategory(ApplicationData applicationData);

    //
    // METHOD 2
    //

    /**
     * @param category the category of the apps to calculate the average
     * @return the average rating for the given category
     */
    Float calculateAverageRating(ApplicationCategory category);

    //
    // METHOD 3
    //

    /**
     * @param category the category of the applications
     * @return the filtered collection
     */
    Collection<ApplicationData> filter(ApplicationCategory category);

    /**
     * @param category the category of the applications
     * @param multideviceNeeded if the applications need to be multidevice
     * @return the filtered collection
     */
    Collection<ApplicationData> filter(ApplicationCategory category, boolean multideviceNeeded);

    /**
     * @param category the category of the applications
     * @param minRating the minimum rating
     * @param minReviews the minimum review count
     * @param minInstalls the minimum installs
     * @param minLastUpdated the minimum lastUpdated date
     * @param multideviceNeeded if the applications need to be multidevice
     * @return the filtered collection
     */
    Collection<ApplicationData> filter(ApplicationCategory category, float minRating, int minReviews, int minInstalls,
                                       LocalDateTime minLastUpdated, boolean multideviceNeeded);

    //
    // METHOD 4
    //

    /**
     * @return the map that groups the applications (values) by category (keys)
     */
    Map<ApplicationCategory, Collection<ApplicationData>> groupByCategory();

    /**
     * @param minRating the minimum rating that an app must have to be counted
     * @param minReviews the minimum reviews that an app must have to be counted
     * @param minInstalls the minimum installs that an app must have to be counted
     * @param minLastUpdated the minimum last updated date that an app must have to be counted
     * @param multideviceNeeded if an app must be multidevice to be counted
     * @return the map that groups the applications (values) by category (keys)
     */
    Map<ApplicationCategory, Collection<ApplicationData>> groupByCategory(float minRating, int minReviews, int minInstalls,
                                                                          LocalDateTime minLastUpdated, boolean multideviceNeeded);

    //
    // METHOD 5
    //

    /**
     * @return the map that counts the installations (values) by category (keys)
     */
    Map<ApplicationCategory, Integer> getInstallsByCategory();
}
