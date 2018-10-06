/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package picturazic1;

import static java.lang.System.out;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Usuario;

/**
 *
 * @author HW
 */
public class ControladorDatos {
    Usuario usuario; //tiene al usuario;
    
    DBManager dbMAnager;
    Connection connection;
    
    public ControladorDatos() {
        inicializarUsuario();
    }
    
    public boolean crearConecxion() throws SQLException {
        //ArrayList<String> resultado = new ArrayList<>();
        if(DBManager.getConnection() != null) {
            connection = DBManager.getConnection();
            return true;
        }else {
            System.out.println("Conexion no se establecio!!!");
            return false;
        } 
    }
    
    public void inicializarUsuario() {
        //this.usuario = new Usuario(1, "Nikolay", "Zabaleta", "correo@gmail.com", "02/03/97", null, 900);
    }
    
    public Usuario obtenerUsuario() {
        return this.usuario;
    }
}
