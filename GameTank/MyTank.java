

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

public class MyTank extends Tank{

	public MyTank(Point point, int direct) {
		super(point, direct);
		this.wayMove = new SelfMove();
		this.wayShot = new SelfShot();
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
		if (direct == 0) {
			g.drawImage(new ImageIcon("./resource/graphics.png").getImage(), point.getX() * 10, point.getY() * 10, (point.getX() + 3) * 10, (point.getY() + 3) * 10,
					865, 0, 895, 30, null);
		} else if (direct == 1) {
			g.drawImage(new ImageIcon("./resource/graphics.png").getImage(), point.getX() * 10, point.getY() * 10, (point.getX() + 3) * 10, (point.getY() + 3) * 10,
					770, 0, 800, 30, null);
		} else if (direct == 2) {
			g.drawImage(new ImageIcon("./resource/graphics.png").getImage(), point.getX()  * 10, point.getY() * 10, (point.getX() + 3) * 10, (point.getY() + 3) * 10,
					800, 0, 830, 30, null);
		} else if (direct == 3) {
			g.drawImage(new ImageIcon("./resource/graphics.png").getImage(), point.getX() * 10, point.getY() * 10, (point.getX() + 3) * 10, (point.getY() + 3) * 10,
					830, 0, 860, 30, null);
		}
		
	}

}
