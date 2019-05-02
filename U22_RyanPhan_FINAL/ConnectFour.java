/*  Ryan Phan
 *  January 16, 2019
 *  Mr. Rosen
 *
 *  This program creates a CONNECT 4 game!
 *          drawTitle() clears the window and displays the title at the top of the window.
 *          pauseProgram() allows the program to pause and wait for the user to tell it to continue.
 *          splashScreen() outputs some graphics related to the program.
 *          mainMenu() creates a main menu.
 *          userName() gets the names of the two players.
 *          resetBoard() resets the board before every match.
 *          movePiece() allows the user to move the piece around the columns and it animates the piece dropping in.
 *          numberOfMoves() returns the number of moves that the player used.
 *          winCheck() checks for a win based on the current player.
 *          highlightWin() highlights the winning pieces with green stars.
 *          gameScreen() draws the game board and screen.
 *          display() outputs the results of the match. winCheck() is called to determine the winner.
 *          createNewSaveFile() is used to either create a new save file using createNewSaveFile(), or it adds the match results to a file.
 *          writeHighScores() method is used to either create a new save file using createNewSaveFile(), or it adds the match results toa file.
 *          getHighScores() retrieves the high scores from the save file and stores it in some arrays. The array is then sorted with sortHighScores()
 *          sortHighScores() is used to sort the high scores inside the file and then the top 10 elements are placed into new arrays.
 *          clearHighScores() allows the user to clear the high scores file and the leaderboard.
 *          highScores() displays the high scores.
 *          instructions() gives instructions on what the program does.
 *          goodbye() outputs the goodbye screen, displaying my name.
 *
 *
 *  NAME                    VARIABLE TYPE               DESCRIPTION
 *  ----------------------------------------------------------------------------------------------------------------------------
 *  choice              |   String                      Stores user choice to control program flow.
 *  playAgain           |   char                        Stores user choice to control if they want to play again.
 *  newName             |   char                        Stores user choice to control if they want a new name.
 *  header              |   String                      Unique Header used at the top of files.
 *  HIGHSCORES_FILE     |   String                      Name of the file that the highscores are stored in.
 *  LEADERBOARD_SIZE    |   int                         Contains the size of the leaderboard [10].
 *  COL                 |   int                         Contains the number of columns in the Connect Four board [7].
 *  ROW                 |   int                         Contains the number of rows in the Connect Four board [6].
 *  boardGrid           |   int[][]                     Stores the layout of the board (Stores what piece is where).
 *  numInColumn         |   int[]                       Stores the number of pieces in each column.
 *  totalMoves          |   int[]                       Stores the number of moves for the leaderboard (Stores all moves).
 *  totalNames          |   String[]                    Stores the winners for the leaderboard (Stores all names).
 *  totalMatchups       |   String[]                    Stores the matchups for the leaderboard (Stores all matches).
 *  currentColumn       |   int                         Stores the current column the user is over.
 *  currentPlayer       |   int                         Stores the player turn.
 *  userName1           |   String                      Player 1's name
 *  userName2           |   String                      Player 2's name
 *
 */

import java.io.*;
import java.awt.*;
import hsa.*;

// The "ConnectFour" Class
public class ConnectFour
{
    Console c;  // Output Console

    // Declaration Statements [Global]
    static char choice, playAgain, newName = 'y';
    final String header = "%HighScores.yan%-CONNECT_FOUR_BETA";
    final String HIGHSCORES_FILE = "HighScores.yan";
    final int LEADERBOARD_SIZE = 10;
    final int COL = 7;
    final int ROW = 6;
    int[] [] boardGrid = new int [COL] [ROW];
    int[] numInColumn = new int [COL];
    int[] totalMoves;
    String[] totalNames;
    String[] totalMatchups;
    int currentPlayer, currentColumn = 0;
    String userName1, userName2;


    // Class Constructor
    public ConnectFour ()
    {
	c = new Console (30, 80, "Connect 4");               // Creates the console window
    }


    /*   This method clears the window and displays the title at the top of the window.
     *   ----------------------------------------------------------------------------------------------------------------
     *   Local Variables:
     *   title - String - A parameter pass used to control the title.
     *
     *   Global Variables Used: None
     *   ----------------------------------------------------------------------------------------------------------------
     *   No input/logic/loop is used.
     */
    public void drawTitle (String title)  // Overloads are used to control what I want in the title
    {
	c.clear ();
	c.print (' ', 40 / 2 + title.length ());  // Makes sure the title is centered (Or close to it)
	c.println (title);
	c.println ();
    }


    /*   This method allows the program to pause and wait for the user to tell it to continue.
     *   ----------------------------------------------------------------------------------------------------------------
     *   Local Variables:
     *   pauseChar - character - Used in pauseProgram to allow the user to tell the program to continue.
     *
     *   Global Variables Used: None
     *   ----------------------------------------------------------------------------------------------------------------
     *   Input is used to store a character in pauseChar, after being stored the program continues.
     */
    public void pauseProgram (String pauseMessage)
    {
	c.println ();
	c.setColor (Color.black);
	c.setFont (new Font ("Courier New", Font.BOLD, 15));
	c.drawString (pauseMessage, 1, 590);                    // Outputs the message recieved via parameter pass
	char pauseChar = c.getChar ();
    }


    /*   This method outputs some graphics related to the program.
     *   ----------------------------------------------------------------------------------------------------------------
     *   Local Variables: None
     *   Global Variables Used: None
     *   ----------------------------------------------------------------------------------------------------------------
     *   Loops are used to animate the pieces falling down.
     */
    public void splashScreen ()
    {
	// Creates the SplashScreen thread
	SplashScreen s = new SplashScreen (c);
	// Starts the thread
	s.run ();
	pauseProgram ("");
    }


    /*  This method creates a main menu.
     *  ----------------------------------------------------------------------------------------------------------------
     *  Local Variables:
     *  choice - String -  Used to allow the user to choose an option.
     *
     *  Global Variables Used: None
     *  ----------------------------------------------------------------------------------------------------------------
     *  A character is inputted and stored in the variable choice.
     *  An if statement is used to decide what each option will lead to.
     */
    public void mainMenu ()
    {
	// Main Menu Options
	drawTitle ("CONNECT FOUR");
	c.print (' ', 34);
	c.println ("Main Menu");
	c.print (' ', 7);
	c.print ("--------------------------------------------------------------------");
	c.println ();
	c.print (' ', 28);
	c.println ("[1] Play CONNECT 4");
	c.print (' ', 28);
	c.println ("[2] High Scores");
	c.print (' ', 28);
	c.println ("[3] Instructions");
	c.print (' ', 28);
	c.println ("[_] Exit (Any other key)");
	c.println ();
	c.print (' ', 30);
	c.print ("Enter An Option... ");
	choice = c.getChar ();                              // Accepts the user choice.
    }


    /*   This method gets the names of the two players.
     *   ----------------------------------------------------------------------------------------------------------------
     *   Local Variables: None
     *
     *   Global Variables Used:
     *   userName1           |   String                      Player 1's name
     *   userName2           |   String                      Player 2's name
     *   ----------------------------------------------------------------------------------------------------------------
     *   Input is stored into two global variables for future use.
     */
    public void userName ()
    {
	drawTitle ("CONNECT FOUR");
	do
	{
	    c.setCursor (3, 6);
	    c.println ("Register your names to appear on the leaderboard! (10 Character Limit)");
	    c.setCursor (5, 27);
	    c.println ();
	    c.setCursor (5, 27);
	    c.print ("Enter your name Player 1: ");
	    userName1 = c.readLine ();
	    if (userName1.length () > 10)                                               // If the name is longer than 10 characters
	    {
		new Message ("Please adhere to the 10 character limit.", "Error!");
		continue;                                                               // Ask for the name again
	    }
	    break;
	}
	while (true);
	do
	{
	    c.setCursor (7, 10);
	    c.println ("-------------------------------------------------------------");
	    c.setCursor (9, 27);
	    c.println ();
	    c.setCursor (9, 27);
	    c.print ("Enter your name Player 2: ");
	    userName2 = c.readLine ();
	    if (userName2.length () > 10)                                               // If the name is longer than 10 characters
	    {
		new Message ("Please adhere to the 10 character limit.", "Error!");
		continue;                                                               // Ask for the name again
	    }
	    break;
	}
	while (true);
    }


    /*   This method resets the board before every match.
     *   ----------------------------------------------------------------------------------------------------------------
     *   Local Variables: None
     *
     *   Global Variables Used:
     *   COL                 |   int                         Contains the number of columns in the Connect Four board [7].
     *   ROW                 |   int                         Contains the number of rows in the Connect Four board [6].
     *   boardGrid           |   int[][]                     Stores the layout of the board (Stores what piece is where).
     *   numInColumn         |   int[]                       Stores the number of pieces in each column.
     *   currentColumn       |   int                         Stores the current column the user is over.
     *   currentPlayer       |   int                         Stores the player turn.
     *   ----------------------------------------------------------------------------------------------------------------
     *   Loops are used to set the array elements back to 0.
     *   The current column resets to 3 to ensure that the piece starts in the center
     *   Setting currentPlayer to 0 just means there isn't any player playing.
     */
    private void resetBoard ()
    {
	currentColumn = 3;                      // Puts the piece in the center of the board.
	currentPlayer = 0;                      // Resets the turn to change properly.
	for (int x = 0 ; x < COL ; x++)
	{
	    numInColumn [x] = 0;                // Resets the number of pieces per column
	    for (int y = 0 ; y < ROW ; y++)
	    {
		boardGrid [x] [y] = 0;          // Reset all board elements to 0.
	    }
	}
    }


    /*   This method allows the user to move the piece around the columns and it animates the piece dropping in.
     *   ----------------------------------------------------------------------------------------------------------------
     *   Local Variables:
     *   userKey - character - User input used to check what column the user wants the piece to be in
     *   currentColor - [Parameter Pass] Color - Used to make sure the color piece used is correct
     *
     *   Global Variables Used:
     *   boardGrid           |   int[][]                     Stores the layout of the board (Stores what piece is where).
     *   numInColumn         |   int[]                       Stores the number of pieces in each column.
     *   currentColumn       |   int                         Stores the current column the user is over.
     *   currentPlayer       |   int                         Stores the player turn.
     *   ----------------------------------------------------------------------------------------------------------------
     *   A while loop is used to allow the user to press either 'a' or 'd' to move the piece. The loop is broken when
     *   enter or the space bar is pressed. However, this will only occur if the column isn't full. An if statement is
     *   used to determine this. Afterward, a for loop animates the piece falling down into the column.
     *
     */
    private void movePiece (Color currentColor)
    {
	char userKey;

	c.fillOval (95 + currentColumn * 65, 60, 50, 50);
	while (true)
	{
	    userKey = c.getChar ();                                 // Gets the user's input.
	    if ((userKey == 10 || userKey == ' ') && numInColumn [currentColumn] != 6) // If the user presses enter or [SPACE] and the number of pieces in the column isn't 6 or above.
	    {
		break;
	    }
	    else if ((userKey == 'a' || userKey == 'A') && currentColumn != 0) // Goes left
	    {
		c.setColor (Color.white);
		c.fillOval (95 + currentColumn * 65, 60, 50, 50);
		currentColumn--;
	    }
	    else if ((userKey == 'd' || userKey == 'D') && currentColumn != 6) // Goes right
	    {
		c.setColor (Color.white);
		c.fillOval (95 + currentColumn * 65, 60, 50, 50);
		currentColumn++;
	    }
	    c.setColor (currentColor);
	    c.fillOval (95 + currentColumn * 65, 60, 50, 50);
	}

	for (int x = 0 ; x <= 390 - numInColumn [currentColumn] * 65 ; x++)     // Drops the piece
	{

	    for (int y = 0 ; y < ROW - numInColumn [currentColumn] ; y++)
	    {
		c.setColor (Color.white);
		c.fillOval (95 + currentColumn * 65, 125 + y * 65, 50, 50);    // Redraws the the column behind the piece.
	    }

	    // Redraws the the column behind the piece.
	    c.setColor (Color.blue);
	    c.fillOval (95 + currentColumn * 65, 59 + x, 50, 50);
	    c.setColor (Color.white);
	    c.fillRect (95 + currentColumn * 65, 59, 50, 56);

	    // Piece animation.
	    c.setColor (currentColor);
	    c.fillOval (95 + currentColumn * 65, 60 + x, 50, 50);

	    try
	    {
		Thread.sleep (2);
	    }
	    catch (Exception e)
	    {
	    }
	}
	numInColumn [currentColumn]++;                                               // Adds 1 to the number of pieces in the column
	boardGrid [currentColumn] [numInColumn [currentColumn] - 1] = currentPlayer; // Puts the player number at the board location.
    }


    /*   This method returns the number of moves that the player used.
     *   ----------------------------------------------------------------------------------------------------------------
     *   Local Variables:
     *   moves - int - Stores how many moves the user used
     *   currentPlayer (Paramater) - int - Stores which player to count the number of moves for.
     *
     *   Global Variables Used: None
     *   ----------------------------------------------------------------------------------------------------------------
     *   A for loop is used to count how many of the player's pieces are in the board, thus counting the number of moves
     *   it took for them to win.
     */
    private int numberOfMoves (int currentPlayer)
    {
	int moves = 0;
	for (int x = 0 ; x < COL ; x++)
	{
	    for (int y = 0 ; y < ROW ; y++)
	    {
		if (boardGrid [x] [y] == currentPlayer)    // If anything in the board is the current player's piece.
		{
		    moves++;                                // Add the their moves.
		}

	    }
	}
	return moves;
    }


    /*   This method checks for a win based on the current player.
     *   ----------------------------------------------------------------------------------------------------------------
     *   Local Variables:
     *   currentPlayer (Paramater) - int - Stores which player to count the number of moves for.
     *   highlightWin (Paramater) - boolean - Used to tell the method if it should highlight where the win occured or not
     *
     *   Global Variables Used:
     *   COL                 |   int                         Contains the number of columns in the Connect Four board [7].
      *  ROW                 |   int                         Contains the number of rows in the Connect Four board [6].
     *   boardGrid           |   int[][]                     Stores the layout of the board (Stores what piece is where).
     *   ----------------------------------------------------------------------------------------------------------------
     *   5 for loops are in use here, with all of them checking the board from left to right. highlightWin() is just used
     *   so the users can see where the win occured.
     *
     *   The first for loop checks for horizontal win conditions, only checking the first 4 columns since checking the
     *   four-in-a-row can only occur in these columns if checking from left to right. This same principle applies to
     *   the descending diagonal and the ascending diagonal checks.
     *
     *   The second for loop checks for vertical win conditions, only checking the first 3 columns for the same reason as
     *   the above loop just going from bottom up.
     *
     *   The third loop checks for descending wins but this time the y value starts at 3 values above the bottom row,
     *   since descending wins can only occur in these areas.
     *
     *   The forth loop checks for ascending wins, with the loop combining the restrictions from the first two loops.
     *
     *   The last loop just checks if there are any open spaces left and this only runs when no win has been detected.
     *   This just allows the program to continue in the event nobody has won. If all the spaces are filled with no win
     *   then a tie has occured.
     */
    private int winCheck (int currentPlayer, boolean highlightWin)
    {
	// Horizontal Check
	for (int x = 0 ; x < COL - 3 ; x++)
	{
	    for (int y = 0 ; y < ROW ; y++)
	    {
		if (boardGrid [x] [y] == currentPlayer && boardGrid [x + 1] [y] == currentPlayer && boardGrid [x + 2] [y] == currentPlayer && boardGrid [x + 3] [y] == currentPlayer)
		{
		    if (highlightWin)
			highlightWin (x, y, 1);
		    return currentPlayer;
		}
	    }
	}

	// Vertical Check
	for (int x = 0 ; x < COL ; x++)
	{
	    for (int y = 0 ; y < ROW - 3 ; y++)
	    {
		if (boardGrid [x] [y] == currentPlayer && boardGrid [x] [y + 1] == currentPlayer && boardGrid [x] [y + 2] == currentPlayer && boardGrid [x] [y + 3] == currentPlayer)
		{
		    if (highlightWin)
			highlightWin (x, y, 2);
		    return currentPlayer;
		}
	    }
	}

	// Descending Check
	for (int x = 0 ; x < COL - 3 ; x++)
	{
	    for (int y = 3 ; y < ROW ; y++)
	    {
		if (boardGrid [x] [y] == currentPlayer && boardGrid [x + 1] [y - 1] == currentPlayer && boardGrid [x + 2] [y - 2] == currentPlayer && boardGrid [x + 3] [y - 3] == currentPlayer)
		{
		    if (highlightWin)
			highlightWin (x, y, 3);
		    return currentPlayer;
		}
	    }
	}

	// Ascending Check
	for (int x = 0 ; x < COL - 3 ; x++)
	{
	    for (int y = 0 ; y < ROW - 3 ; y++)
	    {
		if (boardGrid [x] [y] == currentPlayer && boardGrid [x + 1] [y + 1] == currentPlayer && boardGrid [x + 2] [y + 2] == currentPlayer && boardGrid [x + 3] [y + 3] == currentPlayer)
		{
		    if (highlightWin)
			highlightWin (x, y, 4);
		    return currentPlayer;
		}
	    }
	}

	// If there are empty spaces
	for (int x = 0 ; x < COL ; x++)
	{
	    for (int y = 0 ; y < ROW ; y++)
	    {
		if (boardGrid [x] [y] == 0)
		{
		    return -1;
		}

	    }
	}

	// If board is full and no win has appeared - TIE GAME
	return 0;
    }


    /*   This method highlights the winning pieces with green stars.
     *   ----------------------------------------------------------------------------------------------------------------
     *   Local Variables: x, y, winType - All ints - Used to check where the win occured and what type of win occured.
     *   Global Variables Used: None
     *   ----------------------------------------------------------------------------------------------------------------
     *   An if statement is used to check which kind of win occured and to draw the stars in the right order.
     */
    private void highlightWin (int x, int y, int winType)
    {
	// Highlights the winning pieces
	c.setColor (Color.green);
	if (winType == 1)
	{
	    c.fillStar (105 + x * 65, 135 + (5 - y) * 65, 30, 30);
	    c.fillStar (105 + (x + 1) * 65, 135 + (5 - y) * 65, 30, 30);
	    c.fillStar (105 + (x + 2) * 65, 135 + (5 - y) * 65, 30, 30);
	    c.fillStar (105 + (x + 3) * 65, 135 + (5 - y) * 65, 30, 30);
	}
	else if (winType == 2)
	{
	    c.fillStar (105 + x * 65, 135 + (5 - y) * 65, 30, 30);
	    c.fillStar (105 + x * 65, 135 + (5 - y - 1) * 65, 30, 30);
	    c.fillStar (105 + x * 65, 135 + (5 - y - 2) * 65, 30, 30);
	    c.fillStar (105 + x * 65, 135 + (5 - y - 3) * 65, 30, 30);
	}
	else if (winType == 3)
	{
	    c.fillStar (105 + x * 65, 135 + (5 - y) * 65, 30, 30);
	    c.fillStar (105 + (x + 1) * 65, 135 + (5 - y + 1) * 65, 30, 30);
	    c.fillStar (105 + (x + 2) * 65, 135 + (5 - y + 2) * 65, 30, 30);
	    c.fillStar (105 + (x + 3) * 65, 135 + (5 - y + 3) * 65, 30, 30);
	}
	else
	{
	    c.fillStar (105 + x * 65, 135 + (5 - y) * 65, 30, 30);
	    c.fillStar (105 + (x + 1) * 65, 135 + (5 - y - 1) * 65, 30, 30);
	    c.fillStar (105 + (x + 2) * 65, 135 + (5 - y - 2) * 65, 30, 30);
	    c.fillStar (105 + (x + 3) * 65, 135 + (5 - y - 3) * 65, 30, 30);
	}

    }


    /*   This method draws the game board and screen.
     *   ----------------------------------------------------------------------------------------------------------------
     *   Local Variables:
     *   currentColor - Color - Contains the current players color so the piece color changes.
     *   Global Variables Used: None
     *   ----------------------------------------------------------------------------------------------------------------
     *   A for loop is used to create the holes in the game board.
     *   A while loop is ued to get input after every move.
     *   A if statement is used inside of the while loop to check if a tie or a win has occured.
     */
    public void gameScreen ()
    {
	Color currentColor = new Color (0, 0, 0);    // Current user colour

	drawTitle ("CONNECT FOUR");
	c.print ("", 5);
	c.println ("Use 'a' and 'd' to select a column (Press enter or [SPACE] to drop):");

	// Player Pieces
	c.setColor (Color.yellow);
	c.fillOval (5, 545, 50, 50);
	c.setColor (Color.red);
	c.fillOval (590, 545, 50, 50);

	// Board
	c.setColor (Color.black);
	c.fillRect (55, 165, 520, 370);
	c.setColor (Color.blue);
	c.fillRoundRect (55, 115, 520, 400, 50, 50);
	
	// Draws holes
	for (int x = 0 ; x < COL ; x++)
	{
	    for (int y = 0 ; y < ROW ; y++)
	    {
		c.setColor (Color.white);
		c.fillOval (95 + x * 65, 125 + y * 65, 50, 50);

	    }
	}
	
	// Sets the current turn.
	while (true)
	{
	    currentPlayer++;
	    if (currentPlayer == 1)
	    {
		currentColor = new Color (255, 255, 51);
		c.setCursor (29, 30);
		c.print ("<---------------------");
	    }
	    else if (currentPlayer == 2)
	    {
		currentColor = new Color (255, 51, 51);
		c.setCursor (29, 30);
		c.print ("--------------------->");
	    }
	    else
	    {
		currentPlayer = 0;
		continue;
	    }
	    c.setCursor (29, 10);
	    c.print (userName1);
	    c.setCursor (29, 60);
	    c.print (userName2);
	    c.setColor (currentColor);

	    movePiece (currentColor);
	    
	    // Checks for win or a tie
	    if (winCheck (currentPlayer, false) != -1)
	    {
		winCheck (currentPlayer, true);
		c.setCursor (3, 5);
		c.println ();
		c.setColor (Color.black);
		c.setFont (new Font ("Tw Cen MT", Font.BOLD, 25));
		if (winCheck (currentPlayer, false) == 0)
		    c.drawString ("IT'S A TIE!!! PRESS ANY KEY TO CONTINUE...", 95, 50);
		else
		    c.drawString ("WE HAVE A WINNER!!! PRESS ANY KEY TO CONTINUE...", 25, 50);
		pauseProgram ("");
		break;
	    }
	}
    }


    /*  This method outputs the results of the match. winCheck() is called to determine the winner.
     *  ----------------------------------------------------------------------------------------------------------------
     *  Local Variables: None
     *  Global Variables Used:
     *  playAgain           |   char                        Stores user choice to control if they want to play again.
     *  ----------------------------------------------------------------------------------------------------------------
     *  An if statement is used to decide what to output.
     *  Another if statement is used to find out if the user wants to play again.
     */
    public void display ()
    {
	drawTitle ("CONNECT FOUR");
	c.setFont (new Font ("Tw Cen MT", Font.BOLD, 30));
	
	// Displays results of match. 
	if (winCheck (1, false) == 1)
	{
	    c.drawString (userName1 + " won in " + numberOfMoves (1) + " moves", 25, 50);
	    writeHighScores (userName1, numberOfMoves (1), userName2);
	}
	else if (winCheck (2, false) == 2)
	{
	    c.drawString (userName2 + " won in " + numberOfMoves (2) + " moves", 25, 50);
	    writeHighScores (userName2, numberOfMoves (2), userName1);
	}
	else
	{
	    c.drawString ("TIE GAME", 25, 50);
	}
	
	// Allows user to play again and change their usernames. 
	c.setFont (new Font ("Courier New", Font.BOLD, 15));
	c.drawString ("Would you like to play again?...", 25, 100);
	c.drawString ("Press 'y' to play again. Any other key returns to main menu.", 25, 120);
	playAgain = c.getChar ();

	if (playAgain == 'y' || playAgain == 'Y')
	{
	    c.drawString ("----------------------------------------------------------------", 25, 140);
	    c.drawString ("Would you like to change your usernames?", 25, 160);
	    c.drawString ("Press 'y' to change your names. If not press any other key.", 25, 180);
	    newName = c.getChar ();
	}
    }


    /*   This method is used to create a new save file in the event that there isn't one or the header is incorrect.
     *   ----------------------------------------------------------------------------------------------------------------
     *   Local Variables: output - PrintWriter - Used to output the header and make a new file.
     *   Global Variables Used: None
     *   ----------------------------------------------------------------------------------------------------------------
     *   No input/logic/loop is used.
     */
    private void createNewSaveFile ()
    {
	try
	{
	    PrintWriter output = new PrintWriter (new FileWriter (HIGHSCORES_FILE));
	    output.println (header);
	    output.close ();
	}
	catch (IOException e)
	{
	}
    }


    /*   This method is used to either create a new save file using createNewSaveFile(), or it adds the match results to
     *   a file.
     *   ----------------------------------------------------------------------------------------------------------------
     *   Local Variables:
     *   input - BufferedReader - Used to check for a header.
     *   output - PrintWriter - Used to output the results into the file.
     *   Global Variables Used: None
     *   ----------------------------------------------------------------------------------------------------------------
     *   A while loop is used to ensure that a new file is create if anything goes wrong.
     */
    private void writeHighScores (String winnerName, int numOfMoves, String loserName)
    {
	while (true)
	{
	    try
	    {
		BufferedReader input = new BufferedReader (new FileReader (HIGHSCORES_FILE));
		if (!(input.readLine ().equals (header)))
		{
		    createNewSaveFile ();
		    new Message ("Invalid save file! New file created.", "Error!");
		}
		PrintWriter output = new PrintWriter (new FileWriter (HIGHSCORES_FILE, true));
		output.println (winnerName + " v. " + loserName);
		output.println (winnerName);
		output.println (numOfMoves);
		output.close ();
		break;
	    }
	    catch (IOException e)
	    {
		createNewSaveFile ();
		continue;
	    }
	}
    }


    /*   This method retrieves the high scores from the save file and stores it in some arrays. The array is then sorted
     *   with sortHighScores()
     *   ----------------------------------------------------------------------------------------------------------------
     *   Local Variables:
     *   numOfScores - int - Used to store the number of scores inside the file.
     *   line - String - Used to check how many scores are in the file.
     *   input - BufferedReader - Reads content from the file.
     *
     *   Global Variables Used:
     *   totalMoves          |   int[]                       Stores the number of moves for the leaderboard (Stores all moves).
     *   totalNames          |   String[]                    Stores the winners for the leaderboard (Stores all names).
     *   totalMatchups       |   String[]                    Stores the matchups for the leaderboard (Stores all matches).
     *   ----------------------------------------------------------------------------------------------------------------
     *   A while loop is used to ensure that the program will continue without issues if a problem arises.
     *   Another while loop is finds how many lines are in the file
     */
    private void getHighScores ()
    { 
	int numOfScores = -1;
	String line = "";
	BufferedReader input;

	while (true)
	{
	    try
	    {
		input = new BufferedReader (new FileReader (HIGHSCORES_FILE));
		if (!(input.readLine ().equals (header)))
		{
		    createNewSaveFile ();
		    new Message ("Invalid save file! New file created.", "Error!");
		    continue;
		}
		else
		{
		    while (line != null)            // Finds how many lines are in the file
		    {
			input.readLine ();
			input.readLine ();
			line = input.readLine ();
			numOfScores++;                     
		    }
		    input.close ();
		    break;
		}
	    }
	    catch (IOException e)
	    {
		createNewSaveFile ();
		new Message ("File doesn't exist! New file created.", "Error!");
	    }
	}

	try
	{
	    input = new BufferedReader (new FileReader (HIGHSCORES_FILE));
	    totalMoves = new int [LEADERBOARD_SIZE + numOfScores];
	    totalNames = new String [LEADERBOARD_SIZE + numOfScores];
	    totalMatchups = new String [LEADERBOARD_SIZE + numOfScores];
	    input.readLine ();

	    for (int i = 0 ; i < numOfScores ; i++)
	    {
		totalMatchups [i] = input.readLine ();
		totalNames [i] = input.readLine ();
		line = input.readLine ();
		totalMoves [i] = Integer.parseInt (line);
	    }
	    input.close ();
	}
	catch (Exception e)                                                 // General Exception in case something in the file is invalid. 
	{
	    createNewSaveFile ();
	    new Message ("Invalid File Input! New file created.", "Error!");
	}
	sortHighScores (numOfScores);
    }


    /*   This method is used to sort the high scores inside the file and then the top 10 elements are placed into new
     *   arrays.
     *   ----------------------------------------------------------------------------------------------------------------
     *   Local Variables:
     *   numOfScores - Parameter Passed int - Used to store the number of scores inside the file.
     *   movedScore, movedName, movedMatch - int, String, String - Stores the element in the array to be moved.
     *
     *   Global Variables Used:
     *   totalMoves          |   int[]                       Stores the number of moves for the leaderboard (Stores all moves).
     *   totalNames          |   String[]                    Stores the winners for the leaderboard (Stores all names).
     *   totalMatchups       |   String[]                    Stores the matchups for the leaderboard (Stores all matches).
     *   ----------------------------------------------------------------------------------------------------------------
     *   Two for loops are used to ensure that every element in the array is checked.
     */
    private void sortHighScores (int numOfScores)
    {
	for (int i = 0 ; i < numOfScores ; i++)
	{
	    for (int j = i + 1 ; j < numOfScores ; j++)
	    {
		int movedScore = 0;
		String movedName = "";
		String movedMatch = "";
		if (totalMoves [i] > totalMoves [j])    // If element is greater than one after it. 
		{
		    movedScore = totalMoves [i];        // Store temporary variable
		    totalMoves [i] = totalMoves [j];    // Switch the elements
		    totalMoves [j] = movedScore;        // i to j and j to i

		    movedName = totalNames [i];
		    totalNames [i] = totalNames [j];
		    totalNames [j] = movedName;

		    movedMatch = totalMatchups [i];
		    totalMatchups [i] = totalMatchups [j];
		    totalMatchups [j] = movedMatch;
		}
	    }
	}
    }


    /*   This method allows the user to clear the high scores file and the leaderboard.
     *   ----------------------------------------------------------------------------------------------------------------
     *   Local Variables:
     *   clear - char - Used to check if the user wants to clear the file or not.
     *   Global Variables Used: None
     *   ----------------------------------------------------------------------------------------------------------------
     *   No input/logic/loop is used.
     */
    private boolean clearHighScores (char clear)
    {
	if (clear == 'c' || clear == 'C')
	{
	    createNewSaveFile ();
	    return false;
	}

	return true;
    }


    /*  This method displays the high scores.
     *  ----------------------------------------------------------------------------------------------------------------
     *  Local Variables:
     *  empty - int - Stores the number of empty elements.
     *  valid - int - Stores the number of valid scores.
     *  clear - char - Used to check if the user wants to clear the file or not.
     *
     *  Global Variables Used:
     *  totalMoves          |   int[]                       Stores the number of moves for the leaderboard (Stores all moves).
     *  totalNames          |   String[]                    Stores the winners for the leaderboard (Stores all names).
     *  totalMatchups       |   String[]                    Stores the matchups for the leaderboard (Stores all matches).
     *  ----------------------------------------------------------------------------------------------------------------
     *  A while loop is used to redisplay the leaderboard if the user clears it.
     *  A for loop is used with a to output and check all of the elements in the totalNames.
     */
    public void highScores ()
    {
	while (true)
	{
	    getHighScores ();

	    drawTitle ("High Scores");
	    c.print ("", 30);
	    c.println ("The best of the best");
	    c.println ();
	    c.print ("", 6);
	    c.print ("RANK");
	    c.print ("", 15);
	    c.print ("MATCHUP");
	    c.print ("", 17);
	    c.print ("WINNER");
	    c.print ("", 16);
	    c.println ("MOVES");
	    c.println ("--------------------------------------------------------------------------------");

	    int empty = 0;
	    int valid = 0;
	    // Outputs the leaderboard
	    for (int i = 0 ; i < LEADERBOARD_SIZE ; i++)
	    {
		if (totalNames [i] == null)
		{
		    empty++;
		}
		else
		{
		    c.setCursor (8 + i, 8);
		    c.print (i + 1 - empty);
		    c.setCursor (8 + i, 22);
		    c.print (totalMatchups [i]);
		    c.setCursor (8 + i, 50);
		    c.print (totalNames [i]);
		    c.setCursor (8 + i, 72);
		    c.println (totalMoves [i]);
		    valid++;
		}
	    }
	    
	    // Outputs blank lines if there arent enough people to fill the board. 
	    for (int i = valid ; i < LEADERBOARD_SIZE ; i++)
	    {
		c.setCursor (8 + i, 8);
		c.print (i + 1);
		c.setCursor (8 + i, 22);
		c.print ("------ v. ------");
		c.setCursor (8 + i, 50);
		c.println ("-----");
		c.setCursor (8 + i, 72);
		c.println ("--");

	    }

	    char clear;
	    c.setCursor (25, 5);
	    c.println ("Press c to clear scores...");
	    c.setCursor (26, 5);
	    c.println ("Enter any other key to return");
	    clear = c.getChar ();

	    if (clearHighScores (clear))
		break;
	}
    }


    /*  This method gives instructions on what the program does.
     *  ----------------------------------------------------------------------------------------------------------------
     *  Local Variables: None
     *  Global Variables Used: None
     *  ----------------------------------------------------------------------------------------------------------------
     *  No input/logic/loop is used.
     */
    public void instructions ()
    {
	drawTitle ("How to Play?");
	c.setCursor (3, 3);
	c.println ("To win Connect Four you must be the first player to get four of your coloured");
	c.setCursor (4, 10);
	c.println ("pieces in a row either horizontally, vertically or diagonally.");
	c.setCursor (5, 10);
	c.println ("Use 'a' and 'd' to move your piece left and right respectively.");
	c.setCursor (6, 23);
	c.println ("Use the enter key to drop the piece.");
	pauseProgram ("Enter any key to return...");
    }


    /*  This method outputs the goodbye screen, displaying my name.
     *  ----------------------------------------------------------------------------------------------------------------
     *  Local Variables: None
     *  Global Variables Used: None
     *  ----------------------------------------------------------------------------------------------------------------
     *  No input/logic/loop is used.
     */
    public void goodbye ()
    {
	c.clear ();
	c.setFont (new Font ("Tw Cen MT", 0, 75));
	c.setColor (Color.black);
	c.drawString ("G      DBYE!", 150, 200);

	c.setColor (Color.red);
	c.fillOval (210, 150, 55, 55);
	c.setColor (Color.yellow);
	c.fillOval (275, 150, 55, 55);
	c.setColor (Color.black);
	c.setFont (new Font ("Tw Cen MT", 0, 25));
	c.drawString ("Developed by Ryan Phan", 200, 300);
	pauseProgram ("Enter any key to close the program...");
	c.close ();
    }


    /*   The main method controls the flow of the program.
     *   ----------------------------------------------------------------------------------------------------------------
     *   Local Variables: None
     *   Global Variables Used:
     *   choice              |   String                      Stores user choice to control program flow.
     *   playAgain           |   char                        Stores user choice to control if they want to play again.
     *   newName             |   char                        Stores user choice to control if they want a new name.
     *   ----------------------------------------------------------------------------------------------------------------
     *   A do while loop is used to ensure that the program keeps going until the user wants to quit, which is determined
     *   by a if statement.
     *   Another do while loop is used to allow the user to play again if they want to and an if statement allows them to
     *   change their names.
     */
    public static void main (String[] args)
    {
	ConnectFour f = new ConnectFour ();
	f.splashScreen ();
	do
	{
	    f.mainMenu ();
	    if (choice == '1')
	    {
		do
		{
		    if (newName == 'y' || newName == 'Y')
		    {
			f.userName ();
		    }
		    f.resetBoard ();
		    f.gameScreen ();
		    f.display ();
		}
		while (playAgain == 'y' || playAgain == 'Y');
	    }
	    else if (choice == '2')
		f.highScores ();
	    else if (choice == '3')
		f.instructions ();
	    else
		break;
	}
	while (true);
	f.goodbye ();
    }
}





