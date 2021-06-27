import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;

// This is the main class where the game loop is gonna be present.
public class Game extends Canvas implements Runnable
{
	private static final long serialVersionUID = 1L;

	private boolean isRunning = false;
	
	private Thread thread;
	
	private Handler handler;
	
	private Window window = null;
	
	private BufferedImage level = null;
	private BufferedImage sprite_sheet = null;
	// Handle the floor image rendering in the main class because the floor doesn't belong to any object.
	private BufferedImage floor = null;		
	
	// Game over screen image
	private BufferedImage gameOver = null;
	
	
	private SpriteSheet ss;
	
	private Camera camera;
	
	public int ammo = 100;
	
	public int hp = 100;
	
	public boolean playerDead = false;
	
	
	// INITIALIZE AUDIO CLIPS variables
	private PlaySound sound = null;
	
	private AudioSystem deathSound = null;
	
	File deathSoundFile = null; 
	
	File backgroundSoundFile = null;
		
	AudioInputStream audioIn = null; 
		
	AudioFormat format = null;
	
	Clip audioClip = null;
	
	
	public Game()
	{
		// Open an audio input stream.
		File soundFile = new File("res/gold_dust.wav");
		try {
			audioIn = AudioSystem.getAudioInputStream(soundFile);
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}
		
		window = new Window(1000, 563, "S u b s p a c e    S h o o t e r ", this, audioIn);
		start();
		
		
		handler = new Handler();
		
		camera = new Camera(0, 0);
		
		//LOADING THE IMAGES 
		
		//A. directly
		try {
		    level = ImageIO.read(new File("res/wizard_level.png"));
		    sprite_sheet = ImageIO.read(new File("res/sprite_sheet.png"));
		    gameOver = ImageIO.read(new File("res/game_over.png"));
		} catch (IOException e) {
		}
		
		//B. Or you can also load the images using the custom defined class 
		/*
		BufferedImageLoader  loader = new BufferedImageLoader();
		level = loader.loadImage("/wizard_level.png");
		sprite_sheet = loader.loadImage("/sprite_sheet.png");
		gameOver = loader.loadImage("/game_over.png"); */
		
		ss = new SpriteSheet(sprite_sheet);
		
		floor = ss.grabImage(4, 2, 32, 32);
		
		
		this.addKeyListener(new KeyInput(handler));
		
		this.addMouseListener(new MouseInput(handler, camera, this, ss));
		
		
		loadLevel(level);
		
		sound = new PlaySound();
		
		/*
		// Create a new Wizard manually
		handler.addObject(new Wizard(100, 85, ID.Player, handler));
		
		// Create a new Block manually
		handler.addObject(new Block(200, 100, ID.Block)); 
		*/
		
		/*
		// AUDIO SHIT
		
				// 1. Create an AudioInputStream for the given file
				try {
					deathSoundFile = new File("/Users/anuragsubedi/Desktop/Java/JavaLab/Programs/Wizard Shooter/res/death_sound.wav");
					audioStream = AudioSystem.getAudioInputStream(deathSoundFile);
				} 
				catch(IOException e) { System.out.println(e.getMessage()); }
				catch(UnsupportedAudioFileException e) { System.out.println(e.getMessage()); }
				
				// 2. Acquire audio format and create a DataLine.Infoobject:
				format = audioStream.getFormat();
				DataLine.Info info = new DataLine.Info(Clip.class, format);
				
				// 3. Obtain the Clip:
				try {
					audioClip = (Clip) AudioSystem.getLine(info);
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				}
				
				// 4. Open the AudioInputStream and start playing:
				// 5. Close and release resources
				
		// AUDIO SHIT
		*/
		
	}
	
	// This method starts the thread
	private void start()
	{
		isRunning = true;
		thread = new Thread(this); // Call this class's run method  
		thread.start();
	}
	
	// This method stops the thread
	private void stop()
	{
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	// Our game loop is gonna be in this run method
	// This run method is our thread
	@Override
	public void run() 
	{
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(isRunning)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1)
			{
				tick();
				//updates++;
				delta--;
			}
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;
				frames = 0;
				//updates = 0;
			}
		}
		stop();	
	}
	
	// Update everything in the game
	// This tick method is the FPS of the game and is called 60 times per second
	public void tick()
	{
		// Make the camera move every frame based on the player's position
		for(int i = 0; i<handler.object.size();i++)
		{
			// If object is PLAYER, then put the parameters into the camera
			if(handler.object.get(i).getId() == ID.Player)
			{
				GameObject tempObject = handler.object.get(i);
				camera.tick(handler.object.get(i));
				
				// Delete player if zero health
				if(hp<=0)
				{
					playerDead = true;
					handler.removeObject(tempObject);
					
					window.clip.stop();
					sound.playOof();
					sound.playSad();
					
				}
				
			}
		}
		handler.tick();
	}
	
	// Renders everything in the game
	// This render method is called about ~2000 times per second
	public void render()
	{
		// This BufferStrategy is essentially pre-loading frames behind the actual displayed window to improve performance
		// So, when 1 frame is shown, there are 2 more frames in the queue waiting to be shown 
		
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null)
		{
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D)g;
		////////////////////////////////////////
		// This is where we draw the actual game
		
		// Do not put this background render shit in the translate block or it bugs
		
		/*
		g.setColor(Color.RED);  		// background
		g.fillRect(0, 0, 1000, 563);	// background
		*/
		// Do not put this background render shit in the translate block or it bugs
		
		
		// Everything here is being translated based on g2d
		g2d.translate(-camera.getX(), -camera.getY());
		
		for(int xx=0;xx<30*72;xx+=32)
		{
			for(int yy=0;yy<30*72;yy+=32)
			{
				g.drawImage(floor, xx, yy, null);
			}
		}
		
		// Always put the object renderer after the background render shit
		handler.render(g); // this renders the game object 'Block'
		
		g2d.translate(camera.getX(), camera.getY());
		// Everything here is being translated based on g2d
		
		
		// Rendering Heads Up Display
		g.setColor(Color.white);
		g.drawString("Health bar", 50, 18);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRoundRect(10, 25, 200, 20, 20, 20);
		g.setColor(Color.ORANGE);
		g.fillRoundRect(10, 25, hp*2, 20, 20, 20);
		g.setColor(Color.black);
		g.drawRoundRect(10, 25, 200, 20, 20, 20);
		
		g.setColor(Color.white);
		g.drawString("Ammo :", 50, 65);
		String ammoS = Integer.toString(ammo);
		g.drawString(ammoS, 100, 65);
		
		// Show a text box with a message if player dies
		if(playerDead)
		{

			/*
			// Play death audio
			try {
				audioClip.open(audioStream);
			} catch (LineUnavailableException | IOException e) {
				e.printStackTrace();
			}
			audioClip.start();
			
			// Close audio streams
			audioClip.close();
			try {
				audioStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			*/
						
			// Set Death screen
			g.setColor(Color.white);
			g.drawRect(300, 300, 400, 200);
			g2d.drawImage(gameOver, 300, 200, 400, 400, null);
			//g.drawImage(gameOver, 300, 200, Color.yellow, null );
		}
		
		///////////////////////////////////////
		g.dispose();
		bs.show();
	}
	
	// METHOD THAT LOADS THE LEVEL
	private void loadLevel(BufferedImage image)
	{
		int w = image.getWidth();
		int h = image.getHeight();
		
		for(int xx = 0; xx<w; xx++)
		{
			for(int yy = 0; yy<h; yy++)
			{
				int pixel = image.getRGB(xx, yy);
				int red = (pixel >> 16 ) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				
				if(red == 255)
				{
					handler.addObject(new Block(xx*32, yy*32, ID.Block, ss));
				}
				if(blue == 255 && green ==0)
				{
					handler.addObject(new Wizard(xx*32, yy*32, ID.Player, handler, this, ss));
				}
				if(green == 255 && blue ==0)
				{
					handler.addObject(new Enemy(xx*32, yy*32, ID.Enemy, handler, ss));
				}
				if(green ==255 && blue == 255)
				{
					handler.addObject(new Crate(xx*32, yy*32, ID.Crate, ss));
				}
			}
		}
	}
	
	
	public static void main(String[] args)
	{
		new Game();
		
	}
} 
