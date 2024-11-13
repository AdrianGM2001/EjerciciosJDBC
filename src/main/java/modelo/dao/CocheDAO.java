package modelo.dao;

import modelo.Cliente;
import modelo.Coche;

import java.util.List;
import java.util.Optional;

public interface CocheDAO {
    void save(Coche coche);

    void delete(String matricula);

    void update(Coche coche);

    Optional<Coche> findByMatricula(String matricula);

    Optional<List<Coche>> findByDni(String dni);
}
