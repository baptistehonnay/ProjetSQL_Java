package main;

import java.util.Scanner;

public class ApplicationCentrale {
	private static Scanner scanner = new Scanner(System.in);
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
				// TODO addLocal();
				break;
			case 2: 
				// TODO addExam();
				break;
			case 3: 
				// TODO encodeStartHour();
				break;
			case 4: 
				// TODO bookLocal();
				break;
			case 5: 
				// TODO displayBlocSchedule();
				break;
			case 6: 
				// TODO displayLocalBookings();
				break;
			case 7: 
				// TODO displayNCRExams();
				break;
			case 8: 
				// TODO displayNCRExamNumberByBloc();
				break;
			default:
				quit = true;
			}
		}
	}
	
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
	}
	
	/**
	 * invites the user to type a number between 1 and max
	 * @param max
	 * @return the user's input
	 */
	private static int inputStrictlyPositiveIntegerLE(int max) {
		int res;
		do {
			res = scanner.nextInt();
			if(res <= 0 || res > max) {
				System.out.print("Entrez un nombre entre 1 et " + max + "\n> ");
			}
		}while(res <= 0 || res > max);
		return res;
	}
}
