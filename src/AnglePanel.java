import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


public class AnglePanel extends JPanel{
private int angle= 45;
boolean p1;

public AnglePanel(boolean p1){
	this.p1=p1;
}

	/*
	 * Uses the graphics class to draw the angle boxes when picking shots for Scorched Earth. Trig is used to change the 
	 * angle of the line from 45 degrees to the angle the user has chosen
	 */
	public void paint(Graphics g)
	{
		int length = 100;
		g.clearRect(0,0,100,100);
		double angleR = Math.toRadians(angle);
		g.drawRect(0,0, length,length);
		if(p1)
			g.setColor(Color.blue);
		else{
			g.setColor(Color.red);
		}
		if(p1)
			g.drawLine(0,100, (int) (0+(int)length*Math.cos(angleR)), 100 - (int)(length*Math.sin(angleR)));
		else{
			g.drawLine(100,100, (int) (100-(int)length*Math.cos(angleR)), 100 - (int)(length*Math.sin(angleR)));
		}
		g.drawRect(100,150,50,100);
		
	}
	
	/*
	 * Sets the angle that needs to be displayed- originally the line is set to 45 degrees, but is changed to the number retrieved
	 * from this method
	 */
	public void setAngle(int angle)
	{
		int length = 100;
		double angleR = Math.toRadians(angle);
		this.angle =angle;
	}
	//toggles player in play
	public void changePlayer(){
		p1=!p1;
	}
}
