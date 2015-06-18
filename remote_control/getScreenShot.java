
import java.awt.*;
import java.awt.image.BufferedImage;

public class getScreenShot
{

	// <<field>>
	Robot robot;
	Rectangle screenRect;
	BufferedImage image;

	public getScreenShot(int width, int height) throws Exception
	{
		robot = new Robot();
		screenRect = new Rectangle(0 , 0, width, height);
		
		image = robot.createScreenCapture(screenRect);
	}
}

