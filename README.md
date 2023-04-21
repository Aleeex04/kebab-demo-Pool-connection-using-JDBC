# kebab-demo-Pool-connection-using-JDBC
Este proyecto es una demostración de cómo usar un pool de conexiones JDBC

## ¿Qué es un pool de conexiones JDBC?
Un pool de conexiones JDBC es un patrón de acceso a datos que consiste en mantener un conjunto de conexiones a la base de datos listas para ser reutilizadas, en lugar de crear y cerrar conexiones cada vez que se necesita acceder a la base de datos. Esto reduce el costo de establecer y liberar conexiones, y permite manejar mejor los recursos del sistema.

## ¿Cómo funciona este proyecto?
Este proyecto usa el framework HikariCP para implementar un pool de conexiones JDBC. HikariCP es un framework ligero y rápido que ofrece varias opciones de configuración y optimización. Para usar HikariCP, se necesita agregar la dependencia correspondiente al archivo pom.xml:
~~~
<dependency>
    <groupId>com.zaxxer</groupId>
    <artifactId>HikariCP</artifactId>
    <version>4.0.3</version>
</dependency>
~~~
Luego, se necesita crear una clase que encapsule la lógica para obtener una conexión del pool. En este proyecto, la clase se llama HikariCPDataSource:
~~~
public class HikariCPDataSource {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        config.setJdbcUrl("jdbc:h2:mem:test");
        config.setUsername("user");
        config.setPassword("password");
        config.setMaximumPoolSize(10);
        ds = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private HikariCPDataSource() {}
}
~~~
La clase usa un bloque estático para configurar las propiedades del pool, como la URL, el usuario, la contraseña y el tamaño máximo del pool. Luego, crea una instancia de HikariDataSource con la configuración especificada. Finalmente, expone un método estático para obtener una conexión del pool.

Para usar una conexión del pool, se puede invocar el método getConnection() de la clase HikariCPDataSource:
~~~
Connection con = HikariCPDataSource.getConnection();
~~~
Una vez que se tiene una conexión, se puede usar para realizar operaciones de base de datos como consultas, inserciones, actualizaciones o eliminaciones. Al terminar con la conexión, se debe cerrar explícitamente para devolverla al pool:
~~~
con.close();
~~~
## ¿Cómo ejecutar este proyecto?
Este proyecto usa Maven como herramienta de gestión de dependencias y construcción. Para ejecutar este proyecto, se necesita tener instalado Maven y Java en el sistema. Luego, se puede clonar el repositorio desde GitHub:

git clone https://github.com/Aleeex04/kebab-demo-Pool-connection-using-JDBC.git
Después, se puede ingresar al directorio del proyecto y ejecutar el comando Maven para compilar y ejecutar la aplicación:
~~~
cd kebab-demo-Pool-connection-using-JDBC
mvn compile exec:java
~~~
La aplicación mostrará por consola los resultados de las operaciones de base de datos realizadas con las conexiones del pool.
