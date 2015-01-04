import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MainFrame implements ActionListener {

	JFrame initialFrame;
	JPanel mainPanel;
	JButton tictactoe = new JButton("Play Tic-Tac-Toe");
	JButton scorchedEarth = new JButton("Play Scorched Earth");
	JButton help =new JButton("Help");
	JButton highScores = new JButton("High Scores");
	JButton exit = new JButton("Exit");
	JPanel arcadePanel = new JPanel();
	
	JLabel arcadeLabel = new JLabel("P4 ARCADE");
	
	
	//constructor- creates main frame and initializes it with panels
	public MainFrame(){
		arcadePanel.setBackground(Color.blue);
		arcadePanel.add(arcadeLabel);
		arcadeLabel.setFont(new Font("Arial",Font.BOLD,40));
		arcadeLabel.setForeground(Color.red);
		tictactoe.addActionListener(this);
		scorchedEarth.addActionListener(this);
		help.addActionListener(this);
		highScores.addActionListener(this);
		exit.addActionListener(this);
		initialFrame = new JFrame();
		initialFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initialFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		initialFrame.setLayout(null);
		mainPanel= new JPanel();
		mainPanel.setBackground(Color.BLUE); 	
		
	}
	/*
	 * Creates the layout for the home-screen- adds the four buttons. 
	 */
	public void createMainFrame(){
		initialFrame.setVisible(true);
		GridBagLayout layout=new GridBagLayout();
		mainPanel.setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		GridBagConstraints c1 = new GridBagConstraints();

		c.insets= new Insets(5,5,5,5);
		c.gridx=0;
		c.gridy=0;
		mainPanel.add(tictactoe,c);
		c.gridx=1;
		mainPanel.add(scorchedEarth,c);
		c.gridy=1;
		c.gridx=0;
		mainPanel.add(help,c);
		c.gridx=1;
		mainPanel.add(exit,c);
		initialFrame.setLayout(layout);
		c1.gridx=0;
		c1.gridy=0;
		arcadePanel.setPreferredSize(new Dimension(500,50));
		initialFrame.add(arcadePanel,c1);
		c1.gridy=1;
		mainPanel.setPreferredSize(new Dimension(500,300));
		initialFrame.add(mainPanel,c1);
		
		mainPanel.setBounds(initialFrame.getWidth()/2-200, initialFrame.getHeight()/2-200, 400,400); //200s are 400/2

		
	}
	
	/*
	 * if the help button is clicked, a window pops up that explains to the user how each of the games are played. 
	 */
	public void openHelp(){
		String c="TicTacToe"+'\n'+"Player 1 starts the game by placing an X on any of the 9 squares. Player two goes next and " +
				"places his O in any available square.This continues until one player gets three of their " +
				"pieces in a row or all the tiles are full."+'\n'+"Scorched Earth"+'\n'+"The goal of the game is to shoot the enemy "
						+ "tank with a specified angle and velocity. Players have5 turns to shoot the enemy tank. Once each player has inputted"
						+ "his or her 5 shots, they can press the play button to start the shooting. Every time a player's tank is hit, "
						+ "the opponent gets points, depending on how clean the shot was hit. The player with the highest points at the end of the 5 rounds wins!"+
						"includes konami code-type before pressing play (up,up,down,down,left,right,left,right,B,A) to unlock special repel move when bullet is fired";
		JFrame frame1 = new JFrame();
		JPanel panel1 = new JPanel();
		
		JDialog helpScreen = new JDialog(frame1,"Help");
		helpScreen.setBounds(300, 300, 300, 100);
		JTextArea textArea = new JTextArea();
		textArea.setPreferredSize(new Dimension(500,180));
		textArea.setLineWrap(true);
		textArea.setText(c);
		
		panel1.add(textArea);
		helpScreen.add(panel1);
		
		helpScreen.setVisible(true);
		helpScreen.pack();

	}
	
	@Override
	/*
	 * Depending on the button clicked a different action will be performed 
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==tictactoe){
			TicTacToe t = new TicTacToe();
		}
		/*
		 * if the scorched earth button is clicked, an object of Scorched earth is created and 
		 * players are created to pick their shots
		 */
		else if(e.getSource()==scorchedEarth){
			ScorchedEarth x = new ScorchedEarth();
			Player player1 = new Player();
			x.pickShots(player1);
			
		}
		//if help button is clicked, help screen is shown
		else if(e.getSource()==help){
			openHelp();
		}
		//if exit button is clisked, game exits
		else if(e.getSource()==exit){
			System.exit(0);
		}
		
	}
	
}
