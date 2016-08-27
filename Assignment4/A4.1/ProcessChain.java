/* Henry Pratama Suryadi A0105182R
Assignment 4.1 Submission */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class ProcessChain {
	private int numVertices;
	// Details for the shortest path
	private int shortDist;
	private int shortIndex;
	private int shortStart;
	// Details for the longest path
	private int longDist;
	private int longIndex;
	private int longStart;
	// Topological ordering
	private int[] topOrder;
	private boolean[] visited;
	private NodeStep[] steps;
	private Stack<Integer> sortedSteps;
	private ArrayList<Integer> startPoints;
	private ArrayList<Integer> endPoints;

	// Class constructor
	public ProcessChain(int v) {
		numVertices = v;
		shortDist = numVertices + 1;
		shortIndex = -1;
		longDist = 0;
		longIndex = -1;

		startPoints = new ArrayList<Integer>();
		endPoints = new ArrayList<Integer>();
		steps = new NodeStep[numVertices];
		for (int i = 0; i < v; i++) {
			steps[i] = new NodeStep(i);
		}
	}

	// Add an edge to the list of edges
	public void addEdge(int from, int to) {
		steps[from].edge.add(to);

		// Perform check if it is a start or end point
		if (steps[to].start) steps[to].start = false;
		if (steps[from].end) steps[from].end = false;
	}

	// Do topological ordering
	public void topSort() {
		sortedSteps = new Stack<Integer>();
		topOrder = new int[numVertices];
		visited = new boolean[numVertices];
		Arrays.fill(visited, false);

		for (int i = 0; i < numVertices; i++) {
			if (steps[i].start) {
				visit(i);
				startPoints.add(i);
			} else if (steps[i].end) {
				endPoints.add(i);
			}
		}

		// Convert the stack to array to be processed easily
		for (int i = 0; i < numVertices; i++) {
			topOrder[i] = sortedSteps.pop();
		}
	}

	// Visit function for the topological ordering
	public void visit(int n) {
		if (!visited[n]) {
			visited[n] = true;
			for (Integer index: steps[n].edge) {
				visit(index);
			}
			sortedSteps.push(n);
		}
	}

	// Function to compute the shortest path
	public int computeShortPath() {
		int[] distance = new int[numVertices];
		Arrays.fill(distance, numVertices + 1);
		int cur = -1;

		// Use BFS with DP to compute the shortest path
		for (int i = 0; i < numVertices; i++) {
			cur = steps[topOrder[i]].index;
			if (steps[topOrder[i]].start) distance[cur] = 0;

			// Compare distance only at the end point
			if (steps[topOrder[i]].end) {
				if (distance[cur] < shortDist) {
					shortDist = distance[cur];
					shortIndex = cur;
				}
			} else {
				for (Integer index: steps[topOrder[i]].edge) {
					if (distance[index] > distance[cur] + 1) {
						distance[index] = distance[cur] + 1;
						steps[index].previousShort = cur;
					}
				}
			}
		}

		return shortDist;
	}

	// Function to compute the longest path
	public int computeLongPath() {
		int[] distance = new int[numVertices];
		Arrays.fill(distance, 0);
		int cur = -1;

		// Use BFS with DP to compute the longest path
		for (int i = 0; i < numVertices; i++) {
			cur = steps[topOrder[i]].index;
			if (steps[topOrder[i]].start) distance[cur] = 0;

			// Compare distance only at the end point
			if (steps[topOrder[i]].end) {
				if (distance[cur] > longDist) {
					longDist = distance[cur];
					longIndex = cur;
				}
			} else {
				for (Integer index: steps[topOrder[i]].edge) {
					if (distance[index] < distance[cur] + 1) {
						distance[index] = distance[cur] + 1;
						steps[index].previousLong = cur;
					}
				}
			}
		}

		return longDist;
	}

	// Function to get the path from the details, 0 for shortest, 1 for longest
	public Stack<Integer> getPath(int input) {
		Stack<Integer> out = new Stack<Integer>();
		int cur;

		if (input == 0) cur = shortIndex;
		else cur = longIndex;
		
		while (cur != -1) {
			out.push(cur);
			if (input == 0) {
				shortStart = cur;
				cur = steps[cur].previousShort;
			} else {
				longStart = cur;
				cur = steps[cur].previousLong;
			}
		}

		return out;
	}

	// Get the details for shortest path
	public int getStart(int input) {
		if (input == 0) return shortStart;
		return longStart;
	}

	// Get the details for longest path
	public int getEnd(int input) {
		if (input == 0) return shortIndex;
		return longIndex;
	}

	// Get the starting or ending points, 0 for start, 1 for end
	public ArrayList<Integer> getPoints(int input) {
		if (input == 0) return startPoints;
		else return endPoints;
	}

	// Class to store the process vertex
	class NodeStep {
		int index;
		int previousLong;
		int previousShort;
		boolean start;
		boolean end;
		ArrayList<Integer> edge;

		public NodeStep(int input) {
			index = input;
			previousLong = -1;
			previousShort = -1;
			start = true;
			end = true;
			edge = new ArrayList<Integer>();
		}
	}
}