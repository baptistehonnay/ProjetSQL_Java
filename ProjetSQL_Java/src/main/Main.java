package main;
import java.sql.*;


public class Main {

	public static void main(String[] args) {
		
		//load driver
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver PostgreSQL manquant !");
			System.exit(1);
		}
		
		//connect to database
		String url="jdbc:postgresql://localhost:5432/projet";
		Connection conn=null;
		try {
			conn=DriverManager.getConnection(url,"userProjetSQL", "azerty");
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
