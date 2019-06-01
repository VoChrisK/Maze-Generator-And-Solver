package mancalagame;
import java.util.*;

import javax.swing.event.*;

/**
 * This class constructs the model portion of the mancala game. This class is responsible
 * for storing and maintaining data from each players and listeners. Additionally, it is
 * responsible for changing the values of the pits and mancalas whenever a pit is chosen,
 * keeping track of whether one side of the pits are empty and take all opposing pits' stones,
 * and update the listeners respectively. This is the model portion of the MVC pattern.
 * @author Chris Vo, Kevin Prakasa, Dung Pham
 * @Version 1.0
 */
public class MancalaModel {
	private final int MaxUndos = 3;
	//class variable declarations
	private ArrayList<ChangeListener> listeners; //to update its views when necessary
	private Player[] players; //the amount of players participating in the game
	private int playerid; //to keep track of each player's turns
	private int originalplayerid;
	private MancalaModel backup;
	private int pitTracker; //to keep track of the last pit chosen after the next undo
	private int nextPitSaved;
	private int undos;
	
	/**
	 * Constructor #1: This constructor creates a new instance of MancalaModel with class 
	 * variables initialized to null or equivalent.
	 * @param none
	 * @return none
	 */
	public MancalaModel() {
		listeners = null;
		players = new Player[2];
		for(int i = 0; i < players.length; i++)
			players[i] = new Player(0);
		backup = null;
		originalplayerid = 0;
		playerid = 0;
		undos = 0;
		pitTracker = -1;
		nextPitSaved = 0;
	}
	
	/**
	 * Constructor #2: This constructor creates a new instance of MancalaModel, creating a 
	 * new arraylist of listeners and two new players.
	 * @param MaxStones  the total amount of stones for each pit at the start
	 * @return none
	 */
	public MancalaModel(int MaxStones){
		listeners = new ArrayList<ChangeListener>();
		players = new Player[2];
		playerid = 0; //always start with the first player
		originalplayerid = 0;
		for(int i = 0; i < 2; i++)
			players[i] = new Player(MaxStones); //creates a new player with designated pits 
		    //assigned to the maximum amount of stones
		backup = new MancalaModel();
		backup.listeners = new ArrayList<ChangeListener>();
		for(int i = 0; i < 2; i++)
			backup.players[i] = new Player(MaxStones); //do the same for backup
		undos = 0;
		pitTracker = -1;
		nextPitSaved = 0;
	}
	
	/**
	 * This method returns the total number of players
	 * @param none
	 * @return players  the total number of players in the mancala game
	 */
	public Player[] getPlayers() {
		return players;
	}
	
	public ArrayList<ChangeListener> getListeners() {
		return listeners;
	}
	
	public int getOriginalPlayerID() {
		return originalplayerid;
	}
	
	/**
	 * This method returns the current player's turn in the game
	 * @param none
	 * @return playerid  to keep track of each player's turns
	 */
	public int getPlayerID() {
		return playerid;
	} 
	
	/**
	 * This method sets the value of playerid to a new value - in this case: 
	 * this alternates between players
	 * @param playerid  to keep track of each player's turns
	 * @return none
	 */
	public void setPlayerID(int playerid) {
		this.playerid = playerid;
	}
	
	public boolean setBackup(MancalaModel backup) {
		if(undos < MaxUndos) {
			this.listeners = backup.listeners;
			for(int i = 0; i < backup.players.length; i++) {
				for(int j = 0; j < this.players[i].getPit().length; j++) {
					this.players[i].getPit()[j].setStone(new Integer(backup.players[i].getPit()[j].getStone()));
					this.players[i].getMancala().setStone(new Integer(backup.players[i].getMancala().getStone()));
				}
			}
		
			this.playerid = backup.playerid;
			
			this.players[originalplayerid].getPit()[nextPitSaved].setChosen(false);
			if(pitTracker != -1)
				this.players[originalplayerid].getPit()[pitTracker].setChosen(true);
			pitTracker = nextPitSaved;
		
			for(ChangeListener listener : listeners)
				listener.stateChanged(new ChangeEvent(this));
			
			undos++;
			
			return true;
		}
		else {
			if(originalplayerid != playerid) {
				undos = 0;
				for(int i = 0; i < players[originalplayerid].getPit().length; i++)
					players[originalplayerid].getPit()[i].setChosen(true);
				pitTracker = -1;
			}
			return false;
		}
	}
	
	public MancalaModel getBackup() {
		return backup;
	}

	/**
	 * This method changes and updates the number of stones of subsequent pits when a player chooses
	 * a pit. Additionally, it keeps track of the game's logic: landing an empty pit on the 
	 * player's side will take all stones from the player's and the opposite pits and keeping track of 
	 * whether the player gets a free turn upon putting the last stone in the player's mancala. In the 
	 * end, it will notify the model's listeners after it has completed the process.
	 * @param  index          the pit number that the player chose
	 * @return boolean value  true if the player gains a free turn, false otherwise
	 */
	public boolean ChoosePit(int index){
		//method variable declarations
		nextPitSaved = index;
		int stones = players[playerid].getPit()[index].getStone(); //the amount of stones to be placed in other pits/mancala
		boolean mancalaflag = false; //records whether the player gets a free turn or not
		originalplayerid = playerid; //records the original player because playerid will alternate between players
		boolean EmptyPit = false; //records whether the player lands on an empty pit on their side
		this.backup.listeners = this.listeners;
		
		for(int i = 0; i < this.players.length; i++) {
			for(int j = 0; j < this.players[i].getPit().length; j++) {
				this.backup.players[i].getPit()[j].setStone(new Integer(this.players[i].getPit()[j].getStone()));
				this.backup.players[i].getMancala().setStone(new Integer(this.players[i].getMancala().getStone()));
			}
		}
		
		this.backup.playerid = this.playerid;
		
		players[playerid].getPit()[index].setStone(0);
		
		while(stones > 0) { //as long as there are stones to be placed in other pits
			if((index + 1) < players[playerid].getPit().length) { //if it does not exceed the total # of pits
				if(originalplayerid == playerid) { 
					if(players[originalplayerid].getPit()[index + 1].isEmpty() && stones == 1)
						EmptyPit = true;
				}
				players[playerid].getPit()[index + 1].AddStone();
				index++;
				mancalaflag = false;
			}
			else { //if it exceeds the # of pits, put the stone in the mancala and change to the other players pit
				if(originalplayerid == playerid) //prevent from adding to opponent's mancala
					players[playerid].getMancala().AddStone();
				if(stones == 1) //if the last stone is placed in the player's mancala
					mancalaflag = true; //to get an extra turn
				if(playerid == 0)
					playerid = 1; //changes to player 2
				else
			 		playerid = 0; //changes to player 1
				
				index = -1; //set index to -1 to preserve the if clause's condition
			}
			stones--;
		}
		
		if(index == -1) //set index to 0 if loop terminated early
			index = 0;
		
		if(EmptyPit) {
			if(originalplayerid == 0) //to set the playerid to the opposing player
				playerid = 1;
			else
				playerid = 0;
			//take all the stones from the player's pit and opposite pit and put them all in the player's mancala
			players[originalplayerid].getMancala().AddStone(players[playerid].getPit()[5 - index].getStone());
			players[originalplayerid].getMancala().AddStone(players[originalplayerid].getPit()[index].getStone());
			players[playerid].getPit()[5 - index].setStone(0);
			players[originalplayerid].getPit()[index].setStone(0);
		}
		
		//notify the listeners after the stones have been properly placed
		for(ChangeListener listener : listeners)
			listener.stateChanged(new ChangeEvent(this));
		
		playerid = originalplayerid; //change back to the original player to correctly choose the next player
		return mancalaflag;
	}
	
	/**
	 * This method adds the total amount of stones from each sides' pits, adds them to the opposing
	 * player's mancala, and sets the pits' stone count to 0.
	 * this alternates between players
	 * @param none
	 * @return none
	 */
	public void TakeAllStones() {
		//method variable declarations
		int counter1 = 0;
		int totalstones1 = 0;
		int totalstones2 = 0;
		
		//to count the total number of stones from each players' pits
		for(int i = 0; i < 6; i++) {
			totalstones1 += players[0].getPit()[i].getStone();
			totalstones2 += players[1].getPit()[i].getStone();
			
			if(players[0].getPit()[i].isEmpty())
				counter1++;
		}
		
		//put all stones counted from player 2 to player 1's mancala, and set player 2's pits' stone count to 0
		if(counter1 == 6) {
			players[0].getMancala().AddStone(totalstones2);
			for(int i = 0; i < 6; i++)
				players[1].getPit()[i].setStone(0);
		}
		//put all stones counted from player 1 to player 2's mancala, and set player 1's pits' stone count to 0
		else {
			players[1].getMancala().AddStone(totalstones1);
			for(int i = 0; i < 6; i++)
				players[0].getPit()[i].setStone(0);
		}
		
		//notify the listeners after the pits and mancala have been updated
		for(ChangeListener listener : listeners)
			listener.stateChanged(new ChangeEvent(this));
	}
	
	public int getUndos() {
		return undos;
	}

	public void setUndos(int undos) {
		this.undos = undos;
	}

	/**
	 * This method checks whether each side's total number of pits are all empty
	 * @param none
	 * @return boolean value  true if a side's pits are all empty, false otherwise
	 */
	public boolean CheckIfEmpty() {
		int counter = 0;
		
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 6; j++) {
				if(players[i].getPit()[j].isEmpty()) {
					players[i].getPit()[j].setChosen(false);
					counter++;
				}
				else
					players[i].getPit()[i].setChosen(true);
			}
			if(counter == 6)
				return true;
			else
				counter = 0; //set counter to 0 to check the other side
		}
		
		return false;
	}
	
	/**
	 * This method compares the values of each player's mancala, and choose the winner
	 * accordingly
	 * @param none
	 * @return none
	 */
	public void DeclareWinner() {
		if(players[0].getMancala().getStone() > players[1].getMancala().getStone())
			playerid = 0;
		else
			playerid = 1;
	}
	
	/**
	 * This method adds a view to the model
	 * @param listener  the view variable: to update GUI whenever the model has been changed
	 * @return none
	 */
    public void addChangeListener(ChangeListener listener) {
        listeners.add(listener);
    }
}