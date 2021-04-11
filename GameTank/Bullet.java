

import java.awt.Color;
import java.awt.Graphics;

public class Bullet implements Draw {
	private Point point;
	private int direct;
	private Move wayMove;

	public Bullet(Point point, int direct) {
		super();
		this.point = point;
		this.direct = direct;
		this.wayMove = new SelfMove();
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public int getDirect() {
		return direct;
	}

	public void setDirect(int direct) {
		this.direct = direct;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(point.getX() * 10, point.getY() * 10, 10, 10);
	}

	public void move() {
		this.wayMove.move(this);
	}

	public boolean overBorder() {
		if (this.point.getX() < 0 || this.point.getY()<0 || this.point.getX() > VarPublic.WIDTH/10-1 || this.point.getY()>VarPublic.HEIGHT/10-1)
			return true;
		else
			return false;
	}
}
