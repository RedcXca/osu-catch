
// import statements
import java.awt.*;

// Circle object used in the games class to generate notes
public class Circle {

	// variables
	private int xPosition;
	private double yPosition;
	private double circleSpeed;
	private boolean hyper;

	// constructor to create a circle object
	public Circle(int X, double interval, double speed, double offset) {
		xPosition = X;
		yPosition = (offset - (interval * speed));
		circleSpeed = speed;
		hyper = false;
	}

	// gets hitbox of circle
	// pre: none
	// post: returns a rectangle object representing the hitbox of the circle
	public Rectangle getHitbox() {
		Rectangle circleHitbox = new Rectangle(xPosition + 70, (int) (yPosition + 120), 10, 10);
		return circleHitbox;
	}

	// gets the X position of the circle
	// pre: none
	// post: returns the X position
	public int getX() {
		return xPosition;
	}

	// gets the Y position of the circle
	// pre: none
	// post: returns the Y position
	public double getY() {
		return yPosition;
	}

	// updates the circle's Y position
	// pre: none
	// post: updates the Y postition based on the circle speed
	public void update() {
		if (yPosition <= 720) {
			yPosition += circleSpeed;
		}
	}

	// updates the circle's hyper status (red note)
	// pre: none
	// post: sets the boolean hyper to true
	public void isHyper() {
		hyper = true;
	}

	// gets the circle's hyper status
	// pre: none
	// post: returns the boolean hyper
	public boolean getHyper() {
		return hyper;
	}
}
