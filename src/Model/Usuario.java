package Model;

import java.io.Serializable;

public class Usuario implements Serializable {
    
    private int id;
    private String username;
    private String password;
    private int tipo;

    public Usuario(int id, String username, String password, int tipo) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.tipo = tipo;
    }
    
    public void updateUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public void updateUser(String username, String password, int tipo) {
        this.username = username;
        this.password = password;
        this.tipo = tipo;
    } 
    
    public void updateUser(int id, String username, String password, int tipo) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public int getTipo() {
        return tipo;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
