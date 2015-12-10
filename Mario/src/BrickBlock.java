import javax.swing.ImageIcon;

public class BrickBlock extends Block {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BrickBlock() {
		super();
		breakable = true;
		this.setIcon(new ImageIcon("H:\\Mario\\brickBlock.png"));
	}
}
