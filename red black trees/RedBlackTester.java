/* RedBlackTester.java
   CSC 225 - Summer 2025

 
   B. Bird - 2025-06-19
*/

import java.util.Arrays;
import java.util.stream.*;

public class RedBlackTester{



    private static void treeToSVG(RedBlackTree T, String svg_filename){
        new RedBlackSVGRenderer(T).render(svg_filename);
    }

    private static boolean validateRBTree(RedBlackTree T){
        try{
            RedBlackValidator V = new RedBlackValidator(T);
            V.validateBSTConstraints();
            System.out.println("  BST constraints are satisfied");
            V.validateRBConstraints();
            System.out.println("  Red/Black constraints are satisfied");
        }catch(RedBlackValidator.OutOfOrderException e){
            System.out.println("  Error: Keys "+e.node1.key()+" and "+e.node2.key()+" appear out of order.");
            return false;
        }catch(RedBlackValidator.DegreeViolationException e){
            System.out.println("  Error: Degree constraint violated at node containing key "+e.node.key()+".");
            return false;
        }catch(RedBlackValidator.RootIsRedException e){
            System.out.println("  Error: The root is red.");
            return false;
        }catch(RedBlackValidator.RedOnTheRightException e){
            System.out.println("  Error: The red node containing key "+e.node.key()+" is a right child of its parent.");
            return false;
        }catch(RedBlackValidator.RedChildOfRedException e){
            System.out.println("  Error: The red node containing key "+e.node.key()+" has a red child.");
            return false;
        }catch(RedBlackValidator.UnbalancedException e){
            System.out.println("  Error: Black balance constraint does not hold below the node containing key "+e.node.key()+".");
            return false;
        }
        return true;
    }
    private static int[] parseIntegerParameterSequence( String parameterString ){
        return Arrays.stream(parameterString.split(",")).flatMapToInt(t -> IntStream.of(Integer.parseInt(t))).toArray();
    }
    public static void main( String[] args ){
        RedBlackTree T = new RedBlackTree();
        for( String arg: args ){
            int i = 0;
            while(i < arg.length() && (arg.charAt(i) == ' ' || arg.charAt(i) == '-'))
                i++;
            if (i == arg.length())
                continue;
            char directive = arg.toLowerCase().charAt(i);
            String parameterString = arg.substring(i+1);
            switch(directive){
                case 'i':
                    for( int key: parseIntegerParameterSequence(parameterString) ){
                        System.out.println("Inserting "+key);
                        if (T.insert(key)){
                            System.out.println("  Insertion successful");
                        }else{
                            System.out.println("  Key already present");
                        }
                    }
                    break;
                case 'f':
                    for( int key: parseIntegerParameterSequence(parameterString) ){
                        System.out.println("Searching for "+key);
                        if (T.find(key)){
                            System.out.println("  Key was found");
                        }else{
                            System.out.println("  Key was not found");
                        }
                    }
                    break;
                case 'v':
                    System.out.println("Validating tree structure");
                    validateRBTree(T);
                    break;
                case 's':
                    System.out.println("Saving current tree to "+ parameterString);
                    treeToSVG(T, parameterString);
                    break;
                default:
                    System.err.println("Invalid directive "+directive);
                    return;
            }
        }
    }
}