

import java.util.ArrayList;

public abstract class Tank implements Draw {
	protected Point point;
	protected int direct;
	protected ArrayList<Bullet> bullets;
	protected Shot wayShot;
	protected Move wayMove;

	public Tank(Point point, int direct) {
		super();
		this.point = point;
		this.direct = direct;
		this.bullets = new ArrayList<Bullet>();
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

	public ArrayList<Bullet> getBullets() {
		return bullets;
	}

	public void setBullets(ArrayList<Bullet> bullets) {
		this.bullets = bullets;
	}
	
	public void move() {
		this.unregister();
		this.wayMove.move(this);
		this.register();
	};

	public void shot() {
		this.wayShot.shot(this);
	}
	
	public void processBullet() {
		for(Bullet b: bullets)
		{
			if(b.overBorder()) {
				bullets.remove(b);
				return ;
			}
			if(VarPublic.MAP.getMap()[b.getPoint().getY()][b.getPoint().getX()] == 7) {
				bullets.remove(b);
				VarPublic.MAP.getMap()[b.getPoint().getY()][b.getPoint().getX()] = 0;
				return ;
			}else if(VarPublic.MAP.getMap()[b.getPoint().getY()][b.getPoint().getX()] > 7) {
				bullets.remove(b);
				return ;
			}		
		}
	}
	
	public void register() {
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++) {
				if(VarPublic.MAP.getMap()[this.getPoint().getY()+j][this.getPoint().getX()+i] == 1)
					VarPublic.MAP.getMap()[this.getPoint().getY()+j][this.getPoint().getX()+i] = 4;
				else
					VarPublic.MAP.getMap()[this.getPoint().getY()+j][this.getPoint().getX()+i] = 3;
			}
	}

	public void unregister() {
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++) {
				if(VarPublic.MAP.getMap()[this.getPoint().getY()+j][this.getPoint().getX()+i] == 4)
					VarPublic.MAP.getMap()[this.getPoint().getY()+j][this.getPoint().getX()+i] = 1;
				else 
					VarPublic.MAP.getMap()[this.getPoint().getY()+j][this.getPoint().getX()+i] = 0;
			}
	}
}
