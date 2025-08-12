/* RedBlackValidator.java
   CSC 225 - Summer 2025

   A validator for left-leaning red-black trees.
 
   B. Bird - 2025-06-18
*/

import java.util.ArrayList;

public class RedBlackValidator{

    public class OutOfOrderException extends Exception {
        public RedBlackNode node1, node2;
        public OutOfOrderException( RedBlackNode node1, RedBlackNode node2 ){
            this.node1 = node1;
            this.node2 = node2;
        }
    }
    public class NodeViolationException extends Exception {
        public RedBlackNode node;
        public NodeViolationException( RedBlackNode node ){
            this.node = node;
        }
    }
    public class DegreeViolationException extends NodeViolationException {
        public DegreeViolationException( RedBlackNode node ){
            super(node);
        }
    }
    public class RootIsRedException extends NodeViolationException {
        public RootIsRedException( RedBlackNode node ){
            super(node);
        }
    }
    public class RedOnTheRightException extends NodeViolationException {
        public RedOnTheRightException( RedBlackNode node ){
            super(node);
        }
    }
    public class RedChildOfRedException extends NodeViolationException {
        public RedChildOfRedException( RedBlackNode node ){
            super(node);
        }
    }
    public class UnbalancedException extends NodeViolationException {
        public UnbalancedException( RedBlackNode node ){
            super(node);
        }
    }

    public RedBlackValidator( RedBlackTree T ){
        this.T = T;
    }
    
    private void traverseInOrder(RedBlackNode node, ArrayList<RedBlackNode> traversalList){
        if (node == null)
            return;
        traverseInOrder(node.leftChild(), traversalList);
        traversalList.add(node);
        traverseInOrder(node.rightChild(), traversalList);
    }
    private ArrayList<RedBlackNode> getInOrderTraversal(){
        ArrayList<RedBlackNode> traversal = new ArrayList<RedBlackNode>();
        traverseInOrder(T.getRoot(), traversal);
        return traversal;
    }
    public void validateBSTConstraints() throws OutOfOrderException{
        ArrayList<RedBlackNode> traversal = getInOrderTraversal();
        for (int i = 0; i < traversal.size()-1; i++){
            if (traversal.get(i).key() > traversal.get(i+1).key())
                throw new OutOfOrderException(traversal.get(i), traversal.get(i+1));
        }
    }


    private int validateRBNode(RedBlackNode node)  throws DegreeViolationException, RootIsRedException, RedChildOfRedException, RedOnTheRightException, UnbalancedException {
        if (node == null)
            return 0;
        if (node.isRed() && (node.leftChild() != null && node.leftChild().isRed()) || (node.rightChild() != null && node.rightChild().isRed())){
            throw new RedChildOfRedException(node);
        }
        if (node.rightChild() != null && node.rightChild().isRed()){
            throw new RedOnTheRightException(node.rightChild());
        }
        if (node.leftChild() != null && !node.leftChild().isRed() && node.rightChild() == null || node.leftChild() == null && node.rightChild() != null){
            throw new DegreeViolationException(node);
        }
        int bl = validateRBNode(node.leftChild());
        int br = validateRBNode(node.rightChild());
        if (bl != br)
            throw new UnbalancedException(node);
        return node.isRed()? bl: bl + 1;
    }
    public void validateRBConstraints() throws DegreeViolationException, RootIsRedException, RedChildOfRedException, RedOnTheRightException, UnbalancedException {
        RedBlackNode root = T.getRoot();
        if (root == null)
            return;
        if (root.isRed())
            throw new RootIsRedException(root);
        validateRBNode(root);
    }

    private RedBlackTree T;
}