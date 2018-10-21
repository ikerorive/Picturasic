/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import picturazic1.DBManager;

/**
 *
 * @author HW
 */
public class FuncionesFoto {
	private static final Logger LOGGER = Logger.getLogger("FuncionesFoto");
	public static String retoId = "retoid";

	public static List<Foto> getFotosUsuario(int idUsuario) {

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<Foto> resultado = new ArrayList<>();
		try {
			connection = DBManager.getConnection();
			String selectSql = "SELECT * FROM foto where Autor ='" + idUsuario + "';";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(selectSql);
			// solo devuelve la primera
			while (resultSet.next()) {
				Blob blob = resultSet.getBlob("foto");
				InputStream stream = blob.getBinaryStream(1, blob.length());
				BufferedImage image = ImageIO.read(stream);
				/* resultado.add(image); */
				System.out.println(resultSet.getInt("fotoid"));
				System.out.println(resultSet.getString("descripcion"));
				System.out.println(resultSet.getString("autor"));
				System.out.println(resultSet.getString(retoId));
				System.out.println("-------------------------------");
				resultado.add(new Foto(resultSet.getInt("fotoid"), resultSet.getInt(retoId), image,
						resultSet.getInt("autor")));

			}
		} catch (Exception e) {
			LOGGER.log(Level.FINE, e.toString());
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
		return resultado;
	}

	public static List<Foto> getFotosSeguidos(int idUsuario) {

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<Foto> resultado = new ArrayList<>();
		try {
			connection = DBManager.getConnection();
			String selectSql = "select * from foto where autor in(select seguido from seguidor where seguidor = "
					+ idUsuario + ");";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(selectSql);
			// solo devuelve la primera
			while (resultSet.next()) {
				Blob blob = resultSet.getBlob("foto");
				InputStream stream = blob.getBinaryStream(1, blob.length());
				BufferedImage image = ImageIO.read(stream);
				/* resultado.add(image); */
				System.out.println(resultSet.getInt("fotoid"));
				System.out.println(resultSet.getString("descripcion"));
				System.out.println(resultSet.getString("autor"));
				System.out.println(resultSet.getString("retoId"));
				System.out.println("-------------------------------");

				resultado.add(new Foto(resultSet.getInt("fotoid"), resultSet.getInt(retoId), image,
						resultSet.getInt("autor")));

			}
		} catch (Exception e) {
			LOGGER.log(Level.FINE, e.toString());
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
		return resultado;
	}

	public static String getLikes(int fotoid) {

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		String l = null;
		/* List<Foto> resultado = new ArrayList<>(); */
		try {
			connection = DBManager.getConnection();
			String selectSql = "SELECT count(*) as likes FROM megusta where fotoid='" + fotoid + "';";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(selectSql);
			// solo devuelve la primera
			while (resultSet.next()) {
				
				l = resultSet.getString("likes");

			}
		} catch (Exception e) {
			LOGGER.log(Level.FINE, e.toString());
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
		return l;
	}

	public static void anadirFoto(String descripcion, int autorId, int retoId, String imgPath, String fecha) {
		Connection connection = null;
		PreparedStatement statement = null;
		FileInputStream inputStream = null;
		try {
			File image = new File(imgPath);
			inputStream = new FileInputStream(image);
			connection = DBManager.getConnection();
			statement = connection
					.prepareStatement("INSERT INTO foto (Descripcion, autor, Fecha, retoId, foto) VALUES (?,?,?,?,?);");
			statement.setString(1, descripcion);
			statement.setInt(2, autorId);
			statement.setString(3, fecha);
			statement.setInt(4, retoId);
			statement.setBinaryStream(5, (InputStream) inputStream, (int) (image.length()));
			statement.executeUpdate();
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException: - " + e);
		} catch (SQLException e) {
			System.out.println("SQLException: - " + e);
		} finally {
			try {
				if (connection != null) {
					connection.close();
				
				}
			} catch (SQLException e) {
				System.out.println("SQLException Finally: - " + e);
			}
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {

				LOGGER.log(Level.FINE, e.toString());
			}
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {

				LOGGER.log(Level.FINE, e.toString());
			}
		}
	}

	@SuppressWarnings({ "resource", "null" })
	public static void anadirMegusta(int fotoId, int usuario) throws SQLException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		PreparedStatement prepsInsertProduct = null;
		try {
			connection = DBManager.getConnection();
			String insertSql = "INSERT INTO `megusta` ( `fotoid`, `idusuario`, `megusta`) VALUES (" + fotoId + ", "
					+ usuario + ",1);";

			prepsInsertProduct = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
			prepsInsertProduct.execute();
		} catch (Exception e) {

			if (e instanceof SQLIntegrityConstraintViolationException) {
				String insertSql = "DELETE FROM megusta WHERE fotoid=" + fotoId + "  and idusuario= " + usuario + ";";

				prepsInsertProduct = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
				prepsInsertProduct.execute();

			}
		} finally {
			// Close the connections after the data has been handled.
			if (prepsInsertProduct != null)
				try {
					prepsInsertProduct.close();
				} catch (Exception e) {
				}

			resultSet.close();

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

	static List<Foto> obtenerPopulares() {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<Foto> resultado = new ArrayList<>();
		try {
			connection = DBManager.getConnection();
			String selectSql = "select * from foto f join megusta m on f.fotoId = m.fotoId group by f.fotoId order by count(m.fotoId) DESC limit 25;";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(selectSql);
			// solo devuelve la primera
			while (resultSet.next()) {
				Blob blob = resultSet.getBlob("foto");
				InputStream stream = blob.getBinaryStream(1, blob.length());
				BufferedImage image = ImageIO.read(stream);
				System.out.println(resultSet.getInt("fotoid"));
				System.out.println(resultSet.getString("descripcion"));
				System.out.println(resultSet.getString("autor"));
				System.out.println(resultSet.getString("retoId"));
				System.out.println("-------------------------------");
				resultado.add(new Foto(resultSet.getInt("fotoid"), resultSet.getInt(retoId), image,
						resultSet.getInt("autor")));

			}
		} catch (Exception e) {
			LOGGER.log(Level.FINE, e.toString());
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
		return resultado;
	}
}
