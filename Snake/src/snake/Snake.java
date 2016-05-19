package snake;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;


public class Snake implements ActionListener, KeyListener{

	public static Snake snake;													//Initialize snake variable
	
	public JFrame jframe;														//Initialize jframe variable
	
	public RenderPanel renderPanel;												//Initialize renderPanel variable
		
	public Timer timer = new Timer(20, this);									//Initialize timer	
	
	public ArrayList<Point> snakeParts = new ArrayList<Point>();				//Variable for the snake parts
	
	public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, SCALE = 10;	//Variables for directions
	
	public int ticks = 0, direction = DOWN, score, tailLength=5;				//Variable for ticks, direction snake faces, and score
	
	public Point head, cherry;													//Variable for snake head and cherry
	
	public Random random; 														//Variable for random number
	
	public boolean over, paused;												//Variable for if the snake is out of bounds
	
	public Dimension dim;														//Variable for screen dimensions
	
	public Snake() {
		dim = Toolkit.getDefaultToolkit().getScreenSize();						//Gets the size of the screen
		jframe = new JFrame("Snake");											//Instantiate jFrame with title: "Snake"
		jframe.setVisible(true); 												//Makes the jframe window visible
		jframe.setSize(805, 705); 												//Sets the size of the jframe window
		jframe.setResizable(false);												//Prevents resizing of screen
		jframe.setLocation(dim.width/2-jframe.getWidth()/2,						//Sets the location of the jFrame to be center of screen
				dim.height/2-jframe.getHeight()/2);		
		jframe.add(renderPanel = new RenderPanel());							//Adds the renderPanel to be shown on the jframe
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);					//Closing the jframe will exit JFrame
		jframe.addKeyListener(this);											//Adds listener for keys pressed
		startGame();															//Runs startGame method
	}
	
	public void startGame(){
		over = false;															//Reset over to false
		paused = false;
		score = 0;																//Reset score to 0
		tailLength = 5;															//Reset tail length
		direction = DOWN;														//Reset direction
		snakeParts.clear(); 													//Clear previous snake parts
		head = new Point(0,0);													//Starting point for snake head
		random = new Random();													//Instantiate random variable
		cherry = new Point(random.nextInt(jframe.getWidth()/SCALE),				//Random starting point for cherry
				random.nextInt(jframe.getHeight()/SCALE));
		for(int i = 0; i<tailLength; i++){
			snakeParts.add(new Point(head.x,head.y));							//Place the head
		}	
		timer.start(); 															//Starts the timer
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {								//Abstract method that must be inherited from ActionListener
		renderPanel.repaint();													//Will repaint the renderPanel every tick
		ticks++;																//Increment ticks
		
		if(ticks % 2 == 0 && head != null && !over && !paused){					//Checks if half a second, head is not over, exists, not paused
			snakeParts.add(new Point(head.x,head.y));							//New point for the head
			if(direction == UP)													//Checks if direction is UP
				if(head.y-1 > -1 && noTailAt(head.x,head.y-1))					//Check if head is out of bounds
					head = new Point(head.x,head.y - 1);						//Decrement the y
				else
					over = true;												//Set over to true
			if(direction == DOWN)												//Checks if direction is DOWN
				if(head.y+1 < jframe.getHeight()/SCALE-2 && 
						noTailAt(head.x,head.y+1))								//Checks if head is out of bounds
					head = new Point(head.x,head.y + 1);						//Increment the y
				else
					over = true;												//Set over to true
			if(direction == LEFT)												//Checks if direction is LEFT
				if(head.x-1 > -1 && noTailAt(head.x-1,head.y))					//Checks if head is out of bounds
					head = new Point(head.x-1,head.y);							//Decrement the x
				else
					over = true;												//Set over to true
			if(direction == RIGHT)												//Checks if direction is RIGHT
				if(head.x+1 < jframe.getWidth()/SCALE && 
						noTailAt(head.x+1,head.y))						    	//Checks if head is out of bounds
					head = new Point(head.x+1,head.y);							//Increment the x
				else
					over = true;												//Set over to true
			if(snakeParts.size() > tailLength){									//Check if the snake size exceeds the tail length
				snakeParts.remove(0);											//Remove the first snake part (Last part of the snake)
			}
			if(cherry != null){													//Check if cherry exists
				if(head.equals(cherry)){										//Check if the head got the cherry
					score+= 10;													//Increment score
					tailLength++;												//Increment tail length
					cherry.setLocation(random.nextInt(jframe.getWidth()/SCALE),	//Choose next random location for cherry
							random.nextInt(jframe.getHeight()/SCALE-2));		
				}
			}
		}
	}
	
	public static void main(String[] args){
		snake = new Snake();													//Instantiate snake
	}
	
	public boolean noTailAt(int x, int y){
		for(Point point : snakeParts){											//Go through each snake part
			if(point.equals(new Point(x,y))){									//Check if the snake part is the same as the point
				return false;													//Return false value
			}
		}
		return true;															//Return true value by default
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int i = e.getKeyCode();													//Get currently pressed key
		if((i == KeyEvent.VK_A || i == KeyEvent.VK_LEFT) && direction != RIGHT)	//If key pressed is A or Left arrow
			direction = LEFT;													//Change direction
		if((i == KeyEvent.VK_D || i == KeyEvent.VK_RIGHT) && direction != LEFT) //if key pressed is D or Right arrow
			direction = RIGHT;													//Change direction
		if((i == KeyEvent.VK_W || i == KeyEvent.VK_UP)&& direction != DOWN)		//if key pressed is W or Up arrow
			direction = UP;														//Change direction
		if((i == KeyEvent.VK_S || i == KeyEvent.VK_DOWN) && direction != UP)	//if key pressed is S or Down arrow
			direction = DOWN;													//Change direction
		if(i == KeyEvent.VK_SPACE)												//if key pressed is Spacebar
			if(over)															//Check if the game is over
				startGame();													//Start new game through startGame method
			else																
				paused = !paused;												//Toggle paused
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}


}
