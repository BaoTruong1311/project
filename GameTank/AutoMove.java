

import java.util.Random;

public class AutoMove implements Move {
	private int speedMove = 10; 
	private int amountStep = 7;
	private Random rd = new Random();

	@Override
	public void move(Tank t) {
		int x = t.getPoint().getX();
		int y = t.getPoint().getY();
		int direct = t.getDirect();

		if (speedMove < 0) {
			if (amountStep > 0) {
				if (direct == 0) {
					if (x != 0) {
						if (VarPublic.MAP.getMap()[y][x - 1] < 2 && VarPublic.MAP.getMap()[y + 1][x - 1] < 2
								&& VarPublic.MAP.getMap()[y + 2][x - 1] < 2) {
							t.getPoint().setX(x - 1);
						}
					}
				} else if (direct == 1) {
					if (y != 0) {
						if (VarPublic.MAP.getMap()[y - 1][x] < 2 && VarPublic.MAP.getMap()[y - 1][x + 1] < 2
								&& VarPublic.MAP.getMap()[y - 1][x + 2] < 2) {
							t.getPoint().setY(y - 1);
						}
					}
				} else if (direct == 2) {
					if (x < VarPublic.WIDTH / 10 - 3) {
						if (VarPublic.MAP.getMap()[y][x + 3] < 2 && VarPublic.MAP.getMap()[y + 1][x + 3] < 2
								&& VarPublic.MAP.getMap()[y + 2][x + 3] < 2) {
							t.getPoint().setX(x + 1);
						}
					}
				} else {
					if (y < VarPublic.HEIGHT / 10 - 3)
						if (VarPublic.MAP.getMap()[y + 3][x] < 2 && VarPublic.MAP.getMap()[y + 3][x + 1] < 2
								&& VarPublic.MAP.getMap()[y + 3][x + 2] < 2) {
							t.getPoint().setY(y + 1);
						}
				}
			} else {
				amountStep = 5 + rd.nextInt(4);
				t.setDirect(rd.nextInt(4));
			}
			amountStep--;
			speedMove = 5 + rd.nextInt(5);
		}
		speedMove--;
	}

}
