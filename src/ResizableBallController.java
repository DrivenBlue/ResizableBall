/*
* Name: David Wang
* Login: cs8bagf
* Date: 2/22/12 
* File: ResizableBallController.java
* Sources of Help: google, TA 
*
* This program creates a horizontal and vertical line which intersect. 
The user is able to drag around the lines upon clicking and dragging. 
Each actionevent has a method that defines the users 
actions(click,drag etc). This also creates 3 buttons in start,slow,clear.
Also creates a slider used to control the resizing speeds of the balls.  
*/

import objectdraw.*; 
import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;  
import javax.swing.event.*;

/*
* Name: ResizableBallController extends WindowController
implements ActionListener,ChangeListener,MouseListener,
MouseMotionListener.
* Purpose: This class contains all of the methods that enable
this program to work. This class extends WindowController which
allows the programmer to use methods reserved for only 
WindowController. 
* Parameters: none
* Return: none
*/
    
public class ResizableBallController extends WindowController implements ActionListener, ChangeListener, MouseListener, MouseMotionListener
{

private Line h_line, v_line;
private double widthVar, heightVar;
private double width, height; 
private ResizableBall ballImage; 
private boolean h_grabbed, v_grabbed; 
private static final int MIN_SPEED = 1,
			 MAX_SPEED = 100,
			 PAUSE = 50; 
private JSlider speedSlider;
private JButton startButton, 
                stopButton, 
                clearButton; 
private JLabel speedLabel, 
               titleLabel; 
private int speed = PAUSE; 
/*
* Name: begin()
* Purpose: Creates the lines and indicates their locations.
Creates the buttons, title text, and creates the slider. 
Adds them using action listener. 
* Parameters: None
* Return: void
*/

  public void begin(){

	//creates panels
	JPanel buttonPanel = new JPanel();
	JPanel titlePanel = new JPanel(); 
	JPanel northPanel = new JPanel(); 
        JPanel southPanel = new JPanel(); 
	
        //sets layout and text
	titleLabel = new JLabel("Resizable Ball Controls");
 	northPanel.setLayout(new GridLayout(2,3));

	//creates buttons
	startButton = new JButton("Start");
	stopButton = new JButton("Stop");
	clearButton = new JButton("Clear All");

	//adds actionListener
	startButton.addActionListener(this);
	stopButton.addActionListener(this);
	clearButton.addActionListener(this);

	//adds the labels and panels
	titlePanel.add(titleLabel); 
	buttonPanel.add(startButton);
	buttonPanel.add(stopButton);
	buttonPanel.add(clearButton); 
	northPanel.add(titlePanel);
	northPanel.add(buttonPanel);
	this.add(northPanel, BorderLayout.NORTH);
 	this.validate(); 

	//speed
	
	speedLabel = new JLabel
		("Use the slider to adjust the speed");
	speedSlider = new JSlider(JSlider.HORIZONTAL, 
MIN_SPEED, MAX_SPEED, PAUSE);
	southPanel.add(speedLabel);
	southPanel.add(speedSlider); 
	speedSlider.addChangeListener(this);
        Container contentPane = getContentPane();
        contentPane.add( southPanel, BorderLayout.SOUTH );
        contentPane.validate();

	//sets variables
	widthVar = 0.5;
	heightVar = 0.5; 
	
	//creates new lines
	v_line = new Line(0,heightVar*height,
width,heightVar*height,canvas);

	h_line = new Line(widthVar*width,0,
widthVar*width,height,canvas);

	canvas.addMouseListener(this);
	canvas.addMouseMotionListener(this);
	
}

/*
* Name: onMouseClick
* Purpose: It calls the ResizableBall class
* Parameters: Location point (Location of the click) 
* Return: void
*/

  public void mouseClicked(MouseEvent evt){


	//calls resizableball class
	ballImage = new ResizableBall
		(evt.getX(), evt.getY(), 50, canvas, 
	h_line, v_line, speed);

	speedSlider.addChangeListener(ballImage);
        startButton.addActionListener(ballImage);
        stopButton.addActionListener(ballImage);
        clearButton.addActionListener(ballImage);
        canvas.addMouseListener(ballImage);
        canvas.addMouseMotionListener(ballImage); 

}

  public void mouseEntered(MouseEvent evt){}
  public void mouseExited(MouseEvent evt){}

/*
* Name: stateChanged
* Purpose: Sets the speed for resizing the balls. 
* Parameters: ChangeEvent evt (When the state occurs). 
* Return: void 
*/

  public void stateChanged(ChangeEvent evt){
                      
        speed = speedSlider.getValue();
	if(ballImage != null){

	ballImage.setSpeed(speed); 
	
}
	speedLabel.setText("The speed is " + speed);

 
}

  public void actionPerformed(ActionEvent evt){}

/*
* Name: onMousePress
* Purpose: Creates grabbed variables and click awareness.
* Parameters: Point(Location of the click).
* Return: void 
*/

  public void mousePressed(MouseEvent evt){ 
    

  //Checks if the Location point lies on the h_line
//and creates variable h_grabbed. 
	h_grabbed = h_line.contains
		(new Location(evt.getPoint()));

  //Checks if the Location point lies on the y_line

	v_grabbed = v_line.contains
		(new Location(evt.getPoint()));

}

/*
* Name: onMouseDrag
* Purpose: States if conditions checking if the line is within limits.
This also causes the line to be dragged. 
* Parameters: Point (location of the click)
* Return: Specify the return type and what it represents.
* If no return value, just specify void.
*/

  public void mouseDragged(MouseEvent evt){

  //Drags the h_line if it is pressed. 
  
  if(h_grabbed){

      if((evt.getY() >= 5) && (evt.getY() <= height - 5)){

        heightVar = evt.getY() / height;

        h_line.setEndPoints(0, heightVar * height, width, heightVar * height);

      }

      //makes sure the h_line does not get too close to the top border

      else if(evt.getY() < 5){

        h_line.setEndPoints(0, 5, width, 5);

        heightVar = 5 / height;

      }

      //makes sure the h_line does not get too close to the bottom border

      else{

        h_line.setEndPoints(0, height - 5, width, height - 5);

        heightVar = (height - 5) / height;

      }
  }

      //Drags the v_line if it is pressed

  if(v_grabbed){

      if((evt.getX() >= 5) && (evt.getX() <= width - 5)) {

        widthVar = evt.getX() / width;

        v_line.setEndPoints(widthVar * width, 0, widthVar * width, height);  

      }

     //makes sure the v_line does not get too close to the left border

      else if(evt.getX() < 5){

        v_line.setEndPoints(5, 0, 5, height);

        widthVar = 5 / width;

      }

    //makes sure the v_line does not get too close to the right border

      else{

        v_line.setEndPoints(width - 5, 0,  width - 5, height);

        widthVar = (width - 5) / width;

      }

  }  

}
  public void mouseMoved(MouseEvent evt){} 	

  public void mouseReleased(MouseEvent evt){} 

/*
* Name: paint()
* Purpose: Resets and shifts lines
* Parameters: java.awt.Graphics g(imported graphics)
* Return: void
*/

  public void paint(java.awt.Graphics g){

	super.paint(g); 
      
//reset width and height

    width = canvas.getWidth();

    height = canvas.getHeight();

//shift lines

    h_line.setEndPoints(0, heightVar * height, width, heightVar * height);

    v_line.setEndPoints(widthVar * width, 0, widthVar * width, height);   
	
}
}


