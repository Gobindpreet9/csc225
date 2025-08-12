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
        public Node(int key, Node left, Node right, boolean isRed){
            this.key = key;
            this.left = left;
            this.right = right;
            this.isRed = isRed;
        }
        public int key(){
            return key;
        }
        public boolean isRed(){
            return isRed;
        }
        public RedBlackNode leftChild(){
            return left;
        }
        public RedBlackNode rightChild(){
            return right;
        }
        

        public int key;
        public Node left, right;
        public boolean isRed;
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
     * Time is O(2h) or just O(h) as we traverse from root to leaf. O(h) for find() and O(h) for insert()
     * There is some constant time work to make sure all the constraints are satisfied. 
     * @return true if the key was added, false if the key was not added due to being present already
     */
    public boolean insert(int key){
        if (find(key)) {
            return false; 
        }
        
        root = insert(root, key);
        root.isRed = false; 
        return true; 
    }
    
    private Node insert(Node node, int key) {
        if (node == null) {
            return new Node(key, null, null, true);
        }
        
        if (key < node.key) {
            node.left = insert(node.left, key);
        } else {
            node.right = insert(node.right, key);
        }
        
        node = FixConstraintsIfNecessary(node);

        return node;
    }

    private Node FixConstraintsIfNecessary(Node node) {
        if (isRed(node.right) && !isRed(node.left)) {
            node = rotateLeft(node);
        }
        if (isRed(node.left) && isRed(node.left.left)) {
            node = rotateRight(node);
        }
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }
        return node;
    }

    private boolean isRed(Node node) {
        if (node == null) return false;
        return node.isRed;
    }
    
    private Node rotateLeft(Node node) {
        Node flippedNode = node.right;
        node.right = flippedNode.left;
        flippedNode.left = node;
        flippedNode.isRed = node.isRed;
        node.isRed = true;
        return flippedNode;
    }
    
    private Node rotateRight(Node node) {
        Node flippedNode = node.left;
        node.left = flippedNode.right;
        flippedNode.right = node;
        flippedNode.isRed = node.isRed;
        node.isRed = true;
        return flippedNode;
    }
    
    private void flipColors(Node node) {
        node.isRed = true;
        node.left.isRed = false;
        node.right.isRed = false;
    }

    /**
     * Test whether the provided key is in the tree.
     * Time is O(h) as we traverse from root to leaf.
     * @return true if the key is in the tree, false if the key is not in the tree
     */
    public boolean find(int key){
        Node current = root;
        while (current != null){
            if (current.key() == key){
                return true;
            }
            else if (key < current.key){
                current = current.left;
            }
            else {
                current = current.right;
            }
        }
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