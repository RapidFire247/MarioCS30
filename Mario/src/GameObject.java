import javax.swing.JLabel;





public class GameObject extends JLabel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int GRAVITY = 1;
	public int velocity = 0;
	
	
	public void moveLeft() {
		this.setLocation(this.getX() - 10, this.getY());
	}
	
	public void moveRight() {
		this.setLocation(this.getX() + 10, this.getY());
	}

	public boolean collidesWith(GameObject g) {
		return  ((this.getX() <= g.getX() + g.getWidth()) && (this.getY() <= g.getY() + g.getHeight()) 
				&& (this.getX() + this.getWidth() >= g.getX()) && (this.getY() + this.getHeight() >= g.getY()));
	}
}
