import java.awt.Color;

import javax.swing.ImageIcon;


public class LevelFloor extends Structures {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LevelFloor() {
		this.setSize(10000, 50);
		this.setVisible(true);
		this.setBackground(Color.BLUE);
		this.setOpaque(true);
		this.setIcon(new ImageIcon("H:\\Mario\\levelFloorBlock.png"));

	}
}
