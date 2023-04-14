package com.example.springboot.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MiConexion {
	static Connection con = null;
	static String URL = "jdbc:postgresql://localhost:5432/banco";
	static String USER = "postgres";
	static String PSSW = "root";
	
	public MiConexion() {
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(URL, USER, PSSW);
		}
		catch(SQLException ex) {
			System.out.println("Error al conectarse con la base de datos: " + ex);
            ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
            Logger.getLogger(MiConexion.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
	
	public Connection getConnection() {
		return con;
	}
}
