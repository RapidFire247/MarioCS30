import javax.swing.JLabel;





public class GameObject extends JLabel {
	
	public int speed = 0;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int GRAVITY = 1;
	public int velocity = 0;
	
	
	public void moveLeft() {
		this.setLocation(this.getX() - speed, this.getY());
	}
	
	public void moveRight() {
		this.setLocation(this.getX() + speed, this.getY());
	}

	public boolean collidesWith(GameObject g) {
		// left side right side top bottom
		return ((this.getX() <= g.getX() + g.getWidth())
				&& (this.getY() <= g.getY() + g.getHeight())
				&& (this.getX() + this.getWidth() >= g.getX()) && (this.getY()
				+ this.getHeight() >= g.getY()));
	}
}
