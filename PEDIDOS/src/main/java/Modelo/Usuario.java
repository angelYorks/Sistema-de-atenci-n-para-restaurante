package Modelo;

import java.sql.Timestamp;

public class Usuario {

    private int idUsuario;
    private String nombre;
    private String apellido;
    private String correo;
    private String contraseña;
    private int idRol;
    private Timestamp fechaRegistro;

    public Usuario() {
    }

    public Usuario(int idUsuario, String nombre, String apellido, String correo, String contraseña, int idRol, Timestamp fechaRegistro) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contraseña = contraseña;
        this.idRol = idRol;
        this.fechaRegistro = fechaRegistro;
    }

    public Usuario(int idUsuario, String nombre, String apellido, String correo, String contraseña, int idRol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contraseña = contraseña;
        this.idRol = idRol;
    }

    public Usuario(int idUsuario, String nombre, String apellido) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
    }




    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String obtenerRol() {
        switch (idRol) {
            case 1: return "cliente";
            case 2: return "chef";
            case 3: return "personal";
            default: return null;
        }
    }

    @Override
    public String toString() {
        return "Usuario{"
                + "idUsuario=" + idUsuario
                + ", nombre='" + nombre + '\''
                + ", apellido='" + apellido + '\''
                + ", correo='" + correo + '\''
                + ", contraseña='" + contraseña + '\''
                + ", idRol=" + idRol
                + ", fechaRegistro=" + fechaRegistro
                + '}';
    }
}