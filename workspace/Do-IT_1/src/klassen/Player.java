package klassen;

import java.util.ArrayList;
import java.util.Scanner;

import hauptprogramm.Schach;

public class Player {
	public String name = "";
	public boolean color = true;
	public boolean checked = false;

	public void make_draw(ArrayList<ArrayList<Field>> Game_Field, Scanner main_scanner) {
		String input;
		int start_field[] = { 0, 0 };
		int target_field[] = { 0, 0 };
		boolean valid_move = false;

		// Abfrage, ob König geschlagen wurde
		check_kingstatus(Game_Field, this);

		// Schachmatt abfragen.
		if (!this.checked) {
			check_checkmate(Game_Field, this);
		}

		if (!this.checked) {
			Schach.display_board(Game_Field);
			System.out.println(this.name + " Figurwahl:");
			input = main_scanner.next();
			start_field = start_field_check_move(valid_move, input, start_field, Game_Field, main_scanner);
			if (!inputexitcheck(input)) {
				ArrayList<Field> possibleFields = Game_Field.get(start_field[1]).get(start_field[0])
						.getPossibleMoves(Game_Field);
				// for (Field field : possibleFields) {
				// System.out.println(field);
				// }
				if (!this.checked) {

					valid_move = false;
					System.out.println(this.name + " Zielfeld:");
					input = main_scanner.next();
					if (!inputexitcheck(input)) {
						boolean targetfieldcheck = target_field_check(input, Game_Field);

						target_field = target_field_check_move(targetfieldcheck, valid_move, target_field,
								possibleFields, Game_Field, input, main_scanner);

						targetfieldcheck = false;
						check_figure(Game_Field, target_field, this.color);
						move_figure(Game_Field, target_field, start_field);
						pawn_auto_check(Game_Field);

						// "Schach"-Status abfragen
						check_check(Game_Field, this, target_field);
					}
				}
			}
		}
	}

	private void check_checkmate(ArrayList<ArrayList<Field>> game_Field, Player player) {
		int x_axis;
		int y_axis;
		int kingfield[] = new int[2];
		// Position des eigenen Königs ermitteln
		for (x_axis = 1; x_axis <= 8; x_axis++) {
			for (y_axis = 1; y_axis <= 8; y_axis++) {
				if (game_Field.get(y_axis).get(x_axis).field_figur != null
						&& game_Field.get(y_axis).get(x_axis).field_figur.name == "K"
						&& game_Field.get(y_axis).get(x_axis).field_figur.color == player.color) {
					kingfield[0] = x_axis;
					kingfield[1] = y_axis;
				}
			}
		}
		// mögliche Zugfelder des Königs ermitteln
		ArrayList<Field> possible_king_fields = game_Field.get(kingfield[1]).get(kingfield[0]).field_figur
				.getPossibleFields(game_Field);
		boolean field_found = false;
		int fields_found = 0;
		// für jedes einzelne zugfeld des Königs
		for (int i = 0; i < possible_king_fields.size(); i++) {
			field_found = false;
			x_axis = 1;
			// über ganzes Spielfeld schauen
			while (!field_found && x_axis <= 8) {
				y_axis = 1;
				while (!field_found && y_axis <= 8) {
					// wenn noch kein mögliches Zugfeld abgedeckt wurde, die
					// position leer ist oder eine gegnerische figur drauf steht
					if (!field_found && game_Field.get(y_axis).get(x_axis).field_figur != null
							&& (game_Field.get(y_axis).get(x_axis).field_figur.color != player.color)) {
						// mögliche Zugfelder der gegnerischen Figur ermitteln
						ArrayList<Field> field_figur_possible_fields = game_Field.get(y_axis).get(x_axis).field_figur
								.getPossibleHitFields(game_Field);
						int figurfieldcounter = 0;

						while (!field_found && figurfieldcounter < field_figur_possible_fields.size()) {
							// wenn mögliches Zugfeld des Königs auch mögliches
							// Zugfeld einer gegnerischen Figur ist, Zähler
							// erhöhen
							if (possible_king_fields.get(i).pos_x == field_figur_possible_fields
									.get(figurfieldcounter).pos_x
									&& possible_king_fields.get(i).pos_y == field_figur_possible_fields
											.get(figurfieldcounter).pos_y) {
								field_found = true;
								fields_found++;
							}
							figurfieldcounter++;

						}
					}
					y_axis++;
				}
				x_axis++;
			}
		}
		// wenn jetzt die Anzahl der Zugfelder des Königs und die abgedeckten
		// Felder durch gegnerische Figuren übereinstimmt
		if (possible_king_fields.size() == fields_found) {
			// Falls der König keine Zugmöglichkeit per se hat
			if (possible_king_fields.size() != 0) {
				System.out.println("Schachmatt");
				this.checked = true;
			} else {
				int x = kingfield[0];
				int y = kingfield[1];
				int min_x = (x == 1) ? x : (x - 1);
				int max_x = (x == 8) ? x : (x + 1);
				int min_y = (y == 1) ? y : (y - 1);
				int max_y = (y == 8) ? y : (y + 1);
				// schauen, ob eine eigene Figur neben dem König steht, sonst
				// hätte der König eine Zugmöglichkeit, andernfalls Schachmatt.
				for (x_axis = min_x; x_axis <= max_x; x_axis++) {
					for (y_axis = min_y; y_axis <= max_y; y_axis++) {
						if (x_axis == x & y_axis == y) {
						} else {
							if (game_Field.get(y_axis).get(x_axis).field_figur == null) {
								System.out.println("Schachmatt");
								this.checked = true;
							} else if (game_Field.get(y_axis).get(x_axis).field_figur != null) {
								if (game_Field.get(y_axis).get(x_axis).field_figur.color != this.color) {
									System.out.println("Schachmatt");
									this.checked = true;
								}
							}

						}
					}
				}
			}
		}
	}

	private void check_check(ArrayList<ArrayList<Field>> game_Field, Player player, int target_field[]) {
		ArrayList<Field> next_possible_fields = game_Field.get(target_field[1]).get(target_field[0])
				.getPossibleMoves(game_Field);
		for (int i = 0; i < next_possible_fields.size(); i++) {
			int x = next_possible_fields.get(i).pos_x;
			int y = next_possible_fields.get(i).pos_y;
			if (game_Field.get(y).get(x).field_figur != null) {
				if (game_Field.get(y).get(x).field_figur.name == "K") {
					System.out.println("Schach!");
				}
			}
		}
	}

	private void check_kingstatus(ArrayList<ArrayList<Field>> Game_Field, Player player) {
		// Wenn ein König auf der Checked Position steht und die Spielerfarbe
		// hat, wird Spieler als geschlagen gesetzt und im nächsten Schritt als
		// Verlierer ausgegeben
		if (Game_Field.get(0).get(5).field_figur != null && (Game_Field.get(0).get(5).field_figur.checked == true
				& Game_Field.get(0).get(5).field_figur.color == player.color)) {
			player.checked = true;
		}
		if (Game_Field.get(9).get(5).field_figur != null && (Game_Field.get(9).get(5).field_figur.checked == true
				& Game_Field.get(9).get(5).field_figur.color == player.color)) {
			player.checked = true;
		}

	}

	private void pawn_auto_check(ArrayList<ArrayList<Field>> Game_Field) {
		for (int x_axis = 1; x_axis <= 8; x_axis++) {
			if (Game_Field.get(1).get(x_axis).field_figur != null) {
				if (Game_Field.get(1).get(x_axis).field_figur.name == "B") {
					int check_x = Game_Field.get(1).get(x_axis).field_figur.checkedposition_x;
					int check_y = Game_Field.get(1).get(x_axis).field_figur.checkedposition_y;

					Game_Field.get(1).get(x_axis).field_figur.checked = true;
					Game_Field.get(check_y).get(check_x).deliver(Game_Field.get(1).get(x_axis).field_figur, check_x,
							check_y);
					Game_Field.get(1).get(x_axis).deliver_empty();
					System.out.println("Ihr durchgezogener Bube wurde automatisch vom Spielfeld genommen");
				}
			}
		}
		for (int x_axis = 1; x_axis <= 8; x_axis++) {
			if (Game_Field.get(8).get(x_axis).field_figur != null) {
				if (Game_Field.get(8).get(x_axis).field_figur.name == "B") {
					int check_x = Game_Field.get(8).get(x_axis).field_figur.checkedposition_x;
					int check_y = Game_Field.get(8).get(x_axis).field_figur.checkedposition_y;

					Game_Field.get(8).get(x_axis).field_figur.checked = true;
					Game_Field.get(check_y).get(check_x).deliver(Game_Field.get(8).get(x_axis).field_figur, check_x,
							check_y);
					Game_Field.get(8).get(x_axis).deliver_empty();
					System.out.println("Ihr durchgezogener Bube wurde automatisch vom Spielfeld genommen");
				}
			}
		}

	}

	public int[] start_field_check_move(boolean valid_move, String input, int[] start_field,
			ArrayList<ArrayList<Field>> Game_Field, Scanner main_scanner) {
		boolean startfieldcheck = start_field_check(input, Game_Field);
		// während es kein gültiger zug ist
		while (!inputexitcheck(input) & !valid_move) {
			// Gültigkeit der eingabe und farbzugehörigkeit prüfen
			if (startfieldcheck) {
				// erstes zeichen einlesen
				start_field[0] = Schach.stringreference(input.substring(0, 1));
				// zweites zeichen als int parsen
				start_field[1] = Integer.parseInt(input.substring(1));
				if (Game_Field.get(start_field[1]).get(start_field[0]).getPossibleMoves(Game_Field).isEmpty()) {
					System.out.println("Diese Figur kann nicht bewegt werden!\nNeue Figur auswählen!");
					startfieldcheck = false;
					valid_move = false;
				} else {
					valid_move = true;
					System.out.println(
							"Sie haben " + Game_Field.get(start_field[1]).get(start_field[0]).getName() + " gewählt.");
				}
			} else {
				System.out.println("Fehlerhafte Eingabe, nochmal wiederholen");
				input = main_scanner.next();
				startfieldcheck = start_field_check(input, Game_Field);
			}
		}
		return start_field;
	}

	public int[] target_field_check_move(boolean targetfieldcheck, boolean valid_move, int target_field[],
			ArrayList<Field> possibleFields, ArrayList<ArrayList<Field>> Game_Field, String input,
			Scanner main_scanner) {
		while (!inputexitcheck(input) & !valid_move) {
			if (targetfieldcheck) {
				target_field[0] = Schach.stringreference(input.substring(0, 1));
				target_field[1] = Integer.parseInt(input.substring(1));
				for (int i = 0; i < possibleFields.size(); i++) {
					if (target_field[0] == possibleFields.get(i).pos_x) {
						if (target_field[1] == possibleFields.get(i).pos_y) {
							valid_move = true;
						}
					}
				}
				if (valid_move) {
					targetfieldcheck = false;
				} else {
					System.out.println("Ungültiges Zielfeld gewählt");
					targetfieldcheck = false;
				}
			} else {
				System.out.println("Fehlerhafte Eingabe, nochmal wiederholen");
				input = main_scanner.next();
				targetfieldcheck = target_field_check(input, Game_Field);
			}
		}
		return target_field;
	}

	public void check_figure(ArrayList<ArrayList<Field>> Game_Field, int target_field[], boolean playercolor) {
		// wenn gegnerische figur, dann stell diese Figur auf dessen Checked
		// Position
		int x_axis = target_field[0];
		int y_axis = target_field[1];

		if (Game_Field.get(y_axis).get(x_axis).field_figur != null) {
			int check_x = Game_Field.get(y_axis).get(x_axis).field_figur.checkedposition_x;
			int check_y = Game_Field.get(y_axis).get(x_axis).field_figur.checkedposition_y;
			if (Game_Field.get(y_axis).get(x_axis).field_figur.color != playercolor) {

				Game_Field.get(y_axis).get(x_axis).field_figur.checked = true;

				Game_Field.get(check_y).get(check_x).deliver(Game_Field.get(y_axis).get(x_axis).field_figur, check_x,
						check_y);

				Game_Field.get(y_axis).get(x_axis).deliver_empty();

			}
		}
	}

	public void move_figure(ArrayList<ArrayList<Field>> Game_Field, int target_field[], int start_field[]) {
		// Zielfeld erhält ausgewählte figur
		Game_Field.get(target_field[1]).get(target_field[0]).deliver(
				Game_Field.get(start_field[1]).get(start_field[0]).field_figur, target_field[0], target_field[1]);
		// Figurzähler wird erhöht
		Game_Field.get(target_field[1]).get(target_field[0]).field_figur.counter++;
		// startfeld wird leer
		Game_Field.get(start_field[1]).get(start_field[0]).deliver_empty();
	}

	public boolean inputexitcheck(String input) {
		if (input.contains("exit")) {
			this.checked = true;
			return true;
		} else {
			return false;
		}
	}

	public boolean getColor() {
		return this.color;
	}

	public boolean target_field_check(String input, ArrayList<ArrayList<Field>> Game_Field) {
		switch (input.toLowerCase()) {
		case "a1":
		case "a2":
		case "a3":
		case "a4":
		case "a5":
		case "a6":
		case "a7":
		case "a8":
		case "b1":
		case "b2":
		case "b3":
		case "b4":
		case "b5":
		case "b6":
		case "b7":
		case "b8":
		case "c1":
		case "c2":
		case "c3":
		case "c4":
		case "c5":
		case "c6":
		case "c7":
		case "c8":
		case "d1":
		case "d2":
		case "d3":
		case "d4":
		case "d5":
		case "d6":
		case "d7":
		case "d8":
		case "e1":
		case "e2":
		case "e3":
		case "e4":
		case "e5":
		case "e6":
		case "e7":
		case "e8":
		case "f1":
		case "f2":
		case "f3":
		case "f4":
		case "f5":
		case "f6":
		case "f7":
		case "f8":
		case "g1":
		case "g2":
		case "g3":
		case "g4":
		case "g5":
		case "g6":
		case "g7":
		case "g8":
		case "h1":
		case "h2":
		case "h3":
		case "h4":
		case "h5":
		case "h6":
		case "h7":
		case "h8":
			return true;
		default:
			return false;
		}
	}

	public boolean start_field_check(String input, ArrayList<ArrayList<Field>> Game_Field) {
		switch (input.toLowerCase()) {
		case "a1":
		case "a2":
		case "a3":
		case "a4":
		case "a5":
		case "a6":
		case "a7":
		case "a8":
		case "b1":
		case "b2":
		case "b3":
		case "b4":
		case "b5":
		case "b6":
		case "b7":
		case "b8":
		case "c1":
		case "c2":
		case "c3":
		case "c4":
		case "c5":
		case "c6":
		case "c7":
		case "c8":
		case "d1":
		case "d2":
		case "d3":
		case "d4":
		case "d5":
		case "d6":
		case "d7":
		case "d8":
		case "e1":
		case "e2":
		case "e3":
		case "e4":
		case "e5":
		case "e6":
		case "e7":
		case "e8":
		case "f1":
		case "f2":
		case "f3":
		case "f4":
		case "f5":
		case "f6":
		case "f7":
		case "f8":
		case "g1":
		case "g2":
		case "g3":
		case "g4":
		case "g5":
		case "g6":
		case "g7":
		case "g8":
		case "h1":
		case "h2":
		case "h3":
		case "h4":
		case "h5":
		case "h6":
		case "h7":
		case "h8":
			// figur im feld wird auf spielerfarbe geprüft, sowie ob überhaupt
			// eine figur da ist)
			return (Game_Field.get(Integer.parseInt(input.substring(1)))
					.get(Schach.stringreference(input.substring(0, 1))).getColor(this.color) != this.color) ? false
							: true;
		default:
			return false;

		}
	}
}
