import javax.swing.ImageIcon;

public class QuestionBlock extends Block {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean isOn;
	public static ImageIcon QUESTIONBLOCK = new ImageIcon(
			QuestionBlock.class.getResource("questionBlock.png"));
	public static ImageIcon SOLIDBLOCK = new ImageIcon(
			QuestionBlock.class.getResource("solidBlock.png"));


	public QuestionBlock() {
		super();
		this.setIcon(QUESTIONBLOCK);
		breakable = true;
		isOn = true;
	}
	
	public void changeImageToSolidBlock() {
		this.setIcon(SOLIDBLOCK);
		breakable = false;
	}
}
