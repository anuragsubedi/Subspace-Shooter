import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter
{
	private Handler handler;
	private Camera camera;
	private Game game;
	private SpriteSheet ss;
	public PlaySound sound = new PlaySound();
	
	public MouseInput(Handler handler, Camera camera, Game game, SpriteSheet ss)
	{
		this.handler = handler;
		this.camera = camera;
		this.game = game;
		this.ss = ss;
	}
	
	// Implement the mousePressed method of MouseAdapter
	public void mousePressed(MouseEvent e)
	{
		// (mx,my) is the point where the bullet is gonna head to.
		int toX = (int) (e.getX() + camera.getX());		// mouse X
		int toY = (int) (e.getY() + camera.getY());		// mouse Y
		// Note: We are adding camera here to translate the actual x and y positioning of the mouse
		
		for(int i = 0; i<handler.object.size();i++)
		{
			GameObject tempObject = handler.object.get(i);
			
			// Instantiate the bullet from a position relative to the player
			if(tempObject.getId() == ID.Player && game.ammo >= 1) // also need to check if ammo is present or not. 
			{  
				int fromX = tempObject.getX();
				int fromY = tempObject.getY();
				handler.addObject(new Bullet(fromX, fromY, ID.Bullet, handler, toX, toY, ss));
				sound.playBullet();
				game.ammo--;
			}
		}
	}
}
