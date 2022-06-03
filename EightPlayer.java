/*
 * EightPlayer.java
 * Ray Zeng(Tianrui) & Nick Luo(Haiyu)
 * All group members were present and contributing during all work on this project.
 * We have neither received nor given any unauthorized aid in this assignment.
 */
package hw1;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.*;

import hw1.Node;

/*
 * Solves the 8-Puzzle Game (can be generalized to n-Puzzle)
 */

public class EightPlayer {

	static Scanner scan = new Scanner(System.in);
	static int size=3; //size=3 for 8-Puzzle.
	static int numnodes; //number of nodes generated
	static int nummoves; //number of moves required to reach goal


	public static void main(String[] args)
	{
		int numsolutions = 0;

		int boardchoice = getBoardChoice();
		int algchoice = getAlgChoice();

		//determine numiterations based on user's choices
		int numiterations=0;

		if(boardchoice==0)
			numiterations = 1;
		else {

			switch (algchoice){
			case 0:
				numiterations = 100;//BFS
				break;
			case 1:
				numiterations = 1000;//A* with Manhattan Distance heuristic
				break;
			case 2:
				numiterations = 1000;//A* with your new heuristic
				break;
			}
		}



		Node initNode;

		for(int i=0; i<numiterations; i++){

			if(boardchoice==0)
				initNode = getUserBoard();
			else
				initNode = generateInitialState();//create the random board for a new puzzle

			boolean result=false; //whether the algorithm returns a solution

			switch (algchoice){
				case 0:
					result = runBFS(initNode); //BFS
					break;
				case 1:
					result = runAStar(initNode, 0); //A* with Manhattan Distance heuristic
					break;
				case 2:
					result = runAStar(initNode, 1); //A* with your new heuristic
					break;
			}


			//if the search returns a solution
			if(result){

				numsolutions++;


				System.out.println("Number of nodes generated to solve: " + numnodes);
				System.out.println("Number of moves to solve: " + nummoves);
				System.out.println("Number of solutions so far: " + numsolutions);
				System.out.println("_______");

			}
			else
				System.out.print(".");

		}//for



		System.out.println();
		System.out.println("Number of iterations: " +numiterations);

		if(numsolutions > 0){
			System.out.println("Average number of moves for "+numsolutions+" solutions: "+nummoves/numsolutions);
			System.out.println("Average number of nodes generated for "+numsolutions+" solutions: "+numnodes/numsolutions);
		}
		else
			System.out.println("No solutions in "+numiterations+"iterations.");

	}


	public static int getBoardChoice()
	{

		System.out.println("single(0) or multiple boards(1)");
		int choice = Integer.parseInt(scan.nextLine());

		return choice;
	}

	public static int getAlgChoice()
	{

		System.out.println("BFS(0) or A* Manhattan Distance(1) or A* Col Row Check(2)");
		int choice = Integer.parseInt(scan.nextLine());

		return choice;
	}


	public static Node getUserBoard()
	{

		System.out.println("Enter board: ex. 012345678");
		String stbd = scan.nextLine();

		int[][] board = new int[size][size];

		int k=0;

		for(int i=0; i<board.length; i++){
			for(int j=0; j<board[0].length; j++){
				//System.out.println(stbd.charAt(k));
				board[i][j]= Integer.parseInt(stbd.substring(k, k+1));
				k++;
			}
		}


		for(int i=0; i<board.length; i++){
			for(int j=0; j<board[0].length; j++){
				//System.out.println(board[i][j]);
			}
			//System.out.println();
		}


		Node newNode = new Node(null,0, board);

		return newNode;


	}




	/**
	 * Generates a new Node with the initial board
	 */
	public static Node generateInitialState()
	{
		int[][] board = getNewBoard();

		Node newNode = new Node(null,0, board);

		return newNode;
	}


	/**
	 * Creates a randomly filled board with numbers from 0 to 8.
	 * The '0' represents the empty tile.
	 */
	public static int[][] getNewBoard()
	{

		int[][] brd = new int[size][size];
		Random gen = new Random();
		int[] generated = new int[size*size];
		for(int i=0; i<generated.length; i++)
			generated[i] = -1;

		int count = 0;

		for(int i=0; i<size; i++)
		{
			for(int j=0; j<size; j++)
			{
				int num = gen.nextInt(size*size);

				while(contains(generated, num)){
					num = gen.nextInt(size*size);
				}

				generated[count] = num;
				count++;
				brd[i][j] = num;
			}
		}

		/*
		//Case 1: 12 moves
		brd[0][0] = 1;
		brd[0][1] = 3;
		brd[0][2] = 8;

		brd[1][0] = 7;
		brd[1][1] = 4;
		brd[1][2] = 2;

		brd[2][0] = 0;
		brd[2][1] = 6;
		brd[2][2] = 5;
		*/

		return brd;

	}

	/**
	 * Helper method for getNewBoard()
	 */
	public static boolean contains(int[] array, int x)
	{
		int i=0;
		while(i < array.length){
			if(array[i]==x)
				return true;
			i++;
		}
		return false;
	}


	/**
	 * TO DO:
     * Prints out all the steps of the puzzle solution and sets the number of moves used to solve this board.
     */
    public static void printSolution(Node node) {

    	/*TO DO*/
    	if(node == null) return;
    	printSolution(node.getparent());
    	node.print();

    }




	/**
	 * TO DO:
	 * Runs Breadth First Search to find the goal state.
	 * Return true if a solution is found; otherwise returns false.
	 */
	public static boolean runBFS(Node initNode)
	{
		Queue<Node> Frontier = new LinkedList<Node>();
		ArrayList<Node> Explored = new ArrayList<Node>();

		Frontier.add(initNode);
		Explored.add(initNode);
		int maxDepth = 13;

		/*TO DO*/
		nummoves = 0;
		numnodes = 0;
		while(!Frontier.isEmpty()) {
			int size = Frontier.size();

			for(int i = 0; i < size; i++) {
				Node node = Frontier.remove();

				//reach goal
				if(node.isGoal()) {
					printSolution(node);
					return true;
				}
				//get nei
				ArrayList<int[][]> neighbours = node.expand();

				for(int[][] nei : neighbours) {
					Node newState = new Node(node, nummoves + 1, nei);
					boolean visited = false;
					for(Node n : Explored) {
						if(newState.equals(n)) {
							visited = true;
							break;
						}
					}
					if(visited) continue;


					Frontier.add(newState);
					Explored.add(newState);

					numnodes++;

				}

			}

			nummoves++;
			if(nummoves > maxDepth) return false;
		}
		return false;


	}//BFS



	/***************************A* Code Starts Here ***************************/

	/**
	 * TO DO:
	 * Runs A* Search to find the goal state.
	 * Return true if a solution is found; otherwise returns false.
	 * heuristic = 0 for Manhattan Distance, heuristic = 1 for your new heuristic
	 */
	public static boolean runAStar(Node initNode, int heuristic)
	{	
		//Priority Queue use the fvalue as the key
		PriorityQueue<Node> Frontier = new PriorityQueue<Node>((a,b) -> (int)(a.getfvalue()-b.getfvalue()));
		ArrayList<Node> Explored = new ArrayList<Node>();
		int maxDepth = 13;

		numnodes = 0;

		//set f(n)
		initNode.setgvalue(0);
		//Choose the heuristic based on the provided choice
		double hvalue = heuristic == 0? initNode.evaluateHeuristic() : initNode.evaluateMyHeuristic();
		initNode.sethvalue(hvalue);
		
		Frontier.offer(initNode);

		
		while(!Frontier.isEmpty()) {
			Node X = Frontier.poll();
			//x should not be more than maxDepth
			if(X.getdepth() >= maxDepth){
				return false;
			}
			
			Explored.add(X);
			
			//reach goal
			if(X.isGoal()) {
				printSolution(X);
				nummoves = X.getdepth();
				return true;
				}
			
			else{
				ArrayList<int[][]> children = X.expand();
				for(int[][] child : children){
					Node c = new Node(X, X.getdepth()+1, child);
					
					//Check if visited
					boolean visited = false;
					for(Node n : Explored) {
						if(c.equals(n)) {
							visited = true;
							break;
						}
					}
					if(visited){
						continue;
					}
					
					//Set heuristic for Node c
					c.setgvalue(X.getgvalue()+1);
					double hvalue2 = heuristic == 0? c.evaluateHeuristic() : c.evaluateMyHeuristic();
				    c.sethvalue(hvalue);
				    
				    //Update the Frontier if the c is in Frontier 
				    //and has lower f value than the one already existed
					boolean isInFrontier = false;
					for(Node n : Frontier) {
						if(c.equals(n)) {
							isInFrontier = true;
							if(c.getfvalue() < n.getfvalue()){
								n.setgvalue(c.getgvalue());
								n.sethvalue(c.gethvalue());
							}
							break;
						}
					}
					//If c is not in frontier and not Explored, then put c into Frontier
					if(!visited && !isInFrontier){
						Frontier.offer(c);
						numnodes++;
					}


				}
			}
			
		}

		return false;

	}

}
