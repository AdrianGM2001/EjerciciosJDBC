package modelo.dao.impl;

import modelo.Cliente;
import modelo.Coche;
import modelo.dao.ClienteDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static utils.DatabaseConf.*;

public class JdbcClienteDao implements ClienteDAO {
    private static final String CLIENTES_SAVE_CLIENTE = "INSERT INTO clientes (dni, nombre, apellidos, telefono) VALUES(?, ?, ?, ?)";
    private static final String CLIENTES_DELETE_CLIENTE = "DELETE FROM clientes WHERE dni=?";
    private static final String CLIENTES_UPDATE_CLIENTE = "UPDATE clientes SET nombre=?, apellidos=?, telefono=? WHERE dni=?";
    private static final String CLIENTES_BUSCAR_CLIENTE = "SELECT nombre, apellidos, telefono, matricula, marca, modelo, fecha FROM clientes JOIN coches ON dni=propietario WHERE dni=?";

    @Override
    public void save(Cliente cliente) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(CLIENTES_SAVE_CLIENTE, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, cliente.getDni());
            pstmt.setString(2, cliente.getNombre());
            pstmt.setString(3, cliente.getApellidos());
            pstmt.setString(4, cliente.getTelefono());
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    cliente.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String dni) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(CLIENTES_DELETE_CLIENTE)) {
            pstmt.setString(1, dni);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Cliente cliente) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(CLIENTES_UPDATE_CLIENTE)) {
            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getApellidos());
            pstmt.setString(3, cliente.getTelefono());
            pstmt.setString(4, cliente.getDni());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Cliente> findByDNI(String dni) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(CLIENTES_BUSCAR_CLIENTE)) {
            pstmt.setString(1, dni);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Cliente cliente = new Cliente(rs.getInt("id"), dni, rs.getString("nombre"), rs.getString("apellidos"), rs.getString("telefono"));
                List<Coche> coches = new ArrayList<>();
                do {
                    coches.add(new Coche(rs.getString("matricula"), rs.getString("marca"),rs.getString("modelo"), rs.getDate("fecha"), cliente));
                } while (rs.next());

                return Optional.of(new Cliente(dni, rs.getString("nombre"), rs.getString("apellidos"), rs.getString("telefono"), coches));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
