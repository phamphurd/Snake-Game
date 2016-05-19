package snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class RenderPanel extends JPanel{
	
	public static int curColor = 0;	//Initialize variable curColor, the current color

	@SuppressWarnings("static-access")
	@Override	//Overrides method
	protected void paintComponent(Graphics g) {	//New method
		super.paintComponent(g);				//Method from JPanel that is being overridden
		//==============================//
		//			JFrame color		//
		//==============================//		
		g.setColor(Color.BLACK);		//Sets color of graphics g
		g.fillRect(0, 0, 800, 700);				//Sets g to be a filled rectangle (at x, at y, width, height)
		
		//==============================//
		//			Snake color			//
		//==============================//	
		Snake snake = Snake.snake;				//Create and instantiate snake variable
		g.setColor(Color.BLUE);					//Set snake color
		for(Point point : snake.snakeParts){	//Loop through all the points for each snake part
			g.fillRect(point.x*snake.SCALE, 	//Fills in rectangles where each point is
					point.y*snake.SCALE, 
					snake.SCALE, 
					snake.SCALE);
		}
		g.fillRect(snake.head.x*snake.SCALE, 	//Fills in rectangles where the snake head is
				snake.head.y*snake.SCALE, 
				snake.SCALE, 
				snake.SCALE);
		
		//==============================//
		//			Cherry color		//
		//==============================//	
		g.setColor(Color.RED);
		g.fillRect(snake.cherry.x*snake.SCALE, 	//Fills in rectangles where the cherry is
				snake.cherry.y*snake.SCALE, 
				snake.SCALE, 
				snake.SCALE);
		
		//==============================//
		//		Score, Length, Time		//
		//==============================//			
		String string = "Score: " + snake.score + 	//String for the scoreboard
				", Length: " + snake.tailLength + 
				", Time: " + snake.ticks/25;
		g.setColor(Color.WHITE);				  	//Scoreboard color
		g.drawString(string, snake.jframe.getWidth()/snake.SCALE/2,	//Scoreboard location
				snake.jframe.getHeight()/snake.SCALE-60);
	}
	
}
