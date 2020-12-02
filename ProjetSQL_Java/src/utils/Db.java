package utils;

import java.sql.*;
import config.Context;

public class Db {
	private Connection conn;
	
	public Db() {
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
		this.conn=null;
		try {
			this.conn=DriverManager.getConnection(url, username, userpwd);
		} catch (SQLException e) {
			System.out.println("Impossible de joindre le server !");
			System.exit(1);
		}
	}
	
	public boolean insertLocal(String localName, int seatNumber, boolean computer) {
		try {
			PreparedStatement ps = this.conn.prepareStatement("SELECT( projet.insert_local(?, ?, ?) );");
			ps.setString(1, localName);
			ps.setInt(2, seatNumber);
			ps.setBoolean(3, computer);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
