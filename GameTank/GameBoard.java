

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GameBoard extends JPanel implements ActionListener, KeyListener {
	private Timer timer;
	private int delay = 15;
	private ArrayList<EnemyTank> enemys = new ArrayList<EnemyTank>();
	private MyTank myTank;
	private int timeGenerateEnemy = 100;
	private Random rd = new Random();
	
	public GameBoard() {
		myTank = new MyTank(new Point(40, 57), 0);
		timer = new Timer(delay, this);
		timer.start();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 37) {
			if (myTank.getDirect() != 0)
				myTank.setDirect(0);
			else
				myTank.move();
		} else if (e.getKeyCode() == 38) {
			if (myTank.getDirect() != 1)
				myTank.setDirect(1);
			else
				myTank.move();
		} else if (e.getKeyCode() == 39) {
			if (myTank.getDirect() != 2)
				myTank.setDirect(2);
			else
				myTank.move();
		} else if (e.getKeyCode() == 40) {
			if (myTank.getDirect() != 3)
				myTank.setDirect(3);
			else
				myTank.move();
		} else if (e.getKeyCode() == 32) {
			myTank.shot();
		}else if(e.getKeyCode() == 10) {
			VarPublic.IS_START = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent a) {
		if(VarPublic.IS_START) {
			generateEnemy();
			myTank.processBullet();
			for (Bullet b : myTank.getBullets()) {
				b.move();
			}
			for (EnemyTank e : enemys) {
				e.move();
				e.shot();
				e.processBullet();
				for (Bullet b : e.getBullets())
					b.move();
			}

			killTank();
		}
		repaint();
	}

	public void killTank() {
		for (Bullet myBullet : myTank.getBullets()) {
			for (EnemyTank e : enemys) {
				if (onHitTank(e, myBullet)) {
					e.unregister();
					myTank.getBullets().remove(myBullet);
					enemys.remove(e);
					VarPublic.KILL_TANK++;
					if(VarPublic.KILL_TANK == VarPublic.EXISTS) {
						VarPublic.IS_WIN = true;
					}
					return;
				}
			}
		}
		for (EnemyTank e : enemys) {
			for (Bullet enemyBullet : e.getBullets()) {
				if (onHitTank(myTank, enemyBullet)) {
					e.getBullets().remove(enemyBullet);
					VarPublic.LIFE--;
					if(VarPublic.LIFE <= 0) {
						VarPublic.IS_LOSE = true;
					}
					return;
				}
			}
		}
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, VarPublic.WIDTH, VarPublic.HEIGHT);
		g.setColor(Color.RED);
		g.setFont(new Font("Arial", Font.BOLD, 40));
		FontMetrics fm = g.getFontMetrics();
		String message = "";
        
		if(VarPublic.IS_START) {
			if(VarPublic.IS_LOSE){
				message = "GAME OVER";
				int strWidth = fm.stringWidth(message);
				int strAccent = fm.getAscent();
				g.drawString(message, VarPublic.WIDTH/2-strWidth/2, VarPublic.HEIGHT/2-strAccent/2);
				return ;
			}
			
			if(VarPublic.IS_WIN) {
				message = "YOU WIN";
				int strWidth = fm.stringWidth(message);
				int strAccent = fm.getAscent();
				g.drawString(message, VarPublic.WIDTH/2-strWidth/2, VarPublic.HEIGHT/2-strAccent/2);
				return;
			}	
			
			myTank.draw(g);

			for (EnemyTank e : enemys)
				e.draw(g);
			
			VarPublic.MAP.draw(g);
			
			for(Bullet b:myTank.getBullets())
				b.draw(g);
			for(EnemyTank e: enemys)
				for(Bullet b: e.getBullets())
					b.draw(g);
		}else {			
			message = "ENTER TO START";
			int strWidth = fm.stringWidth(message);
			int strAccent = fm.getAscent();
			g.drawString(message, VarPublic.WIDTH/2-strWidth/2, VarPublic.HEIGHT/2-strAccent/2);
		}
		
	}

	public boolean onHitTank(Tank t, Bullet b) {
		Rectangle boundTank = new Rectangle(t.getPoint().getX(), t.getPoint().getY(), 3, 3);
		Rectangle boundBullet = new Rectangle(b.getPoint().getX(), b.getPoint().getY(), 1, 1);
		return boundTank.intersects(boundBullet);
	}

	public void generateEnemy() {
		if(timeGenerateEnemy<0 && VarPublic.COUNT_TANK>0) {
			int pos = rd.nextInt(3);
			if(pos == 0) {
				enemys.add(new EnemyTank(new Point(0, 0), 3));
			}else if(pos == 1) {
				enemys.add(new EnemyTank(new Point(VarPublic.WIDTH/10/2-3, 0), 3));				
			}else {
				enemys.add(new EnemyTank(new Point(VarPublic.WIDTH/10-3, 0), 3));				
			}
			timeGenerateEnemy = 100;
			VarPublic.COUNT_TANK --;
		}
			timeGenerateEnemy--;
	}
}
