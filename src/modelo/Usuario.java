/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.List;
import java.util.Observable;

/**
 *
 * @author HW
 */
public class Usuario extends Observable{

    
    int id;
    String username;
    String nombre;
    String apellido;
    String correo;
    String fecha;
    BufferedImage avatar;
    double experiencia;
    int nivel;
    double experienciaNivelActual;
    
    public Usuario(int id,
            String nombre,
            String apellido,
            String correo,
            String fecha,
            BufferedImage avatar,
            double experiencia,
            String username) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.fecha = fecha;
        this.avatar = avatar;
        this.experiencia = experiencia;
        this.username = username;
        calcularNivel();
    }
    
    public static Usuario autentificarse(String user, String passw) {
        
        return FuncionesUsuario.verificar(user, passw);
    }
    
    public static void crearUsuario(String username, String nombre, String apellido, String correo, String pass) {
        FuncionesUsuario.anadirUsuario(username, correo, nombre, apellido, true, pass);//prueba
    }
    
    public static void seguir(int seguidor, int segido) throws SQLException {
        FuncionesUsuario.sguir(seguidor, segido);
    }
    
    public static void verificarUsuario(String [] datos) {
        //FuncionesUsuario.verificar(datos);//prueba
    }

    public String getUsername() {
        return username;
    }
    
    public void obtenerReciompensa(int recompensa) {
        FuncionesUsuario.sumarExp(this.id, recompensa);
    }
    
    public List<Usuario> verSeguidores() {
        System.out.println("obtiene usuarios");
        return FuncionesUsuario.buscarSeguidores(id);
    }
    
    public void seguidores() {
        System.out.println("entra en funcion usuario" + this.id);
        setChanged();
        notifyObservers("seguidores");
    }
    
    public static Usuario obtenerUsuarioId(int id) {
        return FuncionesUsuario.buscarUsuarioPorId(id);
    }
    
    public void verUsuario() {
        setChanged();
        notifyObservers("verUser");
    }
    
    public static List<Usuario> obtenerUsuarios(String username) {
        return FuncionesUsuario.buscarUsuario(username, 0);
    }

    private void calcularNivel() {
        this.nivel = (int) (this.experiencia / 100);
        this.experienciaNivelActual = (int) this.experiencia % 100;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public String getFecha() {
        return fecha;
    }

    public BufferedImage getAvatar() {
        return avatar;
    }

    public double getExperiencia() {
        return experiencia;
    }

    public int getNivel() {
        return nivel;
    }

    public double getExperienciaNivelActual() {
        return experienciaNivelActual;
    }
}
