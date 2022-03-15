package fp.googleplay;

import fp.util.Preconditions;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.Objects;

/**
 * Representa los datos de una aplicación alojada en Google Play.
 */
public final class ApplicationData implements Comparable<ApplicationData> {

    private String name;
    private AppCategory category;
    private float rating;
    private int reviews;
    private String size;
    private int installs;
    private float price;
    private LocalDateTime lastUpdated;
    private String currentVersion;
    private String androidVersion;
    private boolean multiDevice;

    /**
     * @param name           nombre de la aplicación
     * @param category       categoría de la aplicación
     * @param rating         valoración media de la aplicación, sobre 5 estrellas, o {@link Float#NaN} si no se puede por falta de datos
     * @param reviews        número de valoraciones de la aplicación
     * @param size           tamaño de la aplicación, aproximado, solo para mostrar (por ejemplo {@code 10M})
     * @param installs       número de instalaciones de la aplicación
     * @param price          precio de la aplicación
     * @param lastUpdated    última actualización de la aplicación
     * @param currentVersion versión actual de la aplicación
     * @param androidVersion versión mínima de Android para funcionar
     * @param multiDevice    si se puede usar en otras plataformas Android (como Android TV)
     */
    public ApplicationData(String name, AppCategory category, float rating, int reviews, String size, int installs,
                           float price, LocalDateTime lastUpdated, String currentVersion, String androidVersion, boolean multiDevice) {

        Preconditions.checkArgument(!name.isEmpty(), "name no puede estar vacío");
        Preconditions.checkArgument(rating >= 0 && rating <= 5, "rating debe estar entre 0 y 5 ambos inclusive");
        Preconditions.checkArgument(reviews >= 0, "reviews no puede ser negativo");
        Preconditions.checkArgument(installs >= 0, "installs no puede ser negativo");
        Preconditions.checkArgument(!lastUpdated.isAfter(LocalDateTime.now()), "lastUpdated está en el futuro");
        Preconditions.checkArgument(
                installs > 0 || reviews == 0,
                "el número de valoraciones no puede ser mayor a 0 si el número de instalaciones es 0"
        );

        this.name = name;
        this.category = category;
        this.rating = rating;
        this.reviews = reviews;
        this.size = size;
        this.installs = installs;
        this.price = price;
        this.lastUpdated = lastUpdated;
        this.currentVersion = currentVersion;
        this.androidVersion = androidVersion;
        this.multiDevice = multiDevice;
    }

    /**
     * Constructor auxiliar para instanciar datos con 0 estrellas, 0 valoraciones, 0 instalaciones y actualizada
     * justo ahora.
     *
     * @param name           nombre de la aplicación
     * @param category       categoría de la aplicación
     * @param size           tamaño de la aplicación, aproximado, solo para mostrar (por ejemplo {@code 10M})
     * @param price          precio de la aplicación
     * @param currentVersion versión actual de la aplicación
     * @param androidVersion versión mínima de Android para funcionar
     * @param multiDevice    si se puede usar en otras plataformas Android (como Android TV)
     */
    public ApplicationData(String name, AppCategory category, String size, float price, String currentVersion,
                           String androidVersion, boolean multiDevice) {
        this(name, category, 0, 0, size, 0, price, LocalDateTime.now(), currentVersion, androidVersion, multiDevice);
    }

    /**
     * @return el tipo de la aplicación. {@link AppType#PAID} si el precio es mayor a 0, {@link AppType#FREE} en caso
     * contrario
     */
    public AppType getType() {
        return this.getPrice() > 0 ? AppType.PAID : AppType.FREE;
    }

    /**
     * @return el tiempo que hace desde la última actualización
     */
    public Duration getTimeSinceLastUpdate() {
        return this.getTimeSinceLastUpdate(Instant.now());
    }

    /**
     * @return el tiempo que hace desde la última actualización hasta el instante pasado como argumento.
     */
    public Duration getTimeSinceLastUpdate(Temporal instant) {
        return Duration.between(this.getLastUpdated(), instant);
    }

    /**
     * Las aplicaciones se ordenan por valoración media, número de valoraciones e instalaciones.
     */
    @Override
    public int compareTo(ApplicationData other) {
        int rate = Float.compare(this.getRating(), other.getRating());
        if (rate == 0) rate = Integer.compare(this.getReviews(), other.getReviews());
        if (rate == 0) rate = Integer.compare(this.getInstalls(), other.getInstalls());
        return rate;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ApplicationData) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.category, that.category) &&
                Float.floatToIntBits(this.rating) == Float.floatToIntBits(that.rating) &&
                this.reviews == that.reviews &&
                Objects.equals(this.size, that.size) &&
                this.installs == that.installs &&
                Float.floatToIntBits(this.price) == Float.floatToIntBits(that.price) &&
                Objects.equals(this.lastUpdated, that.lastUpdated) &&
                Objects.equals(this.currentVersion, that.currentVersion) &&
                Objects.equals(this.androidVersion, that.androidVersion) &&
                this.multiDevice == that.multiDevice;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "ApplicationData[" +
                "name=" + name + ", " +
                "category=" + category + ", " +
                "rating=" + rating + ", " +
                "reviews=" + reviews + ", " +
                "size=" + size + ", " +
                "installs=" + installs + ", " +
                "price=" + price + ", " +
                "lastUpdated=" + lastUpdated + ", " +
                "currentVersion=" + currentVersion + ", " +
                "androidVersion=" + androidVersion + ", " +
                "multiDevice=" + multiDevice + ']';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AppCategory getCategory() {
        return category;
    }

    public void setCategory(AppCategory category) {
        this.category = category;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getReviews() {
        return reviews;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getInstalls() {
        return installs;
    }

    public void setInstalls(int installs) {
        this.installs = installs;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    public boolean isMultiDevice() {
        return multiDevice;
    }

    public void setMultiDevice(boolean multiDevice) {
        this.multiDevice = multiDevice;
    }
}
