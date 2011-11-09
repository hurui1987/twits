package asteroid;

import java.awt.Polygon;
import java.awt.Rectangle;

public class Asteroid extends BaseVectorShape {
	private int[] astx = {-20, -13, 0, 20, 22, 20, 12, 2, -10, -22, -16};
	private int[] asty = {20, 23, 17, 20, 16, -20, -22, -14, -17, -20, -5};
	
	protected double rotVel;

	public double getRotVel() {
		return rotVel;
	}

	public void setRotVel(double rotVel) {
		this.rotVel = rotVel;
	}
	
	public Rectangle getBounds(){
		Rectangle r;
		r = new Rectangle((int)getX() - 20, (int)getY() - 20, 40, 40);
		return r;
		
	}
	
	public Asteroid(){
		setShape(new Polygon(astx, asty, astx.length));
		setAlive(true);
		setRotVel(0.0);
	}

}
