/*
Team: CKD

Team Grade: (100/100)
Weekly Report: (4/4) points
Final Report: (10/10) points
MVC pattern: (6/6) points
Strategy pattern: (6/6) points
Functionality: (74/74) points
 */

package mancalagame;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This class contains the main method. It tests the functionalities of all the other classes
 * and creating a frame for the game.
 * @author Chris Vo, Kevin Prakasa, Dung Pham
 * @Version 1.0
 */
public class MancalaTest {

	public static void main(String[] args) {
		//class variable declarations
		MancalaModel model;
		int Height = 400;
		int Width = 900;
		JFrame frame = new JFrame();
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
		frame.setSize(1500, 900);
		BoardComponent component;
		
		ImageIcon icon = new ImageIcon("C:\\Users\\ab\\workspace\\Mancala_group\\poke1.png");
		
		Object[] numberOfStones = {" A:3 ", " A:4 ", " B:3 ", " B:4 " };
	    int input = JOptionPane.showOptionDialog(null, "\n\nWellcome to MANCALA GAME produced by TEAM: ' CKD ' \nPlease choose Style and Number of Stones:\n  ", "MANCALA CKD", 
	    		  JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION,  icon, numberOfStones, numberOfStones[0]);

	      if (input == 0) {
	    	model = new MancalaModel(3);
	    	component = new BoardComponent(new Style1(Height, Width, model,Color.ORANGE));
	      } else if (input == 1) {
	    	model = new MancalaModel(4);	  		
	  		component = new BoardComponent(new Style1(Height, Width, model,Color.ORANGE));
	      }
	      else if (input ==2){
	    	model = new MancalaModel(3);  		
	  		component = new BoardComponent(new Style2(Height, Width, model,Color.RED));
	      }
	      else if(input == 3){
	    	model = new MancalaModel(4);
	  		component = new BoardComponent(new Style2(Height, Width, model,Color.RED));
	      }
	      else
	      {
	    	 model = new MancalaModel(3);
	    	 component = new BoardComponent(new BoardShape(Height, Width, model));
	         System.exit(0);
	      }
	      
	    JButton undo = new JButton("Undo");
	    undo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!model.setBackup(model.getBackup())) {
					JFrame error = new JFrame();
					error.setLayout(new BoxLayout(error.getContentPane(), BoxLayout.PAGE_AXIS));
					JTextField msg = new JTextField("YOU HAVE REACHED THE MAXIMUM NUMBER OF UNDOS");
					msg.setFont(new Font("Serif", Font.BOLD, 24));
					msg.setEditable(false);
					JButton ok = new JButton("OK");
					ok.setLocation(msg.getWidth(), msg.getHeight());
					ok.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							error.dispose();
						}
						
					});
					JPanel temp = new JPanel();
					temp.add(msg);
					temp.add(ok);
					error.add(temp);
					error.setLocation(frame.getWidth()/4, frame.getHeight()/2);
					error.pack();
					error.setVisible(true);
				}
			}
	    	
	    });
	  
		ChangeListener listener = new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				component.repaint();
			}
		};
		
		model.addChangeListener(listener);

		frame.add(component);
		frame.add(undo);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
