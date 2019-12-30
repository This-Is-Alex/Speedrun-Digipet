package me.thisisalex.speedrun1.menus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import me.thisisalex.speedrun1.logic.DecayingStat;
import me.thisisalex.speedrun1.logic.IncreasingStat;
import me.thisisalex.speedrun1.logic.Pet;
import me.thisisalex.speedrun1.logic.Stat;

public class MenuManager {
	
	private static String[] happyPet = new String[] {
			"             ",
			"     ^-^     ",
			"             "
	};
	
	private static String[] sadPet = new String[] {
			"             ",
			"     ._.     ",
			"             "
	};
	
	private static String[] sleepingPet = new String[] {
			"        zzzzz",
			"     ^-^     ",
			"             "
	};

	static BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
	
	public static String askQuestion() {
		String line = "";
		try {
			line = scanner.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return line;
	}
	
	public static boolean shouldActivateMenu() {
		try {
			if (scanner.ready()) {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean askBooleanQuestion(String question) {
		while (true) {
			System.out.println(question+" [yes/no]");
			String answer = askQuestion();
			if (answer.equalsIgnoreCase("yes")) return true;
			if (answer.equalsIgnoreCase("no")) return true;
		}
	}
	
	public static int askIntegerQuestion(String question) {
		while (true) {
			System.out.println(question+" [number]");
			String answer = askQuestion();
			try {
				return Integer.parseInt(answer);
			} catch (ValueException e) {}
		}
	}
	
	public static int askIntegerRangeQuestion(String question, int min, int max) {
		while (true) {
			System.out.println(question+" ["+min+"-"+max+"]");
			String answer = askQuestion();
			try {
				int i = Integer.parseInt(answer);
				if (i >= min && i <= max) {
					return i;
				}
			} catch (NumberFormatException e) {}
		}
	}
	
	public static void printSummary(Pet pet) {
		for (int i = 0; i < 50; i++) {
			System.out.println();
		}
		if (pet.getMood() == 0) {
			for (String string : happyPet) {
				System.out.println(string);
			}
		} else if (pet.getMood() == 1) {
			for (String string : sadPet) {
				System.out.println(string);
			}
		} else if (pet.getMood() == 3) {
			for (String string : sleepingPet) {
				System.out.println(string);
			}
		}
		for (Stat stat : pet.getStats()) {
			if (stat instanceof IncreasingStat) {
				System.out.println(padString(stat.getName(), 50)+" "+stat.getValue()+"/"+((IncreasingStat)stat).maxValue());
			} else if (stat instanceof DecayingStat) {
				System.out.println(padString(stat.getName(), 50)+" "+stat.getValue()+"/"+((DecayingStat)stat).maxValue());
			} else {
				System.out.println(padString(stat.getName(), 50)+" "+stat.getValue());
			}
		}
		System.out.println("[Press enter for actions list]");
	}
	
	private static String padString(String string, int pad) {
		while (string.length() < pad) {
			string += " ";
		}
		return string;
	}
	
	public static void dialog(String prompt) {
		for (int i = 0; i < 50; i++) {
			System.out.println();
		}
		System.out.println(prompt);
		for (int i = 0; i < 3; i++) {
			System.out.println();
		}
		System.out.println("[Press enter to continue]");
		askQuestion();
	}
}
