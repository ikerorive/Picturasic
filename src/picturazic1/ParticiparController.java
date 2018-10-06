/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package picturazic1;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import modelo.Foto;
import modelo.Reto;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author HW
 */
public class ParticiparController implements Initializable {
    Foto foto;
    Reto reto;
    Usuario user;
    FileChooser fileChooser = new FileChooser();
    
    @FXML
    private ImageView titulo;
    Stage miStage = null;
    File file;
    @FXML
    private Label descripcion;
    @FXML
    private ImageView mostrarMiFoto;
    @FXML
    private Label tituloT;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //miStage = (Stage) descripcion.getScene().getWindow();
    }  
    
    public void pasarStage(Stage stage, Usuario u) {
        this.miStage = stage;
        this.user = u;
        
    }
    
    public void inicializarReto(Reto reto) {
        this.reto = reto;
        descripcion.setText(this.reto.getDescripcion());
        tituloT.setText(this.reto.getTitulo());
    }

    @FXML
    private void subirFoto(MouseEvent event) {
        //file = fileChooser.getSelectedFile();
        String fullPath = file.getAbsolutePath();
        this.reto.subirImagen("", file.getAbsolutePath(), user.getId(), "10-06-08");
        this.user.obtenerReciompensa(25);
        
        
    }

    @FXML
    private void escigerImagen(MouseEvent event) throws IOException {
        file = fileChooser.showOpenDialog(this.miStage);
        System.out.println(file.getCanonicalPath());
        BufferedImage bufferedImage = ImageIO.read(file);
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        mostrarMiFoto.setOpacity(1);
        mostrarMiFoto.setSmooth(true);
        mostrarMiFoto.setFitWidth(350);
        mostrarMiFoto.setFitHeight(250);
        mostrarMiFoto.setImage(image);
        
    }

    @FXML
    private void cancelarReto(MouseEvent event) {
        reto.cancelarReto();
    }
    
}
