package battleship;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import javax.swing.RowFilter;


public class Game {

	private String[][] computerGameBoard;
	private String[][] playerTwoGameBoard;
	private int[] ships;
	
	private int computerScore = 0;
	private int playerTwoScore = 0;
	private HashMap<Integer, Integer> computerScoreBoard;
	private HashMap<Integer, Integer> playerTwoScoreBoard;
	
	private String kill = "XX";
	private String visitedSquare = "--";
	
	private Scanner scanner;
	
	/*
	 *Method to play the game
	 *@param computer game board, player2 game board, name of the players, ship lengths
	 *@return No return value 
	 */
	public void startGame(String[][] computerBoard, String[][] playerTwoBoard, int[] shipLengths) {
		
		scanner = new Scanner(System.in);  
		computerGameBoard = computerBoard;
		playerTwoGameBoard = playerTwoBoard;
		ships = shipLengths;
		int lengthOfGrid = computerGameBoard.length;
		int turn = 1; // setting first turn to computer
		int[] coordinates;
		
		computerScoreBoard = generateHitMap(ships.length); //Generating initial score board of computer
		playerTwoScoreBoard = generateHitMap(ships.length); // Generating initial score board of player2
		
		while (computerScore != ships.length || playerTwoScore != ships.length) {
			
			coordinates = generatecoordinates(lengthOfGrid, turn);
			int row = coordinates[0];
			int col = coordinates[1];

			if (turn%2 != 0) 
			{
				//Computer's turn
				System.out.println("Computer's coordinates are: "+Arrays.toString(coordinates));
				computerScore = getComputerScore(computerScore,row, col);	
				
				if (computerScore == ships.length) 
				{
					//If all ships of computer are sunk, declare computer as a winner
					System.out.println("All ships of player1 were sunk"+"\n");
					System.out.println("Computer has won!!"+'\n');
					break;
				}
			}
			else 
			{
				//Player2's turn
				playerTwoScore = getPlayerTwoScore(playerTwoScore, row, col);	
				
				if (playerTwoScore == ships.length) 
				{
					//If all ships of the player2 are sunk, declare player2 as a winner
					System.out.println("All ships of computer were sunk"+"\n");
					System.out.println("Player2 has won!!"+'\n');
					break;
				}
			}
			
			turn++; 	//Change the turn	
		}
		
		scanner.close();
	}
	
	
	/*
	 * calculates player2's score based on hit or miss. 
	 * @param current score of player2, row index, column index
	 * @return player2 score
	 */
	private int getPlayerTwoScore(int playerTwoScore, int row, int col) {
		
		//Check if ship is present at the coordinates choose by the player's 
		if (computerGameBoard[row][col].contains("S")) 
		{	
			//if ship is present then it's a hit
			System.out.println("Congratulations. It's a hit..!");
			
			String[] ship = computerGameBoard[row][col].split("");
			int shipNumber = Integer.parseInt(ship[1]);
			
			//If there is a hit updating the score board
			playerTwoScoreBoard.put(shipNumber,playerTwoScoreBoard.get(shipNumber) - 1);
			System.out.println("player2's Score Board: "+ playerTwoScoreBoard+ "\n");
			
			//Checking if ship is sunk
			if (playerTwoScoreBoard.get(shipNumber) ==0) 
			{
				System.out.print("computer's ship is sunk"+"\n");		
				playerTwoScore++;
			}
		
			computerGameBoard[row][col] = kill; //Marking square as Kill 
		}
		
		// If there is no ship at the location, consider it as miss
		else 
		{
			System.out.println("miss"+'\n');
			computerGameBoard[row][col] = visitedSquare; //Marking square as visited
		}
		
		return playerTwoScore;
	}

	
	/*
	 * Calculate computer score based on hit or miss
	 * @param current score of computer, row index, column index
	 * @return computer's score
	 */
	private int getComputerScore(int compurtScore, int row, int col) {
		
		//Check if ship is at the location. 
		//If yes, consider as hit and update the score board, score
		if (playerTwoGameBoard[row][col].contains("S")) 
		{
			System.out.println("Congratulations, it's a hit...!");
			String[] ship = playerTwoGameBoard[row][col].split("");
			int shipNumber = Integer.parseInt(ship[1]);
			
			//Updating the score board
			computerScoreBoard.put(shipNumber, computerScoreBoard.get(shipNumber) - 1);
			System.out.println("computer Score Board: "+ computerScoreBoard+"\n");
			
			//Checking if ship is sunk
			if (computerScoreBoard.get(shipNumber) ==0) 
			{
				System.out.print("player2's ship is shunk" + "\n");
				compurtScore++; //Update the score if ship is sunk
			}
			
			playerTwoGameBoard[row][col] = kill; //Mark square as killed
		}
		
		//If there is no ship at the location, consider it as a miss
		else 
		{
			playerTwoGameBoard[row][col] = visitedSquare; //Mark square as visited
			System.out.println("miss" + "\n" );
		}
		
		return compurtScore;
	}


	/*
	 * generates coordinates to fire
	 * @param length of the grid, computer game board, player2 game board, turn
	 * @return coordinates to fire
	 */
	private int[] generatecoordinates(int lengthOfGrid, int turn) {
		if (turn%2 != 0) 
		{
			System.out.println("----Computer's Turn----");
			return generateComputercoordinates(lengthOfGrid); // generate coordinates for computer
		}
		else 
		{
			System.out.println("----Player2's Turn----");
			return getPlayerTwoCoordinates(lengthOfGrid); // generate coordinates for player2
		}
	}
	

	/*
	 * gets player2 coordinates. Takes command line inputs from the player
	 * @param length of the game grid, player 2 game board
	 * @return the coordinates of player 2 
	 */
	private int[] getPlayerTwoCoordinates(int lengthOfGrid) {
		
		int[] coordinates = new int[2]; 
		
	    try {
	    	//check if input is integer
		    System.out.println("Enter row value:");
		    coordinates[0] = Integer.parseInt(scanner.next());
		    
		    System.out.println("Enter col value:");
		    coordinates[1] = Integer.parseInt(scanner.next());
	    }
	    catch(Exception e){
	    	//catch the exception is input is not an integer
	    	System.out.println("Input must be an integer. Please enter the value again");
	    	return getPlayerTwoCoordinates(lengthOfGrid);
	    }
	    
	    int row = coordinates[0];
	    int col = coordinates[1];
	    
		
		//Checking if the user input is valid or not
		//If not valid generate the coordinates again
		if (row >= lengthOfGrid || col >= lengthOfGrid) 
		{
			System.out.println("Wrong Input..!!!"+'\n'+"Row and column value should be less than "+ lengthOfGrid+"\n");
			return getPlayerTwoCoordinates(lengthOfGrid);			
		}
		
		//Checking if the square is already visited
		//If visited get the coordinates again
		if (computerGameBoard[row][col] == visitedSquare || computerGameBoard[row][col] == kill) 
		{
			System.out.println(Arrays.toString(coordinates)+" was already visited. Please select another coordinates");
			return getPlayerTwoCoordinates(lengthOfGrid);
		}
		
		return coordinates;
	}


	/*
	 * generates random computer coordinates. 
	 * @param length of the game grid, computer's game board
	 * @return the coordinates of the computer
	 */
	private int[] generateComputercoordinates(int lengthOfGrid) {
		int[] coordinates = new int[2];
		
		//Adding row index and col index in coordinates array
		for (int i=0; i< 2; i++) 
		{
			coordinates[i] = new Random().nextInt(lengthOfGrid);	
	    }
		
		int row= coordinates[0];
		int col = coordinates[1];
		
		//Checking if the square is already visited
		//If visited get the coordinates again
		if (playerTwoGameBoard[row][col] == visitedSquare || playerTwoGameBoard[row][col] == kill) 
		{
			System.out.println(Arrays.toString(coordinates)+" was already visited. Please select another coordinates");
			return generateComputercoordinates(lengthOfGrid);
		}
		
		return coordinates;
	}

	
	/*
	 * generates hash map to keep track of the hits of each player
	 * @param length of each ship
	 * @return hash map with initial hits
	 */
	private HashMap<Integer, Integer> generateHitMap(int length) {
		
		HashMap<Integer, Integer> hitMap = new HashMap<>();
		
		for (int i =0; i < length; i++) 
		{
			hitMap.put(ships[i], ships[i]);
		}
		
		return hitMap;
	}

}