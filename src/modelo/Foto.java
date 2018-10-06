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
import javafx.beans.InvalidationListener;

/**
 *
 * @author HW
 */
public class Foto extends Observable{
    int id;
    int retoId;
    BufferedImage imagen;
    int usuarioId;
    int likes;
    
    public Foto(int id,
            int retoId,
            BufferedImage imagen,
            int usuarioId) {
        super();
        this.id = id;
        this.retoId = retoId;
        this.imagen = imagen;
        this.usuarioId = usuarioId;
    }
    
    public static List<Foto> obtenerFotosUsuario(int id) {
        return FuncionesFoto.getFotosUsuario(id);
    }
    
    public static List<Foto> obtenerFotosPopulares() {
        
        return FuncionesFoto.obtenerPopulares();
    }
    
    public static List<Foto> obtenerFotoSeguidos(int id) {
        return FuncionesFoto.getFotosSeguidos(id);
    }
    
    public void verComentarios() {
        setChanged();
        notifyObservers("comentarios");
    }
    
    public void megusta(int usuario) throws SQLException {
        FuncionesFoto.anadirMegusta(this.id, usuario);
    }
    
    public void borrarFoto() {
        this.id = 0;
        setChanged();
        notifyObservers("borrar");
        
    }

    public int getId() {
        return id;
    }

    public int getRetoId() {
        return retoId;
    }

    public BufferedImage getImagen() {
        return imagen;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public String obtenerLikes() {
        return FuncionesFoto.getLikes(this.id);
    }
}
