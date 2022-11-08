package battleship;

import java.util.Arrays;
import java.util.Random;


public class Board{
	
	public static void main(String[] args) {	
		String water = "~~";
		String[] players = {"computer", "player1"};
		int[] ships = {2,3,4,5};
		int boardLength = 10;	    
		
		String[][] computerGameBoard = createGameBoard(boardLength,ships,players[0],water);	//Creating computer's game board
		String[][] playerTwoGameBoard = createGameBoard(boardLength,ships,players[1], water); //Creating player2's game board
		
		
		Game game = new Game();
		game.startGame(computerGameBoard, playerTwoGameBoard, ships); //Starting the game
	}
	
	
	/*
	 * Prints the game board
	 * @param game board and the name of the player
	 * @return No return value 
	 */
	private static void  printBoard(String[][] gameBoard, String player) {
		if (player == "computer") 
		{
			System.out.println("Computer's game board"+'\n');
		}
		else 
		{
			System.out.println("Player 2 game board"+'\n');
		}
		
		//printing the game board
		for (int i = 0; i< gameBoard.length; i++) 
		{
			System.out.println(Arrays.toString(gameBoard[i])); 
		}
		
		System.out.println('\n');
	}
	
	
	/*
	 * Creates the game board
	 * @param length of the game board, lengths of each ship, player name
	 * @return the game board
	 */
	private static String[][] createGameBoard(int boardLength, int[] ships, String player, String water) {
		String[][] gameBoard  = new String[boardLength][boardLength];
		
		for (String[] row: gameBoard)
		{
			Arrays.fill(row, water); //initializing all squares in the board to water
		}
		
		return placeShipsonBoard(gameBoard,ships,player, water);
	}
	
	
	/*
	 * Places ships on the game board
	 * @param game board, length of all ships, player name
	 * @return game board after placing ships on the board
	 */
	private static String[][] placeShipsonBoard(String[][] gameBoard, int[] ships, String player, String water) {
		int shipNumber = 0;
		String[] possibleAlignments = {"horizontal", "vertical"}; 
		int lengthofBoard = gameBoard.length;
		
		while (shipNumber < ships.length) 
		{
			// getting random location to place the ship
			int[] randomLocation = generateLocation(lengthofBoard);
			
			//generating random alignment, either horizontal or vertical
			int alignment = generateAlignment(possibleAlignments.length);
			int row= randomLocation[0];
			int col=randomLocation[1];
			
			//checking if the location is empty 
			boolean vacancy = checkForVacancy(row,col,gameBoard,alignment,ships[shipNumber], water);
			if (vacancy == true) 
			{
				int[] position = {row,col};
					
				//If location is empty, place ship on the board
				placeShipAtPosition(gameBoard,position,ships[shipNumber],alignment);
				shipNumber++; //Incrementing the number of ships placed on the board
			}
		}
		
		printBoard(gameBoard,player); //printing the board
		return gameBoard;
	}

	
	/*
	 * checks for vacancy on the board at given position
	 * @param row index, column index, game board, alignment, length of the ship
	 * @return true if the is a vacancy, else returns false
	 */
	private static boolean checkForVacancy(int row, int col,String[][] gameBoard, int alignment,int shipLength, String water) {
		
		if (alignment == 0) 
		{
			//horizontal alignment
			int i = row;
			
			//if ship goes outside of the grid, return false
			if (col+shipLength > gameBoard.length) 
			{
				return false;
			}
			
			//checking if there are no ships at the location
			for (int j=col; j < col+shipLength; j++) 
			{
				if (gameBoard[i][j] != water) {
					return false;
				}
			}
		}
		
		else 
		{
			//vertical alignment
			int j = col;
			
			if (row+shipLength > gameBoard.length) {
				return false;
			}
			
			for (int i=row; i < row+shipLength; i++) {
				//checking if there are no ships at the location
				if (gameBoard[i][j] != water) {
					return false;
				}
			}
		}
		
		return true;
	}

	
	/*
	 * Generates ship alignment, either vertical or horizontal
	 *@param array of all elements that are possible in the game
	 *@return one random alignment
	 */
	private static int generateAlignment(int alignments) {
		int randomAlignment = new  Random().nextInt(alignments); //generating random integer for alignment
		
		return randomAlignment;
	}
	
	
	/*
	 * places ship at a given position
	 * @param game board, position of the ship, length of the ship, alignment
	 * @return game board after placing ship on the board
	 */
	private static String[][] placeShipAtPosition(String[][] gameBoard, int[] position, int shipLength, int alignment) {
		int pos= 0;
		int row = position[0];
		int col = position[1];
		
		if (alignment == 0) 
		{
			while (pos < shipLength) 
			{
				gameBoard[row][col] = "S" + Integer.toString(shipLength) ; //giving a number to the ship
				col++;
				pos++;
			}
		}
		else 
		{
			while (pos < shipLength) 
			{
				gameBoard[row][col] = "S" + Integer.toString(shipLength) ; //giving a number to the ship
				row++;
				pos++;
			}
		}
		
		return gameBoard;
	}

	
	/*
	 * generates random location to place a ship on the game board
	 * @param length of the game board
	 * @return coordinates of the location
	 */
	public static int[] generateLocation(int lengthofBoard) {
		int[] location = new int[2];
		
		for (int i=0; i< 2; i++) 
		{
			location[i] = new Random().nextInt(lengthofBoard);	// generating random location
		}
		
		return location;
	}	
}