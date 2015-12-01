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
	boolean isJumping = true;
	
	public Mario() {
		this.setVisible(true);
		this.setBounds(300, 500, 24, 32); 
		this.setIcon(new ImageIcon("D:\\Mario\\littleMarioStandRight.png"));
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
