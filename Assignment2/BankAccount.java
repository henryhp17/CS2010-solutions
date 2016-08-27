/* Henry Pratama Suryadi
A0105182R Assignment 2 Submission*/

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.util.Date;
import java.util.Comparator;
import java.util.PriorityQueue;

public class BankAccount {
	private double sumTrans;
	private String name, suspicious;
	
	private Date firstTrans = null;
	private Transactions latestTrans;
	private PriorityQueue<Transactions> records = null;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private DecimalFormat dec = new DecimalFormat("#.00");

	// Initialise the comparator for transaction priority queue
	private static final maxHeapComparator transComp = new maxHeapComparator();
	private static class maxHeapComparator implements Comparator<Transactions> {
		@Override
		public int compare(Transactions i, Transactions j) {
			return i.amount > j.amount ? -1 : 1 == j.amount ? 0 : 1;
		}
	}

	// Constructor, create new customers' account
	public BankAccount(String id, Date input, double money) {
		name = id;
		suspicious = null;
		sumTrans = 0;
		records = new PriorityQueue<Transactions>(11, transComp);
		firstTrans = input; // Initialise the first transaction
		latestTrans = new Transactions(input, money);
		insertTrans(input, money);
	}
	
	// Function to insert new transaction
	// It will return the report of any double transaction occurred
	public String insertTrans(Date input, double money) {
		// Check for double transaction if there is any
		float timeDiff = TimeUnit.MINUTES.convert(input.getTime() - latestTrans.time.getTime(), TimeUnit.MILLISECONDS);
		String result = null;
		
		if (timeDiff < 5) {
			result = name + " : $" + dec.format(latestTrans.amount) + "; " + sdf.format(latestTrans.time);
			result = result + "; $" + dec.format(money) + "; " + sdf.format(input) + "\n";
		}
		
		// Update the user latest transaction status
		latestTrans.amount = money;
		latestTrans.time = input;
		sumTrans += money;
		
		// Add new transaction object
		Transactions current = new Transactions(input, money);
		records.add(current);

		return result;
	}
	
	// Function to get the top K transactions
	public String getTopTrans(int input) {
		int numData;
		double top = 0, other = 0;
		String result = name + " :";
		
		// Check how many transaction to be returned, compare with available transactions
		Date timeTop = null;
		if (records.size() < input) {
			numData = records.size();
		} else {
			numData = input;
		}
		Transactions[] temp = new Transactions[numData];

		// Take the transactions, starting from the biggest one 
		for (int i = 0; i < numData; i++) {
			temp[i] = records.poll();
			if (i == 0) {
				// Get the info of the top transaction
				top = temp[i].amount; 
				timeTop = temp[i].time;
			} else {
				other += temp[i].amount; // Other for comparison
			}
			// Top K transaction result
			result = result + " " + dec.format(temp[i].amount);
		}
		
		// Return the transactions to the heap
		for (int j = 0; j < temp.length; j++) {
			records.add(temp[j]);
		}
		
		// Check if the top transaction is suspicious
		other /= (numData - 1);
		if (top > 5 * other) suspicious = name + " : $" + dec.format(top) + "; " + sdf.format(timeTop);

		// Print additional info: number of transactions, average transaction amount
		result = result + "  # of transactions = " + records.size() + ";";
		result = result + " avg = $" + dec.format(sumTrans / records.size() * 1.0);
		
		return result;
	}

	public double getSumTrans() {
		return sumTrans;
	}

	public String getSuspicious() {
		return suspicious;
	}

	public String getStatus() {
		// Get the status string if the user is included in the top 20 spenders
		SimpleDateFormat stat = new SimpleDateFormat("dd/MM/yyyy");
		return name + " : Total Amount = $" + dec.format(sumTrans) + "; First Transaction on " + stat.format(firstTrans) + "; Last Transaction on " + stat.format(latestTrans.time) + "\n";
	}

	// Class to store information for every transaction that the customers made
	private class Transactions {
		public double amount;
		public Date time;

		public Transactions(Date input, double money) {
			time = input;
			amount = money;
		}
	}
}