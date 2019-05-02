/*  Ryan Phan
 *  January 16, 2019
 *  Mr. Rosen
 *
 *  This class contains the draw commands and animations for the splashscreen of my Connect 4 game.
 *      The title() method clears the window and draws the title in the centre of the window.
 *      The piecesFallDown () method animates the pieces falling. 
 *
 *
 *  NAME                    VARIABLE TYPE               DESCRIPTION
 *  ----------------------------------------------------------------------------------------------------------------------------
 *  delay               |   int                         Delay for the pieces falling.
 *
 */

import java.awt.*;
import hsa.Console;
import java.lang.*;     // to access Thread class

public class SplashScreen extends Thread
{
    private Console c;
    private int delay = 2;

    /*   This method clears the window and draws the title in the centre of the window.
     *   ----------------------------------------------------------------------------------------------------------------
     *   Local Variables: None
     *   Global Variables Used: None
     *   ----------------------------------------------------------------------------------------------------------------
     *   No input/logic/loop is used.
     */
    public void title ()
    {
	c.setColor (Color.red);
	c.setFont (new Font ("Tw Cen MT", 0, 75));
	c.drawString ("C   N", 75, 270);
	c.setColor (Color.yellow);
	c.drawString ("F   UR", 420, 270);
	c.setColor (Color.black);
	c.drawString ("NECT", 235, 270);

	c.setFont (new Font ("Courier New", Font.BOLD, 15));
	c.drawString ("ANY KEY", 305, 355);
	c.drawString ("TO CONTINUE...", 290, 375);
    }

    /*   This method animates the pieces falling. 
     *   ----------------------------------------------------------------------------------------------------------------
     *   Local Variables: None
     *   Global Variables Used: delay
     *   ----------------------------------------------------------------------------------------------------------------
     *   Two for loops animates the pieces falling into the title. 
     */
    public void piecesFallDown ()
    {
	for (int x = 0 ; x <= 310 ; x++)
	{
	    // Erase
	    c.setColor (Color.white);
	    c.fillOval (124, -92 + x, 58, 58);

	    // Pieces
	    c.setColor (Color.red);
	    c.fillOval (125, -90 + x, 55, 55);
	    
	    // Delay
	    try
	    {
		Thread.sleep (delay);
	    }
	    catch (Exception e)
	    {
	    }
	}

	for (int x = 0 ; x <= 310 ; x++)
	{
	    // Erase
	    c.setColor (Color.white);
	    c.fillOval (454, -92 + x, 58, 58);

	    // Pieces
	    c.setColor (Color.yellow);
	    c.fillOval (455, -90 + x, 55, 55);
	    
	    // Delay
	    try
	    {
		Thread.sleep (delay);
	    }
	    catch (Exception e)
	    {
	    }
	}
    }

    // Class Constructor
    public SplashScreen (Console con)
    {
	c = con;
    }

    /*   This runs the animation. 
     *   ----------------------------------------------------------------------------------------------------------------
     *   Local Variables: None
     *   Global Variables Used: None
     *   ----------------------------------------------------------------------------------------------------------------
     *   No input/logic/loop is used.
     */
    public void run ()
    {
	title ();
	piecesFallDown ();
    }
}


