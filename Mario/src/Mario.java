import java.awt.Color;

import javax.swing.ImageIcon;

public class Mario extends GameObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static ImageIcon LITTLESTANDRIGHT = new ImageIcon(
			Mario.class.getResource("littleMarioStandRight.png"));
	public static ImageIcon LITTLESTANDLEFT = new ImageIcon(
			Mario.class.getResource("littleMarioStandLeft.png"));
	public static ImageIcon LITTLEJUMPRIGHT = new ImageIcon(
			Mario.class.getResource("littleMarioJumpRight.png"));
	public static ImageIcon LITTLEJUMPLEFT = new ImageIcon(
			Mario.class.getResource("littleMarioJumpLeft.png"));
	public static ImageIcon LITTLEMIDWALKRIGHT1 = new ImageIcon(
			Mario.class.getResource("littleMarioMidWalkRight1.png"));
	public static ImageIcon LITTLEMIDWALKRIGHT2 = new ImageIcon(
			Mario.class.getResource("littleMarioMidWalkRight2.png"));
	public static ImageIcon LITTLEMIDWALKLEFT1 = new ImageIcon(
			Mario.class.getResource("littleMarioMidWalkLeft1.png"));
	public static ImageIcon LITTLEMIDWALKLEFT2 = new ImageIcon(
			Mario.class.getResource("littleMarioMidWalkLeft2.png"));
	public static ImageIcon LITTLESTARTWALKRIGHT = new ImageIcon(
			Mario.class.getResource("littleMarioStartWalkRight.png"));
	public static ImageIcon LITTLESTARTWALKLEFT = new ImageIcon(
			Mario.class.getResource("littleMarioStartWalkLeft.png"));
	public static ImageIcon MARIODEAD = new ImageIcon(
			Mario.class.getResource("marioDead.png"));
	boolean isSuperMario;
	boolean isFireMario;
	boolean isDead;
	public int jumpStrength = -15;
	boolean isJumping = true;
	public int breakBlockSpeed = 20;

	public Mario() {
		isSuperMario = false;
		speed = 6;
		this.setVisible(true);
		this.setBounds(300, 500, 24, 32);
		this.setIcon(LITTLESTANDRIGHT);
		// this.setOpaque(true);
	}

	public void jump() {
		if (isJumping == false) {
			velocity = jumpStrength;
		}
		isJumping = true;
	}
	
	
	
	public void littleJump() {
			velocity = -5;
	}

	public void duck() {

	}

	public boolean jumpsOnEnemy(GameObject g) {
		return ((this.getY() + this.getHeight() >= g.getY() && this.getY() + this.getHeight() < g.getY() + g.getHeight()));
	}
	
	public boolean hitBlock(Block b) {
		return ((b.getY() + b.getHeight() >= this.getY() && b.getY() + b.getHeight() < this.getY() + this.getHeight()));
	}
	
	public boolean collidesFromSide(Block b) {
		return (((this.getX() + this.getWidth() >= b.getX() || this.getX() <= b.getX() + b.getWidth())  && (this.getX() + this.getWidth() < b.getX() + b.getWidth() || this.getX() > b.getX())));
	}
	
	public void bounceDown() {
		velocity = 20;
	}
	
	

}
