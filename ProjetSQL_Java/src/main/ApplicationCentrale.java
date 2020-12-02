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
			"Afficher les réservations d'un local",
			"Afficher les examens qui ne sont pas completement reserves",
			"Afficher le nombre d'examens pas complétement reserves par bloc",
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
		//TODO
	}
	
	private static void encodeStartHour() {
		clear();
		System.out.println("Encoder une date & heure de debut d'examen:");
		
		System.out.print("Enterz le code de l'examen\n> ");
		String codeExam = scanner.nextLine();
		
		System.out.print("Entrez la date & heure de debut\n> ");
		LocalDateTime dateTimeDebut = inputDateTime();
		
		if(!db.updateDateHeureDebut(codeExam, dateTimeDebut)) {
			System.out.println("Une erreur est survenue durant l'ajout dans la base de donnees");
		}
	}

	private static void bookLocal() {
		//TODO
	}
	
	private static void displayBlocSchedule() {
		//TODO
	}
	
	private static void displayLocalBookings() {
		System.out.println("Afficher les réservations sur un local:");
		
		System.out.print("Entrez le nom:\n> ");
		String localName = scanner.nextLine();
		
		if(!db.selectReservationsLocal(localName)) {
			System.out.println("Une erreur est survenue durant l'ajout dans la base de donnees");
		}
	}
	
	private static void displayNCRExams() {
		//TODO
	}
	
	private static void displayNCRExamNumberByBloc() {
		//TODO
	}
	
	
	//business methods
	
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
