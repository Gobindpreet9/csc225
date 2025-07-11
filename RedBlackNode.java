/* RedBlackNode.java

   Interface for a red-black tree node. 

   Do not modify this file in any way.

   B. Bird - 2025-06-18
*/

public interface RedBlackNode{

    /**
     * @return The key stored in this node.
     */
    public int key();

    /**
     * @return true if this node is red, false if this node is black
     */
    public boolean isRed();

    /**
     * @return The left child of this node (or null if the node has no left child)
     */
    public RedBlackNode leftChild();

    /**
     * @return The right child of this node (or null if the node has no left child)
     */
    public RedBlackNode rightChild();
}