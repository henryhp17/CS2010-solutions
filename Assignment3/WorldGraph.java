/* Henry Pratama Suryadi A0105182R
Assignment 3 Submission */
import java.lang.StringBuilder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Queue;

public class WorldGraph {
	private int numCountry;
	private boolean[] blocked;
	private ArrayList<Country> allCountry = null;
	private HashMap<String, Integer> countryID = null;
	
	private static NumberFormat numFormat = NumberFormat.getNumberInstance(Locale.UK);
	private static DecimalFormat dFormat = new DecimalFormat("#.0");

	// Constructor of the graph, no country in the first place
	public WorldGraph() {
		numCountry = 0;
		allCountry = new ArrayList<Country>();
		countryID = new HashMap<String, Integer>();
	}

	// Insert one new country, put the ID mapping to hashmap
	public void insertCountry(String line) {
		Country curCountry = new Country(line, numCountry);
		
		countryID.put(line, numCountry);
		allCountry.add(curCountry);
		numCountry++;
	}

	// Add borders of a country, parse the string and separate connected countries
	public void addBorder(int id, String line) {
		int neighID;
		double length = 0;
		String[] parseBorder = line.split("; ");
		Country curCountry = allCountry.get(id);
		
		for (String border: parseBorder) {
			if (border != "") { // Check if there is any connected countries
				String[] data = border.split(": ");
				if (data.length > 1) {
					// Get the optional border width if available
					data[1] = data[1].substring(0, data[1].length());
					try {
						length = numFormat.parse(data[1]).doubleValue();
					} catch (ParseException e) {
						System.out.println("Incompatible number format");
					}
				} else {
					length = 0;
				}
				neighID = countryID.get(data[0]);
				curCountry.insertBorder(length, neighID);
			}
		}
	}

	// Create new array after all countries has been inserted, set to not blocked
	public void initiateBlocked() {
		blocked = new boolean[numCountry];
		Arrays.fill(blocked, false);
	}
	// Block the path of this country
	public void blockPath(int id) {
		blocked[id] = true;
	}

	// Function to output all countries and their neighbours
	public String listCountries() {
		String name;
		Country curNeighbour;
		StringBuilder output = new StringBuilder();
		ArrayList<Integer> index = null;
		
		for (Country curCountry: allCountry) {
			output.append(curCountry.getData());
			output.append(": ");

			// Get the information of neighbours
			index = curCountry.getNeighbours();
			for (int i = 0; i < index.size(); i++) {
				curNeighbour = allCountry.get(index.get(i));
				if (i != 0) output.append(", ");
				output.append(curNeighbour.getData());
			}
			output.append("\n");
		}

		return output.toString();
	}

	// BFS Function to get the shortest path in terms of country number
	public String shortestPath(int src, int dest) {
		int distance = 0, cur = src;
		double totalBorder = 0, prevBorder = 0;
		boolean finished = false;
		Country curCountry;

		// Next city to progress to destination
		int[] paths = new int[numCountry];
		Arrays.fill(paths, -1);

		StringBuilder output = new StringBuilder();
		Queue<Country> queue = new LinkedList<Country>();
		queue.add(allCountry.get(dest)); // Start from the destination country

		// Search while have not find the source country
		while (paths[src] == -1) {
			if (queue.isEmpty()) return "no path\n"; // No paths available
			curCountry = queue.remove();
			// Check the neighbours accordingly
			for (Integer curInt: curCountry.getNeighbours()) {
				if (paths[curInt] == -1 && !blocked[curInt]) {
					paths[curInt] = curCountry.getID();
					queue.add(allCountry.get(curInt));
				}
			}
		}

		// Compute the final route taken
		while (!finished) {
			curCountry = allCountry.get(cur); // Start from source
			// Add the country data to the string builder
			output.append(curCountry.getData());
			if (cur != dest) {
				output.append(" -> ");
			} else {
				finished = true;
			}
			// Compute the border length while traversing the graph
			ArrayList<Integer> index = curCountry.getNeighbours();
			ArrayList<Double> length = curCountry.getNeighboursData();
			totalBorder -= prevBorder; // Adjacent border
			for (int i = 0; i < index.size(); i++) {
				if (index.get(i) != paths[cur] || cur == dest) {
					totalBorder += length.get(i); // Get border which is not adjacent
				} else {
					prevBorder = length.get(i);
				}
			}
			cur = paths[cur]; // Progress through
			distance++;
		}
		output.insert(0, distance + " : "); // Add the final distance

		// Print the blocked countries from the settings
		String blockCountry = "";
		for (int i = 0; i < numCountry; i++) {
			if (blocked[i]) {
				if (blockCountry != "") blockCountry = blockCountry + ", ";
				blockCountry = blockCountry + allCountry.get(i).getData();
			}
		}
		blockCountry = "Blocked Country: " + blockCountry + "\n";
		
		// Combine everything into the string builder
		output.insert(0, blockCountry);
		output.append("\nlength of border around this path = ");
		output.append(dFormat.format(totalBorder) + " km\n");

		return output.toString();
	}

	// Function to get countries which has num neighbours
	public String listNumNeighbour(int num) {
		StringBuilder output = new StringBuilder("");
		// Simply compare the num of neigbours
		for (Country curCountry: allCountry) {
			if (curCountry.getNumBorder() == num) {
				if (output.length() != 0) output.append(", ");
				output.append(curCountry.getName());
			}
		}

		return output.toString();
	}

	// Function to get countries which has border greater than num
	public String listLenBorder(double len) {
		Country curNeighbour;
		StringBuilder output = new StringBuilder();

		// Compare the total border of the country with the input
		for (Country curCountry: allCountry) {
			if (curCountry.getTotalBorder() > len) {
				output.append(curCountry.getName() + " ");
				output.append(dFormat.format(curCountry.getTotalBorder()) + " km -> ");
				// Get the information of every neighbours of the country
				ArrayList<Double> length = curCountry.getNeighboursData();
				ArrayList<Integer> index = curCountry.getNeighbours();
				for (int i = 0; i < index.size(); i++) {
					if (i != 0) output.append(", ");
					output.append(allCountry.get(index.get(i)).getName() + " ");
					output.append(dFormat.format(length.get(i)) + " km");
				}
				
				output.append("\n");
			}
		}

		return output.toString();
	}

	// Function to find all the connected components of the graph
	public String connectedComponent() {
		int component = 0, index = 0;
		Country curCountry;
		int[] visited = new int[numCountry];
		Arrays.fill(visited, -1);
		
		StringBuilder output = new StringBuilder();
		Queue<Country> queue = new LinkedList<Country>();

		// Check every country that is connected
		while (index < numCountry) {
			visited[index] = component;
			queue.add(allCountry.get(index));

			while (!queue.isEmpty()) { // Check every country neighbours
				curCountry = queue.remove();
				for (Integer curInt: curCountry.getNeighbours()) {
					if (visited[curInt] == -1) {
						visited[curInt] = component;
						queue.add(allCountry.get(curInt));
					}
				}
			}

			while (index < numCountry && visited[index] != -1) index++;
			component++;
		}

		// Initialise all the variables after finding the number of components
		ConnectedComponent[] connect = new ConnectedComponent[component];
		String[] result = new String[component];
		Arrays.fill(result, "");
		int[] numComp = new int[component];
		Arrays.fill(numComp, 0);

		// Add the data of every country to their corresponding component
		for (int i = 0; i < numCountry; i++) {
			if (result[visited[i]].length() != 0) result[visited[i]] = result[visited[i]] + ", ";
			result[visited[i]] = result[visited[i]] + allCountry.get(i).getName();
			numComp[visited[i]]++;
		}
		// Sort the components according to the size of their element
		for (int i = 0; i < component; i++) {
			connect[i] = new ConnectedComponent(numComp[i], result[i]);
		}
		Arrays.sort(connect, new Comparator<ConnectedComponent>() {
			@Override
			public int compare(ConnectedComponent o1, ConnectedComponent o2) {
				return Integer.compare(o2.size, o1.size);
			}
		});

		// Output all the connected components
		for (int i = 0; i < component; i++) {
			output.append("Component ");
			output.append(i + ": ");
			output.append(connect[i].size + "\n  ");
			output.append(connect[i].data + "\n");
		}

		return output.toString();
	}

	// Additional class to help connected components
	class ConnectedComponent {
		int size;
		String data;

		ConnectedComponent(int k, String v) {
			size = k;
			data = v;
		}
	}
}