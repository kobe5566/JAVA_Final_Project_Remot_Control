import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;


public class DesktopInfo {
	static int SCREEN_WIDTH,SCREEN_HEIGHT;
	static Rectangle DEFAULT_RECT,HALF_RECT,QUARTER_RECT;
	
	DesktopInfo(){
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		SCREEN_HEIGHT = d.height;
		SCREEN_WIDTH = d.width;
		DEFAULT_RECT = new Rectangle(0,0,d.width,d.height);
		HALF_RECT =  new Rectangle(0,0,d.width/2,d.height);
		QUARTER_RECT =  new Rectangle(0,0,d.width/2,d.height/2);
	}
}
