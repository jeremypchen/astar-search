package aStarSearch;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class KnightDistanceSearch {
	private int A_Row, A_Col, B_Row, B_Col, size;
	private int numberOfMoves;

	public KnightDistanceSearch(int A_Row, int A_Col, int B_Row, int B_Col, int size){
		this.A_Row = A_Row;
		this.A_Col = A_Col;
		this.B_Row = B_Row; 
		this.B_Col = B_Col;
		this.size = size;
		numberOfMoves = search();
	}

	public void generateChildren(Node current){		
		int currentRow = current.row;
		int currentColumn = current.column;

		// 1 - Up-Left
		if (currentRow>=3 && currentColumn>=2){
			current.addChild(new Node(currentRow-2, currentColumn-1, 1, current));
		}

		// 2 - Up-Right
		if (currentRow>=3 && currentColumn<=size-1){
			current.addChild(new Node(currentRow-2, currentColumn+1, 1, current));
		}

		// 3 - Right-Down
		if (currentRow<=size-1 && currentColumn<=size-2){
			current.addChild(new Node(currentRow+1, currentColumn+2, 1, current));
		}

		// 4 - Right-Up
		if (currentRow>=2 && currentColumn<=size-2){
			current.addChild(new Node(currentRow-1, currentColumn+2, 1, current));
		}

		// 5 - Down-Left
		if (currentRow<=size-2 && currentColumn>=2){
			current.addChild(new Node(currentRow+2, currentColumn-1, 1, current));
		}

		// 6 - Down-Right
		if (currentRow<=size-2 && currentColumn<=size-1){
			current.addChild(new Node(currentRow+2, currentColumn+1, 1, current));
		}

		// 7 - Left-Down
		if (currentRow<=size-1 && currentColumn>=3){
			current.addChild(new Node(currentRow+1, currentColumn-2, 1, current));
		}

		// 8 - Left-Up
		if (currentRow>=2 && currentColumn>=3){
			current.addChild(new Node(currentRow-1, currentColumn-2, 1, current));
		}
	}

	public boolean isNotRepeat(Node n, List<Node> checked){
		for (Node checkedNode : checked){
			if (n.row == checkedNode.row && n.column == checkedNode.column)
				return false;
		}
		return true;
	}

	public int search(){
		Comparator<Node> comparator = new CostComparator();
		PriorityQueue<Node> searchQueue = new PriorityQueue<Node>(1, comparator);
		List<Node> checked = new ArrayList<Node>();
		Node next;

		if (size >= 4){
			searchQueue.add(new Node(A_Row, A_Col)); 				// Initialize Queue with root node (built from start state)
			while (!searchQueue.isEmpty()){ 									// Repeat until (Queue empty)
				next = searchQueue.poll();
				if (next.row == B_Row && next.column == B_Col) 					// If Agent A is at same place as Agent B
					return next.depth;										// Return this node - success.

				generateChildren(next);											// Generate children nodes
				for (Node n : next.Children){
					if (isNotRepeat(n, checked))								// Reject children that have already been considered to avoid loops
						searchQueue.add(n);										// Add these children nodes to queue
				}
				checked.add(next);
			} 
		}
		return -1;														// Node not found, return nothing
	}
	
	public int getNumberOfMoves(){
		return numberOfMoves;
	}
}

