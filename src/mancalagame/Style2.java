package mancalagame;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;

public class Style2 extends BoardShape {
	//class variable declarations
	private Color c;
	/**
	 * constructor which inherit from boardShape
	 * new variable color to set color for the board and stones
	 * @param height
	 * @param width
	 * @param model
	 * @param c
	 */
	
	public Style2(int height, int width, MancalaModel model, Color c)
	{
		super(height, width, model);
		this.c = c;
	}
	/**
	 * get color for the stones and board
	 * @return
	 */
	public Color getColor(){
		return c;
	}
	
	/**
	 * redraw the board and label each pit
	 * as well as set the color for each pit and board 
	 * and change color and shape of the stone as a style
	 */
	public void drawBoard(Graphics2D g2) {
		final int x = 350;
		final int y = 290;
		
		g2.setFont(new Font("Courier New", Font.BOLD, 30));
		g2.setStroke(new BasicStroke(5));
		g2.setColor(Color.GREEN);
		g2.drawString("Player A --->", 720, 720);
		
		g2.setFont(new Font("Courier New", Font.BOLD, 30));
		g2.setStroke(new BasicStroke(5));
		g2.setColor(Color.BLACK);
		g2.drawString("<--- Player B", 720, 270);
		
		g2.setColor(Color.ORANGE);
		g2.setStroke(new BasicStroke(4));
		g2.setFont(new Font("Courier New", Font.BOLD, 30));
		
		g2.drawString("M", 300, 350);
		g2.drawString("A", 300, 380);
		g2.drawString("N", 300, 410);
		g2.drawString("C", 300, 440);
		g2.drawString("A", 300, 470);
		g2.drawString("L", 300, 500);
		g2.drawString("A", 300, 530);
		g2.drawString(" ", 300, 560);
		g2.drawString("B", 300, 565);
		
		//draw mancala description A
		g2.setStroke(new BasicStroke(4));
		g2.setFont(new Font("Courier New", Font.BOLD, 30));
		g2.setColor(Color.PINK);
		g2.drawString("M", 1280, 340);
		g2.drawString("A", 1280, 370);
		g2.drawString("N", 1280, 400);
		g2.drawString("C", 1280, 430);
		g2.drawString("A", 1280, 460);
		g2.drawString("L", 1280, 490);
		g2.drawString("A", 1280, 520);
		g2.drawString(" ", 1280, 550);
		g2.drawString("A", 1280, 555);
		
		g2.setColor(Color.BLUE);
		g2.setFont(new Font("Bold", Font.BOLD, 36));
		RoundRectangle2D board = new RoundRectangle2D.Double(x, y, super.getWidth(), super.getHeight(), x, y);
		g2.draw(board);
		
		String playerid;
		if(super.getModel().getPlayerID() == 0)
			playerid = "A";
		else
			playerid = "B";
		
		if(!super.getModel().CheckIfEmpty())
			g2.drawString("It's player " + playerid + "'s turn!", (int)(board.getCenterX()) - 150, (int)(board.getCenterY() - 300));
		else
			g2.drawString("Player " + playerid + " is the winner!", (int)(board.getCenterX()) - 150, (int)(board.getCenterY() - 300));
		
		RoundRectangle2D mancala1 = new RoundRectangle2D.Double(x + 25, y + super.getHeight()/5.5, super.getWidth()/11, super.getHeight()/1.5, x, y);
		g2.draw(mancala1);
		RoundRectangle2D mancala2 = new RoundRectangle2D.Double(x + 795, y + super.getHeight()/5.5, super.getWidth()/11, super.getHeight()/1.5, x, y);
		g2.draw(mancala2);
		
		g2.setFont(new Font("Bold", Font.BOLD, 22));
		int pitRadius = 100;
		
		final int pitGapX = 116;
		final int pitAGapY = 80;
		final int pitBGapY = 240;		
		String id = new String();
		Ellipse2D temp;
		
		for(int i = 0; i < super.getModel().getPlayers().length; i++) {
			if(i == 0)
				id = "A";
			if(i == 1)
				id = "B";
			for(int j = 0; j < super.getModel().getPlayers()[i].getPit().length; j++) {
				if(i == 0) {
					temp = new Ellipse2D.Double((x + (pitGapX * (j + 1))) - 5 , y + pitBGapY, pitRadius, pitRadius);
					g2.drawString(id + Integer.toString(j + 1), (int)(temp.getCenterX() - 10), (int)(temp.getCenterY() - 60));
				}
				else {
					temp = new Ellipse2D.Double((x + (pitGapX * (super.getModel().getPlayers()[i].getPit().length - j))) - 5 , y + pitAGapY, pitRadius, pitRadius);
					g2.drawString(id + Integer.toString(j + 1), (int)(temp.getCenterX() - 10), (int)(temp.getCenterY() - 60));
				}
				g2.draw(temp);
				super.setEllipses(i, j, temp);
			}
		}
		
		drawStones(g2, mancala1, mancala2);
	}
	
/**
 * draw the stone and set color for the stones
 * draw the shape for style 1
 * @param g2
 * @param mancala1
 * @param mancala2
 */
	private void drawStones(Graphics2D g2, RoundRectangle2D mancala1, RoundRectangle2D mancala2) {
		double XAXIS;
		double YAXIS;
		int counter = 1;
		Ellipse2D temp;
		
		g2.setColor(this.c);
		
		for(int i= 0 ; i< 6 ; i++){
			YAXIS = super.getEllipses()[1][i].getMinY();
			for(int j = 0 ; j < super.getModel().getPlayers()[1].getPit()[i].getStone(); j++) {
				XAXIS = super.getEllipses()[1][i].getMinX() + (15 * counter);
				if(!(super.getEllipses()[1][i].contains(XAXIS, YAXIS))) {
					counter = 1;
					YAXIS += 15;
					XAXIS = super.getEllipses()[1][i].getMinX() + (15 * counter);
				}
				temp = new Ellipse2D.Double(XAXIS, YAXIS, 10, 10);
				g2.fill(temp);
				g2.draw(temp);
				counter++;
			}
			counter = 1;
		}
				
		for(int i= 0 ; i< 6 ; i++){
			YAXIS = super.getEllipses()[0][i].getMinY();
			for(int j = 0 ; j < super.getModel().getPlayers()[0].getPit()[i].getStone(); j++) {
				XAXIS = super.getEllipses()[0][i].getMinX() + (15 * counter);
				if(!(super.getEllipses()[0][i].contains(XAXIS, YAXIS))) {
					counter = 1;
					YAXIS += 15;
					XAXIS = super.getEllipses()[0][i].getMinX() + (15 * counter);
				}
				temp = new Ellipse2D.Double(XAXIS, YAXIS, 10, 10);
				g2.fill(temp);
				g2.draw(temp);
				counter++;
			}
			counter = 1;
		}
		
		XAXIS = mancala1.getMinX() + 15;
		counter = 1;
		
		for(int j = 0; j < super.getModel().getPlayers()[1].getMancala().getStone(); j++) {
			YAXIS = mancala1.getMinY() + (15 * counter);
			if(!(mancala1.contains(XAXIS, YAXIS))) {
				counter = 1;
				XAXIS += 15;
				YAXIS = mancala1.getMinY() + (15 * counter);
			}
			temp = new Ellipse2D.Double(XAXIS, YAXIS, 10, 10);
			g2.fill(temp);
			g2.draw(temp);
			counter++;
		}
		
		XAXIS = mancala2.getMinX() + 15;
		YAXIS = mancala2.getMinY() + (15 * counter);
		counter = 1;
		for(int j = 0 ; j < super.getModel().getPlayers()[0].getMancala().getStone(); j++) {
			g2.setColor(this.c);	
			YAXIS = mancala2.getMinY() + (15 * counter);
			if(!(mancala2.contains(XAXIS, YAXIS))) {
				counter = 1;
				XAXIS += 15;
				YAXIS = mancala1.getMinY() + (15 * counter);
			}
			temp = new Ellipse2D.Double(XAXIS, YAXIS, 10, 10);
			g2.fill(temp);
			g2.draw(temp);
			counter++;
		}
	}
}