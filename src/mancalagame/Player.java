package mancalagame;
/**
 * This class creates a player with their designated pits and mancala. It also keeps track
 * of the number of stones in their designated pits and mancala. This class is essentially 
 * an extension of the model.
 * @author Chris Vo, Kevin Prakasa, Dung Pham
 * @Version 1.0
 */
public class Player {
	//class variable declarations
	private Pit[] pits;
	private Pit Mancala;
	
	/**
	 * Constructor #1: This constructor creates a new instance of Player with class 
	 * variables initialized to null or equivalent.
	 * @param none
	 * @return none
	 */
	public Player() {
		Mancala = null;
		pits = null;
	}
	
	/**
	 * Constructor #2: This constructor creates a new instance of Player, creating a 
	 * new fixed number of pits (6) and a mancala. The pits are filled with a designated
	 * number of stones initially.
	 * @param size   the total amount of stones for each pit at the start
	 * @return none
	 */
	public Player(int size) {
		Mancala = new Pit();
		pits = new Pit[6];
		for(int i = 0; i < pits.length; i++)
			pits[i] = new Pit(size);
	}

	/**
	 * This method returns the total number of pits from a player
	 * @param none
	 * @return pits  a player's designated total number of pits
	 */
	public Pit[] getPit() {
		return pits;
	}
	
	/**
	 * This method returns the mancala from a player
	 * @param none
	 * @return mancala  a player's designated mancala
	 */
	public Pit getMancala() {
		return Mancala;
	}
	
	/**
	 * This inner class creates a pit that stores in the total number of stones. It also contains
	 * methods necessary to change the value of the stones when necessary
	 * @author Chris Vo, Kevin Prakasa, Dung Pham
	 * @Version 1.0
	 */
	protected class Pit {
		//class variable declarations
		private int size; //the amount of stones in the pit
		private boolean chosen;
		
		/**
		 * Constructor #1: This constructor creates a new instance of Pit with class 
		 * variables initialized to 0 or equivalent.
		 * @param none
		 * @return none
		 */
		public Pit() {
			size = 0;
			chosen = true;
		}
		
		/**
		 * Constructor #2: This constructor creates a new instance of Pit, setting
		 * the total number of stones to an initial amount
		 * @param size   the total amount of stones for each pit at the start
		 * @return none
		 */
		public Pit(int size) {
			this.size = size;
			chosen = true;
		}

		public boolean isChosen() {
			return chosen;
		}

		public void setChosen(boolean chosen) {
			this.chosen = chosen;
		}

		/**
		 * This method checks whether the pit contains no stones
		 * @param none
		 * @return boolean value  true if the pit has no stones, false otherwise
		 */
		public boolean isEmpty() {
			if(size == 0)
				return true;
			else
				return false;
		}
		
		/**
		 * This method returns the total number of stones of a pit
		 * @param none
		 * @return size  the amount of stones in the pit
		 */
		public int getStone() {
			return size;
		}
		
		/**
		 * This method sets the total number of stones to a new value
		 * @param size  a different amount of stones (usually 0)
		 * @return none
		 */
		public void setStone(int size) {
			this.size = size;
		}
		
		/**
		 * This method increments the total number of stones by 1
		 * @param none
		 * @return none
		 */
		public void AddStone() {
			size++;
		}
		
		/**
		 * This overloaded method increments the total number of stones by a specific amount
		 * @param stones  the amount of stones to be added in a pit
		 * @return none
		 */
		public void AddStone(int stones) {
			size += stones;
		}
		
		/**
		 * This overloaded method decrements the total number of stones by 1, given that the
		 * pit is not empty
		 * @param none
		 * @return none
		 */
		public void DeleteStone() {
			if(!(isEmpty()))
				size--;
		}
	}
}
