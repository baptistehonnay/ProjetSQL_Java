package main;

import java.time.LocalDateTime;

import db.Db;
import utils.Utils;

public class ApplicationCentrale {
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
			System.out.println();
			System.out.println("--Application Centrale--");
			Utils.displayMenu(MENU_OPTIONS);
			int choice = Utils.inputStrictlyPositiveIntegerLE(MENU_OPTIONS.length);
			
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
		System.out.println();
		System.out.println("Ajouter un local:");
		
		System.out.print("Entrez le nom:\n> ");
		String localName = Utils.nextLine();
		
		System.out.print("Entrez le nombre de places:\n> ");
		int seatNumber = Utils.inputStrictlyPositiveInteger();
		
		System.out.print("Le local est-il un local machine ? (V/F)\n> ");
		boolean computer = Utils.inputBoolean();
		
		if(db.insertLocal(localName, seatNumber, computer)) {
			System.out.println("Le local a bien ete ajoute");
		}
	}

	private static void addExam() {
		System.out.println();
		System.out.println("Ajouter un examen:");
		
		System.out.print("Entrez le code:\n> ");
		String examCode = Utils.nextLine();
		
		System.out.print("Entrez le nom:\n> ");
		String examName = Utils.nextLine();
		
		System.out.print("Entrez le code bloc:\n> ");
		String codeBloc = Utils.nextLine();
		
		System.out.print("Entrez le temps (en minutes):\n> ");
		int timeInMinutes = Utils.inputStrictlyPositiveInteger();
		
		System.out.print("L examen est-il sur machine ? (V/F)\n> ");
		boolean computer = Utils.inputBoolean();
		
		if(db.insertExamen(examCode, examName, codeBloc, timeInMinutes, computer)) {
			System.out.println("L'examen a bien ete ajoute");
		}
	}
	
	private static void encodeStartHour() {
		System.out.println();
		System.out.println("Encoder une date & heure de debut d'examen:");
		
		System.out.print("Entrez le code de l'examen\n> ");
		String examCode = Utils.nextLine();
		
		System.out.print("Entrez la date & heure de debut\n> ");
		LocalDateTime startDateTime = Utils.inputDateTime();
		
		if(!db.updateStartDateTime(examCode, startDateTime)) {
			System.out.println("L'heure a bien ete encodee");
		}
	}

	private static void bookLocal() {
		System.out.println();
		System.out.println("Reserver un local:");
		
		System.out.print("Entrez le code de l'examen:\n> ");
		String examCode = Utils.nextLine();
		
		System.out.print("Entrez le nom du local:\n> ");
		String nomLocal = Utils.nextLine();
		
		if(!db.bookLocal(examCode, nomLocal)) {
			System.out.println("Le local a bien ete reserve");
		}
	}
	
	private static void displayBlocSchedule() {
		System.out.println();
		System.out.println("Affichage de l'horaire d'un bloc:");
		
		System.out.print("Entrez le code du bloc:\n> ");
		String blocCode = Utils.nextLine();
		
		db.displayBlocSchedule(blocCode);
	}
	
	private static void displayLocalBookings() {
		System.out.println();
		System.out.println("Afficher les reservations sur un local:");
		
		System.out.print("Entrez le nom:\n> ");
		String localName = Utils.nextLine();
		
		db.displayReservationsLocal(localName);
	}
	
	private static void displayNCRExams() {
		System.out.println();
		db.displayNCRExams();
	}
	
	private static void displayNCRExamNumberByBloc() {
		System.out.println();
		db.displayNCRExamNumberByBloc();
	}
	
	
}
