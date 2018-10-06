/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

//import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import picturazic1.DBManager;

/**
 *
 * @author HW
 */
public class FuncionesUsuario {
    public static List<Usuario> buscarUsuario(String busqueda, int id) {
		// Declare the JDBC objects.
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<Usuario> resultado = new ArrayList<>();
                String selectSql;
		try {
			connection = DBManager.getConnection();
                        if(id == -1) {
                            selectSql= "SELECT * from usuario where usuario like '%" + busqueda + "%';";
                        }else {
                            selectSql= "SELECT * from usuario where idusuario = " + id + ";";
                        }
			
			statement = connection.createStatement();
			resultSet = statement.executeQuery(selectSql);
			while (resultSet.next()) {
				resultado.add(new Usuario(resultSet.getInt("idUsuario"),
                                            resultSet.getString("nombre"),
                                            resultSet.getString("apellido"),
                                            resultSet.getString("correo"),
                                            "03/04/1997",
                                            null,
                                            (double)resultSet.getInt("expTotal"),
                                            resultSet.getString("usuario")
                                            ));
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
    
    public static List<Usuario> buscarSeguidores(int id) {
		// Declare the JDBC objects.
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<Usuario> resultado = new ArrayList<>();
                String selectSql;
		try {
			connection = DBManager.getConnection();
                        
                        selectSql= "select * from usuario where idusuario in(select seguidor from seguidor where seguido = "+id+");";
                       
                        
			
			statement = connection.createStatement();
			resultSet = statement.executeQuery(selectSql);
			while (resultSet.next()) {
				resultado.add(new Usuario(resultSet.getInt("idUsuario"),
                                            resultSet.getString("nombre"),
                                            resultSet.getString("apellido"),
                                            resultSet.getString("correo"),
                                            "03/04/1997",
                                            null,
                                            (double)resultSet.getInt("expTotal"),
                                            resultSet.getString("usuario")
                                            ));
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
    
    public static void sguir(int seguidor, int seguido) throws SQLException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		PreparedStatement prepsInsertProduct = null;
		try {
			connection = DBManager.getConnection();
			String insertSql = "INSERT INTO `seguidor` ( `seguidor`, `seguido`) VALUES ("+seguidor+", "+seguido+");";

			prepsInsertProduct = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
			prepsInsertProduct.execute();
		} catch (Exception e) {
			//e.printStackTrace();
                        if(e instanceof SQLIntegrityConstraintViolationException ) {
                            String insertSql = "DELETE FROM Seguidor WHERE Seguido="+seguido+"  and seguidor= "+seguidor+";";

                            prepsInsertProduct = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
                            prepsInsertProduct.execute();
                        }
		} 
                finally {
			// Close the connections after the data has been handled.
			if (prepsInsertProduct != null)
				try {
					prepsInsertProduct.close();
				} catch (Exception e) {
				}
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
	}

	public static void anadirUsuario(String usuario, String correo, String nombre, String apellido, boolean admin, String pass) {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		// Si es admin administrador= 1 else =0
		int administrador = 0;
		if (admin) {
			administrador = 1;
		}
		PreparedStatement prepsInsertProduct = null;
		try {
			connection = DBManager.getConnection();
			String insertSql = "INSERT INTO `usuario` ( `usuario`, `correo`, `apellido`, `nombre`, `admin`, `expTotal` , `contra`) VALUES ('"
					+ usuario + "', '" + correo + "', '" + apellido + "', '" + nombre + "', '" + administrador
					+ "', '0',"+ pass +  ");";

			prepsInsertProduct = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
			prepsInsertProduct.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connections after the data has been handled.
			if (prepsInsertProduct != null)
				try {
					prepsInsertProduct.close();
				} catch (Exception e) {
				}
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
	}

	public static void actualizarAvatar(String imgPath, int idUsuario) {
		Connection connection = null;
		PreparedStatement statement = null;
		FileInputStream inputStream = null;
		try {
			File image = new File(imgPath);
			inputStream = new FileInputStream(image);
			connection = DBManager.getConnection();
			statement = connection
					.prepareStatement("UPDATE `usuario` SET `avatar`= ? WHERE `idUsuario`='" + idUsuario + "';");
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

	public static BufferedImage getAvatar(int idUsuario) {

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = DBManager.getConnection();
			String selectSql = "SELECT avatar from usuario where idUsuario=" + idUsuario + "";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(selectSql);
			// solo devuelve la primera
			while (resultSet.next()) {
				Blob blob = resultSet.getBlob(1);
				InputStream stream = blob.getBinaryStream(1, blob.length());
				BufferedImage image = ImageIO.read(stream);
				return image;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close the connections after the data has been handled.
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
		return null;
	}

	public static void sumarExp(int idUsuario, int expSumar) {
		// Declare the JDBC objects.
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		int exp = 0;
		try {
			connection = DBManager.getConnection();
			String selectSql = "SELECT expTotal from usuario where idUsuario='" + idUsuario + "';";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(selectSql);
			while (resultSet.next()) {
				exp = resultSet.getInt(1);
			}
			exp = exp + expSumar;

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
		// String selectSql = "UPDATE `usuario` SET `expTotal`='" + exp + "' WHERE
		// `idUsuario`='" + idUsuario + "';";
		 connection = null;
		 PreparedStatement  statement1 = null;
		try {
			connection = DBManager.getConnection();
			statement1 = connection
					.prepareStatement("UPDATE `usuario` SET `expTotal`= ? WHERE `idUsuario`='" + idUsuario + "';");
			statement1.setInt(1, exp);
			statement1.executeUpdate();
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
        
        public static Usuario buscarUsuarioPorId(int id) {
		// Declare the JDBC objects.
                Usuario usuario = null;
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		ArrayList<String> resultado = new ArrayList<String>();
		try {
			connection = DBManager.getConnection();
			String selectSql = "select * from usuario where idUsuario = " + id + ";";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(selectSql);
                        
			while (resultSet.next()) {
				//resultado.add(resultSet.getString(1));  
                                usuario = new Usuario(resultSet.getInt("idUsuario"),
                                            resultSet.getString("nombre"),
                                            resultSet.getString("apellido"),
                                            resultSet.getString("correo"),
                                            "03/04/1997",
                                            null,
                                            (double)resultSet.getInt("expTotal"),
                                            resultSet.getString("usuario")
                                            );
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
		return usuario;
	}

    static Usuario verificar(String user, String pass) {
         Usuario usuario = null;
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		ArrayList<String> resultado = new ArrayList<String>();
		try {
                        System.out.println(user+pass);
			connection = DBManager.getConnection();
			String selectSql = "select * from usuario where usuario ='" + user + "' and contra = '" + pass + "';";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(selectSql);
                        
			while (resultSet.next()) {
                            usuario = new Usuario(resultSet.getInt("idUsuario"),
                                            resultSet.getString("nombre"),
                                            resultSet.getString("apellido"),
                                            resultSet.getString("correo"),
                                            "03/04/1997",
                                            null,
                                            (double)resultSet.getInt("expTotal"),
                                            resultSet.getString("usuario")
                                            );
                            System.out.println(usuario.getUsername());
                        }
				//resultado.add(resultSet.getString(1));  
                                
			
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
        return usuario;
    }
}
