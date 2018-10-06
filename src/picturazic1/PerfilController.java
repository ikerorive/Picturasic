/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package picturazic1;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import modelo.Foto;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author HW
 */
public class PerfilController implements Initializable {
    Usuario usuario;
    Usuario pri;
    List<Usuario> lista;
    private Label username;
    @FXML
    private ProgressBar barraProgreso;
    @FXML
    private Label Usuraio;
    @FXML
    private JFXButton botonNivel;
    @FXML
    private ImageView avatar;
    @FXML
    private JFXButton seguidores;
    @FXML
    private JFXButton fotos;
    @FXML
    private JFXButton seguir;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    public void obtenerUsuario(Usuario u) {
        this.pri = u;
    }
    
    public void cargarUsuario(Usuario usuario) {
        this.usuario = usuario;
        Usuraio.setText(this.usuario.getUsername());
        barraProgreso.setProgress(this.usuario.getExperienciaNivelActual()/100);
        botonNivel.setText(""+this.usuario.getNivel());
        if(this.usuario.getAvatar() == null) {
            avatar.setImage(new Image("img/icons8-account-100.png"));
        }
        seguidores.setText(""+this.usuario.verSeguidores().size());
        fotos.setText(""+Foto.obtenerFotosUsuario(this.usuario.getId()).size());
        if(this.usuario.getId() == this.pri.getId()) {
            seguir.setVisible(false);
        }
    }

    @FXML
    private void verSeguidores(MouseEvent event) {
        System.out.println("botonclicado");
        this.usuario.seguidores();
    }
    
    public List<Usuario> obtenerUsuarios() {
        return this.lista;
    }

    @FXML
    private void seguirUsuario(MouseEvent event) throws SQLException {
        Usuario.seguir(this.pri.getId(), this.usuario.getId());
    }

    @FXML
    private void verUsuario(MouseEvent event) {
        this.usuario.verUsuario();
    }
}
 
