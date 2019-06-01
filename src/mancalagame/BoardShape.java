package mancalagame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;

/**
 * This class draws the entire mancala board, including the pits, mancala, and stones. This is
 * the view portion of the MVC pattern.
 * @author Chris Vo, Kevin Prakasa, Dung Pham
 * @Version 1.0
 */
public class BoardShape {
	//class variable declarations
	private int width;
	private int height;
	private Ellipse2D[][] ellipses; //to save the pits in the board in order to check if a pit has been clicked
	private MancalaModel model; //contains a reference of the model (to acquire the values from the model 
	//to draw necessary information, it will not change any value from the model)
	
	/**
	 * Constructor #1: This constructor creates a new instance of BoardShape, setting the
	 * height and width. Additionally it creates a new 2-D array of Ellipse2D to store in
	 * the pits and stores a reference to the model.
	 * @param height  to determine the size of particular shapes
	 * @param width   to determine the size of particular shapes
	 * @param model   a reference to the model
	 * @return none
	 */
	public BoardShape(int height, int width, MancalaModel model)
	{
		this.height = height;
		this.width = width;
		ellipses = new Ellipse2D[2][6];
		this.model = model;
	}
	
	/**
	 * This method returns the shape of the pits of the board
	 * @param none
	 * @return ellipses  a 2-D array containing the shapes of the pits
	 */
	public Ellipse2D[][] getEllipses() {
		return ellipses;
	}
	
	public void setEllipses(int i, int j, Ellipse2D ellipse) {
		ellipses[i][j] = ellipse;
	}
	
	/**
	 * This method returns the reference of the model
	 * @param none
	 * @return model  a reference to the model
	 */
	public MancalaModel getModel() {
		return model;
	}
	
	public void setModel(MancalaModel model) {
		this.model = model;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	/**
	 * This method draws the mancala board and the pits
	 * @param g2  Graphics2D necessary to draw shapes and text
	 * @return none
	 */
	public void drawBoard(Graphics2D g2) {
		//method variable declarations
		final int x = 350;
		final int y = 290;
		
		g2.setFont(new Font("Bold", Font.BOLD, 36));
		RoundRectangle2D board = new RoundRectangle2D.Double(x, y, width, height, x, y);
		g2.draw(board);
		
		String playerid;
		if(model.getPlayerID() == 0)
			playerid = "A";
		else
			playerid = "B";
		
		//if a side's pits are empty, declare the winner. Otherwise notify players whose turn is it.
		if(!model.CheckIfEmpty())
			g2.drawString("It's player " + playerid + "'s turn!", (int)(board.getCenterX()) - 150, (int)(board.getCenterY() - 300));
		else
			g2.drawString("Player " + playerid + " is the winner!", (int)(board.getCenterX()) - 150, (int)(board.getCenterY() - 300));
		
		RoundRectangle2D mancala1 = new RoundRectangle2D.Double(x + 25, y + height/5.5, width/11, height/1.5, x, y);
		g2.draw(mancala1);
		RoundRectangle2D mancala2 = new RoundRectangle2D.Double(x + 795, y + height/5.5, width/11, height/1.5, x, y);
		g2.draw(mancala2);
		
		g2.setFont(new Font("Bold", Font.BOLD, 22));
		
		//more method variable declarations
		final int pitGapX = 116;
		final int pitAGapY = 80;
		final int pitBGapY = 240;	
		int pitRadius = 100;
		String id = new String();
		Ellipse2D temp;
		
		//for loop to create the pits for each side
		for(int i = 0; i < model.getPlayers().length; i++) {
			if(i == 0)
				id = "A";
			if(i == 1)
				id = "B";
			for(int j = 0; j < model.getPlayers()[i].getPit().length; j++) {
				if(i == 0) {
					temp = new Ellipse2D.Double((x + (pitGapX * (j + 1))) - 5 , y + pitBGapY, pitRadius, pitRadius);
					g2.drawString(id + Integer.toString(j + 1), (int)(temp.getCenterX() - 10), (int)(temp.getCenterY() - 60));
				}
				else {
					temp = new Ellipse2D.Double((x + (pitGapX * (model.getPlayers()[i].getPit().length - j))) - 5 , y + pitAGapY, pitRadius, pitRadius);
					g2.drawString(id + Integer.toString(j + 1), (int)(temp.getCenterX() - 10), (int)(temp.getCenterY() - 60));
				}
				g2.draw(temp);
				ellipses[i][j] = temp;
			}
		}
		
		drawStones(g2, mancala1, mancala2);
		
			
	}
	
	/**
	 * This method draws the stones for each pit
	 * @param g2        Graphics2D necessary to draw shapes and text
	 * @param mancala1  Player 2's mancala
	 * @param mancala2  Player 1's mancala
	 * @return none
	 */
	private void drawStones(Graphics2D g2, RoundRectangle2D mancala1, RoundRectangle2D mancala2) {
		//method variable declarations
		double XAXIS;
		double YAXIS;
		int counter = 1;
		Ellipse2D temp;
		
		g2.setColor(Color.GREEN);
		
		//to draw the stones in Player 2's (upper side) pits
		for(int i= 0 ; i< 6 ; i++){
			YAXIS = ellipses[1][i].getMinY();
			for(int j = 0 ; j < model.getPlayers()[1].getPit()[i].getStone(); j++) {
				XAXIS = ellipses[1][i].getMinX() + (20 * counter);
				if(!(ellipses[1][i].contains(XAXIS, YAXIS))) {
					counter = 1;
					YAXIS += 20;
					XAXIS = ellipses[1][i].getMinX() + (20 * counter);
				}
				temp = new Ellipse2D.Double(XAXIS, YAXIS, 15, 15);
				g2.fill(temp);
				g2.draw(temp);
				counter++;
			}
			counter = 1;
		}
				
		//to draw the stones in Player 1's (lower side) pits
		for(int i= 0 ; i< 6 ; i++){
			YAXIS = ellipses[0][i].getMinY();
			for(int j = 0 ; j < model.getPlayers()[0].getPit()[i].getStone(); j++) {
				XAXIS = ellipses[0][i].getMinX() + (20 * counter);
				if(!(ellipses[0][i].contains(XAXIS, YAXIS))) {
					counter = 1;
					YAXIS += 20;
					XAXIS = ellipses[0][i].getMinX() + (20 * counter);
				}
				temp = new Ellipse2D.Double(XAXIS, YAXIS, 15, 15);
				g2.fill(temp);
				g2.draw(temp);
				counter++;
			}
			counter = 1;
		}
		
		XAXIS = mancala1.getMinX() + 20;
		counter = 1;
		
		//to draw the stones (if any) in Player 2's mancala
		for(int j = 0; j < model.getPlayers()[1].getMancala().getStone(); j++) {
			YAXIS = mancala1.getMinY() + (20 * counter);
			if(!(mancala1.contains(XAXIS, YAXIS))) {
				counter = 1;
				XAXIS += 20;
				YAXIS = mancala1.getMinY() + (20 * counter);
			}
			temp = new Ellipse2D.Double(XAXIS, YAXIS, 15, 15);
			g2.fill(temp);
			g2.draw(temp);
			counter++;
		}
		
		XAXIS = mancala2.getMinX() + 20;
		YAXIS = mancala2.getMinY() + (20 * counter);
		counter = 1;
		
		//to draw the stones (if any) in Player 1's mancala
		for(int j = 0 ; j < model.getPlayers()[0].getMancala().getStone(); j++) {
			g2.setColor(Color.GREEN);	
			YAXIS = mancala2.getMinY() + (20 * counter);
			if(!(mancala2.contains(XAXIS, YAXIS))) {
				counter = 1;
				XAXIS += 20;
				YAXIS = mancala1.getMinY() + (20 * counter);
			}
			temp = new Ellipse2D.Double(XAXIS, YAXIS, 15, 15);
			g2.fill(temp);
			g2.draw(temp);
			counter++;
		}
	}
}