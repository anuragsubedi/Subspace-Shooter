import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter
{
	// Create a handler variable but DO NOT initialize it here. 
	// This is because, if you initialize a new handler object 
	// then a new LinkedList will be created for the Game Objects.
	// But, we need to get the current set of game objects from 
	// the already initialized handler object in the main game class.
	Handler handler;
	
	
	public KeyInput(Handler handler)
	{
		this.handler = handler;
	}
	
	// Implement the keyPressed method of KeyAdapter
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		for(int i=0; i<handler.object.size();i++)
		{
			GameObject tempObject = handler.object.get(i);
			
			
			// If the game object is the Player, then move it based on the user input.
			if(tempObject.getId() == ID.Player)
			{
				if(key == KeyEvent.VK_W) handler.setUp(true);
				if(key == KeyEvent.VK_S) handler.setDown(true);
				if(key == KeyEvent.VK_A) handler.setLeft(true);
				if(key == KeyEvent.VK_D) handler.setRight(true);
			}
		}
	}
	
	// Implement the keyReleased method of KeyAdapter
	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		for(int i=0; i<handler.object.size();i++)
		{
			GameObject tempObject = handler.object.get(i);
			
			
			// If the game object is the Player, then move it based on the user input.
			if(tempObject.getId() == ID.Player)
			{
				if(key == KeyEvent.VK_W) handler.setUp(false);
				if(key == KeyEvent.VK_S) handler.setDown(false);
				if(key == KeyEvent.VK_A) handler.setLeft(false);
				if(key == KeyEvent.VK_D) handler.setRight(false);
			}
		}
	}
}
