import java.awt.image.BufferedImage;

public class SpriteSheet 
{
	private BufferedImage image;
	
	public SpriteSheet(BufferedImage image)
	{
		this.image = image;
	}
	
	// THis is the method that grabs the image for the game object from the spritesheet
	public BufferedImage grabImage(int col, int row, int width, int height)
	{
		 
		return image.getSubimage((col*32)-32, (row*32)-32, width, height);
		// NOTE: The parameters passed while returning the image depends on the actual dimensions of the spritesheet
		// Since we are using a spritesheet with 32*32 individual images.
	}
}
