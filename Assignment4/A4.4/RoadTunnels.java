/* Henry Pratama Suryadi A0105182R
Assignment 4.4 Submission */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.Queue;

public class RoadTunnels {
	private int numVertices;
	private int numEdges;
	private int curHeight;
	private boolean[] marked;
	private ArrayList<Edge>[] edges;
	private PriorityQueue<Edge> maxHeight;

	// Class constructor
	public RoadTunnels(int v) {
		numVertices = v;
		numEdges = 0;
		marked = new boolean[v];
		Arrays.fill(marked, false);
		edges = new ArrayList[v];
		for (int i = 0; i < v; i++) {
			edges[i] = new ArrayList<Edge>();
		}
		maxHeight = new PriorityQueue<Edge>();
	}

	// Add an edge to the list of edges
	public void addEdge(int a, int b, int h) {
		Edge temp = new Edge(a, b, h);
		edges[a].add(temp);
		edges[b].add(temp);
		numEdges++;
	}

	// Function to find the MST of the graph
	// MST wil maximize the tunnel height
	public void findMST() {
		boolean isFound = false;
		Queue<Edge> mst = new LinkedList<Edge>();

		// Using the Prim's algorithm to compute MST
		for (int i = 0; i < numVertices && !isFound; i++) {
			if (!marked[i]) {
				scan(i);
				while (!maxHeight.isEmpty() && !isFound) {
					Edge temp = maxHeight.poll();
					if (marked[temp.a] && marked[temp.b]) continue;

					// Add edge to connect new vertices
					mst.add(temp);
					if (mst.size() == numVertices - 1) isFound = true;

					if (!marked[temp.a]) scan(temp.a);
					if (!marked[temp.b]) scan(temp.b);
				}
			}
		}

		// Re-build the graph only using needed edges
		for (int i = 0; i < numVertices; i++) {
			edges[i].clear();
		}
		numEdges = 0;
		while (!mst.isEmpty()) {
			Edge temp = mst.poll();
			edges[temp.a].add(temp);
			edges[temp.b].add(temp);
			numEdges++;
		}
	}

	// Scanning function for Prim's algorithm
	private void scan(int input) {
		marked[input] = true;
		for (Edge temp : edges[input]) {
			maxHeight.add(temp);
		}
	}

	// Function to get the route from source to destination
	public Stack<Integer> findRoute(int s, int d) {
		boolean isFound = false;
		boolean[] visited = new boolean[numVertices];
		int next = 0;
		int[] previous = new int[numVertices];
		Arrays.fill(visited, false);
		Stack<Integer> route = new Stack<Integer>();

		// Traverse the MST using DFS to find the destination
		// Stop when destination is reached
		curHeight = 0;
		route.push(s);
		while (!route.isEmpty() && !isFound) {
			int cur = route.pop();
			if (cur == d) isFound = true;
			if (!visited[cur] && !isFound) {
				visited[cur] = true;
				for (Edge temp : edges[cur]) {
					if (temp.a == cur) {
						next = temp.b;
					} else {
						next = temp.a;
					}

					if (!visited[next]) {
						route.push(next);
						previous[next] = cur;
					}
				}
			}
		}

		// After reached destination, traverse back to check height and get path
		Stack<Integer> result = new Stack<Integer>();
		result.push(d);
		while (result.peek() != s) {
			int trace = result.peek();
			next = previous[trace];
			// While traversing, check the minimum height of the path
			for (Edge temp : edges[trace]) {
				if (temp.a == Math.min(trace, next) && temp.b == Math.max(trace, next)) {
					if (trace == d) curHeight = temp.height;
					else curHeight = Math.min(curHeight, temp.height);
				}
			}
			result.push(next);
		}

		return result;
	}

	public int getHeight() {
		return curHeight;
	}

	// Class to store the edges
	private class Edge implements Comparable<Edge> {
		int a;
		int b;
		int height;

		public Edge(int p, int q, int h) {
			a = Math.min(p, q);
			b = Math.max(p, q);
			height = h;
		}

		// Comparator to get the maximum height PQ
		@Override
		public int compareTo(Edge that) {
			if (this.height < that.height) return 1;
			else if (this.height > that.height) return -1;
			else return 0;
		}
	}
}