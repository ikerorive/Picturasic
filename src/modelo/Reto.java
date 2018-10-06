/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Observable;

/**
 *
 * @author HW
 */
public class Reto extends Observable{

    public static Reto obtenerRetoPorId(int retoId) {
        return FuncionesReto.buscarRetoId(retoId);
    }
    
    int retoId;
    String descripcion;
    String titulo;
    BufferedImage ejemplo;
    String fecha;
    
    
    public Reto(int retoId,
            String descripcion,
            BufferedImage ejemplo,
            String fecha,
            String titulo) {
        
        this.retoId = retoId;
        this.descripcion = descripcion;
        this.ejemplo = ejemplo;
        this.fecha = fecha;
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }
    
    public static List<Reto> obtenerRetosDiarios(int idUser) {
       return FuncionesReto.buscarReto(idUser); 
    }
    
    public void participarReto() {
        setChanged();
        notifyObservers("");
    }
    
    public void cancelarReto() {
        setChanged();
        notifyObservers("cancelar");
    }
    
    public Reto cogeme() {
        return this;
    }
    
    public void subirImagen(String decripcion, String imagen, int usuario, String fecha) {
        FuncionesFoto.anadirFoto(decripcion, usuario, this.retoId, imagen, fecha);
        setChanged();
        notifyObservers("subir");
    }

    public int getRetoId() {
        return retoId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public BufferedImage getEjemplo() {
        return ejemplo;
    }

    public String getFecha() {
        return fecha;
    }
}
