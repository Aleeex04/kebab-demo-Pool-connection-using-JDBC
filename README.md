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
  <version>5.0.1</version>
</dependency>
~~~
Luego, se necesita crear una clase que encapsule la lógica para obtener una conexión del pool.
~~~
private static void initDatabaseConnectionPool() {
        dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mariadb://localhost:3306/kebab");
        dataSource.setUsername("alexr");
        dataSource.setPassword("alex2004");

        // if (true) throw new RuntimeException("Simulated error! ");
    }

    private static void closeDatabaseConnectionPool() {
        dataSource.close();
    }
}
~~~
La clase usa un bloque estático para configurar las propiedades del pool, como la URL, el usuario, la contraseña y el tamaño máximo del pool. Luego, crea una instancia de HikariDataSource con la configuración especificada. Finalmente, expone un método estático para obtener una conexión del pool.

## ¿Cómo ejecutar este proyecto?
Este proyecto usa Maven como herramienta de gestión de dependencias y construcción. Para ejecutar este proyecto, se necesita tener instalado Maven y Java en el sistema. Luego, se puede clonar el repositorio desde GitHub:

git clone https://github.com/Aleeex04/kebab-demo-Pool-connection-using-JDBC.git
Después, se puede ingresar al directorio del proyecto y ejecutar el comando Maven para compilar y ejecutar la aplicación:
~~~
cd kebab-demo-Pool-connection-using-JDBC
mvn compile exec:java
~~~
La aplicación mostrará por consola los resultados de las operaciones de base de datos realizadas con las conexiones del pool.
