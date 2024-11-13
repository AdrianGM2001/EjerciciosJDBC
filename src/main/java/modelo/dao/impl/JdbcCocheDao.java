package modelo.dao.impl;

import modelo.Cliente;
import modelo.Coche;
import modelo.dao.CocheDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static utils.DatabaseConf.*;

public class JdbcCocheDao implements CocheDAO {
    private static final String COCHES_SAVE_COCHE = "INSERT INTO coches (matricula, marca, modelo, fecha, propietario) VALUES(?, ?, ?, ?, ?)";
    private static final String COCHES_DELETE_COCHE = "DELETE FROM coches WHERE matricula=?";
    private static final String COCHES_UPDATE_COCHE = "UPDATE coches SET marca=?, modelo=?, fecha=?, propietario=? WHERE matricula=?";
    private static final String COCHES_BUSCAR_COCHE_MATRICULA = "SELECT id, marca, modelo, fecha, propietario, nombre, apellidos, telefono FROM coches JOIN clientes WHERE matricula=?";
    private static final String COCHES_BUSCAR_COCHE_DNI = "SELECT id, matricula, marca, modelo, fecha, nombre, apellidos, telefono FROM coches JOIN clientes ON propietario=dni WHERE propietario=?";

    @Override
    public void save(Coche coche) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(COCHES_SAVE_COCHE)) {
            pstmt.setString(1, coche.getMatricula());
            pstmt.setString(2, coche.getMarca());
            pstmt.setString(3, coche.getModelo());
            pstmt.setDate(4, coche.getFecha());
            pstmt.setString(5, coche.getPropietario().getDni());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String matricula) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(COCHES_DELETE_COCHE)) {
            pstmt.setString(1, matricula);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Coche coche) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(COCHES_UPDATE_COCHE)) {
            pstmt.setString(1, coche.getMarca());
            pstmt.setString(2, coche.getModelo());
            pstmt.setDate(3, coche.getFecha());
            pstmt.setString(4, coche.getPropietario().getDni());
            pstmt.setString(5, coche.getMatricula());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Coche> findByMatricula(String matricula) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(COCHES_BUSCAR_COCHE_MATRICULA)) {
            pstmt.setString(1, matricula);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Cliente propietario = new Cliente(rs.getInt("id"), rs.getString("propietario"), rs.getString("nombre"), rs.getString("apellidos"), rs.getString("telefono"));
                return Optional.of(new Coche(matricula, rs.getString("marca"), rs.getString("modelo"), rs.getDate("fecha"), propietario));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<Coche>> findByDni(String dni) {
        List<Coche> coches;
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(COCHES_BUSCAR_COCHE_DNI)) {
            pstmt.setString(1, dni);
            ResultSet rs = pstmt.executeQuery();

            coches = new ArrayList<>();

            if (rs.next()) {
                Cliente cliente = new Cliente(rs.getInt("id"), dni, rs.getString("nombre"), rs.getString("apellidos"), rs.getString("telefono"));
                do {
                    coches.add(new Coche(rs.getString("matricula"), rs.getString("marca"), rs.getString("modelo"), rs.getDate("fecha"), cliente));
                } while (rs.next());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.of(coches);
    }
}
