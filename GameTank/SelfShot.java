

public class SelfShot implements Shot{

	@Override
	public void shot(Tank t) {
		if(t.getDirect() == 0)
			t.getBullets().add(new Bullet(new Point(t.getPoint().getX(), t.getPoint().getY()+1), t.getDirect()));
		else if(t.getDirect() == 1)
			t.getBullets().add(new Bullet(new Point(t.getPoint().getX()+1, t.getPoint().getY()), t.getDirect()));
		else if(t.getDirect() == 2)
			t.getBullets().add(new Bullet(new Point(t.getPoint().getX()+2, t.getPoint().getY()+1), t.getDirect()));
		else 
			t.getBullets().add(new Bullet(new Point(t.getPoint().getX()+1, t.getPoint().getY()+2), t.getDirect()));
	}

}
