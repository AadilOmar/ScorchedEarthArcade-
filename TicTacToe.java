import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Stack;
 
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
 
import javax.swing.border.Border;
 
 
//The Tic Tac Toe Game
 
public class TicTacToe implements MouseListener, KeyListener {
     
	boolean sameSquare=false;
    boolean gameOver;
    JLabel turns;
    JLabel p1Wins;
    JLabel p2Wins;
    JLabel tieLabel;
    int xwins=0;
    int owins=0;
    int ties=0;
     
    JPanel scorePanel;
    JPanel sidePanel;
    JButton restart;
    JButton help;
    JButton exit;
    JButton undo;
     
    JPanel gamePanel;
    int winner=0;
    JFrame frame;
 
int[][]array= new int[3][3];    
int value=0;
Stack <Integer> stack;
boolean player1turn;
 
protected JLabel[] labelArray = new JLabel[9];
//The tiles of the tic tac toe board that can be clicked to make a move
JLabel tile1;
JLabel tile2;
JLabel tile3;
JLabel tile4;
JLabel tile5;
JLabel tile6;
JLabel tile7;
JLabel tile8;
JLabel tile9;
 
 
 
//Create Icons for X and O
ImageIcon iconX = new ImageIcon("images/X.jpg");
ImageIcon iconO = new ImageIcon("images/O.jpg");
ImageIcon iconBlank = new ImageIcon("images/null.jpg");
 
//Scale the O Image
Image scaleImage = iconO.getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT);
ImageIcon O  = new ImageIcon(scaleImage);
 
//Scale the X Image
Image scaleImage1 = iconX.getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT);
ImageIcon X  = new ImageIcon(scaleImage1);
 
//Scale the blank Image
Image scaleImage2 = iconBlank.getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT);
ImageIcon blank  = new ImageIcon(scaleImage2);
 
 
//Creates the layout for the game board
GridLayout layout = new GridLayout(3,3);
 
int moves;
 
/*
 * Contructor: Creates a Tic Tac Toe Game. Initializes Panels, Creates a blank game board
 * and adds Listeners
 * 
 */
public TicTacToe()
{
    scorePanel=new JPanel();
    sidePanel= new JPanel();
    gamePanel=new JPanel();
    initializeScorePanel();
    initializeSidePanel();
    initializeFrame();
    frame.addKeyListener(this);
    frame.requestFocusInWindow();

 
    for(int x=0;x<3;x++){
        for(int y=0; y<3;y++){
            array[x][y]=0;
        }
    }
    int moves= 0;
    player1turn = true;
    stack=new Stack<Integer>();
    //set the layout manager
    gamePanel.setLayout(layout);
    gamePanel.setPreferredSize(new Dimension(650, 650));
    //Create all the blank tiles
    JLabel tile1= new JLabel(blank);
    //tile1.addKeyListener(this);
    JLabel tile2= new JLabel(blank);
    //tile2.addKeyListener(this);
    JLabel tile3= new JLabel(blank);
    //tile3.addKeyListener(this);
    JLabel tile4= new JLabel(blank);
    //tile4.addKeyListener(this);
    JLabel tile5= new JLabel(blank);
    //tile5.addKeyListener(this);
    JLabel tile6= new JLabel(blank);
    //tile6.addKeyListener(this);
    JLabel tile7= new JLabel(blank);
    //tile7.addKeyListener(this);
    JLabel tile8= new JLabel(blank);
    //tile8.addKeyListener(this);
    JLabel tile9= new JLabel(blank);
    //tile9.addKeyListener(this);
    labelArray[0]=tile1;
    labelArray[1]=tile2;
    labelArray[2]=tile3;
    labelArray[3]=tile4;
    labelArray[4]=tile5;
    labelArray[5]=tile6;
    labelArray[6]=tile7;
    labelArray[7]=tile8;
    labelArray[8]=tile9;
 
    //Creates a border for the JLabel tiles
    Border border = BorderFactory.createLineBorder(Color.BLACK, 3);
     
    //add a border to all the tiles
    for(int x=0; x<labelArray.length; x++){
        labelArray[x].setBorder(border);
    }
     
     
     
     
     
    //changes background color to white
    gamePanel.setBackground(Color.WHITE);
     
    //add tiles to the game boards
    for(int x=0; x<labelArray.length; x++){
        gamePanel.add(labelArray[x]);
    }
     
    //add listener to all labels
        for(int x=0; x<labelArray.length; x++){
            labelArray[x].addMouseListener(this);
        }
 
     
}
/*
 * Creates the frame that willl hold all of the Game Panels. Uses a Gridbag layout to organize panels with it
 * Only called by constructor
 */
public void initializeFrame(){
    frame = new JFrame();
    GridBagLayout layout = new GridBagLayout();
    frame.setLayout(layout);
    GridBagConstraints c = new GridBagConstraints();
    c.insets=new Insets(15,15,15,15);
    frame.add(scorePanel,c);
    frame.add(gamePanel,c);
    frame.add(sidePanel,c);
 
    frame.pack();
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    frame.setVisible(true);
}
 
/*
 * Creates a Panel that will keep track of the number of wins for each player,
 * whose turn it is, and the number of ties.
 * Only called by constructor
 */
public void initializeScorePanel(){
    GridBagLayout gLayout = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    scorePanel.setLayout(gLayout);
    c.insets=new Insets(15,15,15,15);
    c.ipady=20;
    String p1 = "Player1";
    String p2 = "Player2";
    scorePanel.setPreferredSize(new Dimension(150,650));
    turns = new JLabel("Turn: " +p1);
    turns.setFont(new Font("Arial",Font.BOLD,16));
    p1Wins = new JLabel("Player1 Wins: "+xwins);
    p1Wins.setFont(new Font("Arial",Font.BOLD,16));
    p2Wins = new JLabel("Player2 Wins: "+owins);
    p2Wins.setFont(new Font("Arial",Font.BOLD,16));
    tieLabel = new JLabel("Ties: "+ties);
    tieLabel.setFont(new Font("Arial",Font.BOLD,16));
 
    c.gridx=1;
    c.gridy=0;
    scorePanel.add(turns,c);
    c.gridy=1;
    scorePanel.add(p1Wins,c);
    c.gridy=2;
    scorePanel.add(p2Wins,c);
    c.gridy=3;
    scorePanel.add(tieLabel,c);
}
 
/*
 * Creates a panel of buttons on the right side that help with useful tasks such as undo,
 * helpful instructions, restarting the game, and closing the program.
 * 
 * Only called by the constructor
 */
public void initializeSidePanel(){
     
    sidePanel.setPreferredSize(new Dimension(150,650));
    restart=  new JButton("Restart");
    help=  new JButton("Help");
    exit=  new JButton("Exit");
    undo=  new JButton("Undo");
    GridLayout layout = new GridLayout(7,1);
    JLabel label = new JLabel("");
 
 
    sidePanel.setLayout(layout);
    sidePanel.add(help);
    help.addMouseListener(this);
    sidePanel.add(label);
    sidePanel.add(undo);
    restart.addMouseListener(this);
    sidePanel.add(new JLabel());
    sidePanel.add(restart);
    exit.addMouseListener(this);
    sidePanel.add(new JLabel());
    sidePanel.add(exit);
    undo.addMouseListener(this);
     
}
 
/*
 * Checks to see if the game is over. This method is called by other methods that check for a win condition and
 * feed in the results of the game in the form of an int. 
 * If x = 1 then Player 1 wins
 * If x = 2 then Player 2 wins
 * If x = 0 then it is a Tie
 * 
 * The method then displays a dialog box to the user informing them of the outcome of the game
 */
public void gameOver(int x){
    String s="";
    if(x==1)s="PLAYER 1 WINS!";             //Checks to see who wins the game based on the int parameter
    else if(x==2)s="PLAYER 2 WINS!";
    else if(x==0)s="CAT WINS!";
    JFrame frame1 = new JFrame();
    JPanel panel1 = new JPanel();
     
    JDialog endScreen = new JDialog(frame1,"Game Over");
    endScreen.setBounds(frame.getWidth()/2, 50, 600, 400);
    endScreen.setPreferredSize(new Dimension(340,100));
    JTextArea textArea = new JTextArea();
    textArea.setLineWrap(false);
    textArea.setText(s);
    textArea.setFont(new Font("Arial",Font.BOLD,40));
    textArea.setSize(new Dimension(frame.getWidth()+20,50));
    panel1.add(textArea);
    endScreen.add(panel1);
    endScreen.setVisible(true);
    endScreen.pack();
     
}
/*
 * This method resets the board so that a new game can be started.
 * It is used when a game is over to start a new one or to restart mid game.
 * 
 * This does NOT reset the win count for the game
 * 
 * Is called by clicking a button in sidePanel
 */
public  void resetGame(){
    frame.addKeyListener(this);
    frame.requestFocusInWindow();
    undo.removeMouseListener(this);
    undo.addMouseListener(this);      
    turns.setText("Turn: "+"Player1");
    for(int x=0; x<3;x++){               //Resets all the board pieces to blanks
        for(int y=0; y<3;y++){
            array[x][y]=0;
        }
    }
    for(int x=0; x<labelArray.length; x++){          //removes the MouseListener from all Panels to insure that none get 2
        labelArray[x].removeMouseListener(this);
    }
    for(int x=0; x<labelArray.length; x++){          //re-ads a listener to each Panel
        labelArray[x].addMouseListener(this);
    }
    for(int i=0; i<9; i++)                           //Resets all the tiles to blank tiles
    {
        labelArray[i].setIcon(blank);
    }
    while(!stack.empty()){   
        stack.pop();
    }
    player1turn=true;
}
 
/*
 * Undoes the last move played. Plays can be undone until back at the starting board.
 * This method can be called by the button in the sidePanel or by the KeyListener using CTR Z
 * 
 * Works by poping the last move in the stack and reseting the piece played on.
 * It adds the MouseListener back so that it can be played again and switches player turn so they
 * do not get ofset
 * 
 * 
 */
public void undo(){
     
    if(!stack.isEmpty() ){                     
     
    Integer a = (Integer)stack.pop();
     
    labelArray[a].setIcon(blank);
     
    //Checks what is popped from the stack to see which peice to reset
    if(a==0){array[0][0]=0;}
    if(a==1){array[0][1]=0;}
    if(a==2){array[0][2]=0;}
    if(a==3){array[1][0]=0;}
    if(a==4){array[1][1]=0;}
    if(a==5){array[1][2]=0;}
    if(a==6){array[2][0]=0;}
    if(a==7){array[2][1]=0;}
    if(a==8){array[2][2]=0;}
    //Sets the Label to the appropriate players turn from the turn prior
    player1turn=!player1turn;
    if(player1turn){
        turns.setText("Turn: "+"Player1");
    }else{
        turns.setText("Turn: "+"Player2");
    }
    }
     
}
 
/*
 * Removes the ActionListeners from all the tiles and the KeyListener from the Frame
 * 
 * Does this in order to prevent the player from using undo after a game ends 
 */
public void removeListener(){
    for(int x=0; x<labelArray.length; x++){          //Removes all MouseListeners
        labelArray[x].removeMouseListener(this);
    }
     
        frame.removeKeyListener(this);
     
}
/*
 * This method checks the status of the board and returns whether or not the game is a draw
 */
public boolean checkTie(){
    boolean tie=true;
    for(int x=0; x<3;x++){           //Runs through all the positions on the board
        for(int y=0;y<3;y++){
            if(array[x][y]==0){     //if any square is not empty... tie is false
                tie=false;
            }
        }
    }
    if(tie==true){                      //If the game is a draw the Game is Over
        gameOver(0);
        undo.removeMouseListener(this);
    }
    return tie;
}
 
/*
 * Checks to see if on of the Players has won the game
 * 
 * The Method checks every possible way tic tac toe can be won for a row of 3
 * It then return who won in the form of an int
 * returns 1 for Player 1
 * returns 2 for Player 2
 *
 *
 */
public int checkWin(){  
    int totalx=0;
    int totalo=0;
    winner=0;
 
    //Checks for Horizontal Wins
    for(int row=0;row<3; row++){         
        for(int col=0; col<3; col++){
            if(array[row][col]==1)totalx++;
            if(array[row][col]==2)totalo++;
        }
        if(totalx==3){
            winner=1;
            winStatement(1);
            removeListener();
             
            break;
        }
        else if(totalo==3){
            winner=2;
            winStatement(2);
            removeListener();
            break;
        }
        else{totalx=totalo=0;}
    }
    totalx=0;
    totalo=0;
    //Checks for Vertical wins
    for(int row=0;row<3; row++){
        for(int col=0; col<3; col++){
            if(array[col][row]==1)totalx++;
            if(array[col][row]==2)totalo++;
        }
        if(totalx==3){
            winner=1;
            winStatement(1);
            removeListener();
            break;
        }
        else if(totalo==3){
            winner=2;
            winStatement(2);
            removeListener();
            break;
        }
        else{totalx=totalo=0;}
    }
    //for diagonals wins
    if((array[0][0]==1&&array[1][1]==1&&array[2][2]==1)||(array[0][2]==1&&array[1][1]==1&&array[2][0]==1)){
        winner=1;
        winStatement(1);
        removeListener();
    }
    if((array[0][0]==2&&array[1][1]==2&&array[2][2]==2)||(array[0][2]==2&&array[1][1]==2&&array[2][0]==2)){
        winner=2;
        winStatement(2);
        removeListener();
    }
     
    return winner;
     
}
/*
 * Alerts the player to who won the game by adding the appropriate text to a Label
 * which will later be added to Panel
 * 
 */
public void winStatement(int x){              
    if(x==1){                                   //If x won set the text appropriately
         
        xwins++;
        p1Wins.setText("Player1 Wins: "+xwins+"");
        undo.removeMouseListener(this);
        gameOver(1);
 
    }
    else if(x==2){                              //If O won set the text appropriately
         
        owins++;
        p2Wins.setText("Player2 Wins: "+owins+"");
        undo.removeMouseListener(this);
        gameOver(2);
    }
}
 
/*
 * This method lets the user know if the try to select a tile that is not available to them because it has 
 * already been played.
 * 
 * It uses a JDialog to share the message
 */
public void showError(){
     
    JFrame frame1 = new JFrame();
    JPanel panel1 = new JPanel();
     
    JDialog errorScreen = new JDialog(frame1,"Error");
    errorScreen.setBounds(frame.getWidth()/2-140, 50, 600, 400);
    JTextArea textArea = new JTextArea();
    textArea.setLineWrap(true);
    textArea.setText("Error: Chosen spot must be empty!");
    textArea.setFont(new Font("Arial",Font.PLAIN,14));
    textArea.setPreferredSize(new Dimension(240,20));
     
    panel1.add(textArea);
    errorScreen.add(panel1);
     
    errorScreen.setVisible(true);
    errorScreen.pack();
}
/*
 * Returns the array of Labels which creates the grid for the game
 */
public JLabel[] getLabelArray()
{
    return labelArray;
}
 
/*
 * replaces the LabelArray with the given parameter x
 */
public void setLabelArray(JLabel[] x)
{
    this.labelArray = x;
}
 
//Unused Method Required by interface
public void mouseClicked(MouseEvent e) {
    // TODO Auto-generated method stub
     
}
 
/*
 * Listener for the Mouse that does varied actions depending on the source
 * 
 */
public void mousePressed(MouseEvent e) {
     
     
    moves++;
     
    for(int i = 0; i < labelArray.length; i++)           //Cycle through all of the Tile Labels
    {
        if(e.getSource() == labelArray[i])              //See if the source was on the the Tile Labels
        {
             
             
            if(labelArray[i].getIcon()!=blank){         //if picked a spot where there is a tile already... show error
                showError();                            //shows error, breaks out of everything, and accounts for player change                 
                sameSquare=true;
                break;
            }
            value=i;
            if(player1turn)
            {
            turns.setText("Turn: "+"Player2");
            labelArray[i].setIcon(X);               //Set the appropriate Tile with an X
            if(i==0){array[0][0]=1;}
            if(i==1){array[0][1]=1;}
            if(i==2){array[0][2]=1;}
            if(i==3){array[1][0]=1;}
            if(i==4){array[1][1]=1;}
            if(i==5){array[1][2]=1;}
            if(i==6){array[2][0]=1;}
            if(i==7){array[2][1]=1;}
            if(i==8){array[2][2]=1;}
            if(checkWin()==0){                              //Check for a Win
                if(checkTie()==true){                       //Check for a Tie
                    ties++;
                    tieLabel.setText("Ties: "+ties+"");
                }
            }
             
            }
            else{                                       //Otherwise next Players turn
                turns.setText("Turn: "+"Player1");
                labelArray[i].setIcon(O);               //Set appropriate Tile with )
                if(i==0){array[0][0]=2;}
                if(i==1){array[0][1]=2;}
                if(i==2){array[0][2]=2;}
                if(i==3){array[1][0]=2;}
                if(i==4){array[1][1]=2;}
                if(i==5){array[1][2]=2;}
                if(i==6){array[2][0]=2;}
                if(i==7){array[2][1]=2;}
                if(i==8){array[2][2]=2;}
                if(checkWin()==0){                          //Check for a Win
                    if(checkTie()==true){                   //Check for a Tie
                        ties++;
                        tieLabel.setText("Ties: "+ties+"");
                    }
                }
 
            }
            player1turn = !player1turn;    //toggle player turn
        }
    }
     
     
     
             
     
        if(e.getSource()!=undo){                //If Undo button was not the source
        	if(!sameSquare){
        		stack.push(new Integer(value));     //Add the Tile to the Stack
        	}
        	else{
        		sameSquare=false;
        	}
        }
        if(e.getSource()==help){                //If Help was the source display this text
            String c="Player 1 starts the game by placing an X on any of the 9 squares. Player two goes next and " +
                    "places his O in any available square. This continues until one player gets three of their " +
                    "pieces in a row or all the tiles are full.";
            JFrame frame1 = new JFrame();
            JPanel panel1 = new JPanel();
             
            JDialog helpScreen = new JDialog(frame1,"Help");
            helpScreen.setBounds(300, 300, 400, 100);
            JTextArea textArea = new JTextArea();
            textArea.setPreferredSize(new Dimension(400,100 ));
            textArea.setLineWrap(true);
            textArea.setText(c);
 
            panel1.add(textArea);
            helpScreen.add(panel1);
             
            helpScreen.setVisible(true);
            helpScreen.pack();
 
             
        }
        else if(e.getSource()==undo){               //If undo was the souce call the undo method
             
                 
                undo(); 
 
             
        }
        else if(e.getSource()==restart){            //if restart was the source call the reset method
            resetGame();
             
        }
        else if(e.getSource()==exit){               //IF exit was the source end the game
            frame.setVisible(false);
        }
        frame.requestFocusInWindow();
}
 
 
//Unused Method Required by interface
public void mouseReleased(MouseEvent e) {
    // TODO Auto-generated method stub
     
}
 
//Unused Method Required by interface
public void mouseEntered(MouseEvent e) {
    // TODO Auto-generated method stub
     
}
 
//Unused Method Required by interface
public void mouseExited(MouseEvent e) {
    // TODO Auto-generated method stub
     
}
//Unused Method Required by interface
public void keyTyped(KeyEvent e) {
     
     
}
//Listens for CTR Z and undoes the last move if called
public void keyPressed(KeyEvent e) {
     
 
     
    int x = e.getKeyCode();
    if(x==KeyEvent.VK_Z && e.isControlDown()){      //If Z was pressed while control was down
         
        undo();
    }
}
//Unused Method Required by interface
public void keyReleased(KeyEvent e) {
     
     
}
 
 
}