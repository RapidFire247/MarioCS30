import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Creates and sets up the main game window
					GameWindow window = new GameWindow();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	// constructor
	public GameWindow() {
		setBounds(100, 100, 1500, 800);
		setTitle("Super Mario Bros.");
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new CardLayout()); // <- JPanel
		// Create a new JPanel
		JPanel startStage = new JPanel();
		startStage.setSize(getWidth(), getHeight());
		startStage.setVisible(true);
		startStage.setBackground(Color.BLUE);
		// Add a button to panel
		JButton playButton = new JButton("Play");
		playButton.setBackground(Color.CYAN);
		playButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				((CardLayout) getContentPane().getLayout()).show(
						getContentPane(), "game");
			}
		});
		startStage.add(playButton);
		add(startStage, "start"); // put stage in window
		// Create a new JPanel
		JPanel gameStage = new JPanel();
		gameStage.setLayout(null);
		gameStage.setSize(getWidth(), getHeight());
		gameStage.setVisible(true);
		gameStage.setBackground(Color.RED);
		// Add a button to panel
		JButton menuButton = new JButton("Menu");
//		menuButton.setBounds(x, y, width, height);
		menuButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				((CardLayout) getContentPane().getLayout()).show(
						getContentPane(), "start");
			}
		});
		gameStage.add(menuButton);
		add(gameStage, "game"); // put stage in window
		Mario mario = new Mario();
		gameStage.add(mario);
	}

}
