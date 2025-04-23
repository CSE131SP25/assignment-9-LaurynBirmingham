package assignment9;

import java.util.LinkedList;

public class Snake {

	private static final double SEGMENT_SIZE = 0.02;
	private static final double MOVEMENT_SIZE = SEGMENT_SIZE * 1.5;
	private LinkedList<BodySegment> segments;
	private double deltaX;
	private double deltaY;
	private boolean growing;
	
	public Snake() {
		segments = new LinkedList<>();
		deltaX = 0;
		deltaY = 0;
		BodySegment Head = new BodySegment(0.5, 0.5, SEGMENT_SIZE); 
		segments.add(Head);
		growing = false;
	}
	
	public void changeDirection(int direction) {
		if(direction == 1) { //up
			deltaY = MOVEMENT_SIZE;
			deltaX = 0;
		} else if (direction == 2) { //down
			deltaY = -MOVEMENT_SIZE;
			deltaX = 0;
		} else if (direction == 3) { //left
			deltaY = 0;
			deltaX = -MOVEMENT_SIZE;
		} else if (direction == 4) { //right
			deltaY = 0;
			deltaX = MOVEMENT_SIZE;
		}
		
	}
	
	/**
	 * Moves the snake by updating the position of each of the segments
	 * based on the current direction of travel
	 */
	public void move() {
		for (int i = segments.size() - 1; i > 0; i--) {
			BodySegment previous = segments.get(i - 1);
			segments.set(i, new BodySegment(previous.getX(), previous.getY(), SEGMENT_SIZE));
		}
		BodySegment head = segments.getFirst();
		double newX = head.getX() + deltaX;
		double newY = head.getY() + deltaY;
		segments.addFirst( new BodySegment(newX, newY, SEGMENT_SIZE));
		
		if (!growing) {
			segments.removeLast(); 
		} else {
			growing = false;
		}
	}
	
	/**
	 * Draws the snake by drawing each segment
	 */
	public void draw() {
		for (BodySegment segment : segments) {
			segment.draw();
	}
	}
	
	/**
	 * The snake attempts to eat the given food, growing if it does so successfully
	 * @param f the food to be eaten
	 * @return true if the snake successfully ate the food
	 */
	public boolean eatFood(Food f) {
		if (segments.isEmpty()) return false;
		BodySegment head = segments.getFirst();
		double dx = head.getX() - f.getX();
		double dy = head.getY() - f.getY();
		double distance = Math.sqrt(dx * dx + dy * dy);
		
		if(distance< SEGMENT_SIZE + Food.FOOD_SIZE) {
			growing = true;
			return true;
		}
		return false;
	}
	
	/**
	 * Returns true if the head of the snake is in bounds
	 * @return whether or not the head is in the bounds of the window
	 */
	public boolean isInbounds() {
		 if (segments.isEmpty()) return false;

		    BodySegment head = segments.getFirst();
		    double x = head.getX();
		    double y = head.getY();
		    double buffer = SEGMENT_SIZE;
		    return x >= buffer && x >= 0.0 && x <= 1.0 && y >= 0.0 && y <= 1.0;
	}
}
