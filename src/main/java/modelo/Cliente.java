package modelo;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private int id;
    private String dni;
    private String nombre;
    private String apellidos;;
    private String telefono;
    private final List<Coche> coches;

    public Cliente(String dni, String nombre, String apellidos, String telefono, List<Coche> coches) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.coches = coches;
    }

    public Cliente(String dni, String nombre, String apellidos, String telefono) {
        this(dni, nombre, apellidos, telefono, new ArrayList<>());
    }

    public Cliente(int id, String dni, String nombre, String apellidos, String telefono) {
        this(dni, nombre, apellidos, telefono, new ArrayList<>());
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public List<Coche> getCoches() {
        return coches;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void addCoche(Coche coche) {
        coches.add(coche);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id + '\'' +
                ", dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", telefono='" + telefono + '\'' +
                ((!coches.isEmpty()) ? ", coches=" + coches : "") +
                '}';
    }
}
