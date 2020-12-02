package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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
	
	public boolean updateStartDateTime(String codeExam, LocalDateTime dateHeureDebut) {
		try {
			PreparedStatement ps = this.conn.prepareStatement("SELECT(projet.update_date_heure_debut(?, ?));");
			ps.setString(1, codeExam);
			ps.setTimestamp(2, Timestamp.valueOf(dateHeureDebut));
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
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

	public boolean insertExamen(String examCode, String examName, String codeBloc, int timeInMinutes,
			boolean computer) {
		try {
			PreparedStatement ps = this.conn.prepareStatement("SELECT( projet.insert_examen(?, ?, ?, ?::interval, ?) );");
			ps.setString(1, examCode);
			ps.setString(2, examName);
			ps.setString(3, codeBloc);
			ps.setString(4, timeInMinutes + " minutes");
			ps.setBoolean(5, computer);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean bookLocal(String examCode, String nomLocal) {
		try {
			PreparedStatement ps = this.conn.prepareStatement("SELECT( projet.insert_reservation_local(?, ?) );");
			ps.setString(1, examCode);
			ps.setString(2, nomLocal);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean displayBlocSchedule(String blocCode) {
		try {
			PreparedStatement ps = this.conn.prepareStatement("SELECT * FROM projet.select_horaire_examen(?) t(date_heure_debut TIMESTAMP, code_examen CHAR(6), nom_examen VARCHAR(100), nombrelocaux_reservés bigint);");
			ps.setString(1, blocCode);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean displayNCRExams() {
		try {
			PreparedStatement ps = this.conn.prepareStatement("SELECT * FROM projet.select_examens_non_completement_reserve() t(code_examen CHAR(6), nom_examen VARCHAR(100), id_bloc INTEGER, duree INTERVAL, est_sur_machines BOOLEAN, date_heure_debut TIMESTAMP, nombre_inscriptions INTEGER);");
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean displayNCRExamNumberByBloc() {
		try {
			PreparedStatement ps = this.conn.prepareStatement("SELECT * FROM projet.select_nbr_exam_pas_completement_reserves_bloc() t(code_bloc VARCHAR(100), nbr_examen_pas_completement_reserve INTEGER);");
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean displayReservationsLocal (String nomLocal) {
		try {
			PreparedStatement ps = this.conn.prepareStatement("SELECT * FROM projet.select_reservations_examens(?) t(date_heure_debut TIMESTAMP, code_examen CHAR(6), nom_examen VARCHAR(100));");
			ps.setString(1, nomLocal);
			ResultSet res = ps.executeQuery();
			while(res.next()) {
				Timestamp dateTime = res.getTimestamp(1);
				String codeExam = res.getString(2);
				String examName = res.getString(3);
				
				System.out.format("%s (%s) : %tB", examName, codeExam, dateTime);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
