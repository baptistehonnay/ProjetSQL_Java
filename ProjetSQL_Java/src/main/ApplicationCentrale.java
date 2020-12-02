package main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

import utils.Db;

public class ApplicationCentrale {
	private static Scanner scanner = new Scanner(System.in);
	private static Db db = new Db();
	private static final String[] MENU_OPTIONS = {
			"Ajouter un local",
			"Ajouter un examen",
			"Encode l'heure de debut d'un examen",
			"Reserver un local pour un examen",
			"Afficher l'horaire d'examen d'un bloc",
			"Afficher les reservations d'un local",
			"Afficher les examens qui ne sont pas completement reserves",
			"Afficher le nombre d'examens pas completement reserves par bloc",
			"Quitter"};
	
	public static void main(String[] args) {
		boolean quit = false;
		while(!quit) {
			displayMainMenu();
			int choice = inputStrictlyPositiveIntegerLE(MENU_OPTIONS.length);
			
			switch(choice) {
			case 1: 
				addLocal();
				break;
			case 2: 
				addExam();
				break;
			case 3: 
				encodeStartHour();
				break;
			case 4: 
				bookLocal();
				break;
			case 5: 
				displayBlocSchedule();
				break;
			case 6: 
				displayLocalBookings();
				break;
			case 7: 
				displayNCRExams();
				break;
			case 8: 
				displayNCRExamNumberByBloc();
				break;
			default:
				quit = true;
			}
		}
	}
	
	//menu option methods
	
	private static void addLocal() {
		clear();
		System.out.println("Ajouter un local:");
		
		System.out.print("Entrez le nom:\n> ");
		String localName = scanner.nextLine();
		
		System.out.print("Entrez le nombre de places:\n> ");
		int seatNumber = inputStrictlyPositiveInteger();
		
		System.out.print("Le local est-il un local machine ? (V/F)\n> ");
		boolean computer = inputBoolean();
		
		if(!db.insertLocal(localName, seatNumber, computer)) {
			System.out.println("Une erreur est survenue durant l'ajout dans la base de donnees");
		}
	}

	private static void addExam() {
		clear();
		System.out.println("Ajouter un examen:");
		
		System.out.print("Entrez le code:\n> ");
		String examCode = scanner.nextLine();
		
		System.out.print("Entrez le nom:\n> ");
		String examName = scanner.nextLine();
		
		System.out.print("Entrez le code bloc:\n> ");
		String codeBloc = scanner.nextLine();
		
		System.out.print("Entrez le temps (en minutes):\n> ");
		int timeInMinutes = inputStrictlyPositiveInteger();
		
		System.out.print("L examen est-il sur machine ? (V/F)\n> ");
		boolean computer = inputBoolean();
		
		if(!db.insertExamen(examCode, examName, codeBloc, timeInMinutes, computer)) {
			System.out.println("Une erreur est survenue durant l'ajout dans la base de donnees");
		}
	}
	
	private static void encodeStartHour() {
		clear();
		System.out.println("Encoder une date & heure de debut d'examen:");
		
		System.out.print("Enterz le code de l'examen\n> ");
		String examCode = scanner.nextLine();
		
		System.out.print("Entrez la date & heure de debut\n> ");
		LocalDateTime startDateTime = inputDateTime();
		
		if(!db.updateStartDateTime(examCode, startDateTime)) {
			System.out.println("Une erreur est survenue durant l'ajout dans la base de donnees");
		}
	}

	private static void bookLocal() {
		clear();
		System.out.println("Reserver un local:");
		
		System.out.print("Entrez le code de l'examen:\n> ");
		String examCode = scanner.nextLine();
		
		System.out.print("Entrez le nom du local:\n> ");
		String nomLocal = scanner.nextLine();
		
		if(!db.bookLocal(examCode, nomLocal)) {
			System.out.println("Une erreur est survenue durant l'ajout dans la base de donnees");
		}
	}
	
	private static void displayBlocSchedule() {
		clear();
		System.out.println("Affichage de l'horaire d'un bloc:");
		
		System.out.print("Entrez le code du bloc:\n> ");
		String blocCode = scanner.nextLine();
		
		if(!db.displayBlocSchedule(blocCode)) {
			System.out.println("Une erreur est survenue durant l'ajout dans la base de donnees");
		}
	}
	
	private static void displayLocalBookings() {
		System.out.println("Afficher les reservations sur un local:");
		
		System.out.print("Entrez le nom:\n> ");
		String localName = scanner.nextLine();
		
		if(!db.displayReservationsLocal(localName)) {
			System.out.println("Une erreur est survenue durant l'ajout dans la base de donnees");
		}
	}
	
	private static void displayNCRExams() {
		if(!db.displayNCRExams()) {
			System.out.println("Une erreur est survenue durant l'ajout dans la base de donnees");
		}
	}
	
	private static void displayNCRExamNumberByBloc() {
		if(!db.displayNCRExamNumberByBloc()) {
			System.out.println("Une erreur est survenue durant l'ajout dans la base de donnees");
		}
	}
	
	
	// --- business methods ---
	
	private static void clear() {
		for (int i = 0; i < 20; i++) {
			System.out.println();
		}
	}
	
	private static void displayMainMenu() {
		clear();
		System.out.println("--Application Centrale--");
		for (int i = 0; i < MENU_OPTIONS.length; i++) {
			System.out.println((i+1) + ". " + MENU_OPTIONS[i]);
		}
		System.out.print("> ");
	}
	
	/**
	 * invites the user to type a number between 1 and max
	 * @param max
	 * @return the user's input
	 */
	private static int inputStrictlyPositiveIntegerLE(int max) {
		int res;
		do {
			try {
				res = scanner.nextInt();
			} catch (InputMismatchException e) {res = -1;}
			
			if(res <= 0 || res > max) {
				System.out.print("Erreur: Entrez un entier entre 1 et " + max + "\n> ");
			}
		}while(res <= 0 || res > max);
		scanner.nextLine();
		return res;
	}
	
	private static int inputStrictlyPositiveInteger() {
		int res;
		do {
			try {
				res = scanner.nextInt();
			} catch (InputMismatchException e) {res = -1;}
			
			if(res <= 0) {
				System.out.print("Erreur: Entrez un entier strictement positif\n> ");
			}
		}while(res <= 0);
		scanner.nextLine();
		return res;
	}
	
	private static boolean inputBoolean() {
		String input;
		do {
			input = scanner.nextLine();
			if(!input.equals("V") && !input.equals("F")) {
				System.out.print("Erreur: Entrez V ou F\n> ");
			}
		} while(!input.equals("V") && !input.equals("F"));
		return input.equals("V");
	}
	
	private static LocalDateTime inputDateTime() {
		String input;
		boolean parseIssue = true;
		LocalDateTime res = null;
		do {
			input = scanner.nextLine();
			try {
				res = LocalDateTime.parse(input, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
				parseIssue = false;
			}catch(DateTimeParseException e) {
				System.out.print("Erreur: Veuillez respecter le format (jj/mm/aaaa hh:mm)\n> ");
			}
		} while(parseIssue);
		return res;
	}
}
