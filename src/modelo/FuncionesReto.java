/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import picturazic1.DBManager;

/**
 *
 * @author HW
 */
public class FuncionesReto {
    public static void anadirReto(String descripcion, String imgPath) {
		Connection connection = null;
		PreparedStatement statement = null;
		FileInputStream inputStream = null;
		try {
			File image = new File(imgPath);
			inputStream = new FileInputStream(image);
			connection = DBManager.getConnection();
			statement = connection.prepareStatement("INSERT INTO reto (descripcion, ejemplo) VALUES (?,?);");
			statement.setString(1, descripcion);
			statement.setBinaryStream(2, (InputStream) inputStream, (int) (image.length()));
			statement.executeUpdate();
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException: - " + e);
		} catch (SQLException e) {
			System.out.println("SQLException: - " + e);
		} finally {
			try {
				connection.close();
				statement.close();
			} catch (SQLException e) {
				System.out.println("SQLException Finally: - " + e);
			}
		}
	}

	public static void actualizarFotoReto(int idReto, String imgPath) {
		Connection connection = null;
		PreparedStatement statement = null;
		FileInputStream inputStream = null;
		try {
			File image = new File(imgPath);
			inputStream = new FileInputStream(image);
			connection = DBManager.getConnection();
			statement = connection.prepareStatement("UPDATE `reto` SET `ejemplo`= ? WHERE `idReto`='" + idReto + "';");
			statement.setBinaryStream(1, (InputStream) inputStream, (int) (image.length()));
			statement.executeUpdate();
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException: - " + e);
		} catch (SQLException e) {
			System.out.println("SQLException: - " + e);
		} finally {
			try {
				connection.close();
				statement.close();
			} catch (SQLException e) {
				System.out.println("SQLException Finally: - " + e);
			}
		}
	}

	public static List<Reto> buscarReto(int idUser) { // para ver todos los retos mandar string vacio
		// Declare the JDBC objects.
		Connection connection = null;
		Statement statement = null;
                Date today = new Date();
                SimpleDateFormat ft = 
                                     new SimpleDateFormat ("yyyy.MM.dd");
		ResultSet resultSet = null;
		List<Reto> resultado = new ArrayList<>();
                
                Calendar fecha = Calendar.getInstance();
                int ano = fecha.get(Calendar.YEAR);
                int mes = fecha.get(Calendar.MONTH) + 1;
                int dia = fecha.get(Calendar.DAY_OF_MONTH);
		try {
			connection = DBManager.getConnection();
			String selectSql = "select * from reto a where a.retoId not in(select retoid from foto where autor = "+idUser+" group by retoid) and fech > CURDATE();";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(selectSql);
			while (resultSet.next()) {
                                /*System.out.println("Resultado obtener reto: \n");
				System.out.println(resultSet.getString(1) + "----" + resultSet.toString());*/
                                String [] fechaActual = ft.format(today).split(".");
                                //System.out.println("Fecha actual : " + ft.format(today));
                                
                                System.out.println("Fecha actual : " + ano + "-" + mes+ "-" +dia);
                                String [] fechaaReto = ft.format(resultSet.getDate("fech")).split(".");
                                System.out.println("Fecha reto : " + ft.format(resultSet.getDate("fech")));
                                /*if(Integer.valueOf(fechaActual[0]) <= Integer.valueOf(fechaaReto[0])) {
                                    if(Integer.valueOf(fechaActual[1]) <= Integer.valueOf(fechaaReto[1])) {
                                        if(Integer.valueOf(fechaActual[3]) <= Integer.valueOf(fechaaReto[3])) {
                                            
                                        }
                                    }
                                }*/
                                if(true) {
                                    resultado.add(new Reto(resultSet.getInt("retoid"), 
                                                resultSet.getString("descripcion"),
                                                null,
                                                resultSet.getString("fech"),
                                                resultSet.getString("titulo")));
                                }
                                
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (resultSet != null)
				try {
					resultSet.close();
				} catch (Exception e) {
				}
			if (statement != null)
				try {
					statement.close();
				} catch (Exception e) {
				}
			if (connection != null)
				try {
					connection.close();
				} catch (Exception e) {
				}
		}
		return resultado;
	}

    static Reto buscarRetoId(int retoId) {
       // Declare the JDBC objects.
		Connection connection = null;
		Statement statement = null;
                Date today = new Date();
                SimpleDateFormat ft = 
                                     new SimpleDateFormat ("yyyy.MM.dd");
		ResultSet resultSet = null;
		Reto resultado = null;
                
                Calendar fecha = Calendar.getInstance();
                int ano = fecha.get(Calendar.YEAR);
                int mes = fecha.get(Calendar.MONTH) + 1;
                int dia = fecha.get(Calendar.DAY_OF_MONTH);
		try {
			connection = DBManager.getConnection();
			String selectSql = "select * from reto a where a.retoId  = "+retoId+";";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(selectSql);
			while (resultSet.next()) {
                                /*System.out.println("Resultado obtener reto: \n");
				System.out.println(resultSet.getString(1) + "----" + resultSet.toString());*/
                                String [] fechaActual = ft.format(today).split(".");
                                //System.out.println("Fecha actual : " + ft.format(today));
                                
                                System.out.println("Fecha actual : " + ano + "-" + mes+ "-" +dia);
                                String [] fechaaReto = ft.format(resultSet.getDate("fech")).split(".");
                                System.out.println("Fecha reto : " + ft.format(resultSet.getDate("fech")));
                                /*if(Integer.valueOf(fechaActual[0]) <= Integer.valueOf(fechaaReto[0])) {
                                    if(Integer.valueOf(fechaActual[1]) <= Integer.valueOf(fechaaReto[1])) {
                                        if(Integer.valueOf(fechaActual[3]) <= Integer.valueOf(fechaaReto[3])) {
                                            
                                        }
                                    }
                                }*/
                                if(true) {
                                    resultado = new Reto(resultSet.getInt("retoid"), 
                                                resultSet.getString("descripcion"),
                                                null,
                                                resultSet.getString("fech"),
                                                resultSet.getString("titulo"));
                                }
                                
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (resultSet != null)
				try {
					resultSet.close();
				} catch (Exception e) {
				}
			if (statement != null)
				try {
					statement.close();
				} catch (Exception e) {
				}
			if (connection != null)
				try {
					connection.close();
				} catch (Exception e) {
				}
		}
		return resultado;
    }
}
