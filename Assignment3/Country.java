/* Henry Pratama Suryadi A0105182R
Assignment 3 Submission */
import java.util.ArrayList;

public class Country {
	private int countryID, numBorder;
	private double sumBorder;
	private String countryName;
	private ArrayList<Border> allBorder = null;

	// Constructor of new country
	public Country(String name, int id) {
		countryName = name;
		countryID = id;
		numBorder = 0;
		sumBorder = 0;
		allBorder = new ArrayList<Border>();
	}

	// Insert one neighbour using this function
	public void insertBorder(double length, int id) {
		Border curBorder = new Border(length, id);

		sumBorder += 1.0 * length; // Total border of the country
		allBorder.add(curBorder);
		numBorder++;
	}

	// Get name and ID of the country together as a string
	public String getData() {
		return countryName + " (" + countryID + ")";
	}

	public String getName() {
		return countryName;
	}
	
	public int getID() {
		return countryID;
	}

	public int getNumBorder() {
		return numBorder;
	}

	public double getTotalBorder() {
		return sumBorder;
	}

	// Get the index information of the neighbours
	public ArrayList<Integer> getNeighbours() {
		ArrayList<Integer> result = new ArrayList<Integer>();

		for (Border curBorder: allBorder) {
			result.add(curBorder.countryID);
		}

		return result;
	}

	// Get the border length information of the neighbours
	public ArrayList<Double> getNeighboursData() {
		ArrayList<Double> result = new ArrayList<Double>();

		for (Border curBorder: allBorder) {
			result.add(curBorder.length);
		}

		return result;
	}

	// Class to set the borders of the country
	class Border {
		int countryID;
		double length;

		Border(double len, int id) {
			length = len;
			countryID = id;
		}
	}
}