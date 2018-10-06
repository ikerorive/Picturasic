/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package picturazic1;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import modelo.Foto;
import modelo.Reto;
import modelo.Usuario;
import static picturazic1.SerialListener.serialPort;

/**
 *
 * @author HW
 */
public class Picturazic10 extends Application implements Observer, SerialPortEventListener{
    Foto f;
    Usuario user;
    ControladorPrincipal controladorPrincipal;
    InicioSesionController contntroladorInicio;
    static int contadorApagar = 0;
    
    Stage myStage;
    Thread hiloLector;
    TextField leer;
    SerialListener placa;
    //ControladorDatos datos = new ControladorDatos();
    
    @Override
    public void start(Stage stage) throws Exception {
        //ventanaPrincipal(stage);
       //placa= new SerialListener(this);
        
        
        myStage = stage;
        ventanaLogin(stage);
    }
    
    public void apagar() throws SerialPortException {
        SerialListener.apagarPlaca();
        Platform.exit();
    }
    
    public void ventanaPrincipal(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/MainWindow.fxml"));
        Parent root = (Parent) loader.load();
        controladorPrincipal = loader.getController();
        //this.user = Usuario.obtenerUsuarioId(1);
        asignarFunciones();
        //Usuario.crearUsuario(null);
        controladorPrincipal.obtenerStage(stage, this.user, this);
        //controladorPrincipal.inicializarUsuario(Usuario.obtenerUsuarioId(1));
        controladorPrincipal.visualizarFotos(Foto.obtenerFotoSeguidos(user.getId()));
        
        
        /*f = new Foto(5,
            1,
            null,
            1);
        f.addObserver(this);
        f.borrarFoto();*/
        //Usuario.crearUsuario(null);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void ventanaLogin(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/InicioSesion.fxml"));
        Parent root = (Parent) loader.load();
        contntroladorInicio = loader.getController();
        asignarFuncionesLogin();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void asignarFuncionesLogin() {
        contntroladorInicio.setActionLogin(Login);
    }
    
    public void asignarFunciones() {
        controladorPrincipal.setActionHome(TablonPrincipal);
        controladorPrincipal.setActionProfile(BuscarUsuario);
        controladorPrincipal.setActionReto(MostrarRetos);
        controladorPrincipal.setActionHot(populares);
    }

    public void mostrarRetos() throws IOException {
        controladorPrincipal.visualizarRetos(Reto.obtenerRetosDiarios(this.user.getId()), Picturazic10.this);
    }
    
    public void logearse(String usuario, String contraseña) throws IOException {
        
        if(usuario != "" && contraseña != ""){
            this.user = Usuario.autentificarse(usuario,contraseña);
        }else {
            this.user = contntroladorInicio.iniciarSesion();
        }
        if(this.user == null) {
            contntroladorInicio.errorUsuario();
        }else {
            ventanaPrincipal(myStage);
        } 
          
    }
    
    EventHandler<MouseEvent> TablonPrincipal = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            System.out.println("TablonPrincipal");
            try {
                controladorPrincipal.visualizarFotos(Foto.obtenerFotoSeguidos(user.getId()));
            } catch (IOException ex) {
                Logger.getLogger(Picturazic10.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    
     EventHandler<MouseEvent> Login = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            System.out.println("TablonPrincipal");
            try {
                logearse("", "");
                
            } catch (IOException ex) {
                Logger.getLogger(Picturazic10.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
     
     public  void singearse() {
            System.out.println("DSDSDSDSDSDSD");
        }
     
     EventHandler<MouseEvent> SignIn = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            System.out.println("TablonPrincipal");
            singearse();
        }

        
    };
     
     EventHandler<MouseEvent> populares = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            try {
                controladorPrincipal.visualizarFotos(Foto.obtenerFotosPopulares());
            } catch (IOException ex) {
                Logger.getLogger(Picturazic10.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
     
    
    EventHandler<MouseEvent> BuscarUsuario = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            //controladorPrincipal.cargarPerfil();

        }
    };
    
    EventHandler<MouseEvent> MostrarRetos = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            System.out.println("mostrarRetos");
            try {
                mostrarRetos();
            } catch (IOException ex) {
                Logger.getLogger(Picturazic10.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof  Foto ) {
            if(arg.equals("comentarios")) {
                
            }
            
        }
        if(o instanceof Reto /*&& arg.toString().equals("participar")*/) {
            System.out.println("Clika reto");
            try {
                controladorPrincipal.participarReto(((Reto) o).cogeme());
            } catch (IOException ex) {
                Logger.getLogger(Picturazic10.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(o instanceof Reto) {
            if(arg.equals("subir")) {
                try {
                    controladorPrincipal.visualizarRetos(Reto.obtenerRetosDiarios(this.user.getId()), Picturazic10.this);
                } catch (IOException ex) {
                    Logger.getLogger(Picturazic10.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(arg.equals("cancelar")) {
                try {
                    controladorPrincipal.visualizarRetos(Reto.obtenerRetosDiarios(this.user.getId()), Picturazic10.this);
                } catch (IOException ex) {
                    Logger.getLogger(Picturazic10.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if(o instanceof Usuario) {
            System.out.println("aaaaa");
            if(arg.equals("seguidores")) {
                System.out.println("EEEEEEEE");
                try {
                    System.out.println("EEEEEEEE");
                    controladorPrincipal.visualizarUsuarios(((Usuario) o).verSeguidores());
                } catch (IOException ex) {
                    Logger.getLogger(Picturazic10.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(arg.equals("verUser")) {
                System.out.println("entra a ver user");
                try {
                    controladorPrincipal.cargarPerfil(0, (Usuario) o);
                } catch (IOException ex) {
                    Logger.getLogger(Picturazic10.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
   String str = "";
        String str2 = "";
    @Override
    public void serialEvent(SerialPortEvent event) {
     
       if (event.isRXCHAR()) {// If data is available
				// if(event.getEventValue() == 30){//Check bytes count in the input buffer
				// Read data, if 10 bytes available
				try {
					String data = serialPort.readString();
                                        System.out.println("PRUEBA "+data);
					// System.out.println("AQUI: "+data);
                                        if(!data.equals("&")&&!data.equals("@")&&!data.equals("$")){
                                            //str+=data;
                                            System.out.println("EEEEE: "+str);
                                            System.out.println("ENTRA AAAAAAAAAAAAAAAAAAAAAAAAA");
                                        }
					if (data.equals("&")) {
						//System.out.println("&");
						contadorApagar++;
						if (contadorApagar == 5) {
							System.out.println("APAGAR PROGRAMA");
                                                        //escribir("asdad$asasd$")
                                                       // serialPort.writeString("ddd$ddd$");
							contadorApagar=0;
                                                        apagar();
							// APAGAR PROGRAMA AQUI
						}
					} else {
						if (data.equals("@")) {
							//System.out.println("@");
							contadorApagar = 0;
						} else {
							if (data.equals("$")) {
								
								if (str2.equals("")) {
                                                                        System.out.println("VACIO STR");
									str2 = str;
                                                                        str = "";
								} else {
                                                                    System.out.println("USER: " + str2 + " CONT: " + str);
                                                                    try {
                                                                        singearse();
                                                                        //logearse(str2, str);
                                                                        contntroladorInicio.llenar(str2, str);
                                                                    }catch(IllegalStateException exception) {
                                     
                                                                    }
                                                                    
									
									str="";
									str2="";
								}
								
							} else {
								str += data;
							}
						}
					}
					// byte buffer[] = serialPort.readBytes(10);
				}catch (SerialPortException ex) {
					System.out.println(ex);
				}
           // }
 
				// }
			} else if (event.isCTS()) {// If CTS line has changed state
				if (event.getEventValue() == 1) {// If line is ON
					System.out.println("CTS - ON");
				} else {
					System.out.println("CTS - OFF");
				}
			} else if (event.isDSR()) {/// If DSR line has changed state
				if (event.getEventValue() == 1) {// If line is ON
					System.out.println("DSR - ON");
				} else {
					System.out.println("DSR - OFF");
				}
			}
		}
    
    public  void escribir(String texto) {
		try {
                        System.out.println(texto);
			serialPort.writeString(texto);
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
    }
    

