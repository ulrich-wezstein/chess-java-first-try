package klassen;

import java.util.ArrayList;

public class Field {

	Figur field_figur;
	public String name = "  ";
	int pos_x;
	int pos_y;

	public Field(int x, int y) {
		this.pos_x = x;
		this.pos_y = y;
	}

	// Methode um Spielfigur dem Feld zu übergeben
	public Figur deliver(Figur new_figur, int x, int y) {
		field_figur = new_figur;
		field_figur.startposition_x = x;
		field_figur.startposition_y = y;
		return field_figur;
	}

	// delivered mit leerer figur
	public Figur deliver_empty() {
		field_figur = null;
		return field_figur;
	}

	// Methode zur Namensgebung im Spielfeld
	// Falls field ein Objekt hat, dann Objektnamen ermitteln und übergeben,
	// ansonsten " "

	public String getName() {
		return ((field_figur != null) ? field_figur.getName() : this.name);
	}

	public boolean getColor(boolean playercolor) {
		boolean newcolor;
		if (playercolor == true) {
			newcolor = false;
		} else {
			newcolor = true;
		}

		return ((field_figur != null) ? field_figur.getColor() : newcolor);
	}

	public ArrayList<Field> getPossibleMoves(ArrayList<ArrayList<Field>> Game_Field) {
		return field_figur.getPossibleFields(Game_Field);
	}

	@Override
	public String toString() {
		return "Field [pos_x=" + pos_x + ", pos_y=" + pos_y + "]";
	}

	public int getCounter() {
		if (field_figur != null)
			return field_figur.counter;
		else return 0;
	}

	public void increment_counter(int newcounter) {
		if (field_figur != null)
			field_figur.increment_counter(newcounter);
	}

}