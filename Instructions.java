
// import statements
import javax.swing.*;
import java.awt.*;

// Instruction class used in the Main class to demonstrate controls
public class Instructions extends JFrame {

	// variables used for generating the menu
	private int WINDOWWIDTH = 1280;
	private int WINDOWHEIGHT = 720;

	ImageIcon instructions = new ImageIcon("Instructions.png");
	ImageIcon icon = new ImageIcon("Icon.png");

	// constructor to create a closable window
	public Instructions() {
		super("Instructions");
		setSize(WINDOWWIDTH, WINDOWHEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setVisible(true);
		setIconImage(icon.getImage());
	}

	// paints to the screen
	// pre: none
	// post: paints instructions to the window
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(instructions.getImage(), 0, 0, this);
		repaint();
	}
}