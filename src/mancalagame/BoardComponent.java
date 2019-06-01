package mancalagame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

/**
 * This class attaches a mouse listener to the mancala board in order to choose the correct pit
 * and update other pits respectively. This is the controller portion of the MVC pattern.
 * the view portion of the MVC pattern.
 * @author Chris Vo, Kevin Prakasa, Dung Pham
 * @Version 1.0
 */
@SuppressWarnings("serial")
public class BoardComponent extends JComponent{
	   //class variable declarations
	   private BoardShape board;
	   private Point mousePoint;
	   
		/**
		 * Constructor #1: This constructor creates a new instance of BoardComponent, acquiring
		 * a reference of the mancala board and adding the mouse listener to it.
		 * @param board  a reference to the mancala board
		 * @return none
		 */
	   public BoardComponent(BoardShape board) {
	      this.board = board;
	      addMouseListener(new MouseListeners());
	   }
	   
	   public BoardShape getBoard() {
		   return board;
	   }
	   
	   /**
	   * This method overwrites the paintComponent method in order to draw the entirety of the
	   * mancala board
	   * @param g    Graphics necessary to draw shapes and text
	   * @return none
	   */
	   public void paintComponent(Graphics g) {
		   super.paintComponent(g);
		   Graphics2D g2 = (Graphics2D) g;
		   
		   board.drawBoard(g2);
	   }
	   
	   /**
	    * This inner class extends the MouseAdapter abstract class and overwrites the mouseClicked
	    * method to choose a pit and update the values of other pits respectively
	    * @authors Chris Vo, Kevin Prakasa, Dung Pham
	    * @Version 1.0
	    */
	   private class MouseListeners extends MouseAdapter {
		   /**
		   * This method checks if a player choose a pit (and not anywhere else on the board or opposing
		   * player's pit) and run the model's choosePit method.
		   * @param event  whenever a mouse performs an event of some kind
		   * @return none
		   */
		   public void mouseClicked(MouseEvent event) {
			  boolean flag;
		      mousePoint = event.getPoint();
		      
		      for(int j = 0; j < 6; j++) {
		    	  //checks if the player clicks one of their pits
		    	  if(board.getEllipses()[board.getModel().getPlayerID()][j].contains(mousePoint) && board.getModel().getPlayers()[board.getModel().getPlayerID()].getPit()[j].isChosen()) {
		    		  flag = board.getModel().ChoosePit(j);
		    		  if(!flag) { //if the player did not get a free turn, rotate players
		    			  if(board.getModel().getPlayerID() == 0) {
		    				  board.getModel().setPlayerID(1);
		    			  }
		    			  else
		    				  board.getModel().setPlayerID(0);
		    		  }
		    		  break; //no need to check for remaining pits if the pit is found
		    	  }
		      }  
		      
		      //if a player's total number of pits is empty, take all stones and declare the winner
		      //after this segment of code has been executed, no further actions is needed.
			  if(board.getModel().CheckIfEmpty()) {
				  board.getModel().TakeAllStones();
				  board.getModel().DeclareWinner();
			  }
		   }   
	   }
}
