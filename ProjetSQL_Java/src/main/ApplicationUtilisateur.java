package main;

import db.Db;
import utils.BCrypt;
import utils.Utils;

public class ApplicationUtilisateur {
	private static Db db = new Db();
	private static final String[] UNKNOWN_USER_OPTIONS = {
			"Se connecter",
			"Creer un un compte",
			"Quitter"
	};
	private static final String[] MENU_OPTIONS = {
			"Afficher les examens",
			"S'inscrire a un examen",
			"S'inscrire a tous les examens du bloc",
			"Afficher son horaire d'examen",
			"Se deconnecter",
			"Quitter"
	};
	
	public static void main(String[] args) {
		boolean quit = false;
		while(!quit) {
			String username = unknownUserMenu();
			if(username == null) 
				quit = true;
			else {
				boolean connected = true;
				while(connected && !quit) {
					System.out.println();
					System.out.println("--Bienvenue " + username + "--");
					Utils.displayMenu(MENU_OPTIONS);
					int choice = Utils.inputStrictlyPositiveIntegerLE(MENU_OPTIONS.length);
					
					switch(choice) {
						case 1:
							displayExams();
							break;
						case 2:
							signUpExam(username);
							break;
						case 3:
							signUpAll(username);
							break;
						case 4:
							displaySchedule(username);
							break;
						case 5:
							connected = false;
							break;
						default:
							quit = true;
					}
				}
			}
		}
	}

	private static void displayExams() {
		System.out.println();
		db.displayExams();
	}

	private static void signUpExam(String username) {
		System.out.println();
		System.out.println("S'inscrire a un examen");
		System.out.print("Entrez le code de l'examen:\n> ");
		String examCode = Utils.nextLine();
		
		if(db.signUpExams(examCode, username)) {
			System.out.println("Vous avez bien ete inscrit");
		}
	}
	
	private static void signUpAll(String username) {
		System.out.println();
		if(db.signUpAll(username)) {
			System.out.println("Vous avez bien ete inscrit");
		}
	}
	
	private static void displaySchedule(String username) {
		System.out.println();
		db.displaySchedule(username);
	}

	/**
	 * @return username or null to quit the app
	 */
	private static String unknownUserMenu() {
		System.out.println();
		String res;
		do {
			System.out.println();
			System.out.println("--Application Utilisateur--");
			Utils.displayMenu(UNKNOWN_USER_OPTIONS);
			int choice = Utils.inputStrictlyPositiveIntegerLE(UNKNOWN_USER_OPTIONS.length);
			switch(choice) {
				case 1:
					res =  connect();
					if(res == null) {
						System.out.println("Nom d'utilisateur ou mot de passe invalide");
					}
					break;
					
				case 2:
					res = register();
					if(res == null) {
						System.out.println("Une erreur est survenue lors de la tentative d'enregistrement du compte, verifiez les donnees que vous avez entre");
					}
					break;
					
				default:
					return null; // quit
			}
		}while(res == null); // while error 
		return res;
		
	}

	/**
	 * @return username or null in case of unexpected error (db)
	 */
	private static String register() {
		System.out.println();
		String email, username, pwd, salt, pwdHash, bloc;
		System.out.print("Entrez votre adresse e-mail:\n> ");
		email = Utils.nextLine();
		
		System.out.print("Entrez votre nom d'utilisateur:\n> ");
		username = Utils.nextLine();
		
		System.out.print("Entrez votre mot de passe:\n> ");
		pwd = Utils.nextLine();
		
		System.out.print("Entrez le code de votre bloc:\n> ");
		bloc = Utils.nextLine();
		
		salt = BCrypt.gensalt();
		pwdHash = BCrypt.hashpw(pwd, salt);
		
		if(!db.insertUser(email, username, pwdHash, bloc)) 
			return null;
		return username;
	}

	/**
	 * @return username or null in case of unexpected error (db)
	 */
	private static String connect() {
		System.out.println();
		String username, pwd, pwdHashDB;
		System.out.print("Enterz votre nom d'utilisateur:\n> ");
		username = Utils.nextLine();
		
		System.out.print("Enterz votre mot de passe:\n> ");
		pwd = Utils.nextLine();
		
		pwdHashDB = db.getUserHash(username);
		if(pwdHashDB == null || !BCrypt.checkpw(pwd, pwdHashDB)){
			return null;
		}
		return username;
	}
}
