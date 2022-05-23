# Proyecto del Segundo Cuatrimestre Fundamentos de Programación (Curso 2021/22)

Autor/a: Alberto Sánchez Mimbrero uvus:albsanmim

## Estructura del proyecto

- **/src/fp/**: Directorio con el código fuente.
    - **googleplay/**: Paquete que contiene los tipos del proyecto.
        - **factory/**: Paquete que contiene la factoría y su implementación del tipo base.
            - **ApplicationDataFactory.java**: Interfaz para la factoría.
            - **ApplicationDataFactoryImpl.java**: Implementación de la factoría.
        - **service/**: Paquete que contiene el tipo contenedor y sus implementaciones.
            - **AbstractApplicationDataService.java**: Clase abstracta que contiene los métodos comunes para todas las
              implementaciones.
            - **ApplicationDataService.java**: Interfaz para el tipo contenedor.
            - **LoopApplicationDataService.java**: Implementación con bucles del tipo contenedor.
        - **test/**: Paquete que contiene las clases de test del proyecto.
            - **ApplicationDataFactoryTest.java**: test del tipo `ApplicationDataFactory`.
            - **ApplicationDataServiceTest.java**: test del tipo `ApplicationDataService`.
            - **ApplicationDataTest.java**: test del tipo `ApplicationData`.
        - **ApplicationCategory.java**: Enumerado con las categorías que puede tomar una aplicación.
        - **ApplicationData.java**: Tipo base del proyecto. Representa los datos de una aplicación alojada en Google
          Play.
        - **ApplicationType.java**: Enumerado que indica si una aplicación es gratis o de pago.
    - **util/**: Paquete que contiene las clases de utilidad.
        - **test/**: Paquete que contiene una librería de utilidad para hacer tests.
            - **Assertions.java**: Métodos útiles para afirmar condiciones en los tests.
            - **Test.java**: Anotación para indicar que un método es una prueba, para ser ejecutado por el test.
            - **TestResults.java**: Tipo visible solo en el paquete para recopilar información sobre los tests
              ejecutados.
            - **UnitTest.java**: Clase a extender por los tests, que contiene métodos útiles y la lógica de los tests.
        - **LocalDateTimeParser.java**: Clase con métodos para parsear `LocalDateTime`s.
        - **Preconditions.java**: Métodos útiles para validar condiciones.
- **/data/**: Contiene el dataset del proyecto.
    - **mock-data.csv**: Archivo csv que contiene datos de diferentes aplicaciones alojadas en Google Play.

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

### Tipo base - ApplicationData

Representa los datos de una aplicación alojada en Google Play.

**Propiedades**:

Se acceden y modifican a través de sus _getters_ y _setters_.

- **Básicas**:
    - **name**, de tipo `String`, consultable y modificable. Indica el nombre de la aplicación.
    - **category**, de tipo `ApplicationCategory`, consultable y modificable. Indica la categoría de la aplicación.
    - **rating**, de tipo `Float`, consultable y modificable. Indica la media ya calculada de las valoraciones de los
      usuarios.
    - **reviews**, de tipo `Integer`, consultable y modificable. Indica el número de valoraciones de los usuarios.
    - **size**, de tipo `String`, consultable y modificable. Indica el tamaño de la aplicación, con su unidad (por
      ejemplo, `200M` para 200 megabytes). También puede contener `Varies with device` si varía con el dispsitivo, según
      la disponibilidad de las dependencias de la aplicación.
    - **installs**, de tipo `Integer`, consultable y modificable. Indica el número de instalaciones aproximado por
      exceso.
    - **price**, de tipo `Float`, consultable y modificable. Indica el precio de la aplicación (0 si es gratis).
    - **lastUpdated**, de tipo `LocalDateTime`, consultable y modificable. Indica la fecha y hora de la última
      actualización.
    - **currentVersion**, de tipo `String`, consultable y modificable. Indica la versión actual de la aplicación.
    - **androidVersion**, de tipo `String`, consultable y modificable. Indica la versión de Android necesaria para la
      aplicación. Si contiene `and up` al final, la versión especificada es la mínima, pero se puede ejecutar en
      posteriores. También es posible `Varies with device`.
    - **multiDevice**, de tipo `Boolean`, consultable y modificable. Indica si tiene soporte extendido para las otras
      plataformas Android no típicas
      (como Android TV, Android Auto...)

- **Derivadas**:
    - **type**, de tipo `ApplicationType`, consultable. Indica el tipo de la aplicación, es decir, FREE si el precio es
      0 y PAID en caso contrario.

**Constructores**:

- **C1**: Tiene un parámetro por cada propiedad básica del tipo.
- **C2**: Constructor auxiliar con los siguientes
  parámetros: ```String name, ApplicationCategory category, String size, Float price, String currentVersion, String androidVersion, Boolean multiDevice```

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

- **ApplicationCategory**, enum. Puede tomar los valores ART_AND_DESIGN, AUTO_AND_VEHICLES, BEAUTY, BOOKS_AND_REFERENCE,
  BUSINESS, COMICS, COMMUNICATION, DATING, EDUCATION o ENTERTAINMENT.
- **ApplicationType**, enum. Puede tomar los valores FREE y PAID.

### Factoría - ApplicationDataFactory

Clase de factoría para construir objetos de tipo `ApplicationData`.

- **ApplicationData parse(String line) throws IllegalArgumentException**: Parsea una línea con
  formato `String app,ApplicationCategory category,Float rating,Integer reviews,String size, Integer installs,Float price,String lastUpdatedDate,String lastUpdatedTime,String version,String androidVersion, Boolean multidevice`
  a un objeto `ApplicationData`.

- **List\<ApplicationData> parseCsv(String filePath) throws IOException**: Parsea un archivo CSV con el formato
  explicado en el método anterior en sus líneas y las devuelve en una lista de `ApplicationData`.

### Tipo contenedor - ApplicationDataService

Clase contenedora de los objetos de tipo `ApplicationData`. La implementa `LoopApplicationDataService` extendiendo
a `AbstractApplicationDataService`.

**Propiedades:**

- **data**, de tipo `Collection<ApplicationData>`, privada. Almacena internamente los objetos para tratar con ellos en
  los métodos del tipo. Se pueden añadir y eliminar elementos a través de los métodos `#add(ApplicationData data)`
  , `#add(Collection<ApplicationData> data)` y `#remove(ApplicationData data)`.
- **dataSize**, de tipo `Integer`, se accede a través de su _getter_. Devuelve el número de elementos en **data**.

**Constructores (de las implementaciones):**

- **C1**: Constructor por defecto. Asigna a **data** una `ArrayList` vacía.
- **C2**: Constructor con un parámetro de tipo `Collection<ApplicationData>`. Asigna a **data** esa colección.

**Criterio de igualdad (en AbstractApplicationDataService):**

Dos `ApplicationDataService` son iguales si lo son sus propiedades **data**.

**Métodos**:

- **Boolean existsAnAppWithHigherRatingForTheSameCategory(ApplicationData applicationData)**: devuelve `true` si existe
  alguna aplicación con más valoración de la misma categoría que la pasada como argumento.
- **Float calculateAverageRating(ApplicationCategory category)**: devuelve la media de las valoraciones de las
  aplicaciones que sean de la categoría pasada como argumento.
- **Collection\<ApplicationData> filter(ApplicationCategory category, float minRating, int minReviews, int minInstalls,
  LocalDateTime minLastUpdated, boolean multideviceNeeded)**: devuelve una colección de los datos del objeto filtrados
  por la categoría pasada como argumento, mayor o igual valoración media, valoraciones, instalaciones, fecha de última
  actualización y, si necesita ser multidevice, si lo es.
- **Map<ApplicationCategory, Collection<ApplicationData>> groupByCategory(float minRating, int minReviews, int
  minInstalls, LocalDateTime minLastUpdated, boolean multideviceNeeded)**: agrupa las aplicaciones por categoría (clave
  del mapa). Solo tiene en cuenta las categorías que tengan mayor o igual valoración media, valoraciones, instalaciones,
  fecha de última actualización y, si necesita ser multidevice, si lo es.
- **Map<ApplicationCategory, Long> getInstallsByCategory()**: devuelve un mapa donde las claves son categorías y los
  valores son la suma de las instalaciones de todas las aplicaciones de esa categoría.

## Útiles

### LocalDateTimeParser

Clase con métodos para parsear `LocalDateTime`s.

**Métodos estáticos públicos**:

- **LocalDateTime parse(String date, String time)**: Parsea la `LocalDateTime` pasada como argumento con fecha con
  formato `d/M/y` y hora con formato `h:m`.
- **LocalDateTime parse(String line)**: Parsea la `LocalDateTime` pasada como argumento con formato `d/M/y h:m`.

### Preconditions

Métodos útiles para validar condiciones.

**Métodos estáticos públicos**:

- **void checkArgument(Boolean expression)** y
- **void checkArgument(Boolean expression, String exceptionMessage)**: Comprueba que la expresión pasada como argumento
  se evalúa a `true`. En caso contrario, lanzará una `IllegalArgumentException` con el mensaje propuesto.
- **void checkState(Boolean expression)** y
- **void checkState(Boolean expression, String exceptionMessage)**: Comprueba que la expresión pasada como argumento se
  evalúa a `true`. En caso contrario, lanzará una `IllegalArgumentException` con el mensaje propuesto.
- **\<T> T checkNotNull(T toCheck)** y
- **\<T> T checkNotNull(T toCheck, String exceptionMessage)**: Comprueba que la expresión pasada como argumento no
  es `null`. En caso contrario, lanzará una `NullPointerException` con el mensaje propuesto.

### test/Assertions

Métodos útiles para afirmar condiciones en los tests.

**Métodos estáticos públicos**:

- **void assertThat(Boolean expression)** y
- **void assertThat(Boolean expression, String exceptionMessage)**: Comprueba que la expresión pasada como argumento se
  evalúa a `true`.
- **void assertEquals(Object o1, Object o2)** y
- **void assertEquals(Object o1, Object o2, String exceptionMessage)**: Comprueba que los objetos pasados como
  argumentos sean iguales.
- **\<T extends Throwable> T assertThrows(Class\<T> throwableClass, Runnable runnable)** y
- **\<T extends Throwable> T assertThrows(Class\<T> throwableClass, Runnable runnable, String exceptionMessage)**:
  Comprueba que el `Runnable` pasado como argumento lanza una excepción del tipo especificado. Esto hace que la
  excepción no se lance.

### test/Test

Anotación para indicar que un método es una prueba, para ser ejecutado por el test.

**Métodos/propiedades**:

- **int value()**: número para ordenar los métodos de test y ejecutarlos en ese orden.

### test/TestResults

Tipo visible solo en el paquete para recopilar información sobre los tests ejecutados.

**Propiedades**:
Todas las propiedades son accesibles y modificables por sus getters y setters públicos.

- **private Integer successful**: número de métodos que se ejecutaron sin lanzar una excepción.
- **private Integer exceptions**: número de métodos que fallaron lanzando una excepción.

**Métodos**:

- **public void incrementSuccessful()**: incrementa en 1 la propiedad `successful`.
- **public void incrementExceptions()**: incrementa en 1 la propiedad `exceptions`.

### test/UnitTest

Clase a extender por los tests, que contiene métodos útiles y la lógica de los tests.

**Métodos**:

- **protected void printSeparator()**: imprime un separador en la consola (muchos `=` seguidos).
- **protected void print(String s)**: imprime la `String` pasada como argumento en la consola.
- **protected void print(Object o)**: imprime el `Object` pasado como argumento en la consola.
- **public void init()**: Instancia un `TestResults` y, mediante _reflection_, busca los métodos públicos en la clase
  que tengan la anotación `Test`, los ordena por la propiedad de la anotación `#value()` y los invoca. Si falla, suma
  una excepción a los resultados e imprime la excepción. En caso contrario, suma un test correcto. Cuando termina,
  imprime el número de tests correctos y fallidos.
