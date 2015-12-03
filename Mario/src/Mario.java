import java.awt.Color;

import javax.swing.ImageIcon;

public class Mario extends GameObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static ImageIcon LITTLESTANDRIGHT = new ImageIcon(Mario.class.getResource("littleMarioStandRight.png"));
	public static ImageIcon LITTLESTANDLEFT = new ImageIcon(Mario.class.getResource("littleMarioStandLeft.png"));
	public static ImageIcon LITTLEJUMPRIGHT = new ImageIcon(Mario.class.getResource("littleMarioJumpRight.png"));
	public static ImageIcon LITTLEJUMPLEFT = new ImageIcon(Mario.class.getResource("littleMarioJumpLeft.png"));
	public static ImageIcon LITTLEMIDWALKRIGHT1 = new ImageIcon(Mario.class.getResource("littleMarioMidWalkRight1.png"));
	public static ImageIcon LITTLEMIDWALKRIGHT2 = new ImageIcon(Mario.class.getResource("littleMarioMidWalkRight2.png"));
	public static ImageIcon LITTLEMIDWALKLEFT1 = new ImageIcon(Mario.class.getResource("littleMarioMidWalkLeft1.png"));
	public static ImageIcon LITTLEMIDWALKLEFT2 = new ImageIcon(Mario.class.getResource("littleMarioMidWalkLeft2.png"));
	public static ImageIcon LITTLESTARTWALKRIGHT = new ImageIcon(Mario.class.getResource("littleMarioStartWalkRight.png"));
	public static ImageIcon LITTLESTARTWALKLEFT = new ImageIcon(Mario.class.getResource("littleMarioStartWalkLeft.png"));
	boolean isSuperMario;
	boolean isFireMario;
	boolean isDead;
	public int jumpStrength = -15;
	boolean isJumping = true;
	
	public Mario() {
		this.setVisible(true);
		this.setBounds(300, 500, 24, 32); 
		this.setIcon(LITTLESTANDRIGHT);
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
