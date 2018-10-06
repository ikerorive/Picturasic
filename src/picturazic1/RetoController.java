/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package picturazic1;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import modelo.Reto;

/**
 * FXML Controller class
 *
 * @author HW
 */
public class RetoController implements Initializable {
    Reto reto;
    @FXML
    private Label Titulo;
    @FXML
    private Label descripcion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    public void crearReto(Reto reto) {
        this.reto = reto;
        Titulo.setText(reto.getTitulo());
        descripcion.setText(reto.getDescripcion());
    }

    @FXML
    private void participar(MouseEvent event) {
        this.reto.participarReto();
    }
    
}
