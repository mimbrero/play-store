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
public final class ApplicationData implements Comparable<ApplicationData>, Cloneable {

    private String name;
    private AppCategory category;
    private Float rating;
    private Integer reviews;
    private String size;
    private Integer installs;
    private Float price;
    private LocalDateTime lastUpdated;
    private String currentVersion;
    private String androidVersion;
    private Boolean multiDevice;

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
     * @throws IllegalArgumentException si {@code name} está vacío
     * @throws IllegalArgumentException si {@code rating} no está entre 0 y 5 (ambos inclusive)
     * @throws IllegalArgumentException si {@code reviews} o {@code installs} son negativos
     * @throws IllegalArgumentException si {@code lastUpdated} es después de {@link LocalDateTime#now()}
     * @throws IllegalArgumentException si {@code reviews} es un número mayor a 0 mientras que {@code installs} es 0
     */
    public ApplicationData(String name, AppCategory category, Float rating, Integer reviews, String size,
                           Integer installs, Float price, LocalDateTime lastUpdated, String currentVersion,
                           String androidVersion, Boolean multiDevice) {

        Preconditions.checkArgument(!name.isEmpty(), "name no puede estar vacío");
        Preconditions.checkArgument(rating != null && rating >= 0 && rating <= 5, "rating debe estar entre 0 y 5 ambos inclusive");
        Preconditions.checkArgument(reviews != null && reviews >= 0, "reviews no puede ser negativo");
        Preconditions.checkArgument(installs != null && installs >= 0, "installs no puede ser negativo");
        Preconditions.checkArgument(!lastUpdated.isAfter(LocalDateTime.now()), "lastUpdated está en el futuro");
        Preconditions.checkArgument(installs > 0 || reviews == 0, "reviews debe ser 0 si installs es 0");

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
     * @throws IllegalArgumentException si {@code name} está vacío
     */
    public ApplicationData(String name, AppCategory category, String size, Float price, String currentVersion,
                           String androidVersion, Boolean multiDevice) {
        this(name, category, 0F, 0, size, 0, price, LocalDateTime.now(), currentVersion, androidVersion, multiDevice);
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
        int rate = this.getRating().compareTo(other.getRating());
        if (rate == 0) rate = this.getReviews().compareTo(other.getReviews());
        if (rate == 0) rate = this.getInstalls().compareTo(other.getInstalls());
        return rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Preconditions.checkArgument(!name.isEmpty(), "name no puede estar vacío");
        this.name = name;
    }

    public AppCategory getCategory() {
        return category;
    }

    public void setCategory(AppCategory category) {
        this.category = category;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        Preconditions.checkArgument(rating != null && rating >= 0 && rating <= 5, "rating debe estar entre 0 y 5 ambos inclusive");
        this.rating = rating;
    }

    public Integer getReviews() {
        return reviews;
    }

    public void setReviews(Integer reviews) {
        Preconditions.checkArgument(reviews != null && reviews >= 0, "reviews no puede ser negativo");
        Preconditions.checkArgument(installs > 0 || reviews == 0, "reviews debe ser 0 si installs es 0");
        this.reviews = reviews;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getInstalls() {
        return installs;
    }

    public void setInstalls(Integer installs) {
        Preconditions.checkArgument(installs != null && installs >= 0, "installs no puede ser negativo");
        Preconditions.checkArgument(installs > 0 || reviews == 0, "reviews debe ser 0 si installs es 0");
        this.installs = installs;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        Preconditions.checkArgument(!lastUpdated.isAfter(LocalDateTime.now()), "lastUpdated está en el futuro");
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

    public Boolean getMultiDevice() {
        return multiDevice;
    }

    public void setMultiDevice(Boolean multiDevice) {
        this.multiDevice = multiDevice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationData that = (ApplicationData) o;
        return Objects.equals(name, that.name) && category == that.category && Objects.equals(rating, that.rating) && Objects.equals(reviews, that.reviews) && Objects.equals(size, that.size) && Objects.equals(installs, that.installs) && Objects.equals(price, that.price) && Objects.equals(lastUpdated, that.lastUpdated) && Objects.equals(currentVersion, that.currentVersion) && Objects.equals(androidVersion, that.androidVersion) && Objects.equals(multiDevice, that.multiDevice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, category, rating, reviews, size, installs, price, lastUpdated, currentVersion, androidVersion, multiDevice);
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

    @Override
    public ApplicationData clone() {
        try {
            // De momento no hay propiedades mutables, así que no hay que clonar nada más
            return (ApplicationData) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException(e);
        }
    }
}
