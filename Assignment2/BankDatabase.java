/* Henry Pratama Suryadi
A0105182R Assignment 2 Submission*/

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;

public class BankDatabase {
    
    private int nodesNum;
    private int selection;
    private TreeNode root;
	
	private String suspiciousReport = "";
    private PriorityQueue<BankAccount> topSpenders = null;
	
	// Initialise the comparator for topSpenders PQ
    private static final minHeapComparator acctComp = new minHeapComparator();
    private static class minHeapComparator implements Comparator<BankAccount> {
        @Override
        public int compare(BankAccount i, BankAccount j) {
            return i.getSumTrans() > j.getSumTrans() ? 1 : 1 == j.getSumTrans() ? 0 : -1;
        }
    }
    /**
     * Initializes an empty binary tree.
     */
    public BankDatabase(int input) {
        root = new TreeNode();
        nodesNum = 0;
        selection = input;
        topSpenders = new PriorityQueue<BankAccount>(21, acctComp);
    }

    /**
     * Returns true if this tree is empty.
     * @return true if this tree is empty
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the number of nodes in BST.
     * @return the number of nodes
     */
    public int size() {
        return nodesNum;
    }

    /**
     * Inserts <key, value> item where it belongs in the tree
     * @param key the key
     * @param value the value
     * @return true if item is inserted; false if it is already in the tree
     */
    public boolean add(String key, BankAccount value) {
        if (nodesNum == 0) {
            root.key = key;
            root.value = value;
            nodesNum++;
        }
        else {
            TreeNode nextNode = root;
            while (true) {
                if (key.compareTo(nextNode.key) < 0) {
                    if (nextNode.left == null) {
                        nextNode.left = new TreeNode(key, value);
                        nodesNum++;
                        break;
                    }  
                    else {
                        nextNode = nextNode.left;
                    }
                }
                else if (key.compareTo(nextNode.key) > 0) {
                    if (nextNode.right == null) {
                        nextNode.right = new TreeNode(key, value);
                        nodesNum++;
                        break;
                    }
                    else {
                        nextNode = nextNode.right;
                    } 
                }
                else {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    /**
     * find whether key is in this tree
     * @param key the target key
     * @return true if target is found in the tree
     */
    public boolean contains(String key) {
        if (isEmpty()) return false;
        return (find(key) != null);
    }
    
    /**
     * search key in this tree
     * @param key the target key
     * @return the value of key 
     */
    public BankAccount find(String key) {
        TreeNode nextNode = root;
        while (nextNode != null) {
            if (key.compareTo(nextNode.key) == 0)
                return nextNode.value;
            else if (key.compareTo(nextNode.key) < 0)
                nextNode = nextNode.left;
            else
                nextNode = nextNode.right;
        }
        return null;
    }
    
    private String delReturn;  // return value of function delete
    /**
     * remove the node of key from the tree
     * @param key the target key
     * @return the key which is deleted if found; otherwise null
     */
    public String delete(String key) {
        root = _delete(root, key);
        if (root != null)
            nodesNum--;
        return delReturn;
    }
    
    /**
     * remove the node of key from the tree
     * @param key the target key
     * @return true if key is found
     */
    public boolean remove(String key) {
        if (delete(key) != null) {
            return true;
        }
        else {
            return false;
        }
    }
    
    /**
     * recursive function called by function delete to remove target
     * @param localRoot the root of the subtree
     * @param key the node with key to be removed
     * @return the modified local root that does not contain the item
     */
    private TreeNode _delete(TreeNode localRoot, String key) {
        if (localRoot == null) {
            delReturn = null;
            return localRoot;
        }
        
        if (key.compareTo(localRoot.key) < 0) {
            localRoot.left = _delete(localRoot.left, key);
            return localRoot;
        }
        else if (key.compareTo(localRoot.key) > 0) {
            localRoot.right = _delete(localRoot.right, key);
            return localRoot;
        }
        else {
            // the target is at local root
            delReturn = localRoot.key;
            if (localRoot.left == null) {
                // If there is no left child, return right child
                return localRoot.right;
            }
            else if (localRoot.right == null) {
                // If there is no right child, return left child
                return localRoot.left;
            }
            else {
                // If there are two children, replace the target node with 
                // inorder predecessor(largest node smaller then target)
                if (localRoot.left.right == null) {
                    localRoot.left.right = localRoot.right;
                    return localRoot.left;
                }
                else {
                    TreeNode curNode = localRoot.left.right;
                    TreeNode parent = localRoot.left;
                    while (curNode.right != null) {
                        parent = curNode;
                        curNode = curNode.right;
                    }
                    parent.right = curNode.left;
                    curNode.left = localRoot.left;
                    curNode.right = localRoot.right;
                    return curNode;
                }
                
            } // end if localRoot
        } // end if key.compareTo
    }
    
	// Print the report of the transactions check
    public void printRecords(String filename, BufferedWriter out) {
        String result = "";
        BankAccount temp;
        try {
			// Print the top K transactions
            out.write("---------------- Top K Transactions ----------------\n");
            printTransactions(root, out);
			
			// Print the suspicious transactions			
            out.write("Suspicious Transactions\n");
            out.write(suspiciousReport);
			
			// Print the top 20 spenders
            out.write("Top 20 Spenders\n");
            for (int i = 0; i < 20; i++) {
                temp = topSpenders.poll();
                result = temp.getStatus() + result;
            }
            out.write(result);
            
        } catch (IOException e) {
            System.out.println("File write error for (" + filename + ")! ");
            e.printStackTrace();
        }
    }

    private void printTransactions(TreeNode curAcct, BufferedWriter out) {
		// This function will go through the BST nodes one by one
        if (curAcct == null)
            return;
        printTransactions(curAcct.left, out);
        
		// For each node, check for top 20 spenders
		topSpenders.add(curAcct.value);
		if (topSpenders.size() > 20) topSpenders.poll();
        
		try {
			// Get the top K transactions for each user
            out.write(curAcct.value.getTopTrans(selection) + "\n");
			// Get the suspicious transaction if the user has any
			String suspicious = curAcct.value.getSuspicious();
            if (suspicious != null) suspiciousReport = suspiciousReport + suspicious + "\n";
        } catch (IOException e) {
            System.out.println("File write error! ");
            e.printStackTrace();
        }
        
        printTransactions(curAcct.right, out);
        return;
    }

    private class TreeNode {
        public String key;
        public BankAccount value;
        public TreeNode left, right;

        public TreeNode(){
            left = null;
            right = null;
        }
        
        public TreeNode(String key, BankAccount value) {
            this.key = key;
            this.value = value;
            left = null;
            right = null;
        }
    }
    
}
