package Model;

import java.io.Serializable;

public class Trabajador implements Serializable {

    int id;
    String nombre;
    String apellidopaterno;
    String apellidomaterno;
    String telefono;
    String direccion;
    String salario;
    String tipo;
    boolean deleted;

    public Trabajador(int id, String nombre, String apellidopaterno, String apellidomaterno, String telefono, String direccion, String salario, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.apellidopaterno = apellidopaterno;
        this.apellidomaterno = apellidomaterno;
        this.telefono = telefono;
        this.direccion = direccion;
        this.salario = salario;
        this.tipo = tipo;
        this.deleted = false;
    }

    public void updateTrabajador(int id, String nombre, String apellidopaterno, String apellidomaterno, String telefono, String direccion, String salario, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.apellidopaterno = apellidopaterno;
        this.apellidomaterno = apellidomaterno;
        this.telefono = telefono;
        this.direccion = direccion;
        this.salario = salario;
        this.tipo = tipo;
    }

    public void updateTrabajador(String nombre, String apellidopaterno, String apellidomaterno, String telefono, String direccion, String salario, String tipo) {
        this.nombre = nombre;
        this.apellidopaterno = apellidopaterno;
        this.apellidomaterno = apellidomaterno;
        this.telefono = telefono;
        this.direccion = direccion;
        this.salario = salario;
        this.tipo = tipo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidopaterno(String apellidopaterno) {
        this.apellidopaterno = apellidopaterno;
    }

    public void setApellidomaterno(String apellidomaterno) {
        this.apellidomaterno = apellidomaterno;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setSalario(String salario) {
        this.salario = salario;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidopaterno() {
        return apellidopaterno;
    }

    public String getApellidomaterno() {
        return apellidomaterno;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getSalario() {
        return salario;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
