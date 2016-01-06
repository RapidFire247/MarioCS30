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
	public static ArrayList<LevelFloorBlock> lfBlocks = new ArrayList<LevelFloorBlock>();
	private ArrayList<Goomba> goombas = new ArrayList<Goomba>();
	private ArrayList<BrickBlock> brickBlocks = new ArrayList<BrickBlock>();
	private ArrayList<QuestionBlock> questionBlocks = new ArrayList<QuestionBlock>();
	private ArrayList<SolidBlock> solidBlocks = new ArrayList<SolidBlock>();

	GameScreen(GameWindow gw) {
		this.gw = gw;
		this.setLayout(null);
		this.setSize(10000, gw.getContentPane().getHeight());
		this.setPreferredSize(new Dimension(7029, gw.getContentPane()
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
		createLevelFloor();
		createBrickBlocks();
		createQuestionBlocks();
		Goomba goomba = new Goomba();
		goomba.setBounds(500, 600, goomba.getWidth(), goomba.getHeight());
		this.add(goomba);
		goombas.add(goomba);
		animateTimer = new Timer(1000 / 40, this);
		animateTimer.setActionCommand("animate");
		animateTimer.start();
		marioIconChangeTimer = new Timer(1000 / 2, this);
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
				// check if mario and goombas are colliding with level floor
				mario.standingOnBlock = null;
				for (int j = 0; j < lfBlocks.size(); j++) {
					if (mario.standingOnBlock == null) {
						if (mario.collidesWith(lfBlocks.get(j))) {
							mario.isJumping = false;
							mario.setLocation(mario.getX(), lfBlocks.get(j)
									.getY() - mario.getHeight());
							mario.standingOnBlock = lfBlocks.get(j);
							// System.out.println(mario.standingOnBlock);
						}
					}
					for (int i = 0; i < goombas.size(); i++) {
						if (goombas.get(i).collidesWith(lfBlocks.get(j))) {
							goombas.get(i).isJumping = false;
							goombas.get(i).setLocation(
									goombas.get(i).getX(),
									lfBlocks.get(j).getY()
											- goombas.get(i).getHeight());
						}
					}
				}
				checkCollisionWithBrickBlock();
				checkCollisionWithQuestionBlock();
				// apply gravity
				for (int j = 0; j < goombas.size(); j++) {
					if (goombas.get(j).isJumping) {
						goombas.get(j).velocity += GameObject.GRAVITY;
					}
					if (mario.collidesWith(goombas.get(j))) {
						if (!goombas.get(j).isDead) {
							if (mario.jumpsOnTop(goombas.get(j))) {
								goombas.get(j).isDead = true;
								goombas.get(j).setSize(32, 16);
								goombas.get(j).setIcon(
										goombas.get(j).GOOMBADEAD);
								mario.littleJump();
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
				System.out.println(mario.isJumping);
				System.out.println(mario.getIcon().toString());
				System.out.println(mario.standingOnBlock);

				if (mario.standingOnBlock != null) {
					mario.velocity = 0;
					mario.isJumping = false;
				} else {
					mario.isJumping = true;
				}
				if (mario.isJumping) {
					mario.velocity += GameObject.GRAVITY;
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
						System.out.println(mario.getIcon().toString());
						if (mario.getIcon().toString()
								.equals(mario.LITTLESTANDLEFT.toString())
								|| mario.getIcon()
										.toString()
										.equals(mario.LITTLEJUMPLEFT.toString())) {
							// changes to littlestartwalkleft
							mario.setIcon(mario.LITTLESTARTWALKLEFT);
							mario.setSize(24, 32);
							System.out.println("start");
						} else if (mario.getIcon().toString()
								.equals(mario.LITTLESTARTWALKLEFT.toString())) {
							// changes to littlemidwalkleft1
							mario.setIcon(mario.LITTLEMIDWALKLEFT1);
							mario.setSize(22, 30);
							System.out.println("mid");
							// System.out.println(mario.LITTLEMIDWALKLEFT1.toString());
						} else if (mario.getIcon().toString()
								.equals(mario.LITTLEMIDWALKLEFT1.toString())) {
							// changes to littlemidwalkleft2
							mario.setIcon(mario.LITTLEMIDWALKLEFT2);
							mario.setSize(32, 32);
							System.out.println("mid2");
						} else if (mario.getIcon().toString()
								.equals(mario.LITTLEMIDWALKLEFT2.toString())) {
							// changes back to littlestartwalkleft
							mario.setIcon(mario.LITTLESTARTWALKLEFT);
							mario.setSize(24, 32);
							System.out.println("start2");
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


	public void createBrickBlocks() {
		int brickBlocksMade = 0;
		for (int i = 0; i < 219; i++) {
			if (i == 21 || i == 23 || i == 25 || i == 78 || i == 80 || i == 95
					|| i == 101 || i == 102 || i == 119
					|| (i >= 130 && i <= 131) || i == 169 || i == 170
					|| i == 172) {
				BrickBlock BB = new BrickBlock();
				BB.setBounds(this.getX() + (33 * i), this.getHeight()
						- (6 * 33), BB.getWidth(), BB.getHeight());
				this.add(BB);
				brickBlocks.add(BB);
			} else if ((i >= 81 && i <= 88) || (i >= 92 && i <= 94)
					|| (i >= 122 && i <= 124) || i == 129 || i == 132) {
				BrickBlock BB = new BrickBlock();
				BB.setBounds(this.getX() + (33 * i), this.getHeight()
						- (10 * 33), BB.getWidth(), BB.getHeight());
				this.add(BB);
				brickBlocks.add(BB);
			}
		}
	}

	public void createQuestionBlocks() {
		int questionBlocksMade = 0;
		for (int i = 0; i < 219; i++) {
			if (i == 17 || i == 22 || i == 24 || i == 79 || i == 107
					|| i == 113 || i == 171) {
				QuestionBlock QB = new QuestionBlock();
				QB.setBounds(this.getX() + (33 * i), this.getHeight()
						- (6 * 33), QB.getWidth(), QB.getHeight());
				this.add(QB);
				questionBlocks.add(QB);
			} else if (i == 23 || i == 95 || i == 130 || i == 131) {
				QuestionBlock QB = new QuestionBlock();
				QB.setBounds(this.getX() + (33 * i), this.getHeight()
						- (10 * 33), QB.getWidth(), QB.getHeight());
				this.add(QB);
				questionBlocks.add(QB);
			} else if (i == 110) {
				QuestionBlock QB = new QuestionBlock();
				QB.setBounds(this.getX() + (33 * i), this.getHeight()
						- (6 * 33), QB.getWidth(), QB.getHeight());
				this.add(QB);
				questionBlocks.add(QB);
				QuestionBlock QB2 = new QuestionBlock();
				QB2.setBounds(this.getX() + (33 * i), this.getHeight()
						- (10 * 33), QB2.getWidth(), QB2.getHeight());
				this.add(QB2);
				questionBlocks.add(QB2);
			}
		}
	}

	public void createLevelFloor() {
		int lfBlocksMade = 0;
		for (int i = 0; i < 219; i++) {
			if (lfBlocksMade <= 69) {
				LevelFloorBlock lf = new LevelFloorBlock();
				lf.setBounds(this.getX() + (lfBlocksMade * lf.getWidth()),
						this.getHeight() - lf.getHeight(), lf.getWidth(),
						lf.getHeight());
				this.add(lf);
				lfBlocks.add(lf);
				lfBlocksMade++;
			} else if (lfBlocksMade > 69 && lfBlocksMade <= 71) {
				lfBlocksMade++;
			} else if (lfBlocksMade > 71 && lfBlocksMade <= 86) {
				LevelFloorBlock lf = new LevelFloorBlock();
				lf.setBounds(this.getX() + (lfBlocksMade * lf.getWidth()),
						this.getHeight() - lf.getHeight(), lf.getWidth(),
						lf.getHeight());
				this.add(lf);
				lfBlocks.add(lf);
				lfBlocksMade++;
			} else if (lfBlocksMade > 86 && lfBlocksMade <= 89) {
				lfBlocksMade++;
			} else if (lfBlocksMade > 89 && lfBlocksMade <= 153) {
				LevelFloorBlock lf = new LevelFloorBlock();
				lf.setBounds(this.getX() + (lfBlocksMade * lf.getWidth()),
						this.getHeight() - lf.getHeight(), lf.getWidth(),
						lf.getHeight());
				this.add(lf);
				lfBlocks.add(lf);
				lfBlocksMade++;
			} else if (lfBlocksMade > 153 && lfBlocksMade <= 155) {
				lfBlocksMade++;
			} else if (lfBlocksMade > 155 && lfBlocksMade <= 213) {
				LevelFloorBlock lf = new LevelFloorBlock();
				lf.setBounds(this.getX() + (lfBlocksMade * lf.getWidth()),
						this.getHeight() - lf.getHeight(), lf.getWidth(),
						lf.getHeight());
				this.add(lf);
				lfBlocks.add(lf);
				lfBlocksMade++;
			}
		}
		lfBlocksMade = 0;
		for (int i = 0; i < 219; i++) {
			if (lfBlocksMade <= 69) {
				LevelFloorBlock lf = new LevelFloorBlock();
				lf.setBounds(this.getX() + (lfBlocksMade * lf.getWidth()),
						this.getHeight() - (2 * lf.getHeight()), lf.getWidth(),
						lf.getHeight());
				this.add(lf);
				lfBlocks.add(lf);
				lfBlocksMade++;
			} else if (lfBlocksMade > 69 && lfBlocksMade <= 71) {
				lfBlocksMade++;
			} else if (lfBlocksMade > 71 && lfBlocksMade <= 86) {
				LevelFloorBlock lf = new LevelFloorBlock();
				lf.setBounds(this.getX() + (lfBlocksMade * lf.getWidth()),
						this.getHeight() - (2 * lf.getHeight()), lf.getWidth(),
						lf.getHeight());
				this.add(lf);
				lfBlocks.add(lf);
				lfBlocksMade++;
			} else if (lfBlocksMade > 86 && lfBlocksMade <= 89) {
				lfBlocksMade++;
			} else if (lfBlocksMade > 89 && lfBlocksMade <= 153) {
				LevelFloorBlock lf = new LevelFloorBlock();
				lf.setBounds(this.getX() + (lfBlocksMade * lf.getWidth()),
						this.getHeight() - (2 * lf.getHeight()), lf.getWidth(),
						lf.getHeight());
				this.add(lf);
				lfBlocks.add(lf);
				lfBlocksMade++;
			} else if (lfBlocksMade > 153 && lfBlocksMade <= 155) {
				lfBlocksMade++;
			} else if (lfBlocksMade > 155 && lfBlocksMade <= 213) {
				LevelFloorBlock lf = new LevelFloorBlock();
				lf.setBounds(this.getX() + (lfBlocksMade * lf.getWidth()),
						this.getHeight() - (2 * lf.getHeight()), lf.getWidth(),
						lf.getHeight());
				this.add(lf);
				lfBlocks.add(lf);
				lfBlocksMade++;
			}
		}
	}
	
	private void checkCollisionWithBrickBlock() {
		for (int j = 0; j < brickBlocks.size(); j++) {
			if (brickBlocks.get(j).isVisible()) {
				if (mario.collidesWith(brickBlocks.get(j))) {
					if (mario.jumpsOnTop(brickBlocks.get(j))) {
						mario.setLocation(mario.getX(), brickBlocks.get(j)
								.getY() - mario.getHeight());
						mario.isJumping = false;
						mario.standingOnBlock = brickBlocks.get(j);
					} else if (mario.collidesFromLeftSide(brickBlocks.get(j))) {
						mario.setLocation(
								brickBlocks.get(j).getX() - mario.getWidth(),
								mario.getY());
						// mario.standingOnBlock = brickBlocks.get(j);
					} else if (mario.collidesFromRightSide(brickBlocks.get(j))) {
						mario.setLocation(brickBlocks.get(j).getX()
								+ brickBlocks.get(j).getWidth(), mario.getY());
						// mario.standingOnBlock = brickBlocks.get(j);
					} else if (mario.hitBlock(brickBlocks.get(j))) {
						brickBlocks.get(j).setVisible(false);
						mario.bounceDown();
					} else {
						
					}
				}
				for (int i = 0; i < goombas.size(); i++) {
					if (goombas.get(i).collidesWith(brickBlocks.get(j))) {
						goombas.get(i).isJumping = false;
						goombas.get(i).setLocation(
								goombas.get(i).getX(),
								brickBlocks.get(j).getY()
								- goombas.get(i).getHeight());
					}
				}
			}
		}
	}

	public void checkCollisionWithQuestionBlock() {
		for (int j = 0; j < questionBlocks.size(); j++) {
				if (questionBlocks.get(j).isOn) {
				if (mario.collidesWith(questionBlocks.get(j))) {
					if (mario.jumpsOnTop(questionBlocks.get(j))) {
						mario.setLocation(mario.getX(), questionBlocks.get(j)
								.getY() - mario.getHeight());
						mario.isJumping = false;
						mario.standingOnBlock = questionBlocks.get(j);
					} else if (mario
							.collidesFromLeftSide(questionBlocks.get(j))) {
						mario.setLocation(
								questionBlocks.get(j).getX() - mario.getWidth(),
								mario.getY());
						// mario.standingOnBlock = brickBlocks.get(j);
					} else if (mario.collidesFromRightSide(questionBlocks
							.get(j))) {
						mario.setLocation(questionBlocks.get(j).getX()
								+ brickBlocks.get(j).getWidth(), mario.getY());
						// mario.standingOnBlock = brickBlocks.get(j);
					} else if (mario.hitBlock(questionBlocks.get(j))) {
						if (questionBlocks.get(j).isOn) {
							questionBlocks.get(j).isOn = false;
							
						}
						mario.bounceDown();
					} else {

					}
				}
				for (int i = 0; i < goombas.size(); i++) {
					if (goombas.get(i).collidesWith(brickBlocks.get(j))) {
						goombas.get(i).isJumping = false;
						goombas.get(i).setLocation(
								goombas.get(i).getX(),
								brickBlocks.get(j).getY()
										- goombas.get(i).getHeight());
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
