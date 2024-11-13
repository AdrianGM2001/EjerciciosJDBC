package controlador;

import modelo.Cliente;
import modelo.Coche;
import modelo.dao.ClienteDAO;
import modelo.dao.impl.JdbcClienteDao;
import utils.DatabaseConf;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ClienteControlador {
    private static ClienteControlador INSTANCE;
    private final CocheControlador cocheControlador;
    private final ClienteDAO clienteDao;

    private ClienteControlador() {
        clienteDao = new JdbcClienteDao();
        cocheControlador = CocheControlador.getInstance();
    }

    public static ClienteControlador getInstance() {
        if (INSTANCE == null) {
            return new ClienteControlador();
        }
        return INSTANCE;
    }

    public void guardarCliente(Cliente cliente) {
        clienteDao.save(cliente);
    }

    public void eliminarCliente(String dni) {
        clienteDao.delete(dni);
    }

    public void actualizarCliente(Cliente cliente) {
        clienteDao.update(cliente);
    }

    public Optional<Cliente> buscarCliente(String dni) {
        return clienteDao.findByDNI(dni);
    }

    public void guardarCoche(Coche coche) {
        cocheControlador.save(coche);
    }

    public void eliminarCoche(String matricula) {
        cocheControlador.delete(matricula);
    }

    public void actualizarCoche(Coche coche) {
        cocheControlador.update(coche);
    }

    public Optional<Coche> buscarCocheMatricula(String matricula) {
        return cocheControlador.findByMatricula(matricula);
    }

    public Optional<List<Coche>> buscarCocheDni(String dni) {
        return cocheControlador.findByDni(dni);
    }

    public void crearTablas() throws SQLException {
        DatabaseConf.createTables();
    }

    public void borrarYCrearTablas() throws SQLException {
        DatabaseConf.dropAndCreateTables();
    }
}
