/* Henry Pratama Suryadi
A0105182R Assignment 2 Submission*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/* Assignment 2 - CS2010 
 * "Transaction Monitoring"
 * 
 * Please put your name in all of your .java files
 * 
 * YOUR NAME: Henry Pratama Suryadi
 * YOUR MATRICULATION #: A0105182R
 * 
 * COMMENTS:
 * You can modify this code anyway you'd like.  You can add additional class files, etc.
 * This code prompts the user at the command line to enter a filename (e.g. Visa.log) and the size of K.
 * It then each line of the input file as a string and prints that string out to the console.
 * 
 * 
 * Assigned: Feb 2 (Tuesday), 2016
 * Due: Feb 19 (Friday by 11.59pm), 2016
 */

public class TransMonitor {

	public static void main(String[] args) {

		int k;
		double amount;
		String inputFileStr, doubleTrans, filename;
	    String[] dataIn;
	    
	    Date transTime;
	    BankAccount curAcct;
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Scanner scanner = new Scanner(System.in);
        
		// Input filename and "K"
		System.out.print("Enter log file to open? ");
		filename = scanner.next();
		filename = filename.substring(0, filename.length() - 4);
        System.out.print("Number of top transactions to find (K)? ");
        k = scanner.nextInt();
		
		BankDatabase customers = new BankDatabase(k);

        // Read each line of the input file and write it to the console
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename + ".log"));  
			BufferedWriter out = new BufferedWriter(new FileWriter("Report_" + filename + "_" + k + ".txt"));

	        out.write("Double Transactions\n");
	        
			// Input the transactions
	        while ((inputFileStr = in.readLine()) != null) {
				// Parse the line to data, input data accordingly
	        	dataIn = inputFileStr.split("\\s");
	        	amount = Double.parseDouble(dataIn[1].substring(1, dataIn[1].length()));
	        	transTime = sdf.parse(dataIn[2] + " " + dataIn[3]);
	        	
				// Check if there is any previous transaction made by the customer
	        	if (customers.contains(dataIn[0])) {
					// Find the customer's account
	        		curAcct = customers.find(dataIn[0]);
					// Insert transaction, print double transaction if any
	        		doubleTrans = curAcct.insertTrans(transTime, amount);
	        		if (doubleTrans != null) out.write(doubleTrans);
	        	}
	        	else {	
					// Create new user account
	        		curAcct = new BankAccount(dataIn[0], transTime, amount);
	        		customers.add(dataIn[0], curAcct);
	        	}
	        }
	        in.close();
			
			// Print all the remaining required report
	        customers.printRecords(filename, out);
	        out.close();

		} catch (IOException e) {
	        System.out.println("File read error for (" + filename + ")! ");
	        e.printStackTrace();
	    } catch (ParseException e) {
	    	System.out.println("Invalid date format from input file! ");
	    	e.printStackTrace();
	    }

	} /* end main */

} /* TransMonitor.java */
