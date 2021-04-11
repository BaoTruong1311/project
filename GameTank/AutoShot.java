

import java.util.Random;

public class AutoShot implements Shot{
	private int speedShot = 50;
	private Random rd = new Random();

	@Override
	public void shot(Tank t) {
		if(speedShot<0) {
			if(t.getDirect() == 0)
				t.getBullets().add(new Bullet(new Point(t.getPoint().getX(), t.getPoint().getY()+1), t.getDirect()));
			else if(t.getDirect() == 1)
				t.getBullets().add(new Bullet(new Point(t.getPoint().getX()+1, t.getPoint().getY()), t.getDirect()));
			else if(t.getDirect() == 2)
				t.getBullets().add(new Bullet(new Point(t.getPoint().getX()+2, t.getPoint().getY()+1), t.getDirect()));
			else 
				t.getBullets().add(new Bullet(new Point(t.getPoint().getX()+1, t.getPoint().getY()+2), t.getDirect()));
			speedShot = rd.nextInt(50);
		}
		speedShot--;
	}
}