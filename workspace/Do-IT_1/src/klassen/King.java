package klassen;

import java.util.ArrayList;

public class King extends Figur {
	public King(boolean color, int startpos_x, int startpos_y, int checkpos_x, int checkpos_y) {
		super(color, startpos_x, startpos_y, checkpos_x, checkpos_y);
	}

	{
		this.name = "K";
		this.counter = 0;
	}

	public ArrayList<Field> getPossibleHitFields(ArrayList<ArrayList<Field>> Game_Field) {
		ArrayList<Field> possible_moves = getPossibleFields(Game_Field);
		return possible_moves;
	}

	public ArrayList<Field> getPossibleFields(ArrayList<ArrayList<Field>> Game_Field) {
		ArrayList<Field> possible_moves = new ArrayList<>();

		int x = this.startposition_x;
		int y = this.startposition_y;
		int min_x = (x == 1) ? x : (x - 1);
		int max_x = (x == 8) ? x : (x + 1);
		int min_y = (y == 1) ? y : (y - 1);
		int max_y = (y == 8) ? y : (y + 1);
		for (int x_axis = min_x; x_axis <= max_x; x_axis++) {
			for (int y_axis = min_y; y_axis <= max_y; y_axis++) {
				if (x_axis == x & y_axis == y) {

				} else {
					if (Game_Field.get(y_axis).get(x_axis).field_figur == null) {
						possible_moves.add(new Field(x_axis, y_axis));
					} else if (Game_Field.get(y_axis).get(x_axis).field_figur != null) {
						if (Game_Field.get(y_axis).get(x_axis).field_figur.getColor() != this.color) {
							possible_moves.add(new Field(x_axis, y_axis));
						}
					}

				}
			}
		}
		return possible_moves;
	}
}
