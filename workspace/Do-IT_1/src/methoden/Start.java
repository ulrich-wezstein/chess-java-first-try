package methoden;

import java.util.Random;
import java.util.Scanner;

import klassen.Player;

public class Start {

	public static void chessstart(Player player1, Player player2, Scanner main_scanner) {
		// Scanner und Rand initialisieren, Eingabe durch Spieler erwarten
		Random rand = new Random();

		System.out.println("Hallo, dies ist eine erste Version und Versuch Schach zu digitalisieren. \n"
				+ "Das Spiel ist gewonnen, sobald der gegnerische Kˆnig geschlagen worden ist, oder \n"
				+ "der Gegner durch die eingabe 'exit' aufgegeben hat. \n"
				+ "Die Figuren werden durch Tastatureingabe mit den respektiven Feldern (A1 bis H8) ausgew‰hlt\n"
				+ "und mit der gleichen Eingabe (A1 bis H8) bewegt.\n\n"
				+ "Bitte geben Sie den Namen des ersten Spielers ein.");
		player1.name = main_scanner.nextLine();
		System.out.println("Und nun den Namen des zweiten Spielers.");
		player2.name = main_scanner.nextLine();
		// farbwahl durch zufall
		int random_number = rand.nextInt(10000);
		if (random_number >= 5000) {
			player1.color = true;
			player1.name += " (Weiﬂ)";
			player2.color = false;
			player2.name += " (Schwarz)";
		} else {
			player1.color = false;
			player1.name += " (Schwarz)";
			player2.color = true;
			player2.name += " (Weiﬂ)";
		}
		// farbwahlausgabe
		System.out.println(
				"Danke, es werden " + player1.name + " und " + player2.name + " gegeneinander. Das Spiel beginnt!");
		if (player1.color) {
			System.out.println("Es beginnt " + player1.name + "!");
		} else if (player2.color) {
			System.out.println("Es beginnt " + player2.name + "!");
		} else {
			System.out.println("Fehler.");
		}
	}

}
