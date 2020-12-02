package main;

import db.Db;
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
		if(!db.displayExams()) {
			System.out.println("Une erreur est survenue lors la requete dans la base de donnees");
		}
	}

	private static void signUpExam(String username) {
		System.out.println("S'inscrire a un examen");
		System.out.print("Entrez le code de l'examen:\n> ");
		String examCode = Utils.nextLine();
		
		if(!db.signUpExams(examCode, username)) {
			System.out.println("Une erreur est survenue lors la requete dans la base de donnees");
		}
	}
	
	private static void signUpAll(String username) {
		if(!db.signUpAll(username)) {
			System.out.println("Une erreur est survenue lors la requete dans la base de donnees");
		}
	}
	
	private static void displaySchedule(String username) {
		if(!db.displaySchedule(username)) {
			System.out.println("Une erreur est survenue lors la requete dans la base de donnees");
		}
	}

	/**
	 * 
	 * @return username or null to quit the app
	 */
	private static String unknownUserMenu() {
		Utils.displayMenu(UNKNOWN_USER_OPTIONS);
		int choice = Utils.inputStrictlyPositiveIntegerLE(UNKNOWN_USER_OPTIONS.length);
		switch(choice) {
		case 1:
			return connect();
		case 2:
			return register();
		default:
			return null;
		}
	}

	private static String register() {
		return null;
	}

	private static String connect() {
		return null;
	}
}
