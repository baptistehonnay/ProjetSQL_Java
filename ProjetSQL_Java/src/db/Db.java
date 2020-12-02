package db;

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
			//e.printStackTrace();
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
			//e.printStackTrace();
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
			//e.printStackTrace();
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
			//e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean displayBlocSchedule(String blocCode) {
		try {
			PreparedStatement ps = this.conn.prepareStatement("SELECT * FROM projet.select_horaire_examen(?) t(date_heure_debut TIMESTAMP, code_examen CHAR(6), nom_examen VARCHAR(100), nombrelocaux_reservés bigint);");
			ps.setString(1, blocCode);
			ResultSet res = ps.executeQuery();
			while(res.next()) {
				System.out.format("-------------------------%ndate heure debut : %tB%ncode examen : %s%nnom examen : %s%nnombre locaux reservé(s) : %d%n", res.getTimestamp(1), res.getString(2), res.getString(3), res.getInt(4));
			}
		} catch (SQLException e) {
			//e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean displayNCRExams() {
		try {
			PreparedStatement ps = this.conn.prepareStatement("SELECT * FROM projet.select_examens_non_completement_reserve() t(code_examen CHAR(6), nom_examen VARCHAR(100), id_bloc INTEGER, duree INTERVAL, est_sur_machines BOOLEAN, date_heure_debut TIMESTAMP, nombre_inscriptions INTEGER);");
			ResultSet res = ps.executeQuery();
			while(res.next()) {
				System.out.format("-------------------------%ncode examen : %s%nnom examen : %s%nid bloc : %d%nduree : %tB%nest sur machine : %b%ndate heure debut %tB%nnombre inscrits : %d%n", res.getString(1), res.getString(2), res.getInt(3), res.getTimestamp(4), res.getBoolean(5), res.getTimestamp(6), res.getInt(7));
			}
		} catch (SQLException e) {
			//e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean displayNCRExamNumberByBloc() {
		try {
			PreparedStatement ps = this.conn.prepareStatement("SELECT * FROM projet.select_nbr_exam_pas_completement_reserves_bloc() t(code_bloc VARCHAR(100), nbr_examen_pas_completement_reserve INTEGER);");
			ResultSet res = ps.executeQuery();
			while(res.next()) {
				System.out.format("-------------------------%ncode bloc : %s%nnombre examen pas completement reserve: %d%n", res.getString(1), res.getInt(2));
			}
		} catch (SQLException e) {
			//e.printStackTrace();
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
				
				System.out.format("-------------------------%nnom: %s%ncode: %s%ndate de debut: %tB%n", examName, codeExam, dateTime);
			}
		} catch (SQLException e) {
			//e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean displayExams() {
		try {
			PreparedStatement ps = this.conn.prepareStatement("SELECT * FROM projet.select_examens() t(code_examen CHARACTER(6), nom_examen VARCHAR(100), id_bloc INTEGER, duree INTERVAL);");
			ResultSet res = ps.executeQuery();
			while(res.next()) {
				System.out.format("-------------------------%ncode d'examen : %s%nnom d'examen : %s%nid du bloc : %d%nduree : %s%n", res.getString(1), res.getString(2), res.getInt(3), res.getString(4));
			}
		} catch (SQLException e) {
			//e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean signUpExams(String codeExamen, String username) {
		try {
			PreparedStatement ps = this.conn.prepareStatement("SELECT( projet.insert_inscription_examen(?, ?) );");
			ps.setString(1, codeExamen);
			ps.setString(2, username);
			ps.execute();
		} catch (SQLException e) {
			//e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean signUpAll(String username) {
		try {
			PreparedStatement ps = this.conn.prepareStatement("SELECT( projet.insert_toutes_inscriptions_examens_bloc_utilisateur(?) );");
			ps.setString(1, username);
			ps.execute();
		} catch (SQLException e) {
			//e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean displaySchedule(String username) {
		try {
			PreparedStatement ps = this.conn.prepareStatement("SELECT * FROM projet.select_horaire_examens('moi') t(code_examen CHARACTER(6), nom_examen VARCHAR(100), id_bloc INTEGER, date_heure_debut TIMESTAMP, heure_fin TIMESTAMP, locaux VARCHAR(100));");
			ResultSet res = ps.executeQuery();
			while(res.next()) {
				System.out.format("-------------------------%ncode d'examen : %s%nnom d'examen : %s%nid du bloc : %d%ndate heure debut : %tB%nheure de fin %tB%nlocaux : %s%n", res.getString(1), res.getString(2), res.getInt(3), res.getTimestamp(4), res.getTimestamp(5), res.getString(6));
			}
		} catch (SQLException e) {
			//e.printStackTrace();
			return false;
		}
		return true;
	}
}
