import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Wizard extends GameObject 
{
	// Again, create a handler variable to store the currently running handler instance
	Handler handler;
	
	// Create a game instance variable to manipulate the current game object attribute (i.e. bullet and health, etc.)
	Game game;
	
	PlaySound sound = new PlaySound(); 
	
	// Create a buffered image variable for storing the wizard image
	private BufferedImage[] wizard_image = new BufferedImage[3];
	
	
	Animation anim;
	
	
	public Wizard(int x, int y, ID id, Handler handler, Game game, SpriteSheet ss) 
	{
		super(x, y, id, ss);
		this.handler = handler;
		this.game = game;
		
		wizard_image[0] = ss.grabImage(1, 1, 32, 48);
		wizard_image[1] = ss.grabImage(2, 1, 32, 48);
		wizard_image[2] = ss.grabImage(3, 1, 32, 48);
		
		// set up animation object
		anim = new Animation(3, wizard_image);
	}

	@Override
	public void tick() 
	{
		x += velX*1;
		y += velY*1;
		
		
		collision();
		
		
		if(handler.isUp()) velY = -5;
		else if(!handler.isDown()) velY = 0;
		
		if(handler.isDown()) velY = 5;
		else if(!handler.isUp()) velY = 0;
		
		if(handler.isRight()) velX = 5;
		else if(!handler.isLeft()) velX = 0;
		
		if(handler.isLeft()) velX = -5;
		else if(!handler.isRight()) velX = 0;
		
		// Run animation [ animation is drawn later ]
		anim.runAnimation();
		
	}

	// HANDLE COLLISION OF WIZARD AND OTHER OBJECTS
	public void collision()
	{
		for(int i=0; i<handler.object.size();i++)
		{
			 GameObject tempObject = handler.object.get(i);
			 
			 // Handle collision with blocks
			 if(tempObject.getId() == ID.Block)
			 {
				 // The intersects() method is a method of rectangle class.
				 // The intersects() method detects the intersection of the two boundaries.
				 if(getBounds().intersects(tempObject.getBounds()))
				 {
					 x += velX * -1;
					 y += velY * -1;
				 }
				 
			 }
			 
			 // Handle Collision with Crate
			 if(tempObject.getId() == ID.Crate)
			 {
				 if(getBounds().intersects(tempObject.getBounds()))
				 {
					 game.ammo += 15;
					 handler.removeObject(tempObject);
					 sound.playCrate();
				 }
			 }
			 
			 // Handle Collision with enemy
			 if(tempObject.getId() == ID.Enemy)
			 {
				 if(getBounds().intersects(tempObject.getBounds()))
				 {
					 game.hp -= 30;
					 handler.removeObject(tempObject);
					 sound.playEnemy();
				 }
			 }
		}
	}
	
	@Override
	public void render(Graphics g) 
	{
		/*
		g.setColor(Color.YELLOW);
		g.fillRect(x, y, 32, 48);
		*/
		if(velX == 0 && velY == 0)
		{
			g.drawImage(wizard_image[0], x, y, null);
		}
		else
		{
			anim.drawAnimation(g, x, y, 0);
		}
		
	}

	// This methods returns the bounding rectangle of wizard for collision
	@Override
	public Rectangle getBounds() 
	{
		
		// This returns the bounding box of the game object for collision purposes.
		return new Rectangle(x, y, 32, 48);
	}
	
}
