package aStarSearch;

import java.io.*;
import java.util.*;

public class AStarSearch {
	public int A_Row, A_Col, B_Row, B_Col, size;
	public Node start;
	public List<Node> Euclidian_input1 = new ArrayList<Node>();
	public List<Node> Euclidian_input2 = new ArrayList<Node>();
	public List<Node> Euclidian_input3 = new ArrayList<Node>();

	public List<Node> Canberra_input1 = new ArrayList<Node>();
	public List<Node> Canberra_input2 = new ArrayList<Node>();
	public List<Node> Canberra_input3 = new ArrayList<Node>();

	public List<Node> Knight_input1 = new ArrayList<Node>();
	public List<Node> Knight_input2 = new ArrayList<Node>();
	public List<Node> Knight_input3 = new ArrayList<Node>();

	public List<List> allEuclidianList = new ArrayList<List>();
	public List<List> allCanberraList = new ArrayList<List>();
	public List<List> allKnightList = new ArrayList<List>();

	public List<Node> shortest_input1 = new ArrayList<Node>();
	public List<Node> shortest_input2 = new ArrayList<Node>();
	public List<Node> shortest_input3 = new ArrayList<Node>();

	public Heuristic currentHeuristic;

	public enum Heuristic {EUCLIDIAN, CANBERRA, KNIGHT};

	public static final int NUMBER_OF_INPUTS = 3;

	public AStarSearch() throws IOException{
		List<Integer> inputs = new ArrayList<Integer>();

		inputs = readInputFile();

		for (int i = 0; i < NUMBER_OF_INPUTS; i++){
			size = inputs.remove(0);
			int initial_A_Row = inputs.remove(0);
			int initial_A_Col = inputs.remove(0);
			int initial_B_Row = inputs.remove(0);
			int initial_B_Col = inputs.remove(0);
			A_Row = initial_A_Row;
			A_Col = initial_A_Col;
			B_Row = initial_B_Row;
			B_Col = initial_B_Col;
			EuclidianSearch(i);
			A_Row = initial_A_Row;
			A_Col = initial_A_Col;
			B_Row = initial_B_Row;
			B_Col = initial_B_Col;
			CanberraSearch(i);
			A_Row = initial_A_Row;
			A_Col = initial_A_Col;
			B_Row = initial_B_Row;
			B_Col = initial_B_Col;			
			KnightSearch(i);
		}

		allEuclidianList.add(Euclidian_input1);
		allEuclidianList.add(Euclidian_input2);
		allEuclidianList.add(Euclidian_input3);
		allCanberraList.add(Canberra_input1);
		allCanberraList.add(Canberra_input2);
		allCanberraList.add(Canberra_input3);
		allKnightList.add(Knight_input1);
		allKnightList.add(Knight_input2);
		allKnightList.add(Knight_input3);

		for (int i = 0; i < NUMBER_OF_INPUTS; i++){
			List<Node> optimalTemp = allEuclidianList.get(i);
			if (allCanberraList.get(i).size() < optimalTemp.size())
				optimalTemp = allCanberraList.get(i);
			if (allKnightList.get(i).size() < optimalTemp.size())
				optimalTemp = allKnightList.get(i);
			switch(i){
			case 0:
				shortest_input1 = optimalTemp;
				break;
			case 1:
				shortest_input2 = optimalTemp;
				break;
			case 2: 
				shortest_input3 = optimalTemp;
				break;
			}
		}

		outputFile();
	}

	public List<Integer> readInputFile() throws IOException{
		String newLine;
		String inputPath = "src/input.txt";
		FileReader fr = new FileReader(inputPath);
		BufferedReader br = new BufferedReader(fr);
		List<Integer> inputs = new ArrayList<Integer>();

		for (int i = 0; i < NUMBER_OF_INPUTS; i++){
			newLine = br.readLine();
			StringTokenizer st = new StringTokenizer(newLine);
			inputs.add(Integer.parseInt(st.nextToken())); // Size

			newLine = br.readLine();
			st = new StringTokenizer(newLine);
			inputs.add(Integer.parseInt(st.nextToken())); // A_Row
			inputs.add(Integer.parseInt(st.nextToken())); // A_Col

			newLine = br.readLine();
			st = new StringTokenizer(newLine);
			inputs.add(Integer.parseInt(st.nextToken())); // B_Row
			inputs.add(Integer.parseInt(st.nextToken())); // B_Col

			newLine = br.readLine();
		}
		
		br.close();

		return inputs;
	}

	public void outputFile(){
		PrintWriter writer;
		Node nodeToPrint;
		try {
			writer = new PrintWriter("src/output.txt");
			System.out.printf("%-19s %-10s", "", "Nodes Expanded\n");
			System.out.printf("%-10s %-10s", "", "Euclidian   Canberra   Knight");
			for (int i = 0; i < NUMBER_OF_INPUTS; i++){
				System.out.print("\n" + "Input" + (i+1));

				System.out.print("         ");
				if(allEuclidianList.get(i).size()!=0)
					System.out.print(allEuclidianList.get(i).size());
				else 
					System.out.print("-");

				System.out.print("          ");
				if(allCanberraList.get(i).size()!=0)
					System.out.print(allCanberraList.get(i).size());
				else 
					System.out.print("-");

				System.out.print("          ");
				if(allKnightList.get(i).size()!=0)
					System.out.print(allKnightList.get(i).size());
				else 
					System.out.print("-");

			}
			System.out.print("\n\n\n" + "Optimal Path Solution");
			for (int i = 0; i < NUMBER_OF_INPUTS; i++){
				System.out.print("\n" + "Input" + (i+1));
				switch(i){
				case 0:
					if (shortest_input1.size() == 0)
						System.out.print(" -1");
					else shortest_input1.remove(shortest_input1.size()-1);
					while(!shortest_input1.isEmpty()){
						nodeToPrint = (Node) shortest_input1.remove(shortest_input1.size()-1);
						System.out.print(" (" + nodeToPrint.getCoordinates() + ")");
					}
					break;
				case 1:
					if (shortest_input2.size() == 0)
						System.out.print(" -1");
					else shortest_input2.remove(shortest_input2.size()-1);
					while(!shortest_input2.isEmpty()){
						nodeToPrint = (Node) shortest_input2.remove(shortest_input2.size()-1);
						System.out.print(" (" + nodeToPrint.getCoordinates() + ")");
					};
					break;
				case 2:
					if (shortest_input3.size() == 0)
						System.out.print(" -1");
					else shortest_input3.remove(shortest_input3.size()-1);
					while(!shortest_input3.isEmpty()){
						nodeToPrint = (Node) shortest_input3.remove(shortest_input3.size()-1);
						System.out.print(" (" + nodeToPrint.getCoordinates() + ")");
					};
					break;
				}

			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void EuclidianSearch(int iteration){
		List<Node> temp = new ArrayList<Node>();

		currentHeuristic = Heuristic.EUCLIDIAN;

		Node EuclidianGoal = search();

		if(EuclidianGoal!=null){
			temp.add(EuclidianGoal);
			Node parent = EuclidianGoal.Parent;
			while (parent != null){
				temp.add(parent);
				parent = parent.Parent;
			}
		}

		switch(iteration){
		case 0: Euclidian_input1 = temp;
		break;

		case 1: Euclidian_input2 = temp;
		break;

		case 2: Euclidian_input3 = temp;
		break;
		}
	}

	public void CanberraSearch(int iteration){
		List<Node> temp = new ArrayList<Node>();

		currentHeuristic = Heuristic.CANBERRA;

		Node CanberraGoal = search();

		if(CanberraGoal!=null){
			temp.add(CanberraGoal);
			Node parent = CanberraGoal.Parent;
			while (parent != null){
				temp.add(parent);
				parent = parent.Parent;
			}
		}

		switch(iteration){
		case 0: Canberra_input1 = temp;
		break;

		case 1: Canberra_input2 = temp;
		break;

		case 2: Canberra_input3 = temp;
		break;
		}
	}

	public void KnightSearch(int iteration){
		currentHeuristic = Heuristic.KNIGHT;

		List<Node> temp = new ArrayList<Node>();
		Node KnightGoal = search();

		if(KnightGoal!=null){
			temp.add(KnightGoal);
			Node parent = KnightGoal.Parent;
			while (parent != null){
				temp.add(parent);
				parent = parent.Parent;
			}
		}

		switch(iteration){
		case 0: Knight_input1 = temp;
		break;

		case 1: Knight_input2 = temp;
		break;

		case 2: Knight_input3 = temp;
		break;
		}
	}

	public void generateChildren(Node current){		
		int currentRow = current.row;
		int currentColumn = current.column;
		double heuristicCost;

		// A move up, B move right [if A not at top and B not at right-most]
		if (currentRow!=1 && B_Col!=size){
			heuristicCost = calculateHeuristicCost(currentRow-1, currentColumn, 
					B_Row, B_Col+1) + 1; // +1 for path-cost
			current.addChild(new Node(currentRow-1, currentColumn, heuristicCost, current, B_Row, B_Col+1));
		}

		// A move left, B move up [if A not at left-most and B not at top]
		if (currentColumn!=1 && B_Row!=1){
			heuristicCost = calculateHeuristicCost(currentRow, currentColumn-1, 
					B_Row-1, B_Col) + 1; // +1 for path-cost
			current.addChild(new Node(currentRow, currentColumn-1, heuristicCost, current, B_Row-1, B_Col));
		}

		// A move right, B move down [if A not at right-most and B not at bottom]
		if (currentColumn!=size && B_Row!=size){
			heuristicCost = calculateHeuristicCost(currentRow, currentColumn+1, 
					B_Row+1, B_Col) + 1; // +1 for path-cost
			current.addChild(new Node(currentRow, currentColumn+1, heuristicCost, current, B_Row+1, B_Col));
		}

		// A move down, B move left [if A is not at bottom and B not at left-most]
		if (currentRow!=size && B_Col!=1){
			heuristicCost = calculateHeuristicCost(currentRow+1, currentColumn, 
					B_Row, B_Col-1) + 1; // +1 for path-cost
			current.addChild(new Node(currentRow+1, currentColumn, heuristicCost, current, B_Row, B_Col-1));
		}
	}

	public boolean isNotRepeat(Node n, List<Node> checked){
		for (Node checkedNode : checked){
			if (n.row == checkedNode.row && n.column == checkedNode.column)
				return false;
		}
		return true;
	}

	public Node search(){
		Comparator<Node> comparator = new CostComparator();
		PriorityQueue<Node> searchQueue = new PriorityQueue<Node>(1, comparator);
		List<Node> checked = new ArrayList<Node>();
		Node next;

		searchQueue.add(new Node(A_Row, A_Col, B_Row, B_Col)); 				// Initialize Queue with root node (built from start state)
		while (!searchQueue.isEmpty()){ 									// Repeat until (Queue empty)
			next = searchQueue.poll(); 										// Remove first node from front of queue
			B_Row = next.b_row;												// Get Agent B coordinates based on current node
			B_Col = next.b_col;
			if (next.row == B_Row && next.column == B_Col) 					// If Agent A is at same place as Agent B
				return next;												// Return this node - success.

			generateChildren(next);											// Generate children nodes
			for (Node n : next.Children){
				if (isNotRepeat(n, checked))								// Reject children that have already been considered to avoid loops
					searchQueue.add(n);										// Add these children nodes to queue
			}
			checked.add(next);
		} 
		return null;														// Node not found, return nothing
	}

	public double calculateHeuristicCost(int A_Row, int A_Col, int B_Row, int B_Col){
		if (currentHeuristic == Heuristic.EUCLIDIAN)
			return Math.sqrt(Math.pow((B_Row - B_Col), 2) + Math.pow((A_Row - A_Col), 2));
		else if (currentHeuristic == Heuristic.CANBERRA)
			return Math.abs(B_Row-B_Col)/(Math.abs(B_Row)+Math.abs(B_Col)) + 
					Math.abs(A_Row-A_Col)/(Math.abs(A_Row)+Math.abs(A_Col));
		else if (currentHeuristic == Heuristic.KNIGHT)
			return new KnightDistanceSearch(A_Row, A_Col, B_Row, B_Col, size).getNumberOfMoves();
		else
			return 0;
	}

	public static void main(String arg[]) throws IOException{
		new AStarSearch();
	}
}
