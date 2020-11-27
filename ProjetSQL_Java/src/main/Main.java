package main;
import java.sql.*;

import config.Context;


public class Main {

	public static void main(String[] args) {
		
		//load driver
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver PostgreSQL manquant !");
			System.exit(1);
		}
		
		Context.load("config.properties");
		//connect to database
		String url = Context.getProperty("dbUrl");
		String username = Context.getProperty("username");
		String userpwd = Context.getProperty("userpwd");
		Connection conn=null;
		try {
			conn=DriverManager.getConnection(url, username, userpwd);
		} catch (SQLException e) {
			System.out.println("Impossible de joindre le server !");
			System.exit(1);
		}
		
		try {
			Statement s = conn.createStatement();
			try(ResultSet rs= s.executeQuery("SELECT ut.mail FROM projet.utilisateurs ut;")){
				while(rs.next()) {
					System.out.println(rs.getString(1));
				}
			}
		} catch (SQLException se) { 
			se.printStackTrace(); 
			System.exit(1); 
		}
		
	}

}
