import controlador.ClienteControlador;
import modelo.Cliente;
import modelo.Coche;
import utils.DatabaseConf;

import java.sql.Date;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Cliente cliente1 = new Cliente("12345678Z", "Juan", "García", "638945345");
        Cliente cliente2 = new Cliente("87654321A", "María", "López", "638745345");
        Cliente cliente3 = new Cliente("54321678B", "Pedro", "Martínez", "638456345");

        Coche coche1 = new Coche("1234ABC", "Seat", "Ibiza", Date.valueOf("2019-08-29"), cliente1);
        Coche coche2 = new Coche("5678DEF", "Renault", "Clio", Date.valueOf("2012-03-11"), cliente1);
        Coche coche3 = new Coche("9012GHI", "Ford", "Focus", Date.valueOf("2014-11-15"), cliente1);
        Coche coche4 = new Coche("3456JKL", "Opel", "Astra", Date.valueOf("2017-05-22"), cliente2);
        Coche coche5 = new Coche("7890MNO", "Citroen", "Saxo", Date.valueOf("2009-12-03"), cliente2);
        Coche coche6 = new Coche("2345PQR", "Peugeot", "308", Date.valueOf("2016-07-18"), cliente2);
        Coche coche7 = new Coche("6789STU", "Volkswagen", "Golf", Date.valueOf("2018-09-25"), cliente3);
        Coche coche8 = new Coche("1234VWX", "Fiat", "Punto", Date.valueOf("2015-02-14"), cliente3);
        Coche coche9 = new Coche("5678YZA", "Toyota", "Auris", Date.valueOf("2013-10-07"), cliente3);

        ClienteControlador clienteControlador = ClienteControlador.getInstance();

        try {
            clienteControlador.borrarYCrearTablas();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        clienteControlador.guardarCliente(cliente1);
        System.out.println(cliente1);
        clienteControlador.guardarCliente(cliente2);
        System.out.println(cliente2);
        clienteControlador.guardarCliente(cliente3);
        System.out.println(cliente3);
        clienteControlador.guardarCoche(coche1);
        clienteControlador.guardarCoche(coche2);
        clienteControlador.guardarCoche(coche3);
        clienteControlador.guardarCoche(coche4);
        clienteControlador.guardarCoche(coche5);
        clienteControlador.guardarCoche(coche6);
        clienteControlador.guardarCoche(coche7);
        clienteControlador.guardarCoche(coche8);
        clienteControlador.guardarCoche(coche9);

        clienteControlador.buscarCocheDni("12345678Z").ifPresent(lista -> lista.forEach(System.out::println));
        clienteControlador.buscarCocheDni("87654321A").ifPresent(lista -> lista.forEach(System.out::println));
        clienteControlador.buscarCocheDni("54321678B").ifPresent(lista -> lista.forEach(System.out::println));
    }
}
