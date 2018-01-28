package hauptprogramm;

import java.util.ArrayList;
import java.util.Scanner;

import klassen.Field;
import klassen.Player;
import methoden.Create_Game_Field;
import methoden.Start;

public class Schach {

	public static void main(String[] args) {
		Scanner main_scanner = new Scanner(System.in);
		boolean end;
		Player player1 = new Player();
		Player player2 = new Player();
		do {
			int drawnumbers = 0;
			boolean draw = true;
			player1.checked = false;
			player2.checked = false;
			// methode von außen starten
			Start.chessstart(player1, player2, main_scanner);
			// feld generierung und übergabe
			ArrayList<ArrayList<Field>> Game_Field = Create_Game_Field.erstellen();
			
			// eigentliches spiel, solange kein König geschlagen worden ist oder
			// spieler aufgegeben hat
			while (player1.checked == false || player2.checked == false) {
				System.out.println("----------------------------------------------------------------------------------------");
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println("----------------------------------------------------------------------------------------");
				// abwechselnder Zug
				if (player1.color == draw) {
					player1.make_draw(Game_Field, main_scanner);
					// Spielerwechsel
					draw = (player1.color == true) ? false : true;
					// abfrage ob spieler aufgegeben hat oder könig geschlagen
					// ist
					if (player1.checked) {
						break;
					}
					// Anzahl der Züge erhöhen
					drawnumbers++;
				} else if (player2.color == draw) {
					player2.make_draw(Game_Field, main_scanner);
					
					draw = (player2.color == true) ? false : true;
					if (player2.checked) {
						break;
					}
					drawnumbers++;
				} else {
					System.out.println("fehler");
				}
			}

			// spielfeld zum schluss ausgeben
			display_board(Game_Field);
			// gewinner ausgeben
			System.out.println("Glückwunsch, Gewinner ist " + ((player1.checked) ? player2.name : player1.name) + " in "
					+ (drawnumbers) + " Zügen");
			String end_input = "";
			// wird neues spiel gewünscht?
			while (!end_input.contains("ja") && !end_input.contains("nein")) {
				System.out.println("Nochmal Spielen? Ja/Nein");
				end_input = main_scanner.next();
				end_input = end_input.toLowerCase();
			}
			end = (end_input.contains("ja")) ? true : false;
		} while (end);
		// scanner zu
		main_scanner.close();
		System.out.println("Programm beendet, danke dass Sie sich für mein Produkt entschieden haben!");
	}

	public static void display_board(ArrayList<ArrayList<Field>> Game_Field) {
		// Spielfeld wird ausgegeben
		String name;
		System.out.println("----------------------------");
		for (int i = 8; i >= 1; i--) {
			name = "| " + i + "|";
			for (int j = 1; j <= 8; j++) {
				name += Game_Field.get(i).get(j).getName() + "|";
			}
			System.out.println(name);
			System.out.println("----------------------------");
		}
		name = "|  |";
		for (int i = 1; i <= 8; i++) {
			name += " " + numberreference(i) + "|";
		}
		System.out.println(name);
		System.out.println("----------------------------");
	}

	public static char numberreference(int n) {
		char c = 0;
		switch (n) {
		case 1:
			c = 'A';
			break;
		case 2:
			c = 'B';
			break;
		case 3:
			c = 'C';
			break;
		case 4:
			c = 'D';
			break;
		case 5:
			c = 'E';
			break;
		case 6:
			c = 'F';
			break;
		case 7:
			c = 'G';
			break;
		case 8:
			c = 'H';
			break;
		default:
			break;
		}
		return c;
	}

	public static int stringreference(String string) {
		int number = 0;
		switch (string.toUpperCase()) {
		case "A":
			number = 1;
			break;
		case "B":
			number = 2;
			break;
		case "C":
			number = 3;
			break;
		case "D":
			number = 4;
			break;
		case "E":
			number = 5;
			break;
		case "F":
			number = 6;
			break;
		case "G":
			number = 7;
			break;
		case "H":
			number = 8;
			break;
		default:
			break;
		}
		return number;
	}
}