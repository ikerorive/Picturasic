/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package picturazic1;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import modelo.Foto;
import modelo.FuncionesUsuario;
import modelo.Reto;
import modelo.Usuario;

/**
 * FXML Controller class
 *
 * @author HW
 */
public class ControladorPrincipal implements Initializable {
    
    Stage miStage;
    Usuario u;

    @FXML
    private JFXButton mostrarPerfil;
    @FXML
    private JFXButton mostrarHome;
    @FXML
    private JFXButton mostrarInformacion;
    @FXML
    private AnchorPane panelVisualizador;
    @FXML
    private FlowPane panelContenedor; //Retos, Fotos
    @FXML
    private JFXButton mostrarRetos;
    @FXML
    private Label usuarioTop;
    @FXML
    private ScrollPane panelScroll;
    @FXML
    private AnchorPane panelReferencia;
    
    @FXML
    private JFXTextField buscador;
    private Picturazic10 ob;
    @FXML
    private JFXButton verPopulares;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
            //u = Usuario.obtenerUsuarioId(1);
            panelVisualizador.widthProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
                    panelReferencia.setPrefWidth((double)newVal.doubleValue()-15);
                }
            });
    }    
    
    public void obtenerStage(Stage s, Usuario u, Picturazic10 ob) {
        this.miStage = s;
        this.ob = ob;
        this.u = u;
        usuarioTop.setText(this.u.getUsername());
        
    }
    
    public void inicializarUsuario(Usuario usuario) {
        usuarioTop.setText(usuario.getNombre());
    }
    
    
    public void cargarPerfil(int id, Usuario u) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/perfilUsuario.fxml"));
        Parent root = (Parent) loader.load();
        PerfilController controladorPerfil = loader.getController();
        if(u == null) {
            this.u.addObserver(ob);
            controladorPerfil.obtenerUsuario(this.u);
            controladorPerfil.cargarUsuario(this.u);
            panelContenedor.getChildren().clear();
            panelContenedor.getChildren().add(root);
            visualizarFotosUsuario(Foto.obtenerFotosUsuario(this.u.getId()));
        }else {
            u.addObserver(ob);
            controladorPerfil.obtenerUsuario(u);
            controladorPerfil.cargarUsuario(u);
            panelContenedor.getChildren().clear();
            panelContenedor.getChildren().add(root);
            visualizarFotosUsuario(Foto.obtenerFotosUsuario(u.getId()));
        }
        
        
        
        
    }
    
    public void participarReto(Reto o) throws IOException {
        //panelScroll.setVisible(false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Participa.fxml"));
        Parent root = (Parent) loader.load();
        ParticiparController controladorParticipar = loader.getController();
        controladorParticipar.pasarStage(this.miStage, this.u);
        controladorParticipar.inicializarReto(o);
        
        panelContenedor.getChildren().clear();
        panelContenedor.getChildren().add(root);
        
    }
    
     public void visualizarFotos(List<Foto> lista) throws IOException {
        
        //PerfilController controladorPerfil = loader.getController();
        
        panelContenedor.getChildren().clear();
        for(Foto r : lista) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Foto.fxml"));
            Parent root = (Parent) loader.load();
            FotoController controladorReto = loader.getController();
            //r.addObserver(obse);
            controladorReto.crearFoto(r,this.u, ob);
            panelContenedor.getChildren().add(root);
        }
    }  
    
    public void visualizarFotosUsuario(List<Foto> lista) throws IOException {
        
        //PerfilController controladorPerfil = loader.getController();
        for(Foto r : lista) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Foto.fxml"));
            Parent root = (Parent) loader.load();
            FotoController controladorReto = loader.getController();
            //r.addObserver(obse);
            controladorReto.crearFoto(r,this.u, ob);
            panelContenedor.getChildren().add(root);
        }
    }  
    
    public void visualizarUsuarios(List<Usuario> lista) throws IOException {
        panelContenedor.getChildren().clear();
        for(Usuario r : lista) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/perfilUsuario.fxml"));
            Parent root = (Parent) loader.load();
            r.addObserver(this.ob);
            
            PerfilController controladorPerfil = loader.getController();
            controladorPerfil.obtenerUsuario(this.u);
            controladorPerfil.cargarUsuario(r);
            panelContenedor.getChildren().add(root);
        }
    }
    
    public void visualizarRetos(List<Reto> lista, Picturazic10 obse) throws IOException {
        if(!panelScroll.isVisible()) {
            panelScroll.setVisible(true);
        }
        panelContenedor.getChildren().clear();
        for(Reto r : lista) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Reto.fxml"));
            Parent root = (Parent) loader.load();
            RetoController controladorReto = loader.getController();
            r.addObserver(obse);
            controladorReto.crearReto(r);
            panelContenedor.getChildren().add(root);
        }
        if(lista.size() == 0) {
            panelContenedor.getChildren().add(new Label("Ya has completado todos los retos hasta la fecha"));
        }
        
    }
    
    void setActionHot(EventHandler<MouseEvent> popluares) {
        verPopulares.addEventHandler(MouseEvent.MOUSE_CLICKED, popluares);
    }
    
    void setActionHome(EventHandler<MouseEvent> visualizarMainBoard) {
        mostrarHome.addEventHandler(MouseEvent.MOUSE_CLICKED, visualizarMainBoard);
    }

    void setActionProfile(EventHandler<MouseEvent> visualizarPerfil) {
       mostrarPerfil.addEventHandler(MouseEvent.MOUSE_CLICKED, visualizarPerfil);
    }
    
    void setActionReto(EventHandler<MouseEvent> visualizarReto) {
       mostrarRetos.addEventHandler(MouseEvent.MOUSE_CLICKED, visualizarReto);
    }

    @FXML
    private void visualizarUsuarioLocal(MouseEvent event) throws IOException {
        cargarPerfil(0, null);
    }

    @FXML
    private void buscarUsuario(MouseEvent event) throws IOException {
        visualizarUsuarios(FuncionesUsuario.buscarUsuario(buscador.getText(), -1));
    }
    
    @FXML
    private void iker(MouseEvent event) {
        System.out.println("PONER AQUI CODIGO");
    }
    
}
