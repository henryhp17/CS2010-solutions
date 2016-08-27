/******************************************************************************
 *  Compilation:  javac AdjMatrixGraph.java
 *  Execution:    java AdjMatrixGraph V E
 *  Dependencies: StdOut.java
 *
 *  A graph, implemented using an adjacency matrix.
 *  Parallel edges are disallowed; self-loops are allowd.
 *  
 ******************************************************************************/

/* Henry Pratama Suryadi
A0105182R Lab 3 Submission*/

import java.util.Iterator;
import java.util.NoSuchElementException;

public class AdjMatrixMoreEfficientGraph {
    private static final String NEWLINE = System.getProperty("line.separator");
    private int V;
    private int E;
    private boolean[][] adj;
    private int numBool = 0;
    
    // empty graph with V vertices
    public AdjMatrixMoreEfficientGraph(int V) {
        if (V < 0) throw new RuntimeException("Number of vertices must be nonnegative");
        this.V = V;
        this.E = 0;
        this.adj = new boolean[V][];

        // Add number of boolean as arrays for them are being created
        for (int i = 0; i < V; i++) {
            this.adj[i] = new boolean[i];
            numBool += i;
        }
    }
    /**  
     * Initializes a graph from an input stream.
     * The format is the number of vertices <em>V</em>,
     * followed by the number of edges <em>E</em>,
     * followed by <em>E</em> pairs of vertices, with each entry separated by whitespace.
     *
     * @param  in the input stream
     * @throws IndexOutOfBoundsException if the endpoints of any edge are not in prescribed range
     * @throws IllegalArgumentException if the number of vertices or edges is negative
     */
    public AdjMatrixMoreEfficientGraph(In in) {
        this(in.readInt());
        int E = in.readInt();
        if (E < 0) throw new IllegalArgumentException("Number of edges must be nonnegative");
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            addEdge(v, w);
        }
    }
    // random graph with V vertices and E edges
    public AdjMatrixMoreEfficientGraph(int V, int E) {
        this(V);
        if (E < 0) throw new RuntimeException("Number of edges must be nonnegative");
        if (E > V*(V-1) + V) throw new RuntimeException("Too many edges");

        // can be inefficient
        while (this.E != E) {
            int v = StdRandom.uniform(V);
            int w = StdRandom.uniform(V);
            addEdge(v, w);
        }
    }

    // number of vertices and edges
    public int V() { return V; }
    public int E() { return E; }


    // add undirected edge v-w
    public void addEdge(int v, int w) {
        // Check the index and update accordingly
        if (v == w) {
            throw new NoSuchElementException();
        } else if (v < w) {
            int temp = v;
            v = w;
            w = temp;
        }
        if (!adj[v][w]) E++;
        adj[v][w] = true;
    }

    // does the graph contain the edge v-w?
    public boolean contains(int v, int w) {
        // Modify the index accordingly
        if (v < w) {
            return adj[w][v];
        } else if (v > w) {
            return adj[v][w];
        }

        return false;
    }

    // return list of neighbors of v
    public Iterable<Integer> adj(int v) {
        return new AdjIterator(v);
    }
    
    /**
     * Returns the size of this graph.
     *
     * @return the size of this graph.
     */
    public int sizeOfGraph() {
			//YOUR CODE HERE
		return numBool * Size.BOOLEAN;
    }

    // Function to print all the edges contained in the graph
    public void printGraph() {
        StdOut.println(V + " vertices, " + E + " edges");
        for (int i = 0; i < V; i++) {
            StdOut.print(i + ":");
            for (int j = 0; j < V; j++) {
                // Check for the required boolean
                if (i < j) {
                    if (adj[j][i]) StdOut.print(" " + j);
                } else if (i > j) {
                    if (adj[i][j]) StdOut.print(" " + j);
                }
            }
            StdOut.println();
        }

        return;
    }

    // support iteration over graph vertices
    private class AdjIterator implements Iterator<Integer>, Iterable<Integer> {
        private int v;
        private int w = 0;

        AdjIterator(int v) {
            this.v = v;
        }

        public Iterator<Integer> iterator() {
            return this;
        }

        public boolean hasNext() {
            while (w < V) {
                // Check for the correct boolean
                if (v < w) {
                    if (adj[w][v]) return true;
                } else if (v > w) {
                    if (adj[v][w]) return true;
                }
                w++;
            }
            return false;
        }

        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return w++;
        }

        public void remove()  {
            throw new UnsupportedOperationException();
        }
    }   

}