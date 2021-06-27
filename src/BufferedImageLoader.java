import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BufferedImageLoader 
{
	private BufferedImage image;
	
	public BufferedImage loadImage(String path)
	{
		try {
			// the path passed to this method should be relative to the res folder.
			image = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return image;
	}
}
