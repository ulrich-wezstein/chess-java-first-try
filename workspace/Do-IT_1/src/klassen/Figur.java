package klassen;

import java.util.ArrayList;

abstract public class Figur {
	// allgemeine Eigenschaften für alle Klassen
	public String name = "";
	public int counter = 0;
	public boolean color = true;
	public boolean checked = false;
	public int checkedposition_x = 0;
	public int checkedposition_y = 0;
	public int startposition_x = 0;
	public int startposition_y = 0;

	public Figur(boolean color, int startpos_x, int startpos_y, int checkpos_x, int checkpos_y) {
		this.color = color;
		this.startposition_x = startpos_x;
		this.startposition_y = startpos_y;
		this.checkedposition_x = checkpos_x;
		this.checkedposition_y = checkpos_y;
	}

	public String getName() {
		return ((this.color) ? "w" : "s") + this.name;
	}

	public boolean getColor() {
		return this.color;
	}
	public boolean[][] move(ArrayList<ArrayList<Field>> Game_Field){
		return this.move(Game_Field);
	}
	
	abstract public ArrayList<Field> getPossibleFields(ArrayList<ArrayList<Field>> Game_Field);
	
	abstract public ArrayList<Field> getPossibleHitFields(ArrayList<ArrayList<Field>> Game_Field);

	public void increment_counter(int newcounter) {
		this.counter=newcounter;
	}
	

}