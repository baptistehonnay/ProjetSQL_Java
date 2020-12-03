package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Utils {
	private static Scanner scanner = new Scanner(System.in);
	
	//input
	
	/**
	 * invites the user to type a number between 1 and max
	 * @param max
	 * @return the user's input
	 */
	public static int inputStrictlyPositiveIntegerLE(int max) {
		int res;
		do {
			try {
				res = scanner.nextInt();
			} catch (InputMismatchException e) {
				scanner.nextLine();
				res = -1;
			}
			
			if(res <= 0 || res > max) {
				System.out.print("Erreur: Entrez un entier entre 1 et " + max + "\n> ");
			}
		}while(res <= 0 || res > max);
		scanner.nextLine();
		return res;
	}
	
	public static int inputStrictlyPositiveInteger() {
		int res;
		do {
			try {
				res = scanner.nextInt();
			} catch (InputMismatchException e) {
				scanner.nextLine();
				res = -1;
			}
			
			if(res <= 0) {
				System.out.print("Erreur: Entrez un entier strictement positif\n> ");
			}
		}while(res <= 0);
		scanner.nextLine();
		return res;
	}
	
	public static boolean inputBoolean() {
		String input;
		do {
			input = scanner.nextLine();
			if(!input.equals("V") && !input.equals("F")) {
				System.out.print("Erreur: Entrez V ou F\n> ");
			}
		} while(!input.equals("V") && !input.equals("F"));
		return input.equals("V");
	}
	
	public static LocalDateTime inputDateTime() {
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
	
	public static String nextLine() {
		return scanner.nextLine();
	}
	
	// display
	
	public static void displayMenu(String[] menuOptions) {
		for (int i = 0; i < menuOptions.length; i++) {
			System.out.println((i+1) + ". " + menuOptions[i]);
		}
		System.out.print("> ");
	}
	
}
