import javax.swing.ImageIcon;

public class BrickBlock extends Block {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static ImageIcon BRICKBLOCK = new ImageIcon(
			BrickBlock.class.getResource("brickBlock.png"));

	public BrickBlock() {
		super();
		breakable = true;
		this.setIcon(BRICKBLOCK);
	}
	
}
