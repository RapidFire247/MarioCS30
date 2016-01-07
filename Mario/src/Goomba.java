import javax.swing.ImageIcon;

public class Goomba extends Enemies {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static ImageIcon GOOMBARIGHT = new ImageIcon(
			Mario.class.getResource("goombaRight.png"));
	public static ImageIcon GOOMBALEFT = new ImageIcon(
			Mario.class.getResource("goombaLeft.png"));
	public static ImageIcon GOOMBADEAD = new ImageIcon(
			Mario.class.getResource("goombaDead.png"));
	boolean direction = true;

	public Goomba() {
		speed = 3;
		this.setVisible(true);
		this.setIcon(GOOMBALEFT);
		this.setSize(32, 32);
	}
	


}
