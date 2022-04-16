package com.snake;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.concurrent.TimeUnit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Board extends JPanel implements ActionListener {

	private final int B_WIDTH = 500;// frame width
	private final int B_HEIGHT = 500;// frame height
	private final int DOT_SIZE = 10;
	private final int ALL_DOTS = 900;
	private final int RAND_POS = 29;
	private final int TARGET = 4;
	private final int CUBE_DEPTH = 5; // parameter for 3d cube

	private final int x[] = new int[ALL_DOTS];
	private final int y[] = new int[ALL_DOTS];

	private int dots;
	private int apple_x;
	private int apple_y;
	private int bombx, bomby;
	private int delay = 140;// controls speed
	private int scorenum = 0;
	private String win = "";
	private boolean newLevel = false;

	private boolean leftDirection = false;
	private boolean rightDirection = true;
	private boolean upDirection = false;
	private boolean downDirection = false;
	private boolean inGame = true;
	private boolean winner = false;

	private Timer timer;
	private Image bomb;
	private Image apple;
	private Image backgrnd;
	Cube cube;

	private int level = 1;

	public Board() {

		initBoard();
	}

	private void initBoard() {
		
		addKeyListener(new TAdapter());
		setBackground(Color.black);
		setFocusable(true);

		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
		loadImages();
		initGame();
	}

	// use this method to import images into the game
	private void loadImages() {
		// load in background image
		ImageIcon background = new ImageIcon("Java-Snake-Game/src/resources/sand.jpg");
		//backgrnd = background.getImage();
		
		Icon icon = new ImageIcon("Java-Snake-Game/src/resources/bck4.gif");
		backgrnd = ((ImageIcon) icon).getImage();
		//setContentPane(new JLabel(icon));
		// ImageIcon iia = new ImageIcon("src/resources/apple.png");
		// apple = iia.getImage();

	}

	private void initGame() {

		dots = 3;

		for (int z = 0; z < dots; z++) {
			x[z] = B_WIDTH / 2 - z * 10;
			y[z] = B_HEIGHT / 2;
		}
		locateBomb();
		locateApple();

		// makes the snake go faster
		// delay = delay ;

		timer = new Timer(delay, this);
		timer.start();

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// draw background before other items
		g.drawImage(backgrnd, 0, 0, this);

		try {
			// prints the win variable which is empty until the level is complete
			Font small = new Font("Helvetica", Font.BOLD, 14);
			FontMetrics metr = getFontMetrics(small);
			g.setColor(Color.BLACK);
			g.setFont(small);
			g.drawString(win, (B_WIDTH - metr.stringWidth(win)) / 2, B_HEIGHT / 2);
			g.setColor(Color.white);

			doDrawing(g);
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void doDrawing(Graphics g) throws InterruptedException, IOException {

		if (inGame) {

			if (dots == TARGET) {
				// increments to the next level once target score is reached
				newLevel = true;
				level++;
				initGame();
				// TimeUnit.SECONDS.sleep(2);

			} else {

				// check if there is a new level to display win screen
				if (!win.equals("") && newLevel) {
					TimeUnit.SECONDS.sleep(3);
				}
				newLevel = false;
				win = "";
				// game info
				g.setColor(java.awt.Color.GREEN);
				Font small2 = new Font("Helvetica", Font.BOLD, 12);
				g.setFont(small2);
				// g.fillRect(20, 20, DOT_SIZE, DOT_SIZE);
				String score = "Current Snake Size = " + dots;
				String scoreVal = "Current Score = " + scorenum;
				String target = "Target Snake Size = " + TARGET;
				String info = "Red = apple";
				String info2 = "Black = bomb";
				String levelInfo = "Level " + level;

				// printing out game info onto the panel
				g.setColor(java.awt.Color.blue);
				g.drawString(scoreVal, B_WIDTH - 150, 30);
				g.drawString(score, B_WIDTH - 150, 50);
				g.drawString(target, B_WIDTH - 150, 70);
				g.drawString(levelInfo, B_WIDTH - 150, 90);
				g.setColor(java.awt.Color.RED);
				g.drawString(info, B_WIDTH - 150, 110);
				g.setColor(java.awt.Color.black);
				g.drawString(info2, B_WIDTH - 150, 130);
				// g.drawImage(apple, apple_x, apple_y, this);

				// draw cubes for all objects in the game
				g.setColor(java.awt.Color.RED);

				// draw apple
				cube = new Cube(apple_x, apple_y, DOT_SIZE, CUBE_DEPTH);
				cube.drawCube(g);
				g.setColor(java.awt.Color.BLACK);

				// draw bomb
				cube = new Cube(bombx, bomby, DOT_SIZE, CUBE_DEPTH);
				cube.drawCube(g);

				// draw snake
				for (int z = 0; z < dots; z++) {
					if (z == 0) {
						g.setColor(java.awt.Color.GREEN);

						cube = new Cube(x[z], y[z], DOT_SIZE, CUBE_DEPTH);
						cube.drawCube(g);
					} else {
						g.setColor(java.awt.Color.BLUE);

						cube = new Cube(x[z], y[z], DOT_SIZE, CUBE_DEPTH);
						cube.drawCube(g);
					}
				}

				Toolkit.getDefaultToolkit().sync();
			}

		} else {
			// prints the game over screen when the player loses
			gameOver(g);

		}
	}

	private void gameOver(Graphics g) throws IOException {

		String msg = "Game Over";
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metr = getFontMetrics(small);
		String content;
		int filescore = 0;
		String output;

		g.setColor(Color.black);
		g.setFont(small);
		g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2, B_HEIGHT / 2);
		File file = new File("Java-Snake-Game/src/resources/HighScore.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		content = br.readLine();// check highscores from a file
		br.close();

		filescore = Integer.parseInt(content);
		// add highscore to file if the current score is bigger
		if (filescore <= scorenum) {
			FileWriter writer = new FileWriter("Java-Snake-Game/src/resources/HighScore.txt");
			BufferedWriter bw = new BufferedWriter(writer);

			// content="New HighScore!! "+scorenum;
			output = "New HighScore!! " + scorenum;
			g.drawString(output, (B_WIDTH - metr.stringWidth(msg)) / 2, (B_HEIGHT / 2) + 20);
			bw.write(String.valueOf(scorenum));
			bw.close();
		} else if (!content.equals("0")) {
			//write down the highscore and player score if the score is lower than the highscore
			content = "High Score = " + content;
			String yourScore = "Score = " + scorenum;
			g.drawString(content, (B_WIDTH - metr.stringWidth(msg)) / 2, (B_HEIGHT / 2) + 20);
			g.drawString(yourScore, (B_WIDTH - metr.stringWidth(msg)) / 2, (B_HEIGHT / 2) + 40);
		}
	}

	// checks if the snake makes contact with an apple
	private void checkApple() {

		if ((x[0] == apple_x) && (y[0] == apple_y)) {

			dots++;
			scorenum = scorenum + (level * 5);// increase score when apple is eaten
			if (dots == TARGET) {
				win = "Level " + (level + 1) + " starting...";
			}
			locateApple();
		}
	}

	// checks if the snake makes contact with a bomb
	private void checkBomb() {

		if ((x[0] == bombx) && (y[0] == bomby)) {

			inGame = false;
		}
	}

	// moves the snake based on arrow keys
	private void move() {

		for (int z = dots; z > 0; z--) {
			x[z] = x[(z - 1)];
			y[z] = y[(z - 1)];
		}

		if (leftDirection) {
			x[0] -= DOT_SIZE;
		}

		if (rightDirection) {
			x[0] += DOT_SIZE;
		}

		if (upDirection) {
			y[0] -= DOT_SIZE;
		}

		if (downDirection) {
			y[0] += DOT_SIZE;
		}
	}

	// checks if the snake goes out of the game area
	private void checkCollision() {

		for (int z = dots; z > 0; z--) {

			if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
				inGame = false;
			}
		}

		if (y[0] >= B_HEIGHT) {
			inGame = false;
		}

		if (y[0] < 0) {
			inGame = false;
		}

		if (x[0] >= B_WIDTH) {
			inGame = false;
		}

		if (x[0] < 0) {
			inGame = false;
		}

		if (!inGame) {
			timer.stop();
		}
	}

	// sets the location of the apple
	private void locateApple() {

		int r = (int) (Math.random() * RAND_POS);
		apple_x = ((r * DOT_SIZE));

		r = (int) (Math.random() * RAND_POS);
		apple_y = ((r * DOT_SIZE));
	}

	// sets the location of the bomb
	private void locateBomb() {
		int r = (int) (Math.random() * RAND_POS);
		bombx = ((r * DOT_SIZE));

		r = (int) (Math.random() * RAND_POS);
		bomby = ((r * DOT_SIZE));

		if (bombx == apple_x && bomby == apple_y) {
			r = (int) (Math.random() * RAND_POS);
			bombx = ((r * DOT_SIZE));

			r = (int) (Math.random() * RAND_POS);
			bomby = ((r * DOT_SIZE));
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (inGame) {

			checkApple();
			checkBomb();
			checkCollision();
			move();

		}

		repaint();
	}

	// checks for keyboard input to move the snake
	private class TAdapter extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {

			int key = e.getKeyCode();

			if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
				leftDirection = true;
				upDirection = false;
				downDirection = false;
			}

			if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
				rightDirection = true;
				upDirection = false;
				downDirection = false;
			}

			if ((key == KeyEvent.VK_UP) && (!downDirection)) {
				upDirection = true;
				rightDirection = false;
				leftDirection = false;
			}

			if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
				downDirection = true;
				rightDirection = false;
				leftDirection = false;
			}
		}
	}
}
