
// import statements
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;

// Game class where the game takes place
public class Game extends JFrame implements KeyListener {
	// variables for the constants of the window and the catcher
	private static final int WINDOWWIDTH = 1280, WINDOWHEIGHT = 720, BORDER = 300;
	private static final int CATCHERSPEED = 7, CATCHERWIDTH = 240;

	// variables used to store information for generateCircles()
	private static ArrayList<Circle> circles = new ArrayList<Circle>();
	private static ArrayList<Double> distances = new ArrayList<Double>();
	private static double notes, verticalDistance, horizontalDistance, approachRate, offset;

	// variables used to display the catcher
	private double catcherX, catcherY = WINDOWHEIGHT - 100;
	private boolean lastLeft = false, left = false, right = false, dash = false, hyper = false;

	// variables used to display combo, score, and accuracy
	private static int combo, score, notesFallen, notesCaught;
	private static double accuracy;

	// variables used for playSound()
	private static String song;
	private static Clip clip;

	// varibales used for double buffering to reduce flickering
	private Image doubleImage;
	private Graphics doubleGraphics;

	// various images used in this class
	ImageIcon icon = new ImageIcon("Icon.png");
	ImageIcon catcher = new ImageIcon("Catcher.png");
	ImageIcon catcherL = new ImageIcon("CatcherL.png");
	ImageIcon dashCatcher = new ImageIcon("DashCatcher.png");
	ImageIcon dashCatcherL = new ImageIcon("DashCatcherL.png");
	ImageIcon hyperCatcher = new ImageIcon("HyperCatcher.png");
	ImageIcon hyperCatcherL = new ImageIcon("HyperCatcherL.png");
	ImageIcon fruit = new ImageIcon("Fruit.png");
	ImageIcon hyperFruit = new ImageIcon("HyperFruit.png");
	ImageIcon zero = new ImageIcon("Zero.png");
	ImageIcon one = new ImageIcon("One.png");
	ImageIcon two = new ImageIcon("Two.png");
	ImageIcon three = new ImageIcon("Three.png");
	ImageIcon four = new ImageIcon("Four.png");
	ImageIcon five = new ImageIcon("Five.png");
	ImageIcon six = new ImageIcon("Six.png");
	ImageIcon seven = new ImageIcon("Seven.png");
	ImageIcon eight = new ImageIcon("Eight.png");
	ImageIcon nine = new ImageIcon("Nine.png");
	ImageIcon smallZero = new ImageIcon("SmallZero.png");
	ImageIcon smallOne = new ImageIcon("SmallOne.png");
	ImageIcon smallTwo = new ImageIcon("SmallTwo.png");
	ImageIcon smallThree = new ImageIcon("SmallThree.png");
	ImageIcon smallFour = new ImageIcon("SmallFour.png");
	ImageIcon smallFive = new ImageIcon("SmallFive.png");
	ImageIcon smallSix = new ImageIcon("SmallSix.png");
	ImageIcon smallSeven = new ImageIcon("SmallSeven.png");
	ImageIcon smallEight = new ImageIcon("SmallEight.png");
	ImageIcon smallNine = new ImageIcon("SmallNine.png");
	ImageIcon decimalPoint = new ImageIcon("DecimalPoint.png");
	ImageIcon percentSign = new ImageIcon("PercentSign.png");

	// constructor to create a game window
	public Game(String title) {
		// creates a window with the given title
		super(title);
		// resets variables such as combo and catcher position
		reset();
		song = title;
		playSound(song);
		clip.addLineListener(e -> {
			if (e.getType() == LineEvent.Type.STOP) {
				dispose();
				new EndScreen(score, notesCaught, notesFallen, accuracy);
			}
		});
		generateCircles(song);
		addKeyListener(this);
		setIconImage(icon.getImage());
		setSize(WINDOWWIDTH, WINDOWHEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBackground(Color.black);
	}

	// resets all variables
	// pre: none
	// post: resets all game variables
	public void reset() {
		combo = 0;
		score = 0;
		notesFallen = 0;
		notesCaught = 0;
		catcherX = WINDOWWIDTH / 2;
		circles.clear();
		distances.clear();
	}

	// generates circles based on the song
	// higher offset -> notes come earlier
	// pre: song must be one of the ones listed below
	// post: generates an array of circles and another array of the distances to the
	// previous circle
	public void generateCircles(String song) {
		double distance;
		if (song.equals("Zenzenzense")) {
			notes = 100;
			verticalDistance = 39.7;
			horizontalDistance = 400;
			approachRate = 5;
			offset = 140;
		} else if (song.equals("Yoake to Hotaru")) {
			notes = 150;
			verticalDistance = 20;
			horizontalDistance = 250;
			approachRate = 7;
			offset = 0;
		} else if (song.equals("Asu no Yozora Shoukaihan")) {
			notes = 350;
			verticalDistance = 25;
			horizontalDistance = 500;
			approachRate = 9;
			offset = 0;
		} else if (song.equals("Hibana")) {
			notes = 1600;
			verticalDistance = 12;
			horizontalDistance = 400;
			approachRate = 15;
			offset = -230;
		}

		// generates the first circle which is assigned a distance of 0
		// since there is no previous circle
		int temp = (int) ((Math.random() * 700) + 225);
		Circle firstCircle = new Circle(temp, 0, approachRate, offset);
		circles.add(firstCircle);
		distances.add(0.0);

		// generates random circle objects based on the variables given
		for (int i = 1; i <= notes; i++) {
			int X = (int) ((Math.random() * horizontalDistance * 2) - horizontalDistance + temp);
			// if in between the bounds of the screen
			if (X > 225 && X < 925) {
				Circle circle = new Circle(X, verticalDistance * i, approachRate, offset);
				circles.add(circle);
				distance = Math.abs(X - temp);
				distances.add(distance);
				// if distance is too large (between circles) a hyperdash will be required
				if (distance > 18 * verticalDistance && distance > CATCHERWIDTH) {
					circles.get(i - 1).isHyper();
				}
				temp = X;
			} else
				i--;
		}
	}

	// detect directional and speed keys pressed for catcher movement
	// pre: KeyListener must be implemented
	// post: sets up booleans for the movement and display of the catcher
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == 'l' || e.getKeyChar() == 'L') {
			right = true;
			lastLeft = false;
		} else if (e.getKeyChar() == 'j' || e.getKeyChar() == 'J') {
			left = true;
			lastLeft = true;
		}
		if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A') {
			dash = true;
		}
	}

	// detect directional and speed keys released for catcher movement
	// pre: KeyListener must be implemented
	// post: sets up booleans for the movement and display of the catcher
	public void keyReleased(KeyEvent e) {
		if (e.getKeyChar() == 'l' || e.getKeyChar() == 'L') {
			right = false;
		}
		if (e.getKeyChar() == 'j' || e.getKeyChar() == 'J') {
			left = false;
		}
		if (e.getKeyChar() == 'a' || e.getKeyChar() == 'A') {
			dash = false;
		}
	}

	// method used for double buffering to reduce flickering
	// pre: none
	// post: paints the screen rendered in paintComponent() to the window
	public void paint(Graphics g) {
		doubleImage = createImage(getWidth(), getHeight());
		doubleGraphics = doubleImage.getGraphics();
		paintComponent(doubleGraphics);
		g.drawImage(doubleImage, 0, 0, this);
		catcherMovement();
	}

	// paint different components to the screen
	// pre: none
	// post: paints components to be painted in paint()
	public void paintComponent(Graphics g) {
		paintCatcher(g);
		paintCircles(g);
		paintCombo(g);
		paintScore(g);
		paintAccuracy(g);
		pause(8);
		repaint();
	}

	// paints the catcher depending on the booleans set up by KeyPressed() and
	// KeyReleased()
	// pre: none
	// post: paints the catcher
	public void paintCatcher(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		if (hyper) {
			if (lastLeft)
				g2.drawImage(hyperCatcherL.getImage(), (int) catcherX - 140, (int) catcherY - 20, this);
			else
				g2.drawImage(hyperCatcher.getImage(), (int) catcherX - 140, (int) catcherY - 20, this);
		} else if (dash) {
			if (dash && lastLeft)
				g2.drawImage(dashCatcherL.getImage(), (int) catcherX - 140, (int) catcherY - 20, this);
			else
				g2.drawImage(dashCatcher.getImage(), (int) catcherX - 140, (int) catcherY - 20, this);
		} else {
			if (lastLeft)
				g2.drawImage(catcherL.getImage(), (int) catcherX - 140, (int) catcherY - 20, this);
			else
				g2.drawImage(catcher.getImage(), (int) catcherX - 140, (int) catcherY - 20, this);
		}
	}

	// paints the circles falling on the screen and updates variables
	// pre: none
	// post: paints the circles, check whether they are caught, and updates
	// score, combo, notesFallen, and notesCaught
	// if a note is caught or missed, they are removed from the arraylist
	public void paintCircles(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		for (int i = 0; i < circles.size(); i++) {
			Circle circle = circles.get(i);
			circle.update();
			if (caught(circle) == 0 && circle.getY() > -200) {
				if (!circle.getHyper())
					g2.drawImage(fruit.getImage(), circle.getX(), (int) circle.getY(), this);
				else
					g2.drawImage(hyperFruit.getImage(), circle.getX(), (int) circle.getY(), this);
			} else if (caught(circle) == 1) {
				hyper = false;
				circles.remove(i);
				distances.remove(i);
				if (combo >= 20)
					playSound("Combo Break");
				combo = 0;
				notesFallen++;
			} else if (caught(circle) == 2) {
				hyper = false;
				playSound("Hitsound");
				circles.remove(i);
				distances.remove(i);
				score += 300 + (combo * horizontalDistance / 100 * 12);
				combo++;
				notesFallen++;
				notesCaught++;
			} else if (caught(circle) == 3) {
				hyper = true;
				playSound("Hitsound");
				circles.remove(i);
				distances.remove(i);
				score += 300 + (combo * horizontalDistance / 100 * 12);
				combo++;
				notesFallen++;
				notesCaught++;
			}
		}
	}

	// paints the combo on top of the catcher
	// pre: none
	// post: paints the combo
	public void paintCombo(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		int comboOnes = combo % 10;
		int comboTens = (combo / 10) % 10;
		int comboHundreds = (combo / 100) % 10;
		int comboThousands = (combo / 1000) % 10;
		int comboHeight = 320;

		if (combo >= 1000) {
			if ((comboOnes == 0 && comboTens == 0 && comboHundreds == 0 && comboThousands != 0)
					|| (comboOnes == 0 && comboTens == 0 && comboHundreds != 0) || (comboOnes == 0 && comboTens != 0))
				g2.drawImage(zero.getImage(), (int) catcherX + 20, (int) comboHeight, this);
			else if (comboOnes == 1)
				g2.drawImage(one.getImage(), (int) catcherX + 20, (int) comboHeight, this);
			else if (comboOnes == 2)
				g2.drawImage(two.getImage(), (int) catcherX + 20, (int) comboHeight, this);
			else if (comboOnes == 3)
				g2.drawImage(three.getImage(), (int) catcherX + 20, (int) comboHeight, this);
			else if (comboOnes == 4)
				g2.drawImage(four.getImage(), (int) catcherX + 20, (int) comboHeight, this);
			else if (comboOnes == 5)
				g2.drawImage(five.getImage(), (int) catcherX + 20, (int) comboHeight, this);
			else if (comboOnes == 6)
				g2.drawImage(six.getImage(), (int) catcherX + 20, (int) comboHeight, this);
			else if (comboOnes == 7)
				g2.drawImage(seven.getImage(), (int) catcherX + 20, (int) comboHeight, this);
			else if (comboOnes == 8)
				g2.drawImage(eight.getImage(), (int) catcherX + 20, (int) comboHeight, this);
			else if (comboOnes == 9)
				g2.drawImage(nine.getImage(), (int) catcherX + 20, (int) comboHeight, this);

			if ((comboTens == 0 && comboHundreds == 0 && comboThousands != 0) || (comboTens == 0 && comboHundreds != 0))
				g2.drawImage(zero.getImage(), (int) catcherX - 20, (int) comboHeight, this);
			else if (comboTens == 1)
				g2.drawImage(one.getImage(), (int) catcherX - 20, (int) comboHeight, this);
			else if (comboTens == 2)
				g2.drawImage(two.getImage(), (int) catcherX - 20, (int) comboHeight, this);
			else if (comboTens == 3)
				g2.drawImage(three.getImage(), (int) catcherX - 20, (int) comboHeight, this);
			else if (comboTens == 4)
				g2.drawImage(four.getImage(), (int) catcherX - 20, (int) comboHeight, this);
			else if (comboTens == 5)
				g2.drawImage(five.getImage(), (int) catcherX - 20, (int) comboHeight, this);
			else if (comboTens == 6)
				g2.drawImage(six.getImage(), (int) catcherX - 20, (int) comboHeight, this);
			else if (comboTens == 7)
				g2.drawImage(seven.getImage(), (int) catcherX - 20, (int) comboHeight, this);
			else if (comboTens == 8)
				g2.drawImage(eight.getImage(), (int) catcherX - 20, (int) comboHeight, this);
			else if (comboTens == 9)
				g2.drawImage(nine.getImage(), (int) catcherX - 20, (int) comboHeight, this);

			if ((comboHundreds == 0 && comboThousands != 0))
				g2.drawImage(zero.getImage(), (int) catcherX - 60, (int) comboHeight, this);
			else if (comboHundreds == 1)
				g2.drawImage(one.getImage(), (int) catcherX - 60, (int) comboHeight, this);
			else if (comboHundreds == 2)
				g2.drawImage(two.getImage(), (int) catcherX - 60, (int) comboHeight, this);
			else if (comboHundreds == 3)
				g2.drawImage(three.getImage(), (int) catcherX - 60, (int) comboHeight, this);
			else if (comboHundreds == 4)
				g2.drawImage(four.getImage(), (int) catcherX - 60, (int) comboHeight, this);
			else if (comboHundreds == 5)
				g2.drawImage(five.getImage(), (int) catcherX - 60, (int) comboHeight, this);
			else if (comboHundreds == 6)
				g2.drawImage(six.getImage(), (int) catcherX - 60, (int) comboHeight, this);
			else if (comboHundreds == 7)
				g2.drawImage(seven.getImage(), (int) catcherX - 60, (int) comboHeight, this);
			else if (comboHundreds == 8)
				g2.drawImage(eight.getImage(), (int) catcherX - 60, (int) comboHeight, this);
			else if (comboHundreds == 9)
				g2.drawImage(nine.getImage(), (int) catcherX - 60, (int) comboHeight, this);

			if (comboThousands == 1)
				g2.drawImage(one.getImage(), (int) catcherX - 100, (int) comboHeight, this);
			else if (comboThousands == 2)
				g2.drawImage(two.getImage(), (int) catcherX - 100, (int) comboHeight, this);
			else if (comboThousands == 3)
				g2.drawImage(three.getImage(), (int) catcherX - 100, (int) comboHeight, this);
			else if (comboThousands == 4)
				g2.drawImage(four.getImage(), (int) catcherX - 100, (int) comboHeight, this);
			else if (comboThousands == 5)
				g2.drawImage(five.getImage(), (int) catcherX - 100, (int) comboHeight, this);
			else if (comboThousands == 6)
				g2.drawImage(six.getImage(), (int) catcherX - 100, (int) comboHeight, this);
			else if (comboThousands == 7)
				g2.drawImage(seven.getImage(), (int) catcherX - 100, (int) comboHeight, this);
			else if (comboThousands == 8)
				g2.drawImage(eight.getImage(), (int) catcherX - 100, (int) comboHeight, this);
			else if (comboThousands == 9)
				g2.drawImage(nine.getImage(), (int) catcherX - 100, (int) comboHeight, this);
		}

		else if (combo >= 100) {
			if ((comboOnes == 0 && comboTens != 0) || (comboOnes == 0 && comboTens == 0 && comboHundreds != 0))
				g2.drawImage(zero.getImage(), (int) catcherX, (int) comboHeight, this);
			else if (comboOnes == 1)
				g2.drawImage(one.getImage(), (int) catcherX, (int) comboHeight, this);
			else if (comboOnes == 2)
				g2.drawImage(two.getImage(), (int) catcherX, (int) comboHeight, this);
			else if (comboOnes == 3)
				g2.drawImage(three.getImage(), (int) catcherX, (int) comboHeight, this);
			else if (comboOnes == 4)
				g2.drawImage(four.getImage(), (int) catcherX, (int) comboHeight, this);
			else if (comboOnes == 5)
				g2.drawImage(five.getImage(), (int) catcherX, (int) comboHeight, this);
			else if (comboOnes == 6)
				g2.drawImage(six.getImage(), (int) catcherX, (int) comboHeight, this);
			else if (comboOnes == 7)
				g2.drawImage(seven.getImage(), (int) catcherX, (int) comboHeight, this);
			else if (comboOnes == 8)
				g2.drawImage(eight.getImage(), (int) catcherX, (int) comboHeight, this);
			else if (comboOnes == 9)
				g2.drawImage(nine.getImage(), (int) catcherX, (int) comboHeight, this);

			if (comboTens == 0 && comboHundreds != 0)
				g2.drawImage(zero.getImage(), (int) catcherX - 40, (int) comboHeight, this);
			else if (comboTens == 1)
				g2.drawImage(one.getImage(), (int) catcherX - 40, (int) comboHeight, this);
			else if (comboTens == 2)
				g2.drawImage(two.getImage(), (int) catcherX - 40, (int) comboHeight, this);
			else if (comboTens == 3)
				g2.drawImage(three.getImage(), (int) catcherX - 40, (int) comboHeight, this);
			else if (comboTens == 4)
				g2.drawImage(four.getImage(), (int) catcherX - 40, (int) comboHeight, this);
			else if (comboTens == 5)
				g2.drawImage(five.getImage(), (int) catcherX - 40, (int) comboHeight, this);
			else if (comboTens == 6)
				g2.drawImage(six.getImage(), (int) catcherX - 40, (int) comboHeight, this);
			else if (comboTens == 7)
				g2.drawImage(seven.getImage(), (int) catcherX - 40, (int) comboHeight, this);
			else if (comboTens == 8)
				g2.drawImage(eight.getImage(), (int) catcherX - 40, (int) comboHeight, this);
			else if (comboTens == 9)
				g2.drawImage(nine.getImage(), (int) catcherX - 40, (int) comboHeight, this);

			if (comboHundreds == 1)
				g2.drawImage(one.getImage(), (int) catcherX - 80, (int) comboHeight, this);
			else if (comboHundreds == 2)
				g2.drawImage(two.getImage(), (int) catcherX - 80, (int) comboHeight, this);
			else if (comboHundreds == 3)
				g2.drawImage(three.getImage(), (int) catcherX - 80, (int) comboHeight, this);
			else if (comboHundreds == 4)
				g2.drawImage(four.getImage(), (int) catcherX - 80, (int) comboHeight, this);
			else if (comboHundreds == 5)
				g2.drawImage(five.getImage(), (int) catcherX - 80, (int) comboHeight, this);
			else if (comboHundreds == 6)
				g2.drawImage(six.getImage(), (int) catcherX - 80, (int) comboHeight, this);
			else if (comboHundreds == 7)
				g2.drawImage(seven.getImage(), (int) catcherX - 80, (int) comboHeight, this);
			else if (comboHundreds == 8)
				g2.drawImage(eight.getImage(), (int) catcherX - 80, (int) comboHeight, this);
			else if (comboHundreds == 9)
				g2.drawImage(nine.getImage(), (int) catcherX - 80, (int) comboHeight, this);
		}

		else if (combo >= 10) {
			if (comboOnes == 0 && comboTens != 0)
				g2.drawImage(zero.getImage(), (int) catcherX - 20, (int) comboHeight, this);
			else if (comboOnes == 1)
				g2.drawImage(one.getImage(), (int) catcherX - 20, (int) comboHeight, this);
			else if (comboOnes == 2)
				g2.drawImage(two.getImage(), (int) catcherX - 20, (int) comboHeight, this);
			else if (comboOnes == 3)
				g2.drawImage(three.getImage(), (int) catcherX - 20, (int) comboHeight, this);
			else if (comboOnes == 4)
				g2.drawImage(four.getImage(), (int) catcherX - 20, (int) comboHeight, this);
			else if (comboOnes == 5)
				g2.drawImage(five.getImage(), (int) catcherX - 20, (int) comboHeight, this);
			else if (comboOnes == 6)
				g2.drawImage(six.getImage(), (int) catcherX - 20, (int) comboHeight, this);
			else if (comboOnes == 7)
				g2.drawImage(seven.getImage(), (int) catcherX - 20, (int) comboHeight, this);
			else if (comboOnes == 8)
				g2.drawImage(eight.getImage(), (int) catcherX - 20, (int) comboHeight, this);
			else if (comboOnes == 9)
				g2.drawImage(nine.getImage(), (int) catcherX - 20, (int) comboHeight, this);

			if (comboTens == 0 && comboHundreds != 0)
				g2.drawImage(zero.getImage(), (int) catcherX - 60, (int) comboHeight, this);
			else if (comboTens == 1)
				g2.drawImage(one.getImage(), (int) catcherX - 60, (int) comboHeight, this);
			else if (comboTens == 2)
				g2.drawImage(two.getImage(), (int) catcherX - 60, (int) comboHeight, this);
			else if (comboTens == 3)
				g2.drawImage(three.getImage(), (int) catcherX - 60, (int) comboHeight, this);
			else if (comboTens == 4)
				g2.drawImage(four.getImage(), (int) catcherX - 60, (int) comboHeight, this);
			else if (comboTens == 5)
				g2.drawImage(five.getImage(), (int) catcherX - 60, (int) comboHeight, this);
			else if (comboTens == 6)
				g2.drawImage(six.getImage(), (int) catcherX - 60, (int) comboHeight, this);
			else if (comboTens == 7)
				g2.drawImage(seven.getImage(), (int) catcherX - 60, (int) comboHeight, this);
			else if (comboTens == 8)
				g2.drawImage(eight.getImage(), (int) catcherX - 60, (int) comboHeight, this);
			else if (comboTens == 9)
				g2.drawImage(nine.getImage(), (int) catcherX - 60, (int) comboHeight, this);
		}

		else {
			if (comboOnes == 1)
				g2.drawImage(one.getImage(), (int) catcherX - 40, (int) comboHeight, this);
			else if (comboOnes == 2)
				g2.drawImage(two.getImage(), (int) catcherX - 40, (int) comboHeight, this);
			else if (comboOnes == 3)
				g2.drawImage(three.getImage(), (int) catcherX - 40, (int) comboHeight, this);
			else if (comboOnes == 4)
				g2.drawImage(four.getImage(), (int) catcherX - 40, (int) comboHeight, this);
			else if (comboOnes == 5)
				g2.drawImage(five.getImage(), (int) catcherX - 40, (int) comboHeight, this);
			else if (comboOnes == 6)
				g2.drawImage(six.getImage(), (int) catcherX - 40, (int) comboHeight, this);
			else if (comboOnes == 7)
				g2.drawImage(seven.getImage(), (int) catcherX - 40, (int) comboHeight, this);
			else if (comboOnes == 8)
				g2.drawImage(eight.getImage(), (int) catcherX - 40, (int) comboHeight, this);
			else if (comboOnes == 9)
				g2.drawImage(nine.getImage(), (int) catcherX - 40, (int) comboHeight, this);
		}
	}

	// paints the score on the top right corner
	// pre: none
	// post: paints the score
	public void paintScore(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		// local variables used to identify the digits in the score
		int scoreOnes = score % 10;
		int scoreTens = (score / 10) % 10;
		int scoreHundreds = (score / 100) % 10;
		int scoreThousands = (score / 1000) % 10;
		int scoreTenThousands = (score / 10000) % 10;
		int scoreHundredThousands = (score / 100000) % 10;
		int scoreMillions = (score / 1000000) % 10;
		int scoreTenMillions = (score / 10000000) % 10;
		int scoreHeight = 35;

		if (scoreOnes == 0)
			g2.drawImage(smallZero.getImage(), WINDOWWIDTH - 40, scoreHeight, this);
		else if (scoreOnes == 1)
			g2.drawImage(smallOne.getImage(), WINDOWWIDTH - 40, scoreHeight, this);
		else if (scoreOnes == 2)
			g2.drawImage(smallTwo.getImage(), WINDOWWIDTH - 40, scoreHeight, this);
		else if (scoreOnes == 3)
			g2.drawImage(smallThree.getImage(), WINDOWWIDTH - 40, scoreHeight, this);
		else if (scoreOnes == 4)
			g2.drawImage(smallFour.getImage(), WINDOWWIDTH - 40, scoreHeight, this);
		else if (scoreOnes == 5)
			g2.drawImage(smallFive.getImage(), WINDOWWIDTH - 40, scoreHeight, this);
		else if (scoreOnes == 6)
			g2.drawImage(smallSix.getImage(), WINDOWWIDTH - 40, scoreHeight, this);
		else if (scoreOnes == 7)
			g2.drawImage(smallSeven.getImage(), WINDOWWIDTH - 40, scoreHeight, this);
		else if (scoreOnes == 8)
			g2.drawImage(smallEight.getImage(), WINDOWWIDTH - 40, scoreHeight, this);
		else if (scoreOnes == 9)
			g2.drawImage(smallNine.getImage(), WINDOWWIDTH - 40, scoreHeight, this);

		if (scoreTens == 0)
			g2.drawImage(smallZero.getImage(), WINDOWWIDTH - 65, scoreHeight, this);
		else if (scoreTens == 1)
			g2.drawImage(smallOne.getImage(), WINDOWWIDTH - 65, scoreHeight, this);
		else if (scoreTens == 2)
			g2.drawImage(smallTwo.getImage(), WINDOWWIDTH - 65, scoreHeight, this);
		else if (scoreTens == 3)
			g2.drawImage(smallThree.getImage(), WINDOWWIDTH - 65, scoreHeight, this);
		else if (scoreTens == 4)
			g2.drawImage(smallFour.getImage(), WINDOWWIDTH - 65, scoreHeight, this);
		else if (scoreTens == 5)
			g2.drawImage(smallFive.getImage(), WINDOWWIDTH - 65, scoreHeight, this);
		else if (scoreTens == 6)
			g2.drawImage(smallSix.getImage(), WINDOWWIDTH - 65, scoreHeight, this);
		else if (scoreTens == 7)
			g2.drawImage(smallSeven.getImage(), WINDOWWIDTH - 65, scoreHeight, this);
		else if (scoreTens == 8)
			g2.drawImage(smallEight.getImage(), WINDOWWIDTH - 65, scoreHeight, this);
		else if (scoreTens == 9)
			g2.drawImage(smallNine.getImage(), WINDOWWIDTH - 65, scoreHeight, this);

		if (scoreHundreds == 0)
			g2.drawImage(smallZero.getImage(), WINDOWWIDTH - 90, scoreHeight, this);
		else if (scoreHundreds == 1)
			g2.drawImage(smallOne.getImage(), WINDOWWIDTH - 90, scoreHeight, this);
		else if (scoreHundreds == 2)
			g2.drawImage(smallTwo.getImage(), WINDOWWIDTH - 90, scoreHeight, this);
		else if (scoreHundreds == 3)
			g2.drawImage(smallThree.getImage(), WINDOWWIDTH - 90, scoreHeight, this);
		else if (scoreHundreds == 4)
			g2.drawImage(smallFour.getImage(), WINDOWWIDTH - 90, scoreHeight, this);
		else if (scoreHundreds == 5)
			g2.drawImage(smallFive.getImage(), WINDOWWIDTH - 90, scoreHeight, this);
		else if (scoreHundreds == 6)
			g2.drawImage(smallSix.getImage(), WINDOWWIDTH - 90, scoreHeight, this);
		else if (scoreHundreds == 7)
			g2.drawImage(smallSeven.getImage(), WINDOWWIDTH - 90, scoreHeight, this);
		else if (scoreHundreds == 8)
			g2.drawImage(smallEight.getImage(), WINDOWWIDTH - 90, scoreHeight, this);
		else if (scoreHundreds == 9)
			g2.drawImage(smallNine.getImage(), WINDOWWIDTH - 90, scoreHeight, this);

		if (scoreThousands == 0)
			g2.drawImage(smallZero.getImage(), WINDOWWIDTH - 115, scoreHeight, this);
		else if (scoreThousands == 1)
			g2.drawImage(smallOne.getImage(), WINDOWWIDTH - 115, scoreHeight, this);
		else if (scoreThousands == 2)
			g2.drawImage(smallTwo.getImage(), WINDOWWIDTH - 115, scoreHeight, this);
		else if (scoreThousands == 3)
			g2.drawImage(smallThree.getImage(), WINDOWWIDTH - 115, scoreHeight, this);
		else if (scoreThousands == 4)
			g2.drawImage(smallFour.getImage(), WINDOWWIDTH - 115, scoreHeight, this);
		else if (scoreThousands == 5)
			g2.drawImage(smallFive.getImage(), WINDOWWIDTH - 115, scoreHeight, this);
		else if (scoreThousands == 6)
			g2.drawImage(smallSix.getImage(), WINDOWWIDTH - 115, scoreHeight, this);
		else if (scoreThousands == 7)
			g2.drawImage(smallSeven.getImage(), WINDOWWIDTH - 115, scoreHeight, this);
		else if (scoreThousands == 8)
			g2.drawImage(smallEight.getImage(), WINDOWWIDTH - 115, scoreHeight, this);
		else if (scoreThousands == 9)
			g2.drawImage(smallNine.getImage(), WINDOWWIDTH - 115, scoreHeight, this);

		if (scoreTenThousands == 0)
			g2.drawImage(smallZero.getImage(), WINDOWWIDTH - 140, scoreHeight, this);
		else if (scoreTenThousands == 1)
			g2.drawImage(smallOne.getImage(), WINDOWWIDTH - 140, scoreHeight, this);
		else if (scoreTenThousands == 2)
			g2.drawImage(smallTwo.getImage(), WINDOWWIDTH - 140, scoreHeight, this);
		else if (scoreTenThousands == 3)
			g2.drawImage(smallThree.getImage(), WINDOWWIDTH - 140, scoreHeight, this);
		else if (scoreTenThousands == 4)
			g2.drawImage(smallFour.getImage(), WINDOWWIDTH - 140, scoreHeight, this);
		else if (scoreTenThousands == 5)
			g2.drawImage(smallFive.getImage(), WINDOWWIDTH - 140, scoreHeight, this);
		else if (scoreTenThousands == 6)
			g2.drawImage(smallSix.getImage(), WINDOWWIDTH - 140, scoreHeight, this);
		else if (scoreTenThousands == 7)
			g2.drawImage(smallSeven.getImage(), WINDOWWIDTH - 140, scoreHeight, this);
		else if (scoreTenThousands == 8)
			g2.drawImage(smallEight.getImage(), WINDOWWIDTH - 140, scoreHeight, this);
		else if (scoreTenThousands == 9)
			g2.drawImage(smallNine.getImage(), WINDOWWIDTH - 140, scoreHeight, this);

		if (scoreHundredThousands == 0)
			g2.drawImage(smallZero.getImage(), WINDOWWIDTH - 165, scoreHeight, this);
		else if (scoreHundredThousands == 1)
			g2.drawImage(smallOne.getImage(), WINDOWWIDTH - 165, scoreHeight, this);
		else if (scoreHundredThousands == 2)
			g2.drawImage(smallTwo.getImage(), WINDOWWIDTH - 165, scoreHeight, this);
		else if (scoreHundredThousands == 3)
			g2.drawImage(smallThree.getImage(), WINDOWWIDTH - 165, scoreHeight, this);
		else if (scoreHundredThousands == 4)
			g2.drawImage(smallFour.getImage(), WINDOWWIDTH - 165, scoreHeight, this);
		else if (scoreHundredThousands == 5)
			g2.drawImage(smallFive.getImage(), WINDOWWIDTH - 165, scoreHeight, this);
		else if (scoreHundredThousands == 6)
			g2.drawImage(smallSix.getImage(), WINDOWWIDTH - 165, scoreHeight, this);
		else if (scoreHundredThousands == 7)
			g2.drawImage(smallSeven.getImage(), WINDOWWIDTH - 165, scoreHeight, this);
		else if (scoreHundredThousands == 8)
			g2.drawImage(smallEight.getImage(), WINDOWWIDTH - 165, scoreHeight, this);
		else if (scoreHundredThousands == 9)
			g2.drawImage(smallNine.getImage(), WINDOWWIDTH - 165, scoreHeight, this);

		if (scoreMillions == 0)
			g2.drawImage(smallZero.getImage(), WINDOWWIDTH - 190, scoreHeight, this);
		else if (scoreMillions == 1)
			g2.drawImage(smallOne.getImage(), WINDOWWIDTH - 190, scoreHeight, this);
		else if (scoreMillions == 2)
			g2.drawImage(smallTwo.getImage(), WINDOWWIDTH - 190, scoreHeight, this);
		else if (scoreMillions == 3)
			g2.drawImage(smallThree.getImage(), WINDOWWIDTH - 190, scoreHeight, this);
		else if (scoreMillions == 4)
			g2.drawImage(smallFour.getImage(), WINDOWWIDTH - 190, scoreHeight, this);
		else if (scoreMillions == 5)
			g2.drawImage(smallFive.getImage(), WINDOWWIDTH - 190, scoreHeight, this);
		else if (scoreMillions == 6)
			g2.drawImage(smallSix.getImage(), WINDOWWIDTH - 190, scoreHeight, this);
		else if (scoreMillions == 7)
			g2.drawImage(smallSeven.getImage(), WINDOWWIDTH - 190, scoreHeight, this);
		else if (scoreMillions == 8)
			g2.drawImage(smallEight.getImage(), WINDOWWIDTH - 190, scoreHeight, this);
		else if (scoreMillions == 9)
			g2.drawImage(smallNine.getImage(), WINDOWWIDTH - 190, scoreHeight, this);

		if (scoreTenMillions == 0)
			g2.drawImage(smallZero.getImage(), WINDOWWIDTH - 215, scoreHeight, this);
		else if (scoreTenMillions == 1)
			g2.drawImage(smallOne.getImage(), WINDOWWIDTH - 215, scoreHeight, this);
		else if (scoreTenMillions == 2)
			g2.drawImage(smallTwo.getImage(), WINDOWWIDTH - 215, scoreHeight, this);
		else if (scoreTenMillions == 3)
			g2.drawImage(smallThree.getImage(), WINDOWWIDTH - 215, scoreHeight, this);
		else if (scoreTenMillions == 4)
			g2.drawImage(smallFour.getImage(), WINDOWWIDTH - 215, scoreHeight, this);
		else if (scoreTenMillions == 5)
			g2.drawImage(smallFive.getImage(), WINDOWWIDTH - 215, scoreHeight, this);
		else if (scoreTenMillions == 6)
			g2.drawImage(smallSix.getImage(), WINDOWWIDTH - 215, scoreHeight, this);
		else if (scoreTenMillions == 7)
			g2.drawImage(smallSeven.getImage(), WINDOWWIDTH - 215, scoreHeight, this);
		else if (scoreTenMillions == 8)
			g2.drawImage(smallEight.getImage(), WINDOWWIDTH - 215, scoreHeight, this);
		else if (scoreTenMillions == 9)
			g2.drawImage(smallNine.getImage(), WINDOWWIDTH - 215, scoreHeight, this);
	}

	// paints the accuracy on the top right corner
	// pre: none
	// post: paints the accuracy
	public void paintAccuracy(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		// local variables used to identify the digits in the accuracy
		int accHundredth = (int) ((accuracy * 100) % 10);
		int accTenth = (int) ((accuracy * 10) % 10);
		int accOnes = (int) (accuracy % 10);
		int accTens = (int) ((accuracy / 10) % 10);
		int accHundreds = (int) ((accuracy / 100) % 10);
		int accHeight = 70;

		// accuracy percentage calculation (no notes = 100%)
		accuracy = (double) notesCaught / notesFallen * 100;
		if (notesFallen == 0)
			accuracy = 100;

		g2.drawImage(percentSign.getImage(), WINDOWWIDTH - 52, accHeight, this);

		if (accHundredth == 0)
			g2.drawImage(smallZero.getImage(), WINDOWWIDTH - 82, accHeight, this);
		else if (accHundredth == 1)
			g2.drawImage(smallOne.getImage(), WINDOWWIDTH - 82, accHeight, this);
		else if (accHundredth == 2)
			g2.drawImage(smallTwo.getImage(), WINDOWWIDTH - 82, accHeight, this);
		else if (accHundredth == 3)
			g2.drawImage(smallThree.getImage(), WINDOWWIDTH - 82, accHeight, this);
		else if (accHundredth == 4)
			g2.drawImage(smallFour.getImage(), WINDOWWIDTH - 82, accHeight, this);
		else if (accHundredth == 5)
			g2.drawImage(smallFive.getImage(), WINDOWWIDTH - 82, accHeight, this);
		else if (accHundredth == 6)
			g2.drawImage(smallSix.getImage(), WINDOWWIDTH - 82, accHeight, this);
		else if (accHundredth == 7)
			g2.drawImage(smallSeven.getImage(), WINDOWWIDTH - 82, accHeight, this);
		else if (accHundredth == 8)
			g2.drawImage(smallEight.getImage(), WINDOWWIDTH - 82, accHeight, this);
		else if (accHundredth == 9)
			g2.drawImage(smallNine.getImage(), WINDOWWIDTH - 82, accHeight, this);

		if (accTenth == 0)
			g2.drawImage(smallZero.getImage(), WINDOWWIDTH - 107, accHeight, this);
		else if (accTenth == 1)
			g2.drawImage(smallOne.getImage(), WINDOWWIDTH - 107, accHeight, this);
		else if (accTenth == 2)
			g2.drawImage(smallTwo.getImage(), WINDOWWIDTH - 107, accHeight, this);
		else if (accTenth == 3)
			g2.drawImage(smallThree.getImage(), WINDOWWIDTH - 107, accHeight, this);
		else if (accTenth == 4)
			g2.drawImage(smallFour.getImage(), WINDOWWIDTH - 107, accHeight, this);
		else if (accTenth == 5)
			g2.drawImage(smallFive.getImage(), WINDOWWIDTH - 107, accHeight, this);
		else if (accTenth == 6)
			g2.drawImage(smallSix.getImage(), WINDOWWIDTH - 107, accHeight, this);
		else if (accTenth == 7)
			g2.drawImage(smallSeven.getImage(), WINDOWWIDTH - 107, accHeight, this);
		else if (accTenth == 8)
			g2.drawImage(smallEight.getImage(), WINDOWWIDTH - 107, accHeight, this);
		else if (accTenth == 9)
			g2.drawImage(smallNine.getImage(), WINDOWWIDTH - 107, accHeight, this);

		g2.drawImage(decimalPoint.getImage(), WINDOWWIDTH - 122, accHeight, this);

		if (accOnes == 0)
			g2.drawImage(smallZero.getImage(), WINDOWWIDTH - 147, accHeight, this);
		else if (accOnes == 1)
			g2.drawImage(smallOne.getImage(), WINDOWWIDTH - 147, accHeight, this);
		else if (accOnes == 2)
			g2.drawImage(smallTwo.getImage(), WINDOWWIDTH - 147, accHeight, this);
		else if (accOnes == 3)
			g2.drawImage(smallThree.getImage(), WINDOWWIDTH - 147, accHeight, this);
		else if (accOnes == 4)
			g2.drawImage(smallFour.getImage(), WINDOWWIDTH - 147, accHeight, this);
		else if (accOnes == 5)
			g2.drawImage(smallFive.getImage(), WINDOWWIDTH - 147, accHeight, this);
		else if (accOnes == 6)
			g2.drawImage(smallSix.getImage(), WINDOWWIDTH - 147, accHeight, this);
		else if (accOnes == 7)
			g2.drawImage(smallSeven.getImage(), WINDOWWIDTH - 147, accHeight, this);
		else if (accOnes == 8)
			g2.drawImage(smallEight.getImage(), WINDOWWIDTH - 147, accHeight, this);
		else if (accOnes == 9)
			g2.drawImage(smallNine.getImage(), WINDOWWIDTH - 147, accHeight, this);

		if (accTens == 0 && accHundreds != 0)
			g2.drawImage(smallZero.getImage(), WINDOWWIDTH - 172, accHeight, this);
		else if (accTens == 1)
			g2.drawImage(smallOne.getImage(), WINDOWWIDTH - 172, accHeight, this);
		else if (accTens == 2)
			g2.drawImage(smallTwo.getImage(), WINDOWWIDTH - 172, accHeight, this);
		else if (accTens == 3)
			g2.drawImage(smallThree.getImage(), WINDOWWIDTH - 172, accHeight, this);
		else if (accTens == 4)
			g2.drawImage(smallFour.getImage(), WINDOWWIDTH - 172, accHeight, this);
		else if (accTens == 5)
			g2.drawImage(smallFive.getImage(), WINDOWWIDTH - 172, accHeight, this);
		else if (accTens == 6)
			g2.drawImage(smallSix.getImage(), WINDOWWIDTH - 172, accHeight, this);
		else if (accTens == 7)
			g2.drawImage(smallSeven.getImage(), WINDOWWIDTH - 172, accHeight, this);
		else if (accTens == 8)
			g2.drawImage(smallEight.getImage(), WINDOWWIDTH - 172, accHeight, this);
		else if (accTens == 9)
			g2.drawImage(smallNine.getImage(), WINDOWWIDTH - 172, accHeight, this);

		if (accHundreds == 1)
			g2.drawImage(smallOne.getImage(), WINDOWWIDTH - 197, accHeight, this);
	}

	// checkes whether a circle is caught by detecting collision between hitboxes
	// pre: none
	// post: return 0 if still falling, return 1 if missed, return 2 if caught, and
	// return 3 if a hyperdash is caught
	public int caught(Circle circle) {
		Rectangle catcherHitbox = new Rectangle((int) catcherX - 130, (int) catcherY + 30, CATCHERWIDTH, 10);
		if (circle.getY() > WINDOWHEIGHT - 100)
			return 1;
		else if (catcherHitbox.intersects(circle.getHitbox()) && !circle.getHyper()) {
			return 2;
		} else if (catcherHitbox.intersects(circle.getHitbox()) && circle.getHyper())
			return 3;

		return 0;
	}

	// plays a given audio clip
	// pre: none
	// post: plays an audio clip
	public static void playSound(String sound) {
		clip = null;
		try {
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File("" + sound + ".wav")));
			clip.start();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}

	// updates the x position of the catcher based on KeysPressed() and
	// KeysReleased()
	// pre: none
	// post: updates the x position of the catcher
	public void catcherMovement() {
		if (catcherX < BORDER + 25) {
			catcherX = BORDER + 26;
		} else if (catcherX > WINDOWWIDTH - BORDER) {
			catcherX = WINDOWWIDTH - BORDER - 1;
		}
		if (!(left && right)) {
			if (dash && !hyper) {
				if (left) {
					catcherX -= CATCHERSPEED * 2;
				} else if (right) {
					catcherX += CATCHERSPEED * 2;
				}
			} else if (dash && hyper) {
				if (left) {
					catcherX -= CATCHERSPEED * distances.get(0) / verticalDistance / 7;
				} else if (right) {
					catcherX += CATCHERSPEED * distances.get(0) / verticalDistance / 7;
				}
			} else if (!dash && hyper) {
				if (left) {
					catcherX -= CATCHERSPEED * distances.get(0) / verticalDistance / 14;
				} else if (right) {
					catcherX += CATCHERSPEED * distances.get(0) / verticalDistance / 14;
				}
			} else {
				if (left) {
					catcherX -= CATCHERSPEED;
				} else if (right) {
					catcherX += CATCHERSPEED;
				}
			}
		}
	}

	// pauses the program for a given time
	// pre: none
	// post: pauses the program for some ms
	public void pause(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void keyTyped(KeyEvent e) {
	}
}