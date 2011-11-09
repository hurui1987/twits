package asteroid;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Asteroids extends Applet implements Runnable, KeyListener {

	Thread gameloop;
	
	BufferedImage backbuffer;
	
	Graphics2D g2d;
	
	boolean showBounds = false;
	
	int ASTEROIDS = 20;
	
	Asteroid[] ast = new Asteroid[ASTEROIDS];
	
	int BULLETS = 10;
	Bullet[] bullet = new Bullet[BULLETS];
	int currentBullet = 0;
	
	Ship ship = new Ship();
	
	AffineTransform identity = new AffineTransform();
	
	Random rand = new Random();
	
	public void init(){
		backbuffer = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
		g2d = backbuffer.createGraphics();
		
		ship.setX(320);
		ship.setY(240);
		
		for(int n = 0; n < BULLETS; n++){
			bullet[n] = new Bullet();
		}
		
		for(int n = 0; n < ASTEROIDS; n++){
			ast[n] = new Asteroid();
			ast[n].setRotVel(rand.nextInt(3) + 1);
			ast[n].setX((double)rand.nextInt(600) + 20);
			ast[n].setY((double)rand.nextInt(440) + 20);
			ast[n].setMoveAngle(rand.nextInt(360));
			double ang = ast[n].getMoveAngle() - 90;
			ast[n].setVelX(calcAngleMoveX(ang));
			ast[n].setVelY(calcAngleMoveY(ang));
		}
		
		addKeyListener(this);
	}
	
	public void update(Graphics g){
		g2d.setTransform(identity);
		
		g2d.setPaint(Color.BLACK);
		g2d.fillRect(0, 0, getSize().width, getSize().height);
		
		g2d.setColor(Color.WHITE);
		g2d.drawString("Ship: " + Math.round(ship.getX()) + "," +
				Math.round(ship.getY()), 5, 10);
		g2d.drawString("Move angle: " + Math.round(
				ship.getMoveAngle()) + 90, 5, 25);
		g2d.drawString("Face angle: " + Math.round(
				ship.getFaceAngle()), 5, 40);
		
		drawShip();
		drawBullets();
		drawAsteroids();
		
		paint(g);
	}
	
	public void drawShip(){
		g2d.setTransform(identity);
		g2d.translate(ship.getX(), ship.getY());
		g2d.rotate(Math.toRadians(ship.getFaceAngle()));
		g2d.setColor(Color.ORANGE);
		g2d.fill(ship.getShape());
	}
	
	public void drawBullets(){
		for(int n = 0; n < BULLETS; n++){
			if (bullet[n].isAlive()){
				g2d.setTransform(identity);
				g2d.translate(bullet[n].getX(), bullet[n].getY());
				g2d.setColor(Color.MAGENTA);
				g2d.draw(bullet[n].getShape());
			}
		}
	}
	
	public void drawAsteroids(){
		for(int n = 0; n < ASTEROIDS; n++){
			if (ast[n].isAlive()){
				g2d.setTransform(identity);
				g2d.translate(ast[n].getX(), ast[n].getY());
				g2d.rotate(Math.toRadians(ast[n].getMoveAngle()));
				g2d.setColor(Color.DARK_GRAY);
				g2d.fill(ast[n].getShape());
			}
		}
	}
	
	public void paint(Graphics g){
		g.drawImage(backbuffer, 0, 0, this);
	}
	
	public void start(){
	    gameloop = new Thread(this);
	    gameloop.start();
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Thread t = Thread.currentThread();
		while(t == gameloop){
			try{
				gameUpdate();
				Thread.sleep(20);
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
			repaint();
		}

	}
	
	public void stop(){
		gameloop = null;
	}
	
	public void updateShip(){
		ship.incX(ship.getVelX());
		
		if(ship.getX() < -10)
			ship.setX(getSize().width + 10);
		else if (ship.getX() > getSize().width + 10)
			ship.setX(-10);
		
		ship.incY(ship.getVelY());
		
		if(ship.getY() < -10)
			ship.setY(getSize().height + 10);
		else if (ship.getY() > getSize().height + 10)
			ship.setX(-10);
	}
	
	public void updateBullets(){
		
	}
	
	private void gameUpdate(){
		updateShip();
		updateBullets();
		updateAsteroids();
		checkCollisions();
	}

}
