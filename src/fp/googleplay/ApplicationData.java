package fp.googleplay;

import fp.util.Preconditions;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;

/**
 * Representa los datos de una aplicación alojada en Google Play.
 *
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
public record ApplicationData(String name, AppCategory category, float rating, int reviews, String size, int installs,
                              float price, LocalDateTime lastUpdated, String currentVersion, String androidVersion,
                              boolean multiDevice) implements Comparable<ApplicationData> {

    //
    // Constructores
    //

    public ApplicationData {
        Preconditions.checkArgument(!name.isEmpty(), "name no puede estar vacío");
        Preconditions.checkArgument(rating >= 0 && rating <= 5, "rating debe estar entre 0 y 5 ambos inclusive");
        Preconditions.checkArgument(reviews >= 0, "reviews no puede ser negativo");
        Preconditions.checkArgument(installs >= 0, "installs no puede ser negativo");
        Preconditions.checkArgument(!lastUpdated.isAfter(LocalDateTime.now()), "lastUpdated está en el futuro");
        Preconditions.checkArgument(
                installs > 0 || reviews == 0,
                "el número de valoraciones no puede ser mayor a 0 si el número de instalaciones es 0"
        );
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

    //
    // Métodos
    //

    /**
     * @return el tipo de la aplicación. {@link AppType#PAID} si el precio es mayor a 0, {@link AppType#FREE} en caso
     * contrario
     */
    public AppType type() {
        return this.price() > 0 ? AppType.PAID : AppType.FREE;
    }

    /**
     * @return el tiempo que hace desde la última actualización
     */
    public Duration timeSinceLastUpdate() {
        return this.timeSinceLastUpdate(Instant.now());
    }

    /**
     * @return el tiempo que hace desde la última actualización hasta el instante pasado como argumento.
     */
    public Duration timeSinceLastUpdate(Temporal instant) {
        return Duration.between(this.lastUpdated(), instant);
    }

    /**
     * Las aplicaciones se ordenan por valoración media, número de valoraciones e instalaciones.
     */
    @Override
    public int compareTo(ApplicationData other) {
        int rate = Float.compare(this.rating(), other.rating());
        if (rate == 0) rate = Integer.compare(this.reviews(), other.reviews());
        if (rate == 0) rate = Integer.compare(this.installs(), other.installs());
        return rate;
    }
}
