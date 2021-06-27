import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends GameObject 
{
	Handler handler;
	
	PlaySound sound = new PlaySound();
	
	private BufferedImage[] enemy_image = new BufferedImage[3];
	
	Animation anim;
	
	Random r = new Random();
	int choose = 0;
	int hp = 100;
	
	public Enemy(int x, int y, ID id, Handler handler, SpriteSheet ss) 
	{
		super(x, y, id, ss);
		this.handler = handler;
		
		enemy_image[0] = ss.grabImage(4, 1, 32, 32);
		enemy_image[1] = ss.grabImage(5, 1, 32, 32);
		enemy_image[2] = ss.grabImage(6, 1, 32, 32);
		
		anim = new Animation(3, enemy_image);

	}

	@Override
	public void tick() 
	{
		x += velX;
		y += velY;
		
		
		choose = r.nextInt(10);		// Choose random number from 0 to 9. 
		
		for(int i = 0;i < handler.object.size();i++)
		{
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Block)
			{
				if( getBoundsBig().intersects(tempObject.getBounds()))
				{
					x += (velX * 2) * -1;	// Increase velocity and reverse direction 
					x += (velY * 2) * -1;
					velX *= -1;
					velY *= -1;
				} else if(choose == 0)
				{
					int max = 3; int min = -3;
					//velX = r.nextInt((max-min) + min);
					//velY = r.nextInt((max-min) + min);
					velX = (r.nextInt(2*max+1) - max);
					velY = (r.nextInt(2*max+1) - max);
				}
			}
			
			if(tempObject.getId() == ID.Bullet)
			{
				if(getBounds().intersects(tempObject.getBounds()))
				{
					hp -= 50;
					handler.removeObject(tempObject);
				}
			}
		}
		
		if(hp <= 0)
		{
			handler.removeObject(this);
			sound.playCheckmate();
		}
		
		// Run animation [ animation is drawn later ]
		anim.runAnimation();
		
	}

	@Override
	public void render(Graphics g) 
	{
		/*
		g.setColor(Color.CYAN);
		g.fillRect(x, y, 32, 32);
		*/
		
		/*
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.GREEN);
		g2d.draw(getBoundsBig());
		*/ 
	
		/*
		g.drawImage(enemy_image, x, y, null);
		*/
		
		anim.drawAnimation(g, x, y, 0);
		
	}

	@Override
	public Rectangle getBounds() 
	{
		return new Rectangle(x, y, 32, 32);
	}
	
	public Rectangle getBoundsBig() 
	{
		return new Rectangle(x-16, y-16, 64, 64);
	}
	
}
