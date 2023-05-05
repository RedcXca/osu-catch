
// import statments
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// End screen to display the results after a game
public class EndScreen extends JFrame implements ActionListener {
	// variables for the constants of the window and the end screen
	private int WINDOWWIDTH = 1280;
	private int WINDOWHEIGHT = 720;
	private JButton b1;

	// variables from Game class
	public int score, missed;
	public double accuracy;

	// variables used for generating endscreen
	ImageIcon background = new ImageIcon("Background.png");
	ImageIcon icon = new ImageIcon("Icon.png");
	
	// variables to display the socre/accuracy
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

	// variables to display rank based on accuracy
	ImageIcon SS = new ImageIcon("SS.png");
	ImageIcon S = new ImageIcon("S.png");
	ImageIcon A = new ImageIcon("A.png");
	ImageIcon B = new ImageIcon("B.png");
	ImageIcon C = new ImageIcon("C.png");
	ImageIcon D = new ImageIcon("D.png");
	ImageIcon miss = new ImageIcon("Miss.png");
	ImageIcon clear = new ImageIcon("Clear.png");

	// constructor used to make the endscreen window
	public EndScreen(int score, int notesCaught, int notesFallen, double accuracy) {
		// makes window with given title
		super("Endscreen");
		this.score = score;
		this.missed = notesFallen - notesCaught;
		this.accuracy = accuracy;
		setSize(WINDOWWIDTH, WINDOWHEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setButtons();
		setIconImage(icon.getImage());
		setVisible(true);
	}

	// adds clickable buttons to the menu screen
	// pre: ActionListener needs to be implemented
	// post: prints buttons to the screen
	public void setButtons() {
		JPanel c = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(background.getImage(), 0, 0, this);
				paintScore(g);
				paintMissed(g);
				paintAccuracy(g);
			}

			// paints the score on the screen
			// pre: none
			// post: paints the score
			public void paintScore(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;

				int scoreOnes = score % 10;
				int scoreTens = (score / 10) % 10;
				int scoreHundreds = (score / 100) % 10;
				int scoreThousands = (score / 1000) % 10;
				int scoreTenThousands = (score / 10000) % 10;
				int scoreHundredThousands = (score / 100000) % 10;
				int scoreMillions = (score / 1000000) % 10;
				int scoreTenMillions = (score / 10000000) % 10;

				if (scoreOnes == 0)
					g2.drawImage(zero.getImage(), 380, 50, this);
				else if (scoreOnes == 1)
					g2.drawImage(one.getImage(), 380, 50, this);
				else if (scoreOnes == 2)
					g2.drawImage(two.getImage(), 380, 50, this);
				else if (scoreOnes == 3)
					g2.drawImage(three.getImage(), 380, 50, this);
				else if (scoreOnes == 4)
					g2.drawImage(four.getImage(), 380, 50, this);
				else if (scoreOnes == 5)
					g2.drawImage(five.getImage(), 380, 50, this);
				else if (scoreOnes == 6)
					g2.drawImage(six.getImage(), 380, 50, this);
				else if (scoreOnes == 7)
					g2.drawImage(seven.getImage(), 380, 50, this);
				else if (scoreOnes == 8)
					g2.drawImage(eight.getImage(), 380, 50, this);
				else if (scoreOnes == 9)
					g2.drawImage(nine.getImage(), 380, 50, this);

				if (scoreTens == 0)
					g2.drawImage(zero.getImage(), 340, 50, this);
				else if (scoreTens == 1)
					g2.drawImage(one.getImage(), 340, 50, this);
				else if (scoreTens == 2)
					g2.drawImage(two.getImage(), 340, 50, this);
				else if (scoreTens == 3)
					g2.drawImage(three.getImage(), 340, 50, this);
				else if (scoreTens == 4)
					g2.drawImage(four.getImage(), 340, 50, this);
				else if (scoreTens == 5)
					g2.drawImage(five.getImage(), 340, 50, this);
				else if (scoreTens == 6)
					g2.drawImage(six.getImage(), 340, 50, this);
				else if (scoreTens == 7)
					g2.drawImage(seven.getImage(), 340, 50, this);
				else if (scoreTens == 8)
					g2.drawImage(eight.getImage(), 340, 50, this);
				else if (scoreTens == 9)
					g2.drawImage(nine.getImage(), 340, 50, this);

				if (scoreHundreds == 0)
					g2.drawImage(zero.getImage(), 300, 50, this);
				else if (scoreHundreds == 1)
					g2.drawImage(one.getImage(), 300, 50, this);
				else if (scoreHundreds == 2)
					g2.drawImage(two.getImage(), 300, 50, this);
				else if (scoreHundreds == 3)
					g2.drawImage(three.getImage(), 300, 50, this);
				else if (scoreHundreds == 4)
					g2.drawImage(four.getImage(), 300, 50, this);
				else if (scoreHundreds == 5)
					g2.drawImage(five.getImage(), 300, 50, this);
				else if (scoreHundreds == 6)
					g2.drawImage(six.getImage(), 300, 50, this);
				else if (scoreHundreds == 7)
					g2.drawImage(seven.getImage(), 300, 50, this);
				else if (scoreHundreds == 8)
					g2.drawImage(eight.getImage(), 300, 50, this);
				else if (scoreHundreds == 9)
					g2.drawImage(nine.getImage(), 300, 50, this);

				if (scoreThousands == 0)
					g2.drawImage(zero.getImage(), 260, 50, this);
				else if (scoreThousands == 1)
					g2.drawImage(one.getImage(), 260, 50, this);
				else if (scoreThousands == 2)
					g2.drawImage(two.getImage(), 260, 50, this);
				else if (scoreThousands == 3)
					g2.drawImage(three.getImage(), 260, 50, this);
				else if (scoreThousands == 4)
					g2.drawImage(four.getImage(), 260, 50, this);
				else if (scoreThousands == 5)
					g2.drawImage(five.getImage(), 260, 50, this);
				else if (scoreThousands == 6)
					g2.drawImage(six.getImage(), 260, 50, this);
				else if (scoreThousands == 7)
					g2.drawImage(seven.getImage(), 260, 50, this);
				else if (scoreThousands == 8)
					g2.drawImage(eight.getImage(), 260, 50, this);
				else if (scoreThousands == 9)
					g2.drawImage(nine.getImage(), 260, 50, this);

				if (scoreTenThousands == 0)
					g2.drawImage(zero.getImage(), 220, 50, this);
				else if (scoreTenThousands == 1)
					g2.drawImage(one.getImage(), 220, 50, this);
				else if (scoreTenThousands == 2)
					g2.drawImage(two.getImage(), 220, 50, this);
				else if (scoreTenThousands == 3)
					g2.drawImage(three.getImage(), 220, 50, this);
				else if (scoreTenThousands == 4)
					g2.drawImage(four.getImage(), 220, 50, this);
				else if (scoreTenThousands == 5)
					g2.drawImage(five.getImage(), 220, 50, this);
				else if (scoreTenThousands == 6)
					g2.drawImage(six.getImage(), 220, 50, this);
				else if (scoreTenThousands == 7)
					g2.drawImage(seven.getImage(), 220, 50, this);
				else if (scoreTenThousands == 8)
					g2.drawImage(eight.getImage(), 220, 50, this);
				else if (scoreTenThousands == 9)
					g2.drawImage(nine.getImage(), 220, 50, this);

				if (scoreHundredThousands == 0)
					g2.drawImage(zero.getImage(), 180, 50, this);
				else if (scoreHundredThousands == 1)
					g2.drawImage(one.getImage(), 180, 50, this);
				else if (scoreHundredThousands == 2)
					g2.drawImage(two.getImage(), 180, 50, this);
				else if (scoreHundredThousands == 3)
					g2.drawImage(three.getImage(), 180, 50, this);
				else if (scoreHundredThousands == 4)
					g2.drawImage(four.getImage(), 180, 50, this);
				else if (scoreHundredThousands == 5)
					g2.drawImage(five.getImage(), 180, 50, this);
				else if (scoreHundredThousands == 6)
					g2.drawImage(six.getImage(), 180, 50, this);
				else if (scoreHundredThousands == 7)
					g2.drawImage(seven.getImage(), 180, 50, this);
				else if (scoreHundredThousands == 8)
					g2.drawImage(eight.getImage(), 180, 50, this);
				else if (scoreHundredThousands == 9)
					g2.drawImage(nine.getImage(), 180, 50, this);

				if (scoreMillions == 0)
					g2.drawImage(zero.getImage(), 140, 50, this);
				else if (scoreMillions == 1)
					g2.drawImage(one.getImage(), 140, 50, this);
				else if (scoreMillions == 2)
					g2.drawImage(two.getImage(), 140, 50, this);
				else if (scoreMillions == 3)
					g2.drawImage(three.getImage(), 140, 50, this);
				else if (scoreMillions == 4)
					g2.drawImage(four.getImage(), 140, 50, this);
				else if (scoreMillions == 5)
					g2.drawImage(five.getImage(), 140, 50, this);
				else if (scoreMillions == 6)
					g2.drawImage(six.getImage(), 140, 50, this);
				else if (scoreMillions == 7)
					g2.drawImage(seven.getImage(), 140, 50, this);
				else if (scoreMillions == 8)
					g2.drawImage(eight.getImage(), 140, 50, this);
				else if (scoreMillions == 9)
					g2.drawImage(nine.getImage(), 140, 50, this);

				if (scoreTenMillions == 0)
					g2.drawImage(zero.getImage(), 100, 50, this);
				else if (scoreTenMillions == 1)
					g2.drawImage(one.getImage(), 100, 50, this);
				else if (scoreTenMillions == 2)
					g2.drawImage(two.getImage(), 100, 50, this);
				else if (scoreTenMillions == 3)
					g2.drawImage(three.getImage(), 100, 50, this);
				else if (scoreTenMillions == 4)
					g2.drawImage(four.getImage(), 100, 50, this);
				else if (scoreTenMillions == 5)
					g2.drawImage(five.getImage(), 100, 50, this);
				else if (scoreTenMillions == 6)
					g2.drawImage(six.getImage(), 100, 50, this);
				else if (scoreTenMillions == 7)
					g2.drawImage(seven.getImage(), 100, 50, this);
				else if (scoreTenMillions == 8)
					g2.drawImage(eight.getImage(), 100, 50, this);
				else if (scoreTenMillions == 9)
					g2.drawImage(nine.getImage(), 100, 50, this);
			}

			// paints the notes missed on the screen
			// pre: none
			// post: paints the notes missed
			public void paintMissed(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;

				int missedOnes = missed % 10;
				int missedTens = (missed / 10) % 10;
				int missedHundreds = (missed / 100) % 10;
				int missedThousands = (missed / 1000) % 10;

				if (missedOnes == 0)
					g2.drawImage(zero.getImage(), 220, 220, this);
				else if (missedOnes == 1)
					g2.drawImage(one.getImage(), 220, 220, this);
				else if (missedOnes == 2)
					g2.drawImage(two.getImage(), 220, 220, this);
				else if (missedOnes == 3)
					g2.drawImage(three.getImage(), 220, 220, this);
				else if (missedOnes == 4)
					g2.drawImage(four.getImage(), 220, 220, this);
				else if (missedOnes == 5)
					g2.drawImage(five.getImage(), 220, 220, this);
				else if (missedOnes == 6)
					g2.drawImage(six.getImage(), 220, 220, this);
				else if (missedOnes == 7)
					g2.drawImage(seven.getImage(), 220, 220, this);
				else if (missedOnes == 8)
					g2.drawImage(eight.getImage(), 220, 220, this);
				else if (missedOnes == 9)
					g2.drawImage(nine.getImage(), 220, 220, this);

				if ((missedTens == 0 && missedHundreds != 0 && missedThousands != 0)
						|| (missedTens == 0 && missedHundreds != 0))
					g2.drawImage(zero.getImage(), 180, 220, this);
				else if (missedTens == 1)
					g2.drawImage(one.getImage(), 180, 220, this);
				else if (missedTens == 2)
					g2.drawImage(two.getImage(), 180, 220, this);
				else if (missedTens == 3)
					g2.drawImage(three.getImage(), 180, 220, this);
				else if (missedTens == 4)
					g2.drawImage(four.getImage(), 180, 220, this);
				else if (missedTens == 5)
					g2.drawImage(five.getImage(), 180, 220, this);
				else if (missedTens == 6)
					g2.drawImage(six.getImage(), 180, 220, this);
				else if (missedTens == 7)
					g2.drawImage(seven.getImage(), 180, 220, this);
				else if (missedTens == 8)
					g2.drawImage(eight.getImage(), 180, 220, this);
				else if (missedTens == 9)
					g2.drawImage(nine.getImage(), 180, 220, this);

				if (missedHundreds == 0 && missedThousands != 0)
					g2.drawImage(zero.getImage(), 140, 220, this);
				else if (missedHundreds == 1)
					g2.drawImage(one.getImage(), 140, 220, this);
				else if (missedHundreds == 2)
					g2.drawImage(two.getImage(), 140, 220, this);
				else if (missedHundreds == 3)
					g2.drawImage(three.getImage(), 140, 220, this);
				else if (missedHundreds == 4)
					g2.drawImage(four.getImage(), 140, 220, this);
				else if (missedHundreds == 5)
					g2.drawImage(five.getImage(), 140, 220, this);
				else if (missedHundreds == 6)
					g2.drawImage(six.getImage(), 140, 220, this);
				else if (missedHundreds == 7)
					g2.drawImage(seven.getImage(), 140, 220, this);
				else if (missedHundreds == 8)
					g2.drawImage(eight.getImage(), 140, 220, this);
				else if (missedHundreds == 9)
					g2.drawImage(nine.getImage(), 140, 220, this);

				if (missedThousands == 1)
					g2.drawImage(one.getImage(), 100, 220, this);
				else if (missedThousands == 2)
					g2.drawImage(two.getImage(), 100, 220, this);
				else if (missedThousands == 3)
					g2.drawImage(three.getImage(), 100, 220, this);
				else if (missedThousands == 4)
					g2.drawImage(four.getImage(), 100, 220, this);
				else if (missedThousands == 5)
					g2.drawImage(five.getImage(), 100, 220, this);
				else if (missedThousands == 6)
					g2.drawImage(six.getImage(), 100, 220, this);
				else if (missedThousands == 7)
					g2.drawImage(seven.getImage(), 100, 220, this);
				else if (missedThousands == 8)
					g2.drawImage(eight.getImage(), 100, 220, this);
				else if (missedThousands == 9)
					g2.drawImage(nine.getImage(), 100, 220, this);

				g2.drawImage(clear.getImage(), 150, 400, this);
				g2.drawImage(miss.getImage(), 200, 120, this);
			}

			// paints the accuracy on the screen
			// pre: none
			// post: paints the accuracy
			public void paintAccuracy(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
				if (accuracy == 100)
					g2.drawImage(SS.getImage(), 550, 0, this);
				else if (accuracy < 100 && accuracy >= 98)
					g2.drawImage(S.getImage(), 650, 0, this);
				else if (accuracy < 98 && accuracy >= 94)
					g2.drawImage(A.getImage(), 650, 0, this);
				else if (accuracy < 94 && accuracy >= 90)
					g2.drawImage(B.getImage(), 650, 0, this);
				else if (accuracy < 90 && accuracy >= 85)
					g2.drawImage(C.getImage(), 650, 0, this);
				else if (accuracy < 85)
					g2.drawImage(D.getImage(), 650, 0, this);
			}
		};
		setContentPane(c);

		// name of the button
		b1 = new JButton("Return to Main Menu");

		// adds button to a content pane
		c.setLayout(null);
		c.add(b1);

		// size and location of the button
		b1.setSize(250, 40);
		b1.setLocation(500, 525);

		// adds action listeners
		b1.addActionListener(this);
	}

	// allows user to return to main menu after playing game
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b1) {
			dispose();
		}
	}
}