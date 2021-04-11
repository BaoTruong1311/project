

public interface Move {
	public void move(Tank t);
	public default void move(Bullet b) {
		if(b.getDirect() == 0)
			b.getPoint().setX(b.getPoint().getX()-1);
		else if(b.getDirect() == 1)
			b.getPoint().setY(b.getPoint().getY()-1);
		else if(b.getDirect() == 2)
			b.getPoint().setX(b.getPoint().getX()+1);
		else 
			b.getPoint().setY(b.getPoint().getY()+1);
	}
}
