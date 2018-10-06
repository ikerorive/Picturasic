/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package picturazic1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author HW
 */
public class DBManager {
    static String USER = "root";
	static String PASS = "";
	static String DBNAME = "fotos";

	// Connect to your database.
	// Replace server name, username, and password with your credentials

	public static Connection getConnection() {
		// TODO Auto-generated method stub
		String connectionString = "jdbc:mysql://localhost/" + DBNAME + "?" + "user=" + USER + "&password=" + PASS;
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(connectionString);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

}
