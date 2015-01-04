import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.SliderUI;


public class ScorchedEarth implements ChangeListener,ActionListener{
	
	
	JFrame gameFrame;
	ScorchedEarthGame game;

	boolean played1=false;
	boolean played2=false;
	boolean p1Turn=true;
	
	JFrame shotBox;
	JPanel scorePanel;
	JPanel gamePanel;
	JPanel sidePanel;
	
	JLabel playerName;
	JLabel shotCount;
	JLabel angleLabel;
	JLabel powerLabel;
	
	JSlider power;
	JButton submit;
	
	Queue <Round>queue;
	
	AnglePanel anglePanel;
	JSpinner shotAngle;	//sets angle of the shot
	
	int timesShot=0;
	int angle=45;	//angle of the shot
	int speed=50; //power of the shot
	
	private int shotNumber=1;
	
	String p="";

/*
 * Constructor: Initialize the queue and initializes the different panels shown in the shot frame. 
 */
	public ScorchedEarth()
	{
		queue = new LinkedList<Round>();
		game =new ScorchedEarthGame();
		JFrame frame = new JFrame();
		scorePanel = new JPanel();
		gamePanel = new JPanel();
		sidePanel = new JPanel();
	
		
		
	}
	
	/*
	 * Sets the layout of the frame in which the user will pick his angle/power values. Initially empty, a slider, spinner, 
	 * two labels, and an angle panel are added to the frame.
	 */
	public void pickShots(Player player)
	{
		Font f1 =new Font("Arial",Font.BOLD,20);
 		if(timesShot<5){
 			p = "Turn: Player 1";
 		}
 		else if(timesShot<10){
 			p="Turn: Player 2";
 		}
 	
 		
		playerName = new JLabel(p);
		playerName.setFont(f1);
		shotCount = new JLabel("Shot #"+shotNumber);
		shotCount.setFont(f1);
		angleLabel = new JLabel("Choose Angle (degrees)");
		SpinnerNumberModel angles = new SpinnerNumberModel(45, 0 , 90, 1);
		shotAngle =  new JSpinner(angles);
		shotAngle.setSize(new Dimension(50,50));
		powerLabel = new JLabel("Choose Power");
		anglePanel = new AnglePanel(p1Turn);
		submit = new JButton("Submit");
		submit.addActionListener(this);
		
		power = new JSlider(JSlider.VERTICAL, 0, 100,50);
		power.addChangeListener(this);
		power.setMajorTickSpacing(10);
		power.setMinorTickSpacing(5);
		power.setPaintLabels(true);
		power.setPaintTicks(true);
		power.setAutoscrolls(true);

		shotBox = new JFrame();
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c =new GridBagConstraints();
		shotBox.setLayout(layout);
	//creating layout
		c.gridy=0;
		c.gridx=1;
		c.ipady=10;
		shotBox.add(shotCount,c);
		c.gridy=1;
		shotBox.add(playerName,c);
		c.ipady=0;
		c.gridx=0;
		c.gridy=2;
		c.insets =new Insets(20,15,20,15);

		shotBox.add(angleLabel,c);
		c.gridx=1;
		shotBox.add(shotAngle,c);
		c.gridx=2;
		c.ipadx=100;
		c.ipady=110;
		shotBox.add(anglePanel,c);
		c.ipadx=0;
		c.ipady=0;
		c.gridy=3;
		c.gridx=0;
		shotBox.add(powerLabel,c);
		c.gridx=1;
		c.ipady=100;
		shotBox.add(power,c);
		c.ipady=0;
		c.gridy=3;
		c.gridx=2;
		shotBox.add(submit,c);

	
		anglePanel.setVisible(true);
		anglePanel.setBackground(Color.YELLOW);

		
		shotAngle.addChangeListener(this);
		

		shotBox.setVisible(true);
		shotBox.setSize(new Dimension(500,440));
		shotBox.setBounds(0, 0, 500, 440);
		shotBox.setResizable(false);
		
	}

	/*
	 * Takes the round that user inputed and adds it to the queue. 
	 */
	public void addRoundToQueue(Round roundToAdd){
		queue.add(roundToAdd);
	}
	
	/*
	 * Runs every time a shot is inputed. Changes the labels on top of the box, showing what shot number the player is on and 
	 * whose turn it is 
	 */
	public void updateInitialFrame(){
		shotCount.setText("Shot #"+shotNumber);
		if(p1Turn)p="Turn: Player 1";
		else{
			p="Turn: Player 2";
		}
		playerName.setText(p);
		shotBox.revalidate();
		shotBox.repaint();
	}
	
	
	


	public void stateChanged(ChangeEvent e) {

		//if the angle spinner is changed, the variable for angle is automatically updated to coincide with it. Initial angle value 
		//is 45 degrees
		if(e.getSource() == shotAngle)
		{
			angle = (int) shotAngle.getValue();
			anglePanel.setAngle(angle);
			anglePanel.repaint();
		}
		
		//if the user changes the slider value, the corresponding variable is updated. Initial slider value is 50
		else if(e.getSource()==power){
			speed=power.getValue();
		}
	}

	@Override
	
	public void actionPerformed(ActionEvent e) {
		
		/*
		 * Every time submit is pressed(a user enters in his shot), a round is created that stores the angle/power values. 
		 * The round is then added to the queue. Every time this is run, the player changes 
		 */
		if(e.getSource()==submit){
			Round aRound = new Round(angle,speed);
			addRoundToQueue(aRound);
		
			power.setValue(50);
			shotAngle.setValue(45);
				if(p1Turn){
					played1=true;
				}
				else{
					played2=true;
				}
				
				p1Turn=!p1Turn;
				anglePanel.changePlayer();

				timesShot++;
				
		/*
		 *if each player submits 5 rounds, The queue is now filled and a game object is created. The queue is passed on to the 
		 *game object, ready to begin the simulation of the game.   
		 */
				if(timesShot>=10){
					shotBox.setVisible(false);
					shotBox.revalidate();
					shotBox.repaint();
					
					game.addQueue(queue);
				}
				
				if(played1==true&&played2==true){
				shotNumber++;
				played1=false;played2=false;
				}
				updateInitialFrame();
			}			

		
	}
	
}
