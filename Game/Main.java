/* Authors: Max Lu (RedcXca) and Michael Kim (infinity21)
 * Last Updated: June 23 2021
 * 
 * The program simulates games of osu! catch where the player moves a catcher to catch cirlces falling from the top of the screen
 * An instructions page can be found on the menu to learn the controls of the game
 * After each game (or map), an end screen displays to show your results
 */

// import statements
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Main class where the menu and instructions can be seen
public class Main extends JFrame implements ActionListener {

	// variables used for generating the menu
	private int WINDOWWIDTH = 1280;
	private int WINDOWHEIGHT = 720;
	private JButton b1, b2, b3, b4, b5, b6;

	ImageIcon menu = new ImageIcon("Menu.png");
	ImageIcon instructions = new ImageIcon("Icon.png");
	ImageIcon icon = new ImageIcon("Icon.png");

	// constructor to create a closable window (if this one closes all windows
	// close)
	public Main() {
		super("osu! catch");
		setSize(WINDOWWIDTH, WINDOWHEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setButtons();
		setVisible(true);
		setIconImage(icon.getImage());
	}

	// starts the main method which creates a menu
	public static void main(String[] args) {
		new Main();
	}

	// adds clickable buttons to the menu screen
	// pre: ActionListener needs to be implemented
	// post: prints buttons to the screen
	public void setButtons() {
		JPanel c = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(menu.getImage(), 0, 0, this);
			}
		};
		setContentPane(c);

		// name of the buttons
		b1 = new JButton("Easy - Zen Zen Zense");
		b2 = new JButton("Normal - Yoake to Hotaru");
		b3 = new JButton("Hard - Asu no Yozora Shoukaihan");
		b4 = new JButton("Challenge - Hibana");
		b5 = new JButton("Instructions");
		b6 = new JButton("Exit Game");

		// adds buttons to a content pane
		c.setLayout(null);
		c.add(b1);
		c.add(b2);
		c.add(b3);
		c.add(b4);
		c.add(b5);
		c.add(b6);

		// size and location of the buttons
		b1.setSize(250, 40);
		b1.setLocation(WINDOWWIDTH / 2 - 125, 250);
		b2.setSize(250, 40);
		b2.setLocation(WINDOWWIDTH / 2 - 125, 300);
		b3.setSize(250, 40);
		b3.setLocation(WINDOWWIDTH / 2 - 125, 350);
		b4.setSize(250, 40);
		b4.setLocation(WINDOWWIDTH / 2 - 125, 400);
		b5.setSize(250, 40);
		b5.setLocation(WINDOWWIDTH / 2 - 125, 500);
		b6.setSize(250, 40);
		b6.setLocation(WINDOWWIDTH / 2 - 125, 550);

		// adds action listeners
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		b6.addActionListener(this);
	}

	// adds clickable buttons to the menu screen
	// pre: ActionListener needs to be implemented
	// post: prints buttons to the screen
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b1) {
			new Game("Zen Zen Zense");
		} else if (e.getSource() == b2) {
			new Game("Yoake to Hotaru");
		} else if (e.getSource() == b3) {
			new Game("Asu no Yozora Shoukaihan");
		} else if (e.getSource() == b4) {
			new Game("Hibana");
		} else if (e.getSource() == b5) {
			new Instructions();
		} else if (e.getSource() == b6) {
			dispose();
		}
	}
}