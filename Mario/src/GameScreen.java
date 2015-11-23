import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameScreen extends JPanel implements ActionListener, KeyListener {
	boolean moveLeft, moveRight;
	Mario mario = new Mario();
	Timer animateTimer;
	GameWindow gw;
	LevelFloor lf = new LevelFloor();

	GameScreen(GameWindow gw) {
		this.gw = gw;
		this.setLayout(null);
		this.setSize(gw.getContentPane().getWidth(), gw.getContentPane().getHeight());
		this.setVisible(true);
		//this.setBackground(Color.RED);
		this.addKeyListener(this);
		gw.add(this, "game"); // put stage in window
		this.add(mario);
		this.setFocusable(true);
		this.setFocusTraversalKeysEnabled(false);
		//this.requestFocusInWindow();


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

		lf.setBounds(this.getX(), this.getHeight() - lf.getHeight(), lf.getWidth(), lf.getHeight());
		this.add(lf);
		
		animateTimer = new Timer(1000 / 40, this);
		animateTimer.setActionCommand("animate");
		animateTimer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("animate")) {
			// move mario
			if (moveLeft) {
				mario.moveLeft();
			}
			if (moveRight) {
				mario.moveRight();
			}
			if (mario.velocity != 0) {
				mario.setLocation(mario.getX(), mario.getY() + mario.velocity);
			}
			// apply gravity
			if (mario.collidesWith(lf)) {
				mario.isJumping = false;
				mario.setLocation(mario.getX(), lf.getY() - mario.getHeight());
			} else {
				mario.velocity += GameObject.GRAVITY;
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("key pressed");
		if (e.getKeyCode() == KeyEvent.VK_A) {
			moveLeft = true;
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			moveRight = true;
		//	mario.setIcon(new ImageIcon("H:\\Mario\\bigMarioStandActual.png"));
		} else if (e.getKeyCode() == KeyEvent.VK_W) {
			mario.jump();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_A) {
			moveLeft = false;
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			moveRight = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}