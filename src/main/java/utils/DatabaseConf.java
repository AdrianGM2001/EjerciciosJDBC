package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConf {
    public static final String URL = "jdbc:mysql://localhost:3306/concesionario";
    public static final String USER = "root";
    public static final String PASS = "admin";
    public static final String CREATE_TABLE_CLIENTES = "CREATE TABLE IF NOT EXISTS clientes (" +
            "id INT AUTO_INCREMENT PRIMARY KEY," +
            "dni VARCHAR(255) NOT NULL UNIQUE," +
            "nombre VARCHAR(255) NOT NULL," +
            "apellidos VARCHAR(255) NOT NULL," +
            "telefono VARCHAR(255) NULL UNIQUE" +
            ");";

    public static final String CREATE_TABLE_COCHES = "CREATE TABLE IF NOT EXISTS coches (" +
            "matricula VARCHAR(255) PRIMARY KEY," +
            "marca VARCHAR(255) NOT NULL," +
            "modelo VARCHAR(255) NOT NULL," +
            "fecha DATE NULL," +
            "propietario VARCHAR(255)," +
            "FOREIGN KEY(propietario) REFERENCES clientes(dni) ON DELETE NO ACTION ON UPDATE CASCADE" +
            ");";

    private static final String DROP_TABLE_CLIENTE = "DROP TABLE IF EXISTS clientes";

    private static final String DROP_TABLE_COCHES = "DROP TABLE IF EXISTS coches";

    public static void createTables() throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {
            stmt.execute(CREATE_TABLE_CLIENTES);
            System.out.println("Se ha creado la tabla clientes");
            stmt.execute(CREATE_TABLE_COCHES);
            System.out.println("Se ha creado la tabla coches");
        }
    }

    public static void dropAndCreateTables() throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = conn.createStatement()) {
            stmt.execute(DROP_TABLE_COCHES);
            System.out.println("Se ha borrado la tabla coches");
            stmt.execute(DROP_TABLE_CLIENTE);
            System.out.println("Se ha borrado la tabla clientes");
            createTables();
        }
    }
}
