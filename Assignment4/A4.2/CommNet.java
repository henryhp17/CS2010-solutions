/* Henry Pratama Suryadi A0105182R
Assignment 4.2 Submission */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class CommNet {
	private int numVertices;
	private int numEdges;
	private int cableCost;
	private int numTerminators;
	private int numSwitches;
	private int numRouters;
	private boolean[] marked;
	private ArrayList<Edge>[] edges;
	private Queue<Edge> mst;
	private PriorityQueue<Edge> minHeap;

	// Class constructor
	public CommNet(int v) {
		numVertices = v;
		numEdges = 0;
		cableCost = 0;
		numTerminators = 0;
		numSwitches = 0;
		numRouters = 0;
		
		marked = new boolean[v];
		Arrays.fill(marked, false);
		edges = new ArrayList[v];
		for (int i = 0; i < v; i++) {
			edges[i] = new ArrayList<Edge>();
		}
		
		mst = new LinkedList<Edge>();
		minHeap = new PriorityQueue<Edge>();
	}

	// Add an edge to the list of edges
	public void addEdge(int a, int b, int w) {
		Edge temp = new Edge(a, b, w);
		edges[a].add(temp);
		edges[b].add(temp);
		numEdges++;
	}

	public int getNumEdge() {
		return mst.size();
	}

	public int getTerminators() {
		return numTerminators;
	}

	public int getSwitches() {
		return numSwitches;
	}

	public int getRouters() {
		return numRouters;
	}

	// Function to find the MST of the graph
	// MST will minimize the cable cost
	public int findMST() {
		boolean isFound = false;
		int[] edgeMST = new int[numVertices];
		Arrays.fill(edgeMST, 0);

		// Using the Prim's algorithm to compute MST
		for (int i = 0; i < numVertices && !isFound; i++) {
			if (!marked[i]) {
				scan(i);
				while (!minHeap.isEmpty() && !isFound) {
					Edge temp = minHeap.poll();
					if (marked[temp.a] && marked[temp.b]) continue;

					// Add the edge to connect new vertices
					mst.add(temp);
					cableCost += temp.weight;
					// Keep track of the number of edges for each vertices
					edgeMST[temp.a]++;
					edgeMST[temp.b]++;
					if (mst.size() == numVertices - 1) isFound = true;

					if (!marked[temp.a]) scan(temp.a);
					if (!marked[temp.b]) scan(temp.b);
				}
			}
		}
		checkEquipment(edgeMST); // Check for additional equipment

		return cableCost;
	}

	// Scanning function for Prim's algorithm
	private void scan(int input) {
		marked[input] = true;
		for (Edge temp : edges[input]) {
			minHeap.add(temp);
		}
	}

	// Function to check the number of edges, get the number of additional equipment
	private void checkEquipment(int[] edgeMST) {
		for (int i = 0; i < numVertices; i++) {
			if (edgeMST[i] >= 20) numRouters++;
			else if (edgeMST[i] >= 5) numSwitches++;
			else if (edgeMST[i] == 1) numTerminators++;
		}
	}

	// Class to store the edges
	private class Edge implements Comparable<Edge> {
		int a;
		int b;
		int weight;

		public Edge(int p, int q, int w) {
			a = p;
			b = q;
			weight = w;
		}

		// Comparator to get minimum cost PQ
		@Override
		public int compareTo(Edge that) {
			if (this.weight < that.weight) return -1;
			else if (this.weight > that.weight) return 1;
			else return 0;
		}
	}
}