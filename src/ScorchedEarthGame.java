import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


public class ScorchedEarthGame extends JPanel implements ActionListener, KeyListener{

	boolean konami=false;
	JButton p1Konami;
	JButton p2Konami;
	int[] cheat = new int[10];

	boolean playSelected=false;
	boolean win=false;
	boolean timeNotTick=true;
	boolean printLabel=true;
	Timer timerBomb= new Timer(1000,this);
	
	Round round;
	boolean hitMade = false;
	boolean isDone=false;
	boolean hitRect1=false;
	boolean hitRect2=false;
	Rectangle rect1;
	Rectangle rect2;
	Rectangle plaueBody;
	Rectangle plaueHead;
	
	Polygon polygonTank1= new Polygon();
	Polygon polygonTank2= new Polygon();
	int[]tank1Arrayx=new int[4];
	int[]tank1Arrayy=new int[4];
	int[]tank2Arrayx=new int[4];
	int[]tank2Arrayy=new int[4];

	
	private boolean player1Shot = false;
	
	JButton play = new JButton("Play");
	double time=0;
	boolean popped=false;
	int tank1x;
	int tank2x;
	
	Timer timer = new Timer(60,this);
	
	int[]array = new int[4];


	double angle=45;
	double power=50;
	int bulletx=0;
	int bullety=0;
	
	int a = -10;

	int distance = 50;
	double vx;
	double vy;
	int x=50;
	int y=50;
	double t=0;
	
	
	int add=0;
	int width =0;
	
	int shift=0;
	int shift1=0;
	
	JLabel bomb1 = new JLabel();
	JLabel bomb2 = new JLabel();
	
	JLabel player1Score = new JLabel("Player 1 Score: "+0);
	JLabel player2Score = new JLabel("Player 2 Score: "+0);
	int p1Score=0;
	int p2Score=0;
	
	JFrame gameFrame;
	JPanel scorePanel;
	Queue <Round>queue;
	
	Graphics g=getGraphics();
	BufferedImage map ;
	BufferedImage tank1;
	BufferedImage tank2;
	BufferedImage bullet;
	BufferedImage bomb;

	
	Random r = new Random();
	private boolean freebee = true;
	
	//constructor- initializes frame and panels and creates images
	public ScorchedEarthGame(){
	
		queue = new LinkedList<Round>();
		gameFrame=new JFrame();
		gameFrame.setVisible(true);
		//gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		gameFrame.setSize(new Dimension(1200,700));
		this.setSize(new Dimension(500,400));
		scorePanel=new JPanel();
		
		this.setBackground(Color.white);
		scorePanel=new JPanel();
		scorePanel.setSize(new Dimension(gameFrame.getWidth(),50));
		scorePanel.setBackground(Color.lightGray);
		gameFrame.add(scorePanel);
		
		
		gameFrame.add(this);	
		gameFrame.addKeyListener(this);
		gameFrame.requestFocusInWindow();
		initializePanel();
		shift = r.nextInt((int) (gameFrame.getWidth()*.30));
		shift1 = r.nextInt((int) (gameFrame.getWidth()*.30));
		
		try{
		map = ImageIO.read(new File("images/map.png"));
		tank1 = ImageIO.read(new File("images/tank1.png"));
		tank2 = ImageIO.read(new File("images/tank2.png"));
		bullet= ImageIO.read(new File("images/bullet.png"));
		bomb= ImageIO.read(new File("images/bomb.png"));
		}
		catch(Exception e){
		}
		
		
	}
	
	/*
	 * Score panel is initialized and acts like a menu bar. Initial condition: Just set as a bar on top of the frame. 
	 * Final condition: scores of both players are added as well as a play button in the middle of the panel
	 */
	public void initializePanel(){
		
		player1Score.setPreferredSize(new Dimension(20,gameFrame.getWidth()/2-50));
		player2Score.setPreferredSize(new Dimension(20,gameFrame.getWidth()/2-50));
		player1Score.setFont(new Font("Arial",Font.BOLD,20));
		player2Score.setFont(new Font("Arial",Font.BOLD,20));	
	
		scorePanel.setLayout(null);
		player1Score.setBounds(gameFrame.getWidth()/5+20,10,400,20);
		player2Score.setBounds(gameFrame.getWidth()/5*3-20+50,10,400,20);
		scorePanel.add(player1Score);
		play.setBounds(gameFrame.getWidth()/2-40,0,100,50);
		play.setSize(new Dimension(100,50));
		scorePanel.add(play);
		scorePanel.add(player2Score);
		
	}
	
	/*
	 * paints the location of the tanks, the background, and the bullet. initial condition: Empty screen with no graphics objects. 
	 * Final condition: tanks and bullet is drawn. Bullet's coordinates are changed 
	 */
	public void paintComponent(Graphics g){
		scorePanel.revalidate();

		scorePanel.repaint();
		tank1x=50+shift;
		tank2x=this.getWidth()-100-shift1;



		int x1;
		int x2;
		
		g.drawImage(map, 0, 0, this.getWidth(), this.getHeight(), null);
		
		//drawing position of tank 1
		g.drawImage(tank1, tank1x, this.getHeight()-50, 50, 50, null);
		x1=50+shift;
		rect1=new Rectangle(tank1x,this.getHeight()-50,50,50);
		plaueBody = new Rectangle(gameFrame.getWidth()/2-90+53, gameFrame.getHeight()/4+208, 70, 500);
		plaueHead = new Rectangle((gameFrame.getWidth()/2-95)+75, gameFrame.getHeight()/4+170, 40, 70);

		//drawing position of tank 2
		g.drawImage(tank2, tank2x, this.getHeight()-50, 50, 50, null);
		x1=50+shift;
		x2=this.getWidth()-100-shift;
		rect2=new Rectangle(tank2x,this.getHeight()-50,50,50);	
		
		if(queue.size()==10){
			play.addActionListener(this);
			if(playSelected){
				System.out.println("play is selected HERER");
					popQueu();

			}
		}
			
			/*
			 * Player 1's shot. Initial condition: No bullet. Final condition: bullet image painted and drawn in coordinates
			 * specified by the physics formulas.  
			 */
				if(player1Shot){
				System.out.println("P1");
				//kinematic formulas for x and y position as a function of time
				y= (int) ((vy*t)+(.5*a*t*t));
				x=(int) ((vx*t));

				if(this.getHeight()-50-y>this.getHeight()){
					isDone=true;
				}
				
				g.drawImage(bullet, tank1x+30+x, this.getHeight()-50-y, 20,10, null);
				
				//checks if the bullet has entered the dimensions of the tank of player 1. If it has, the score is incremented
				if(rect1.contains(tank1x+30+x, this.getHeight()-50-y)&&(t>.4||konami)){ 
					bulletx=tank1x+30+x;
					bullety=this.getHeight()-50-y;
					hitMade=true;
					hitRect1=true;
					p2Score++;
					timer.stop();
					timer.start();

				}
				//checks if the bullet has entered the dimensions of the tank of player 1. If it has, score is incremented 
				if(rect2.contains(tank1x+30+x, this.getHeight()-50-y)&&(t>.4||konami)){
					bulletx=tank1x+30+x;
					bullety=this.getHeight()-50-y;					
					hitMade=true;
					hitRect2=true;
					p1Score++;
					timer.stop();
					timer.start();
				}
				//check if bullet has entered the dimension of the barrier(plaue) in the middle. If it has, the turn expires
				if(plaueHead.contains(tank1x+30+x+50, this.getHeight()-50-y)||plaueBody.contains(tank1x+30+x, this.getHeight()-50-y)){
					isDone=true;
				}
			}
			

				/*
				 * Player 2's shot. Initial condition: No bullet. Final condition: bullet image painted and drawn in coordinates
				 * specified by the physics formulas.  
				 */	
			else if(!player1Shot){
				System.out.println("P2");
				//kinematic formulas for x and y position as a function of time
				y= (int) ((vy*t)+(.5*a*t*t));
				x=(int) ((vx*t));

				if(this.getHeight()-50-y>this.getHeight()){
					isDone=true;
				
				}
				g.drawImage(bullet, tank2x+30-x-25, this.getHeight()-50-y, 20,10, null);
				
				//checks if the bullet has entered the dimensions of the tank of player 1. If it has, score is incremented 
				if(rect1.contains(tank2x+30-x, this.getHeight()-50-y)&&(t>.4||konami)){
						bulletx=tank1x+30-x;
						bullety=this.getHeight()-50-y;						
						hitMade=true;
						hitRect1=true;
						p2Score++;
						System.out.println("p2 increment");
						timer.stop();
						timer.start();
				}
				
				//check if bullet has entered the dimension of the barrier(plaue) in the middle. If it has, the turn expires
				if(rect2.contains(tank2x+30-x, this.getHeight()-50-y)&&(t>.4||konami)){
					bulletx=tank1x+30-x;
					bullety=this.getHeight()-50-y;						
					hitMade=true;
					hitRect2=true;
					p1Score++;
					if (konami && freebee)
					{
						p1Score++;
						freebee =false;
					}
					System.out.println("p1Score");
					timer.stop();
					timer.start();
				}
				
				//check if bullet has entered the dimension of the barrier(plaue) in the middle. If it has, the turn expires
				if(plaueHead.contains(tank2x+30-x-20, this.getHeight()-50-y)||plaueBody.contains(tank2x+30-x, this.getHeight()-50-y)){
					isDone=true;
				}
				
			}
			
				/*
				 * conditions check if tank 1 was hit. If it was, image of bomb is displayed on that tank
				 */
			if(hitRect1){
				g.drawImage(bomb, tank1x-60, this.getHeight()-140, 180, 180, null);
				timerBomb.start();
				g.setFont(new Font("Arial", 24,24));
				g.setColor(Color.red);
				g.drawString("HIT!", tank1x-60, this.getHeight()-190);
			
			}
			/*
			 * conditions check if tank 2 was hit. If it was, image of bomb is displayed on that tank
			 */
			else if(hitRect2){
				g.drawImage(bomb, tank2x-60,this.getHeight()-140,180,180,null);	
				timerBomb.start();
				g.setFont(new Font("Arial", 24,24));
				g.setColor(Color.red);
				g.drawString("HIT!", tank2x-60, this.getHeight()-190);
				
			}
			player1Score.setText("Player 1 Score: "+p1Score);
			player2Score.setText("Player 2 Score: "+p2Score);
		
			if(playSelected){
			if(isDone){
				System.out.println("its done");
				t=0;
				if(konami){
					x=-x;
				}
				konami=false;
				hitMade=false;
				hitRect1=false;
				hitRect2=false;
				isDone=false;
				popQueu();
			}
			}			
		
			/*
			 * If all turns are over: Initial condition: play stops. Final condition: the player with the highest score wins the 
			 * game and the winner's name is displayed at the top of the screen. 
			 */
		if(win){
			System.out.println("atleast it reaches here");
			String winText="";
			if(p1Score>p2Score){
				winText="PLAYER 1 WINS WITH "+ p1Score + " POINTS";
			}
			else if(p1Score<p2Score){
				winText="PLAYER 2 WINS WITH "+ p2Score + " POINTS";
			}
			else{
				winText="THE GAME RESULTED IN A TIE OF "+p1Score +" POINTS";
			}
			
				g.setFont(new Font("Arial",Font.BOLD,40));
				g.drawString(winText, gameFrame.getWidth()/8,gameFrame.getHeight()/4);
		}
	
	}
	
	
	//whenever a hit has been made, add label. 
	public void HitMade(int p, Graphics g){
		g.setFont(new Font("Arial", 24,24));
		g.drawString("HIT WAS MADE!!", 50, 50);
	}

	/*
 	*When the game is first created, the parameter queue is copied to be used by this class.
 	*/
	public void addQueue(Queue roundQueue){
		initializePanel();
		this.queue=roundQueue;
		printQueue();
	}
	
	//check to see if queue has stuff in it. Prints the size of the queue
	public void printQueue(){
		System.out.println("SIZE: "+queue.size());
		this.repaint();
	}

	/*
	 * The queue of Round's first element is taken out and the angle and power are set to their corresponding variables. 
	 *  Initial Condition: queue is same size. Final condition: queue has one less and the angle/power variables are updated.  
	 */
	public void popQueu(){
			System.out.println("play has been selected");
		player1Shot=!player1Shot;
		//removes the round from the queue if it is empty
		if(!queue.isEmpty()){
			round = queue.poll();
			angle=Math.toRadians(round.getAngle());
			power = round.getPower()*1.3;
			
			vx= (power*Math.abs(Math.cos(angle)));
			vy= (power*Math.abs(Math.sin(angle)));
			timer.start();
			gameFrame.invalidate();
			gameFrame.revalidate();
			gameFrame.repaint();
			scorePanel.revalidate();
			scorePanel.repaint();
		}
		//if the queue is empty, the game is over, as all the rounds have already been removed and played. 
		else if(queue.isEmpty()){
			timer.stop();
			win=true;
			System.out.println("game over");
			endGame();
		}
		
	}
	//sets variable of konami true when konami button is clicked and flips the direction of the x coordinates of the shot
	public void player1Konami(){
		if(player1Shot){
			konami=true;
			x=-x;
		}
	}
	//sets variable of konami true when konami button is clicked and flips the direction of the x coordinates of the shot
	public void player2Konami(){
		if(!player1Shot){
			konami=true;
			x=-x;
		}
	}
	//toggles win boolean to show the win statements
	public void endGame(){
		win=true;
		this.repaint();
	}
	/*
	 * if konami code is entered, repel buttons are given to both players. Initial condition: only play button shown in the score
	 * panel. Final condition: repel buttons for both players also shown in the score panel. 
	 */
	public void addKonami(){
		System.out.println("added konami buttons");
		p1Konami=new JButton("Repel");
		p1Konami.setBounds(0, 0, 100, 60);
		p1Konami.setSize(new Dimension(100,60));
		p2Konami = new JButton("Repel");
		p1Konami.setBounds(this.getWidth()-100, 0, 100, 60);
		p2Konami.setSize(new Dimension(100,60));
		p1Konami.addActionListener(this);
		p2Konami.addActionListener(this);
		scorePanel.add(p1Konami);
		scorePanel.add(p2Konami);
		p2Konami.repaint();
		p1Konami.repaint();
		this.repaint();
		scorePanel.repaint();
		gameFrame.repaint();

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		/*
		 * Timer called every 60 milleseconds that increments the time for the kinematic equations. If konami code is called,
		 * time decrements, allowing for the bullet to reverse its trajectory. 
		 */
		if(e.getSource()==timer){
			if(!konami){
				t+=.2;
			}
			else{
				System.out.println("OKOK!!");
				t-=.2;
			}
			this.repaint();
			//isDone=true;
		}
		//tracks the amount of time the bomb picture is displayed. Initial condition: no image is shown. Final condition: bomb image
		//removed after 1 second.
		else if(e.getSource()==timerBomb){
			System.out.println("TIMERLABEL HAS HIT");
			timerBomb.stop();
			hitRect1=false;
			hitRect2=false;
			hitMade=false;
			
		}
		//starts simulation of the game when play is pressed. Initial condition: game is at a standstil. Final condition:
		//simulation of the game begins and the tanks fire at each other.
		else if(e.getSource()==play){
			playSelected=true;
			System.out.println("toggle works");
			this.repaint();
			System.out.println("...........");
		}
		//konami button for player one was pressed. Initial condition: normal gameplay proceeds. Final condition: calls method
		//that changes the values of bullet's coordinates, making the bullet reverse its trajectory. 
		else if(e.getSource()==p1Konami){
			System.out.println("konami1 pressed!");
			player1Konami();
			p1Konami.removeActionListener(this);
		}
		//konami button for player two was pressed. Initial condition: normal gameplay proceeds. Final condition: calls method
		//that changes the values of bullet's coordinates, making the bullet reverse its trajectory.
		else if(e.getSource()==p2Konami){
			System.out.println("konami2 pressed!");
			player2Konami();
			p2Konami.removeActionListener(this);
		}
		

	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	//if the series of keys are typed that correspond to the konami code, the code initializes. Initial condition: game proceeds
	//as normal. Final condition, two repel buttons are given to each player to be able to redirect a bullet
	public void keyPressed(KeyEvent e) {
		System.out.println("pressed");
		int[] konami = new int[10];
		konami[0] = e.VK_UP;
		konami[1] = e.VK_UP;
		konami[2] = e.VK_DOWN;
		konami[3] = e.VK_DOWN;
		konami[4] = e.VK_LEFT;
		konami[5] = e.VK_RIGHT;
		konami[6] = e.VK_LEFT;
		konami[7] = e.VK_RIGHT;
		konami[8] = e.VK_B;
		konami[9] = e.VK_A;
		
		int x = e.getKeyCode();
		
		int size = 0;
		//number of cheat characters entered
		for(int i = 0; i <9; i++)
		{
			if(cheat[i] != 0)
			{
				size++;
			}
		}
		
		if(x == konami[size]){
			cheat[size] = x;
			System.out.println(cheat[size]);
		}
		else{
			cheat = new int[10];
		}
		
		if(Arrays.equals(cheat, konami))
		{
			System.out.println("Komoni Activated");
			addKonami();
			x=-x;
			
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
