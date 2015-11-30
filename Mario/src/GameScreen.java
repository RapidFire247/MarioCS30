import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameScreen extends JPanel implements ActionListener, KeyListener {
	boolean moveLeft, moveRight;
	Mario mario = new Mario();
	Timer animateTimer, marioIconChangeTimer;
	GameWindow gw;
	private ArrayList<LevelFloorBlock> blocks = new ArrayList<LevelFloorBlock>();

	GameScreen(GameWindow gw) {
		this.gw = gw;
		this.setLayout(null);
		this.setSize(10000, gw.getContentPane().getHeight());
		this.setVisible(true);
		this.setBackground(Color.CYAN);
		this.addKeyListener(this);
		gw.add(this, "game"); // put stage in window
		this.add(mario);
		this.setFocusable(true);
		this.setFocusTraversalKeysEnabled(false);
		// this.requestFocusInWindow();

		// Add a button to panel
		JButton menuButton = new JButton("Menu");
		menuButton.setBounds(10, 10, 100, 50);
		menuButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				((CardLayout) gw.getContentPane().getLayout()).show(
						gw.getContentPane(), "start");
			}
		});
		this.add(menuButton);
		// lf.setBounds(this.getX(), this.getHeight() - lf.getHeight(),
		// lf.getWidth(), lf.getHeight());
		// this.add(lf);
		int lfTemp = 0;
		for (int i = 0; i < 100; i++) {
			LevelFloorBlock lf = new LevelFloorBlock();
			lf.setBounds(this.getX() + lfTemp,
					this.getHeight() - lf.getHeight(), lf.getWidth(),
					lf.getHeight());
			this.add(lf);
			blocks.add(lf);
			lfTemp += lf.getWidth();
		}

		animateTimer = new Timer(1000 / 40, this);
		animateTimer.setActionCommand("animate");
		animateTimer.start();
		marioIconChangeTimer = new Timer(1000 / 10, this);
		marioIconChangeTimer.setActionCommand("marioIconChange");
		marioIconChangeTimer.start();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// System.out.println(mario.getLocationOnScreen().x);
		// System.out.println(mario.getX());
		if (e.getActionCommand().equals("animate")) {
			// move mario
			if (moveLeft) {
				mario.moveLeft();
				if (mario.getX() <= gw.getWidth() / 4) {
					this.setLocation(this.getX() + 10, this.getY());
				}
				if (mario.getIcon().toString()
						.equals("H:\\Mario\\littleMarioStandRight.png")) {
					mario.setIcon(new ImageIcon(
							"H:\\Mario\\littleMarioStandLeft.png"));
					mario.setSize(24, 32);
				}
				// mario.setIcon(new
				// ImageIcon("H:\\Mario\\littleMarioStartWalkLeft.png"));
				// mario.setIcon(new
				// ImageIcon("H:\\Mario\\littleMarioMidWalkLeft1.png"));
				// mario.setIcon(new
				// ImageIcon("H:\\Mario\\littleMariMidWalkLeft2.png"));

			}
			if (moveRight) {
				mario.moveRight();
				if (mario.getX() >= gw.getWidth() / 2) {
					this.setLocation(this.getX() - 10, this.getY());
				}
				if (mario.getIcon().toString()
						.equals("H:\\Mario\\littleMarioStandLeft.png")) {
					mario.setIcon(new ImageIcon(
							"H:\\Mario\\littleMarioStandRight.png"));
					mario.setSize(24, 32);
				}
			}
			if (mario.velocity != 0) {
				mario.setLocation(mario.getX(), mario.getY() + mario.velocity);
			}
			for (int j = 0; j < blocks.size(); j++) {

				if (mario.collidesWith(blocks.get(j))) {
					mario.isJumping = false;
					mario.setLocation(mario.getX(), blocks.get(j).getY()
							- mario.getHeight());
				}
			}
			// apply gravity
			if (mario.isJumping) {
				mario.velocity += GameObject.GRAVITY;
			}
		}
		if (e.getActionCommand().equals("marioIconChange")) {
			if (moveLeft) {
				if (mario.getIcon().toString()
						.equals("H:\\Mario\\littleMarioStandLeft.png")) {
					mario.setIcon(new ImageIcon(
							"H:\\Mario\\littleMarioStartWalkLeft.png"));
					mario.setSize(24, 32);
				} else if (mario.getIcon().toString()
						.equals("H:\\Mario\\littleMarioStartWalkLeft.png")) {
					mario.setIcon(new ImageIcon(
							"H:\\Mario\\littleMarioMidWalkLeft1.png"));
					mario.setSize(22, 30);
				} else if (mario.getIcon().toString()
						.equals("H:\\Mario\\littleMarioMidWalkLeft1.png")) {
					mario.setIcon(new ImageIcon(
							"H:\\Mario\\littleMarioMidWalkLeft2.png"));
					mario.setSize(32, 32);
				} else if (mario.getIcon().toString()
						.equals("H:\\Mario\\littleMarioMidWalkLeft2.png")) {
					mario.setIcon(new ImageIcon(
							"H:\\Mario\\littleMarioStartWalkLeft.png"));
					mario.setSize(24, 32);
				}
			}
			if (moveRight) {
				if (mario.getIcon().toString()
						.equals("H:\\Mario\\littleMarioStandRight.png")) {
					mario.setIcon(new ImageIcon(
							"H:\\Mario\\littleMarioStartWalkRight.png"));
					mario.setSize(24, 32);
				} else if (mario.getIcon().toString()
						.equals("H:\\Mario\\littleMarioStartWalkRight.png")) {
					mario.setIcon(new ImageIcon(
							"H:\\Mario\\littleMarioMidWalkRight1.png"));
					mario.setSize(22, 30);
				} else if (mario.getIcon().toString()
						.equals("H:\\Mario\\littleMarioMidWalkRight1.png")) {
					mario.setIcon(new ImageIcon(
							"H:\\Mario\\littleMarioMidWalkRight2.png"));
					mario.setSize(32, 32);
				} else if (mario.getIcon().toString()
						.equals("H:\\Mario\\littleMarioMidWalkRight2.png")) {
					mario.setIcon(new ImageIcon(
							"H:\\Mario\\littleMarioStartWalkRight.png"));
					mario.setSize(24, 32);
				}
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// System.out.println("key pressed");
		if (e.getKeyCode() == KeyEvent.VK_A) {
			moveLeft = true;
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			moveRight = true;
			// mario.setIcon(new
			// ImageIcon("H:\\Mario\\bigMarioStandActual.png"));
		} else if (e.getKeyCode() == KeyEvent.VK_W) {
			mario.jump();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_A) {
			moveLeft = false;
			mario.setIcon(new ImageIcon("H:\\Mario\\littleMarioStandLeft.png"));
			mario.setSize(24, 32);
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			moveRight = false;
			mario.setIcon(new ImageIcon("H:\\Mario\\littleMarioStandRight.png"));
			mario.setSize(24, 32);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}
