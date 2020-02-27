package Model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Cita implements Serializable {

    int id;
    int idcliente;
    int idtrabajador;
    String procedimiento;
    String costo;
    Timestamp fecha;
    String estado;

    public Cita(int id, int idcliente, int idtrabajador, String procedimiento, String costo, Timestamp fecha, String estado) {
        this.id = id;
        this.idcliente = idcliente;
        this.idtrabajador = idtrabajador;
        this.procedimiento = procedimiento;
        this.costo = costo;
        this.fecha = fecha;
        this.estado = estado;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }

    public void setIdtrabajador(int idtrabajador) {
        this.idtrabajador = idtrabajador;
    }

    public void setProcedimiento(String procedimiento) {
        this.procedimiento = procedimiento;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public int getIdtrabajador() {
        return idtrabajador;
    }

    public String getProcedimiento() {
        return procedimiento;
    }

    public String getCosto() {
        return costo;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public String getEstado() {
        return estado;
    }
}
