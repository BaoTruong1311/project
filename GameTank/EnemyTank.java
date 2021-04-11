

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

public class EnemyTank extends Tank {

	public EnemyTank(Point point, int direct) {
		super(point, direct);
		this.wayMove = new AutoMove();
		this.wayShot = new AutoShot();
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		if (direct == 0) {
			g.drawImage(new ImageIcon("./resource/graphics.png").getImage(), point.getX() * 10, point.getY() * 10,
					(point.getX() + 3) * 10, (point.getY() + 3) * 10, 95, 0, 125, 30, null);
		} else if (direct == 1) {
			g.drawImage(new ImageIcon("./resource/graphics.png").getImage(), point.getX() * 10, point.getY() * 10,
					(point.getX() + 3) * 10, (point.getY() + 3) * 10, 0, 0, 30, 30, null);
		} else if (direct == 2) {
			g.drawImage(new ImageIcon("./resource/graphics.png").getImage(), point.getX() * 10, point.getY() * 10,
					(point.getX() + 3) * 10, (point.getY() + 3) * 10, 30, 0, 65, 30, null);
		} else if (direct == 3) {
			g.drawImage(new ImageIcon("./resource/graphics.png").getImage(), point.getX() * 10, point.getY() * 10,
					(point.getX() + 3) * 10, (point.getY() + 3) * 10, 67, 0, 93, 30, null);
		}
	}

}
