package modelo.dao.impl;

public interface Dao {
    void save();

    void delete();

    void update();

    Object findByDni(String dni);
}
