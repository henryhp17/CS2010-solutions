/* Henry Pratama Suryadi A0105182R
Assignment 4.3 Submission */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class HospitalMap {
	private int numVertices;
	private int numEdges;
	private Node[] vertices;
	private PriorityQueue<Node> minHeap;

	// Class constructor
	public HospitalMap(int v) {
		numVertices = v;
		vertices = new Node[v];
		for (int i = 0; i < v; i++) {
			vertices[i] = new Node();
		}
		minHeap = new PriorityQueue<Node>();
	}

	// Add an edge to the list of edges
	public void addEdge(int a, int b, double w) {
		vertices[a].addNeighbour(b, w);
		vertices[b].addNeighbour(a, w);
	}

	// Function to compute the distance of each houses
	public ArrayList<Integer>[] computeDistance(ArrayList<Integer> loc) {
		int index = 0, near = 0;
		double dist = 0, addDist = 0;
		ArrayList<Integer>[] result = new ArrayList[numVertices];
		for (int i = 0; i < numVertices; i++) {
			result[i] = new ArrayList<Integer>();
		}

		// Set the distance of every hospital to 0
		for (int i = 0; i < loc.size(); i++) {
			vertices[loc.get(i)].distance = 0.0;
			vertices[loc.get(i)].hospital = loc.get(i);
			minHeap.add(vertices[loc.get(i)]);
		}

		// Use the Dijkstra's algorithm with multiple sources
		while(!minHeap.isEmpty()) {
			Node cur = minHeap.poll();
			dist = cur.distance;
			near = cur.hospital;
			for (int i = 0; i < cur.neighbours.size(); i++) {
				// Keep track of the distance and sources throughout the process
				index = cur.neighbours.get(i);
				addDist = cur.weight.get(i);
				// Process unvisited vertices
				if (vertices[index].distance < 0) {
					vertices[index].distance = dist + addDist;
					vertices[index].hospital = near;
					minHeap.add(vertices[index]);
				} else {
					// Re-process the vertex if it has shorter distance
					if (vertices[index].distance > dist + addDist) {
						vertices[index].distance = dist + addDist;
						vertices[index].hospital = near;
						minHeap.add(vertices[index]);
					// Change to smaller index if distance is equal
					} else if (vertices[index].distance == dist + addDist) {
						if (vertices[index].hospital > near) {
							vertices[index].hospital = near;
							minHeap.add(vertices[index]);
						}
					}
				}
			}
		}

		// Collect all the houses which corresponds to the hospital
		for (int i = 0; i < numVertices; i++) {
			if (i != vertices[i].hospital) result[vertices[i].hospital].add(i);
		}

		return result;
	}

	public int getVertices() {
		return numVertices;
	}

	public double getDistance(int input) {
		return vertices[input].distance;
	}

	// Class to store each house or hospital nodes
	private class Node implements Comparable<Node> {
		int hospital;
		int numEdges;
		Double distance;
		ArrayList<Integer> neighbours;
		ArrayList<Double> weight;

		public Node() {
			distance = -1.0;
			numEdges = 0;
			neighbours = new ArrayList<Integer>();
			weight = new ArrayList<Double>();
		}

		// Add edge in the form of neighbour
		public void addNeighbour(int n, double w) {
			neighbours.add(n);
			weight.add(w);
		}

		// Comparator to get minimum distance PQ
		@Override
		public int compareTo(Node that) {
			if (this.distance < that.distance) return -1;
			else if (this.distance > that.distance) return 1;
			else return 0;
		}
	}
}