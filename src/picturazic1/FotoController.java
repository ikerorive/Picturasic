/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package picturazic1;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import modelo.Foto;
import modelo.Reto;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author HW
 */
public class FotoController implements Initializable {
    Foto foto;
    Usuario usuario;
    Usuario pri;
    Reto reto;
    @FXML
    private JFXButton username;
    @FXML
    private ImageView imagen;
    @FXML
    private JFXButton likeButton;
    @FXML
    private Label retoDesc;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void crearFoto(Foto foto, Usuario u, Picturazic10 a) {
        this.foto = foto;
        pri = u;
        this.usuario = Usuario.obtenerUsuarioId(this.foto.getUsuarioId());
        this.reto = Reto.obtenerRetoPorId(this.foto.getRetoId());
        //this.usuario = u;
        this.usuario.addObserver(a);
        likeButton.setText(foto.obtenerLikes());
        crearPanel();
    }

    private void crearPanel() {
        username.setText(""+this.usuario.getUsername());
        Image imagen = SwingFXUtils.toFXImage(foto.getImagen(), null );
        this.imagen.setImage(imagen);
        retoDesc.setText(this.reto.getTitulo());
    }


    @FXML
    private void megusta(MouseEvent event) throws SQLException {
        this.foto.megusta(this.pri.getId());
        likeButton.setText(this.foto.obtenerLikes());
    }

    
    

    @FXML
    private void verUsuario(MouseEvent event) throws SQLException {
        //Usuario.seguir(this.pri.getId(), this.foto.getUsuarioId());
        System.out.println("Ver clicadoniko");
        this.usuario.verUsuario();
    }
    
}
