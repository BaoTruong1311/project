

public class SelfMove implements Move {

	@Override
	public void move(Tank t) {
		int x = t.getPoint().getX();
		int y = t.getPoint().getY();
		int direct = t.getDirect();
		
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
	}

}
