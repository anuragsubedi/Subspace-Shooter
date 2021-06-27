// Now, to handle and update each of the game objects, instead of doing it in 
// the render() method of main Game.java class, we can use this class to run 
// through a loop, update all the objects that are in the game and finally 
// we can run a single method of this class to tick

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;

public class Handler 
{
	// Create a linked list to store the game objects
	ArrayList<GameObject> object = new ArrayList<GameObject>();
	
	// Input keys initialization
	private boolean up = false, down = false, left = false, right = false;
	
	
	public void tick()
	{
		for(int i=0;i<object.size();i++)
		{
			GameObject tempObject = object.get(i);
			
			tempObject.tick();
		}
	}
	
	public void render(Graphics g)
	{
		for(int i=0;i<object.size();i++)
		{
			GameObject tempObject = object.get(i);
			
			tempObject.render(g);
		}
	}
	
	// Add game object to the linked list
	public void addObject(GameObject tempObject)
	{
		object.add(tempObject);
	}
	
	// Remove game object from the linked list
	public void removeObject(GameObject tempObject)
	{
		object.remove(tempObject);
	}

	
	
	// GETTERS AND SETTERS FOR UP, DOWN, LEFT and RIGHT variables
	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}
		
}
