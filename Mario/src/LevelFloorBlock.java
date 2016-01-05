import java.awt.Color;

import javax.swing.ImageIcon;

public class LevelFloorBlock extends Block {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static ImageIcon LEVELFLOORBLOCK = new ImageIcon(
			LevelFloorBlock.class.getResource("levelFloorBlock.png"));

	public LevelFloorBlock() {
		super();
		this.setBackground(Color.BLUE);
		this.setOpaque(true);
		this.setIcon(LEVELFLOORBLOCK);
		breakable = false;

	}
	
	
}
