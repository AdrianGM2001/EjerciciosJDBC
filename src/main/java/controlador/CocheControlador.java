package controlador;

import modelo.Coche;
import modelo.dao.CocheDAO;
import modelo.dao.impl.JdbcCocheDao;

import java.util.List;
import java.util.Optional;

public class CocheControlador {
    private static CocheControlador INSTANCE;
    private final CocheDAO cocheDao;

    private CocheControlador() {
        cocheDao = new JdbcCocheDao();
    }

    public static CocheControlador getInstance() {
        if (INSTANCE == null) {
            return new CocheControlador();
        }
        return INSTANCE;
    }

    public void save(Coche coche) {
        cocheDao.save(coche);
    }

    public void delete(String matricula) {
        cocheDao.delete(matricula);
    }

    public void update(Coche coche) {
        cocheDao.update(coche);
    }

    public Optional<Coche> findByMatricula(String matricula) {
        return cocheDao.findByMatricula(matricula);
    }

    public Optional<List<Coche>> findByDni(String dni) {
        return cocheDao.findByDni(dni);
    }
}
