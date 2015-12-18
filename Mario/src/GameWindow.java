import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

public class GameWindow extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean moveLeft, moveRight;
	public static Mario mario = new Mario();
	Timer animateTimer;
	JScrollPane scrollPane;

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
		setBounds(0, 0, 1500, 800);
		setTitle("Super Mario Bros.");
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setSize(this.getWidth(), this.getHeight());
		getContentPane().setPreferredSize(getSize());
		this.pack();
		getContentPane().setLayout(new CardLayout()); // <- JPanel
		// Create a new JPanel
		// JPanel startStage = new JPanel();
		// startStage.setSize(getWidth(), getHeight());
		// startStage.setVisible(true);
		// startStage.setBackground(Color.BLUE);
		// // Add a button to panel
		// JButton playButton = new JButton("Play");
		// playButton.setBackground(Color.CYAN);
		// playButton.addMouseListener(new MouseAdapter() {
		// @Override
		// public void mouseClicked(MouseEvent arg0) {
		// ((CardLayout) getContentPane().getLayout()).show(
		// getContentPane(), "game");
		// }
		// });
		// startStage.add(playButton);
		// add(startStage, "start"); // put stage in window
		// Create a new JPanel
		GameScreen gameStage = new GameScreen(this);
		scrollPane = new JScrollPane(gameStage);
		scrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		this.getContentPane().add(scrollPane, BorderLayout.CENTER);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("animate")) {
			if (moveLeft) {
				mario.moveLeft();
			}
			if (moveRight) {
				mario.moveRight();
			}
		}

	}

}
