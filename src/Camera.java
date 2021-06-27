
public class Camera 
{
	private float x, y;
	
	public Camera(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void tick(GameObject object)
	{
		//x = object.getX() + 1000/2;  // You can also do this
		//y = object.getY() + 563/2;
		
		x += ((object.getX() - x) - 1000/2) * 0.05f;
		y += ((object.getY() - y) - 563/2) * 0.05f;
		
		
		// Do not let the CAMERA move outside the specified boundary
		if(x<=0) x = 0;
		if(x>=1032) x = 1032; 
		if(y<=0) y = 0;
		if(y>=563+48) y = 563+48; 
 

	}

	public float getX() {
		return x;
	}

	public void setX(float x) { 
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
}
