import java.awt.Color;

public class Mario extends GameObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean isSuperMario;
	boolean isFireMario;
	boolean isDead;
	
	public Mario() {
		this.setVisible(true);
		this.setBounds(300, 200, 10, 15);
		this.setBackground(Color.YELLOW);
	}

	public void jump() {

	}

	public void move(int direction) {
		// 0 for left 1 for right
		if (direction == 1) {
			this.setLocation(this.getX() + 10, this.getY());
		} else {
			this.setLocation(this.getX() - 10, this.getY());
		}
	}
	
	public void duck() {
		
	}
	
	
	
}
