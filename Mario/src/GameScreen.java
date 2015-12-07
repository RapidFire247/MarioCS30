import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
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
		this.setPreferredSize(new Dimension(10000, gw.getContentPane().getHeight()));
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
		Goomba goomba1 = new Goomba();
		goomba1.setLocation(500, 600);
		this.add(goomba1);

		animateTimer = new Timer(1000 / 40, this);
		animateTimer.setActionCommand("animate");
		animateTimer.start();
		marioIconChangeTimer = new Timer(1000 / 10, this);
		marioIconChangeTimer.setActionCommand("marioIconChange");
		marioIconChangeTimer.start();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("animate")) {
			// move mario
			if (mario.isJumping) {
				
				if (moveLeft
						|| mario.getIcon().toString()
								.equals(mario.LITTLESTANDLEFT.toString())) {
					if (mario.getX() <= this.gw.scrollPane.getViewport().getViewPosition().getX() + gw.scrollPane.getWidth() / 4 && this.gw.scrollPane.getViewport().getViewPosition().getX() > 0) {
						this.gw.scrollPane.getViewport().setViewPosition(new Point((int) this.gw.scrollPane.getViewport().getViewPosition().getX() - 10, 0));
					}
					mario.setIcon(mario.LITTLEJUMPLEFT);
					mario.setSize(32, 30);

				}
				if (moveRight
						|| mario.getIcon().toString()
								.equals(mario.LITTLESTANDRIGHT.toString())) {
					if (mario.getX() >= this.gw.scrollPane.getViewport().getViewPosition().getX() + gw.scrollPane.getWidth() / 2) {
						this.gw.scrollPane.getViewport().setViewPosition(new Point((int) this.gw.scrollPane.getViewport().getViewPosition().getX() + 10, 0));
					}
					mario.setIcon(mario.LITTLEJUMPRIGHT);
					mario.setSize(32, 30);
				}
			}
			if (moveLeft) {
				if (mario.getX() > 0) {
				mario.moveLeft();
				}
				if (mario.getX() <= this.gw.scrollPane.getViewport().getViewPosition().getX() + gw.scrollPane.getWidth() / 4 && this.gw.scrollPane.getViewport().getViewPosition().getX() > 0) {
					this.gw.scrollPane.getViewport().setViewPosition(new Point((int) this.gw.scrollPane.getViewport().getViewPosition().getX() - 10, 0));
				}
				if (mario.getIcon().toString()
						.equals(mario.LITTLESTANDRIGHT.toString())) {
					mario.setIcon(mario.LITTLESTANDLEFT);
					mario.setSize(24, 32);
				}

			}
			if (moveRight) {
				mario.moveRight();
				if (mario.getX() >= this.gw.scrollPane.getViewport().getViewPosition().getX() + gw.scrollPane.getWidth() / 2) {
					this.gw.scrollPane.getViewport().setViewPosition(new Point((int) this.gw.scrollPane.getViewport().getViewPosition().getX() + 10, 0));
				}
				if (mario.getIcon().toString()
						.equals(mario.LITTLESTANDLEFT.toString())) {
					mario.setIcon(mario.LITTLESTANDRIGHT);
					mario.setSize(24, 32);
				}
			}
			if (!mario.isJumping && !moveRight && !moveLeft) {
				if (mario.getIcon().toString()
						.equals(mario.LITTLEJUMPLEFT.toString())) {
					mario.setIcon(mario.LITTLESTANDLEFT);
				} else if (mario.getIcon().toString()
						.equals(mario.LITTLEJUMPRIGHT.toString())) {
					mario.setIcon(mario.LITTLESTANDRIGHT);
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
			if (!mario.isJumping) {
				if (moveLeft) {
//					System.out.println("moving left");
//					System.out.println(mario.getIcon().toString());
//					System.out.println(mario.LITTLESTANDLEFT);
					if (mario.getIcon().toString()
							.equals(mario.LITTLESTANDLEFT.toString())
							|| mario.getIcon()
									.toString()
									.equals(mario.LITTLEJUMPLEFT.toString())) {
						//System.out.println("changes to littlestartwalkleft");
						mario.setIcon(mario.LITTLESTARTWALKLEFT);
						mario.setSize(24, 32);
					} else if (mario.getIcon().toString()
							.equals(mario.LITTLESTARTWALKLEFT.toString())) {
					//	System.out.println("changes to littlemidwalkleft1");
						mario.setIcon(mario.LITTLEMIDWALKLEFT1);
						mario.setSize(22, 30);
					} else if (mario.getIcon().toString()
							.equals(mario.LITTLEMIDWALKLEFT1.toString())) {
					//	System.out.println("changes to littlemidwalkleft2");
						mario.setIcon(mario.LITTLEMIDWALKLEFT2);
						mario.setSize(32, 32);
					} else if (mario.getIcon().toString()
							.equals(mario.LITTLEMIDWALKLEFT2.toString())) {
					//	System.out.println("changes back to littlestartwalkleft");
						mario.setIcon(mario.LITTLESTARTWALKLEFT);
						mario.setSize(24, 32);
					}
				}
				if (moveRight) {
					if (mario.getIcon().toString()
							.equals(mario.LITTLESTANDRIGHT.toString())
							|| mario.getIcon()
									.toString()
									.equals(mario.LITTLEJUMPRIGHT.toString())) {
						mario.setIcon(mario.LITTLESTARTWALKRIGHT);
						mario.setSize(24, 32);
					} else if (mario.getIcon().toString()
							.equals(mario.LITTLESTARTWALKRIGHT.toString())) {
						mario.setIcon(mario.LITTLEMIDWALKRIGHT1);
						mario.setSize(22, 30);
					} else if (mario.getIcon().toString()
							.equals(mario.LITTLEMIDWALKRIGHT1.toString())) {
						mario.setIcon(mario.LITTLEMIDWALKRIGHT2);
						mario.setSize(32, 32);
					} else if (mario.getIcon().toString()
							.equals(mario.LITTLEMIDWALKRIGHT2.toString())) {
						mario.setIcon(mario.LITTLESTARTWALKRIGHT);
						mario.setSize(24, 32);
					}
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
		} else if (e.getKeyCode() == KeyEvent.VK_W) {
			mario.jump();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_A) {
			moveLeft = false;
			System.out.println("key released");
			mario.setIcon(mario.LITTLESTANDLEFT);
			mario.setSize(24, 32);
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			moveRight = false;
			mario.setIcon(mario.LITTLESTANDRIGHT);
			mario.setSize(24, 32);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}
