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
	Goomba goomba1 = new Goomba();
	Timer animateTimer, marioIconChangeTimer, animateGoombaTimer;
	GameWindow gw;
	private ArrayList<LevelFloorBlock> blocks = new ArrayList<LevelFloorBlock>();
	private ArrayList<Goomba> goombas = new ArrayList<Goomba>();

	GameScreen(GameWindow gw) {
		this.gw = gw;
		this.setLayout(null);
		this.setSize(10000, gw.getContentPane().getHeight());
		this.setPreferredSize(new Dimension(10000, gw.getContentPane()
				.getHeight()));
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
		Goomba goomba = new Goomba();
		goomba.setBounds(500, 600, goomba.getWidth(), goomba.getHeight());
		this.add(goomba);
		goombas.add(goomba);

		animateTimer = new Timer(1000 / 40, this);
		animateTimer.setActionCommand("animate");
		animateTimer.start();
		marioIconChangeTimer = new Timer(1000 / 10, this);
		marioIconChangeTimer.setActionCommand("marioIconChange");
		marioIconChangeTimer.start();
		animateGoombaTimer = new Timer(1000 / 5, this);
		animateGoombaTimer.setActionCommand("goombaIconChange");
		animateGoombaTimer.start();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("animate")) {
			if (!mario.isDead) {
				if (mario.isJumping) {
					// moving when mario is jumping
					if (moveLeft
							|| mario.getIcon().toString()
									.equals(mario.LITTLESTANDLEFT.toString())) {
						if (mario.getX() <= this.gw.scrollPane.getViewport()
								.getViewPosition().getX()
								+ gw.scrollPane.getWidth() / 4
								&& this.gw.scrollPane.getViewport()
										.getViewPosition().getX() > 0) {
							this.gw.scrollPane.getViewport().setViewPosition(
									new Point((int) this.gw.scrollPane
											.getViewport().getViewPosition()
											.getX() - 10, 0));
						}
						mario.setIcon(mario.LITTLEJUMPLEFT);
						mario.setSize(32, 30);

					}
					if (moveRight
							|| mario.getIcon().toString()
									.equals(mario.LITTLESTANDRIGHT.toString())) {
						if (mario.getX() >= this.gw.scrollPane.getViewport()
								.getViewPosition().getX()
								+ gw.scrollPane.getWidth() / 2) {
							this.gw.scrollPane.getViewport().setViewPosition(
									new Point((int) this.gw.scrollPane
											.getViewport().getViewPosition()
											.getX() + 10, 0));
						}
						mario.setIcon(mario.LITTLEJUMPRIGHT);
						mario.setSize(32, 30);
					}
				}
				// move mario
				if (moveLeft) {
					if (mario.getX() > 0) {
						mario.moveLeft();
					}
					if (mario.getX() <= this.gw.scrollPane.getViewport()
							.getViewPosition().getX()
							+ gw.scrollPane.getWidth() / 4
							&& this.gw.scrollPane.getViewport()
									.getViewPosition().getX() > 0) {
						this.gw.scrollPane.getViewport().setViewPosition(
								new Point(
										(int) this.gw.scrollPane.getViewport()
												.getViewPosition().getX() - 10,
										0));
					}
					if (mario.getIcon().toString()
							.equals(mario.LITTLESTANDRIGHT.toString())) {
						mario.setIcon(mario.LITTLESTANDLEFT);
						mario.setSize(24, 32);
					}

				}
				if (moveRight) {
					mario.moveRight();
					if (mario.getX() >= this.gw.scrollPane.getViewport()
							.getViewPosition().getX()
							+ gw.scrollPane.getWidth() / 2) {
						this.gw.scrollPane.getViewport().setViewPosition(
								new Point(
										(int) this.gw.scrollPane.getViewport()
												.getViewPosition().getX() + 10,
										0));
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
					mario.setLocation(mario.getX(), mario.getY()
							+ mario.velocity);
				}
				for (int j = 0; j < goombas.size(); j++) {
					if (goombas.get(j).velocity != 0) {
						goombas.get(j)
								.setLocation(
										goombas.get(j).getX(),
										goombas.get(j).getY()
												+ goombas.get(j).velocity);
					}
				}

				for (int j = 0; j < blocks.size(); j++) {

					if (mario.collidesWith(blocks.get(j))) {
						mario.isJumping = false;
						mario.setLocation(mario.getX(), blocks.get(j).getY()
								- mario.getHeight());
					}
					for (int i = 0; i < goombas.size(); i++) {
						if (goombas.get(i).collidesWith(blocks.get(j))) {
							goombas.get(i).isJumping = false;
							goombas.get(i).setLocation(
									goombas.get(i).getX(),
									blocks.get(j).getY()
											- goombas.get(i).getHeight());
						}
					}
				}
				// apply gravity
				if (mario.isJumping) {
					mario.velocity += GameObject.GRAVITY;
				}
				for (int j = 0; j < goombas.size(); j++) {
					if (goombas.get(j).isJumping) {
						goombas.get(j).velocity += GameObject.GRAVITY;
					}
					if (mario.collidesWith(goombas.get(j))) {
						if (!goombas.get(j).isDead) {
							if (mario.jumpsOnEnemy(goombas.get(j))) {
								goombas.get(j).isDead = true;
								goombas.get(j).setSize(32, 16);
								goombas.get(j).setIcon(
										goombas.get(j).GOOMBADEAD);
							} else {
								mario.isDead = true;
								mario.setIcon(mario.MARIODEAD);
							}
						}
					}
					if (!goombas.get(j).isDead) {
						goombas.get(j).moveLeft();
					}
				}

			}
		}
		if (e.getActionCommand().equals("marioIconChange")) {
			if (!mario.isDead) {
				if (!mario.isJumping) {
					if (moveLeft) {
						// System.out.println("moving left");
						// System.out.println(mario.getIcon().toString());
						// System.out.println(mario.LITTLESTANDLEFT);
						if (mario.getIcon().toString()
								.equals(mario.LITTLESTANDLEFT.toString())
								|| mario.getIcon()
										.toString()
										.equals(mario.LITTLEJUMPLEFT.toString())) {
							// changes to littlestartwalkleft
							mario.setIcon(mario.LITTLESTARTWALKLEFT);
							mario.setSize(24, 32);
						} else if (mario.getIcon().toString()
								.equals(mario.LITTLESTARTWALKLEFT.toString())) {
							// changes to littlemidwalkleft1
							mario.setIcon(mario.LITTLEMIDWALKLEFT1);
							mario.setSize(22, 30);
						} else if (mario.getIcon().toString()
								.equals(mario.LITTLEMIDWALKLEFT1.toString())) {
							// changes to littlemidwalkleft2
							mario.setIcon(mario.LITTLEMIDWALKLEFT2);
							mario.setSize(32, 32);
						} else if (mario.getIcon().toString()
								.equals(mario.LITTLEMIDWALKLEFT2.toString())) {
							// changes back to littlestartwalkleft
							mario.setIcon(mario.LITTLESTARTWALKLEFT);
							mario.setSize(24, 32);
						}
					}
					if (moveRight) {
						if (mario.getIcon().toString()
								.equals(mario.LITTLESTANDRIGHT.toString())
								|| mario.getIcon()
										.toString()
										.equals(mario.LITTLEJUMPRIGHT
												.toString())) {
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
		if (e.getActionCommand().equals("goombaIconChange")) {
			if (!mario.isDead) {
				for (int i = 0; i < goombas.size(); i++) {
					if (!goombas.get(i).isDead) {
						if (goombas.get(i).getIcon().toString()
								.equals(goombas.get(i).GOOMBARIGHT.toString())) {
							goombas.get(i).setIcon(goombas.get(i).GOOMBALEFT);
						} else if (goombas.get(i).getIcon().toString()
								.equals(goombas.get(i).GOOMBALEFT.toString())) {
							goombas.get(i).setIcon(goombas.get(i).GOOMBARIGHT);
						}
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
		if (!mario.isDead) {
			// stop moving
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
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}
