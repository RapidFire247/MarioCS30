import javax.swing.ImageIcon;

public class StairBlock extends Block {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static ImageIcon STAIRBLOCK = new ImageIcon(
			StairBlock.class.getResource("stairBlock.png"));

	
	StairBlock() {
		super();
		this.setIcon(STAIRBLOCK);
		breakable = false;
	}

}
