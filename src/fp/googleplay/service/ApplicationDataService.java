package fp.googleplay.service;

import fp.googleplay.ApplicationCategory;
import fp.googleplay.ApplicationData;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

// not actually a service as it stores the data to work with, but we'll stick with this
public interface ApplicationDataService {

    // ----------------------------------------------------------
    // DATA MANAGEMENT
    // ----------------------------------------------------------

    /**
     * @return the number of elements stored to work with
     */
    Integer getDataSize();

    /**
     * Adds an {@link ApplicationData} to work with.
     * @param data the {@link ApplicationData} to add
     * @see #add(Collection)
     */
    void add(ApplicationData data);
    
    /**
     * Adds multiple {@link ApplicationData} to work with.
     * @param data the collection of {@link ApplicationData} to add
     * @see #add(ApplicationData)
     */
    void add(Collection<ApplicationData> data);

    /**
     * Removes the given {@link ApplicationData} from the internal collection.
     * @param data the {@link ApplicationData} to stop working with
     */
    void remove(ApplicationData data);

    // ----------------------------------------------------------
    // METHOD 1
    // ----------------------------------------------------------

    /**
     * @param applicationData app to check if there is another one of the same category and a higher rating
     * @return if there is an app of the same category and a higher rating than the given one in the collection
     */
    Boolean existsAnAppWithHigherRatingForTheSameCategory(ApplicationData applicationData);

    // ----------------------------------------------------------
    // METHOD 2
    // ----------------------------------------------------------

    /**
     * @param category the category of the apps to calculate the average
     * @return the average rating for the given category
     */
    Double calculateAverageRating(ApplicationCategory category);

    // ----------------------------------------------------------
    // METHOD 3
    // ----------------------------------------------------------

    /**
     * @param category the category of the applications
     * @return the filtered collection
     */
    Collection<ApplicationData> filter(ApplicationCategory category);

    /**
     * @param category          the category of the applications
     * @param multideviceNeeded if the applications need to be multidevice
     * @return the filtered collection
     */
    Collection<ApplicationData> filter(ApplicationCategory category, boolean multideviceNeeded);

    /**
     * @param category          the category of the applications
     * @param minRating         the minimum rating
     * @param minReviews        the minimum review count
     * @param minInstalls       the minimum installs
     * @param minLastUpdated    the minimum lastUpdated date
     * @param multideviceNeeded if the applications need to be multidevice
     * @return the filtered collection
     */
    Collection<ApplicationData> filter(ApplicationCategory category, float minRating, int minReviews, int minInstalls,
                                       LocalDateTime minLastUpdated, boolean multideviceNeeded);

    // ----------------------------------------------------------
    // METHOD 4
    // ----------------------------------------------------------

    /**
     * @return the map that groups the applications (values) by category (keys)
     */
    Map<ApplicationCategory, List<ApplicationData>> groupByCategory();

    /**
     * @param minRating         the minimum rating that an app must have to be counted
     * @param minReviews        the minimum reviews that an app must have to be counted
     * @param minInstalls       the minimum installs that an app must have to be counted
     * @param minLastUpdated    the minimum last updated date that an app must have to be counted
     * @param multideviceNeeded if an app must be multidevice to be counted
     * @return the map that groups the applications (values) by category (keys)
     */
    Map<ApplicationCategory, List<ApplicationData>> groupByCategory(float minRating, int minReviews, int minInstalls,
                                                                    LocalDateTime minLastUpdated, boolean multideviceNeeded);

    // ----------------------------------------------------------
    // METHOD 5
    // ----------------------------------------------------------

    /**
     * @return the map that counts the installations (values) by category (keys)
     */
    Map<ApplicationCategory, Long> getInstallsByCategory();
}
