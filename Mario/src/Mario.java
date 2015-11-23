import java.awt.Color;

import javax.swing.ImageIcon;

public class Mario extends GameObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean isSuperMario;
	boolean isFireMario;
	boolean isDead;
	public int jumpStrength = -15;
	boolean isJumping = false;
	
	public Mario() {
		this.setVisible(true);
		this.setBounds(300, 700, 33, 44); 
		this.setIcon(new ImageIcon("H:\\Mario\\bigMarioStandRight.png"));
		//this.setOpaque(true);
	}

	
	public void jump() {
		if (isJumping == false) {
			velocity = jumpStrength;
		} 
		isJumping = true;
	}

	
	public void duck() {
		
	}
	
	
	
}
