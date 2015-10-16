/*
* Name: David Wang
* Login: cs8bagf
* Date: 2/22/12
* File: ResizableBall.java
* Sources of Help: google, TA
*
* This program creates the balls and causes them to grow and 
shrink after a user click. This gives the balls color according
to which quadrant they reside in. There is also adjustment to 
resizing the window and moving around the lines.If the buttons
are pressed then they will react according to their purpose.    
*/
import objectdraw.*; 
import java.awt.*;
import java.awt.event.*; 
import javax.swing.*;  
import javax.swing.event.*;

/*
* Name: ResizableBall extends ActiveObject implements
MouseListener,ChangeListener,ActionListener.
* Purpose: Creates a ctor, and run() method. Each has its
own function that contributes to the overall program. Extends ActiveObject
which enables the run method.Creates constants. 
* Parameters: none
* Return: none
*/

public class ResizableBall extends ActiveObject implements 
MouseListener,MouseMotionListener, ChangeListener, ActionListener
{

//creates the ball
	private FilledOval ball;
	//variables for resizing and pause
	private static final double MAX_SIZE = 100,
				    GROWTH = 2,
	                            PAUSE = 50,
	                            MIN_SIZE = 50;
//center x & y location
	double cxLoc,cyLoc;
//the size
	double size = 50;
	
//used for canvas
	private DrawingCanvas canvas; 
//used to initialize lines
	private Line hLine, 
                     vLine; 
//speed
	private int speed; 
//condition for run()
	private boolean b_grabbed,
                        grow = true;   
//used for calling buttons
	private JButton startButton, 
                        stopButton, 
                        clearButton; 
	
        private JSlider speedSlider; 



//ResizableBall ctor

/*
* Name: ResizableBall
* Purpose: Implements color checks and uses parameters.  
* Parameters: xLoc&yLoc(used to find center coordinates),
size(indicator of color),canvas(the "world),hLine&vLine(The lines),
initspeed(used for speed).
(uses actions on click). 
* Return: none
*/
 
  public ResizableBall(double xLoc, double yLoc, double size, 
		DrawingCanvas aCanvas, Line hLine, Line vLine, int initSpeed){
	
        canvas = aCanvas; 
	this.hLine = hLine;
	this.vLine = vLine; 
	//finds center location
 	cxLoc = xLoc - size / 2;

 	cyLoc = yLoc - size / 2;
    
 	ball = new FilledOval(cxLoc, cyLoc, size, size, canvas);

 	
 	speed = initSpeed;
	
	start();
    }

/*
* Name: changeColor()
* Purpose: Changes balls according to quadrant 
* Parameters: none
* Return: void
*/

public void changeColor()
{

//set initial color of top left quadrant 

    if((cxLoc < vLine.getStart().getX()) && 
		(cyLoc < hLine.getStart().getY())){

              ball.setColor(Color.cyan);
}
//set initial color of top right quadrant

    if((cxLoc > vLine.getStart().getX()) && 
		(cyLoc < hLine.getStart().getY())){

	      ball.setColor(Color.magenta);
    }
//set initial color of bottom left quadrant

    if((cxLoc < vLine.getStart().getX()) && 
		(cyLoc > hLine.getStart().getY())){

              ball.setColor(Color.yellow);
    }
//set initial color of bottom right quadrant

    if((cxLoc > vLine.getStart().getX()) && 
		(cyLoc > hLine.getStart().getY())){

              ball.setColor(Color.black);
    }
}

  /*
* Name: mouseClicked
* Purpose: If mouse is clicked. 
* Parameters: evt
* Return: void
*/
  public void mouseClicked(MouseEvent evt){}
 /*
* Name: mouseEntered
* Purpose: If mouse enters.
* Parameters: evt
* Return: void
*/
 public void mouseEntered(MouseEvent evt){}
 /*
* Name: mouseExited
* Purpose: If mouse exits.
* Parameters: evt
* Return: void
*/
 public void mouseExited(MouseEvent evt){}
/*
* Name: stateChanged
* Purpose: Changes speed
* Parameters: ChangeEvent evt
* Return: void
*/
  public void stateChanged(ChangeEvent evt){
 
//get the JSlider value
speed = ((JSlider)(evt.getSource())).getValue(); 

}

/*
* Name: actionPerformed
* Purpose: Conditions depending on whether
buttons are pressed.
* Parameters: evt
* Return: void
*/

  public void actionPerformed(ActionEvent evt){

 
  
//if start is pressed, activate run method
    if(evt.getActionCommand().equals("Start")){

	start();  
}
//if stop is pressed, stop all of the balls
//and change color.
	if(evt.getActionCommand().equals("Stop")){

	   stop();
	   changeColor(); 
} 
//if clear all is pressed, clear all of the balls
//from the canvas. 
		if(evt.getActionCommand().equals("Clear All")){

			ball.removeFromCanvas(); 
}
  }

/*
* Name: mousePressed
* Purpose: Conditions depending on whether
buttons are pressed.
* Parameters: evt
* Return: void
*/

  public void mousePressed(MouseEvent evt){

 //Checks if the MouseEvent evt lies on the ball
//and creates variable b_grabbed. 
	b_grabbed = ball.contains
		(new Location(evt.getPoint()));

}

/*
* Name: mouseDragged
* Purpose: checks to see if the ball 
contains an evt.
* Parameters: evt
* Return: void
*/

public void mouseDragged(MouseEvent evt){

if(b_grabbed){} 
}

/*
* Name:mouseMoved
* Purpose: If mouse moved.
* Parameters: evt
* Return: void
*/
public void mouseMoved(MouseEvent evt){}
/*
* Name: mouseReleased
* Purpose: If mouse released. 
* Parameters: evt
* Return: void
*/
public void mouseReleased(MouseEvent evt){} 

/*
* Name: setSpeed()
* Purpose: Creates setSpeed method
for slider use. 
* Parameters: newSpeed
* Return: void
*/

public void setSpeed(int newSpeed)
{
	speed = newSpeed;
}


/*
* Name: resizeBall()
* Purpose: resizes the ball 
* Parameters: none
* Return: void
*/

public void resizeBall(){

//if it grows set xLoc, YLoc and
//increment, otherwise false. 
if(grow){

      if(size < MAX_SIZE){
        double xLoc = cxLoc + size/2;

        double yLoc = cyLoc + size/2;

        size += GROWTH;

        ball.setSize(size, size);

        cxLoc = xLoc - size / 2;

        cyLoc = yLoc - size / 2;

        ball.moveTo(cxLoc, cyLoc);

      }else{

        grow = false;

      }

    }else{

//otherwise shrink

       if((size <= MAX_SIZE) && (size > MIN_SIZE)){

        double xLoc = cxLoc + size/2;

        double yLoc = cyLoc + size/2;

        size -= GROWTH;

        ball.setSize(size, size);

        cxLoc = xLoc - size / 2;

        cyLoc = yLoc - size / 2;

        ball.moveTo(cxLoc, cyLoc);

      }else{

        grow = true;

      }
    }
//time delay to prevent lag	 
	pause(100 - speed);  
    }

/*
* Name: run()
* Purpose: Calls changeColor, creates a infinite loop
* that shrinks and grows the balls. This also centers the 
* area where the ball grows. 
* Parameters: none
* Return: void 
*/

public void run(){ 
 
//infinite loop
while(true){
  
changeColor();
resizeBall(); 

  }
}
}
