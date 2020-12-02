package main;

import db.Db;
import utils.Utils;

public class ApplicationUtilisateur {
	private static Db db = new Db();
	private static final String[] MENU_OPTIONS = {
			"Afficher les examens",
			"S'inscrire à un examen",
			"S'inscrire a tous les examens du bloc",
			"Afficher son horaire d'examen",
			"Se deconnecter",
			"Quitter"
	};
	
	public static void main(String[] args) {
		boolean quit = false;
		while(!quit) { 
			String username = connect();
			boolean connected = true;
			while(connected && !quit) {
				Utils.displayMainMenu(MENU_OPTIONS);
				int choice = Utils.inputStrictlyPositiveIntegerLE(MENU_OPTIONS.length);
				
				switch(choice) {
				case 1:
					displayExams();
					break;
				case 2:
					signUpExam();
					break;
				case 3:
					signUpAll();
					break;
				case 4:
					displaySchedule();
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

	private static void displayExams() {
		// TODO Auto-generated method stub
		
	}

	private static void signUpExam() {
		// TODO Auto-generated method stub
		
	}
	
	private static void signUpAll() {
		// TODO Auto-generated method stub
		
	}
	
	private static void displaySchedule() {
		// TODO Auto-generated method stub
		
	}

	private static String connect() {
		return "";
	}
}
