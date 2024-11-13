package modelo.dao;

import modelo.Cliente;

import java.util.Optional;

public interface ClienteDAO {
    void save(Cliente cliente);

    void delete(String dni);

    void update(Cliente cliente);

    Optional<Cliente> findByDNI(String dni);


}
