/* RedBlackTree.java
   CSC 225 - Summer 2025

   Put your _entire_ implementation in this file.

 
   B. Bird - 2025-06-18
   Gobindpreet Makkar / V01075365 / 2025-07-10
*/

public class RedBlackTree{

    /* This is a basic implementation of the RedBlackNode interface that could
       be a starting point for your own implementation. You are welcome to change 
       (or completely remove) this definition, as long as the node type you create
       implements the RedBlackNode interface.

       Remember that if your code does not properly implement the necessary interfaces,
       we will be unable to mark it and it will receive a mark of zero.
    */
    private class Node implements RedBlackNode{
        public Node(int key, Node left, Node right, boolean red){
            this.key = key;
            this.left = left;
            this.right = right;
            this.red = red;
        }
        public int key(){
            return key;
        }
        public boolean isRed(){
            return red;
        }
        public RedBlackNode leftChild(){
            return left;
        }
        public RedBlackNode rightChild(){
            return right;
        }
        

        public int key;
        public Node left, right;
        public boolean red;
    }
    
    public RedBlackTree(){
        // Add code as needed
        // (You can add other constructors as well, but this default constructor will be used
        //  for testing)
        root = null;
    }

    /**
     * Attempt to insert the provided key into the tree.
     * If the key is not already in the tree, add it to the tree and return true.
     * If the key is already in the tree, return false without modifying the tree.
     * @return true if the key was added, false if the key was not added due to being present already
     */
    public boolean insert(int key){
        /* Your code here */
        return false;
    }

    /**
     * Test whether the provided key is in the tree.
     * @return true if the key is in the tree, false if the key is not in the tree
     */
    public boolean find(int key){
        return false;
    }

    /** 
     * @return The root of the tree
     * (A real implementation would not expose the root to the outside world like this, but we need
     * this feature for validation)
     * 
     * IMPORTANT: If this method does not work correctly, it will be impossible to mark your implementation.
     */
    public RedBlackNode getRoot(){
        return root;
    }

    Node root;
}