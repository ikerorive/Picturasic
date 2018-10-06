package picturazic1;


import java.util.Observable;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class SerialListener extends Observable{

        public SerialListener(Picturazic10 a) throws SerialPortException {
            this.ma = a;
            leido("");
            addLis();
        }
	static int contadorApagar = 0;
        Picturazic10 ma;

	static SerialPort serialPort;
        
        public void addLis() throws SerialPortException {
            serialPort.addEventListener(this.ma);
        }
	// LLAMAR A SERIAL LISTENER LEIDO CON EL TEXTO LEIDO POR EL LECTOR QR
	public static void leido(String textoLeido) {
		serialPort = new SerialPort("COM3");
		try {
			serialPort.openPort();// Open port
			serialPort.setParams(9600, 8, 1, 0);// Set params
			int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;// Prepare mask
			serialPort.setEventsMask(mask);// Set mask
			// Add SerialPortEventListener
			//serialPort.writeString("asda$asdas$");
		} catch (SerialPortException ex) {
			System.out.println(ex);
		}
	}
        
        static void apagarPlaca() throws SerialPortException {
            serialPort.closePort();
        }

	/*
	 * In this class must implement the method serialEvent, through it we learn
	 * about events that happened to our port. But we will not report on all events
	 * but only those that we put in the mask. In this case the arrival of the data
	 * and change the status lines CTS and DSR
	 */
	static class SerialPortReader implements SerialPortEventListener {
		String str = "";
		String str2 = "";
                private Picturazic10 main;
                
                public void pasarMain(Picturazic10 main) {
                    this.main = main;
                }
		public void serialEvent(SerialPortEvent event) {
			if (event.isRXCHAR()) {// If data is available
				// if(event.getEventValue() == 30){//Check bytes count in the input buffer
				// Read data, if 10 bytes available
				try {
					String data = serialPort.readString();
					// System.out.println("AQUI: "+data);
					
                                        if (data.equals("&")) {
                                            
                                                    
                                                    
						System.out.println("&");
						contadorApagar++;
						if (contadorApagar == 5) {
							System.out.println("APAGAR PROGRAMA");
							contadorApagar=0;
                                                        
							// APAGAR PROGRAMA AQUI
						}
					} else {
						if (data.equals("@")) {
							System.out.println("@");
							contadorApagar = 0;
						} else {
							if (data.equals("$")) {
								// System.out.println(str);
								if (str2.equals("")) {
									str2 = str;
								} else {
									// inicio sesion(user, contr); user->str2 cont-> str
									System.out.println("USER: " + str2 + " CONT: " + str);
									str="";
									str2="";
								}
								str = "";
							} else {
								str += data;
							}
						}
					}
					// byte buffer[] = serialPort.readBytes(10);
				} catch (SerialPortException ex) {
					System.out.println(ex);
				}
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
	}


	public static void escribir(String texto) {
		try {
                        System.out.println(texto);
			serialPort.writeString(texto);
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
}