package methoden;

import java.util.ArrayList;

import klassen.*;

public class Create_Game_Field {

	public static ArrayList<ArrayList<Field>> erstellen() {
		// 2d arraylist erstellen und füllen
		ArrayList<ArrayList<Field>> game_field = Chessboard.create();
		// Liste für die Figuren erstellen
		Figur[] chess_figures = new Figur[32];
		// Liste mit Figuren füllen
		chess_figures = create_figures();
		for (int i = 0; i < chess_figures.length; i++) {
			game_field.get(chess_figures[i].startposition_y).get(chess_figures[i].startposition_x)
					.deliver(chess_figures[i], chess_figures[i].startposition_x, chess_figures[i].startposition_y);
		}
		return game_field;
	}

	// Manuell Schachfiguren als Objekte erstellen (da einmaligkeit in
	// Attribute) und in Array geschrieben)
	public static Figur[] create_figures() {
		Figur[] Schachfiguren = new Figur[32];
		for (int i = 0; i <= 7; i++) {
			Schachfiguren[i] = new Pawn(true, i + 1, 2, 0, i + 1);
			Schachfiguren[i + 8] = new Pawn(false, i + 1, 7, 9, i + 1);
		}
		Schachfiguren[16] = new Tower(true, 1, 1, 1, 9);
		Schachfiguren[17] = new Tower(true, 8, 1, 8, 9);
		Schachfiguren[18] = new Tower(false, 1, 8, 1, 0);
		Schachfiguren[19] = new Tower(false, 8, 8, 8, 0);

		Schachfiguren[20] = new Knight(true, 2, 1, 2, 9);
		Schachfiguren[21] = new Knight(true, 7, 1, 7, 9);
		Schachfiguren[22] = new Knight(false, 2, 8, 2, 0);
		Schachfiguren[23] = new Knight(false, 7, 8, 7, 0);

		Schachfiguren[24] = new Bishop(true, 3, 1, 3, 9);
		Schachfiguren[25] = new Bishop(true, 6, 1, 6, 9);
		Schachfiguren[26] = new Bishop(false, 3, 8, 3, 0);
		Schachfiguren[27] = new Bishop(false, 6, 8, 6, 0);

		Schachfiguren[28] = new King(true, 5, 1, 5, 9);
		Schachfiguren[29] = new King(false, 5, 8, 5, 0);

		Schachfiguren[30] = new Queen(true, 4, 1, 4, 9);
		Schachfiguren[31] = new Queen(false, 4, 8, 4, 0);

		return Schachfiguren;
	}
}
