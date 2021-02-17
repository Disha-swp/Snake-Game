import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE =25;//size of the grid!!!!!!!!
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;   //how many objects we can fit on the screen
	static final int DELAY = 1000;     //delay for our timer
	final int x[] = new int[GAME_UNITS]; //containing the x coordinate of snake's including head
	final int y[] = new int[GAME_UNITS]; //containing the y coordinate of snake's 
	int bodyParts = 6;
	int applesEaten;
	int appleX; //showing the x coordinate of the apple location it will be random
	int appleY; //showing the y coordinate of the apple location
	char direction='R';
	boolean running = false;
	Timer timer;
	Random random;
	GamePanel()
	{
		random=new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
		//System.out.println(":::::hello");
		
	}
	public void startGame()
	{
		newApple();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//System.out.println(":::::hello");
		draw(g);
	}
	public void draw(Graphics g)
	{
		if(running){
			for(int i=0;i<(SCREEN_HEIGHT/UNIT_SIZE);i++)
			{
				//System.out.println("!!!!!");
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
				g.drawLine( 0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
			}
			g.setColor(Color.red);//red
			g.fillOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE);
		
			for(int i = 0;i<bodyParts;i++)
			{
				//System.out.println("!!!!!");
				if(i==0)
				{
					g.setColor(Color.green);
					g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
				}
				else
				{
					//g.setColor(new Color(45,180,0));
					g.setColor(new Color(random.nextInt(225),random.nextInt(225),random.nextInt(225)));
					g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
				}
			}
			g.setColor(Color.yellow);
			g.setFont( new Font("Ink Free",Font.BOLD,40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score : "+applesEaten,(SCREEN_WIDTH - metrics.stringWidth("Score : "+applesEaten))/2,g.getFont().getSize());
			
		}
		else
		{
			gameOver(g);
		}
	}
	public void newApple()
	{
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
		
	}
	public void move()
	{
		for(int i = bodyParts;i>0;i--)
		{
			x[i]=x[i-1];
			y[i]=y[i-1];
		}
		switch(direction)
		{
			case'U':
				y[0] = y[0] - UNIT_SIZE;
				break;
			case'D':
				y[0] = y[0] + UNIT_SIZE;
				break;
			case'L':
				x[0] = x[0] - UNIT_SIZE;
				break;
			case'R':
				x[0] = x[0] + UNIT_SIZE;
				break;
		}
	}
	public void checkApple()
	{
		if((x[0] == appleX) && (y[0] == appleY))
		{
			bodyParts++;
			applesEaten++;
			newApple();
		}
		
	}
	public void checkCollision()
	{
		//this checks if head collides with body
		for( int i=bodyParts;i>0;i--){
			if((x[0] == x[i])&&(y[0] == y[i]))
			{
				running = false;
			}
		}
		//check if head touches left border
		if(x[0]<0)
		{
			running = false;
		}
		//checks if head touches rigth border
		if(x[0]>SCREEN_WIDTH)
		{
			running = false;
		}
		//checks if head touches the top border
		if(y[0]<0)
		{
			running = false;
		}
		//check if head touches bottom border
		if(y[0]>SCREEN_HEIGHT)
		{
			running = false;
		}
		if(!running)
		{
			timer.stop();
		}
	
	}
	public void gameOver(Graphics g)
	{
		//Game over text
		g.setColor(Color.yellow);
		g.setFont( new Font("Ink Free",Font.BOLD,75));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Game Over",(SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2,SCREEN_HEIGHT/2);
		g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD,40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score : "+applesEaten,(SCREEN_WIDTH - metrics1.stringWidth("Score : "+applesEaten))/2,g.getFont().getSize());
			
		
	}
	public void actionPerformed(ActionEvent e)
	{
		if(running){
			move();
			checkApple();
			checkCollision();
		}
		repaint();
		
		
	}
	public class MyKeyAdapter extends KeyAdapter  //interclass
	{
		public void keyPressed(KeyEvent e)
		{
			switch(e.getKeyCode())
			{
				case KeyEvent.VK_LEFT:
					if(direction!= 'R')
					{
						direction = 'L';
					}
					break;
				case KeyEvent.VK_RIGHT:
					if(direction != 'L')
					{
						direction = 'R';
					}
					break;
				case KeyEvent.VK_UP:
					if(direction != 'D')
					{
						direction = 'U';
					}
					break;
				case KeyEvent.VK_DOWN:
					if(direction != 'U')
					{
						direction = 'D';
					}
					break;
			
			}
		}
	}
}
