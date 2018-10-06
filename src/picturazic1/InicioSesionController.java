/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package picturazic1;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author HW
 */
public class InicioSesionController implements Initializable {

    @FXML
    private JFXTextField username;
    @FXML
    private JFXTextField pass;
    @FXML
    private JFXButton login;
    @FXML
    private VBox panelLog;
    @FXML
    private JFXButton gosing;
    @FXML
    private VBox panelSing;
    @FXML
    private JFXTextField usuarioLabel;
    @FXML
    private JFXTextField nombreLabel;
    @FXML
    private JFXTextField apellidoLabel;
    @FXML
    private JFXTextField correoLabel;
    @FXML
    private JFXTextField passLabel;
    @FXML
    private JFXTextField textoPlaca;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public Usuario iniciarSesion() {
        String user = username.getText();
        String passw = pass.getText();
        return Usuario.autentificarse(user,passw);
    }
    
    void setActionLogin(EventHandler<MouseEvent> login) {
        this.login.addEventHandler(MouseEvent.MOUSE_CLICKED, login);
    }

    private Usuario login(MouseEvent event) {
        String user = username.getText();
        String passw = pass.getText();
        return Usuario.autentificarse(user,passw);
    }

    void errorUsuario() {
        username.setText("");
        pass.setText("");
    }

    @FXML
    private void signPanel(ActionEvent event) {
        panelLog.setVisible(false);
        panelSing.setVisible(true);
    }

    @FXML
    private void crearUsuario(ActionEvent event) {
        String user = usuarioLabel.getText();
        String nombre = nombreLabel.getText();
        String apellido = apellidoLabel.getText();
        String correo = correoLabel.getText();
        String pass = passLabel.getText();
        Usuario.crearUsuario(user, nombre, apellido, correo, pass);
        panelLog.setVisible(true);
        panelSing.setVisible(false);
        String encriptarStr = ""+user+"$"+pass+"$";
        Dynamic_QR.generate_qr("qr", encriptarStr, correo);
    }

    @FXML
    private void mandarPlaca(ActionEvent event) {
        //System.out.println(textoPlaca.getText());
       SerialListener.escribir(textoPlaca.getText());
       textoPlaca.setText("");
    }
    
    public void llenar(String n, String p) {
        username.setText(n);
        pass.setText(p);
    }

    @FXML
    private void cancelarrr(ActionEvent event) {
        panelLog.setVisible(true);
        panelSing.setVisible(false);
        usuarioLabel.setText("");
        nombreLabel.setText("");
        apellidoLabel.setText("");
        correoLabel.setText("");
        passLabel.setText("");
    }
    
}
