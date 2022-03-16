# Proyecto del Segundo Cuatrimestre Fundamentos de Programación (Curso 2021/22)

Autor/a: Alberto Sánchez Mimbrero uvus:albsanmim

## Estructura de las carpetas del proyecto

- **/src**: Directorio con el código fuente.
    * **fp.googleplay**: Paquete que contiene los tipos del proyecto.
        * **test**: Paquete que contiene las clases de test del proyecto.
    * **fp.util**: Paquete que contiene las clases de utilidad.
        * **test**: Paquete que contiene una librería de utilidad para hacer tests.
- **/data**: Contiene el dataset del proyecto.
    * **chess.csv**: Archivo csv que contiene datos de diferentes aplicaciones alojadas en Google Play.

## Estructura del _dataset_

- **App**: de tipo cadena, indica el nombre de la aplicación.
- **Category**: de tipo cadena, indica la categoría de la aplicación. Puede tomar los valores ART_AND_DESIGN,
  AUTO_AND_VEHICLES, BEAUTY, BOOKS_AND_REFERENCE, BUSINESS, COMICS, COMMUNICATION, DATING, EDUCATION o ENTERTAINMENT.
- **Rating**: de tipo número real, indica la media ya calculada de las valoraciones de los usuarios.
- **Reviews**: de tipo entero, indica el número de valoraciones de los usuarios.
- **Size**: de tipo cadena, indica el tamaño de la aplicación, con su unidad (por ejemplo, `200M` para 200 megabytes).
  También puede contener `Varies with device` si varía con el dispsitivo, según la disponibilidad de las dependencias de
  la aplicación.
- **Installs**: de tipo cadena, indica el número de instalaciones aproximado por exceso, con comas cada tres cifras y
  un `+` al final.
- **Price**: de tipo número real, indica el precio de la aplicación (0 si es gratis).
- **Last updated date**: de tipo cadena, indica la fecha de la última actualización, con formato `d/M/y`.
- **Last updated time**: de tipo cadena, indica la hora de la última actualización, con formato `h:m`.
- **Current Ver**: de tipo cadena, indica la versión actual de la aplicación.
- **Android Ver**: de tipo cadena, indica la versión de Android necesaria para la aplicación. Si contiene `and up` al
  final, la versión especificada es la mínima, pero se puede ejecutar en posteriores. También es
  posible `Varies with device`.
- **Multidevice**: de tipo booleano, indica si tiene soporte extendido para las otras plataformas Android no típicas
  (como Android TV, Android Auto...)

## Tipos implementados

Los tipos que se han implementado en el proyecto son los siguientes:

### Tipo Base - ApplicationData

Representa los datos de una aplicación alojada en Google Play.

**Propiedades**:

Se acceden y modifican a través de sus _getters_ y _setters_.

- **Básicas**:
    - **name**, de tipo `String`, consultable y modificable. Indica si las partidas han sido calificadas o no, es decir
      si son partidas amistosas o de tipo clasificatorio.
    - **category**, de tipo `AppCategory`, consultable y modificable. Indica la categoría de la aplicación.
    - **rating**, de tipo `float`, consultable y modificable. Indica la media ya calculada de las valoraciones de los
      usuarios.
    - **reviews**, de tipo `int`, consultable y modificable. Indica el número de valoraciones de los usuarios.
    - **size**, de tipo `String`, consultable y modificable. Indica el tamaño de la aplicación, con su unidad (por
      ejemplo, `200M` para 200 megabytes). También puede contener `Varies with device` si varía con el dispsitivo, según
      la disponibilidad de las dependencias de la aplicación.
    - **installs**, de tipo `int`, consultable y modificable. Indica el número de instalaciones aproximado por exceso.
    - **price**, de tipo `float`, consultable y modificable. Indica el precio de la aplicación (0 si es gratis).
    - **lastUpdated**, de tipo `LocalDateTime`, consultable y modificable. Indica la fecha y hora de la última
      actualización.
    - **currentVersion**, de tipo `String`, consultable y modificable. Indica la versión actual de la aplicación.
    - **androidVersion**, de tipo `String`, consultable y modificable. Indica la versión de Android necesaria para la
      aplicación. Si contiene `and up` al final, la versión especificada es la mínima, pero se puede ejecutar en
      posteriores. También es posible `Varies with device`.
    - **multiDevice**, de tipo `boolean`, consultable y modificable. Indica si tiene soporte extendido para las otras
      plataformas Android no típicas
      (como Android TV, Android Auto...)

- **Derivadas**:
    - **type**, de tipo `AppType`, consultable. Indica el tipo de la aplicación, es decir, FREE si el precio es 0 y PAID
      en caso contrario.

**Constructores**:

- **C1**: Tiene un parámetro por cada propiedad básica del tipo.
- **C2**: Crea un objeto de tipo ```Partida``` a partir de los siguientes
  parámetros: ```String name, AppCategory category, String size, float price, String currentVersion, String androidVersion, boolean multiDevice```

**Restricciones**:

- El parámetro `name` no puede ser una `String` vacía.
- El parámetro `rating` debe estar entre 0 y 5, ambos inclusive.
- Los parámetros `reviews` y `installs` no pueden ser negativos.
- El parámetro `lastUpdated` no puede ser un `LocalDateTime` que represente un instante en el futuro.
- El parámetro `reviews` debe ser 0 si el parámetro `installs` es 0.

**Criterio de igualdad**:

Dos aplicaciones son iguales si todas sus propiedades básicas son iguales.

**Criterio de ordenación**:

Por valoración media, número de valoraciones e instalaciones.

**Otras operaciones**:

- **Duration getTimeSinceLastUpdate()**: Devuelve el tiempo que hace desde la última actualización
- **Duration getTimeSinceLastUpdate(Temporal instant)**: Devuelve el tiempo que hace desde la última actualización hasta
  el instante pasado como argumento.

#### Tipos auxiliares

- **AppCategory**, enum. Puede tomar los valores ART_AND_DESIGN, AUTO_AND_VEHICLES, BEAUTY, BOOKS_AND_REFERENCE,
  BUSINESS, COMICS, COMMUNICATION, DATING, EDUCATION o ENTERTAINMENT.
- **AppType**, enum. Puede tomar los valores FREE y PAID.
