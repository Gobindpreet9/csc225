/* RedBlackSVGRenderer.java
 
   B. Bird - 2025-06-19
*/

import java.util.Map;
import java.util.TreeMap;
import java.io.PrintStream;
import java.io.File;

public class RedBlackSVGRenderer{

    private static final int node_size = 50;
    private static final int level_height = 100;
    private static final int node_padding = 20;


    private class XYPair{
        double x, y;
        public XYPair(double x, double y){
            this.x = x;
            this.y = y;
        }
    }
    private class WidthPair{
        double x_min, x_max;
        public WidthPair(double x_min, double x_max){
            this.x_min = x_min;
            this.x_max = x_max;
        }
    }

    public RedBlackSVGRenderer( RedBlackTree T ){
        this.T = T;
        nodes_by_index = new TreeMap<Integer, RedBlackNode>();
        node_coordinates = new TreeMap<Integer, XYPair>();
    }
    private void gatherNodes(RedBlackNode node, int node_idx){
        if (node == null)
            return;
        nodes_by_index.put(node_idx, node);
        gatherNodes(node.leftChild(), 2*node_idx);
        gatherNodes(node.rightChild(), 2*node_idx + 1);
    }
    private WidthPair setChildInitialCoordinates(RedBlackNode node, int node_idx){
        if (node == null)
            return new WidthPair(-node_size/2 - node_padding/2, node_size/2 + node_padding/2);
        WidthPair width = new WidthPair(-node_size/2 - node_padding/2, node_size/2 + node_padding/2);
        WidthPair leftWidth = setChildInitialCoordinates( node.leftChild(), node_idx*2);
        WidthPair rightWidth = setChildInitialCoordinates( node.rightChild(), node_idx*2 + 1);

        double offset = (leftWidth.x_max - rightWidth.x_min)/2;
        double leftOffset = 0;
        double rightOffset = 0;
        if (node.leftChild() != null){
            leftOffset = -offset - node_padding/2;
            node_coordinates.put(node_idx*2, new XYPair(leftOffset,level_height));
        }
        if (node.rightChild() != null){
            rightOffset = offset + node_padding/2;
            node_coordinates.put(node_idx*2+1, new XYPair(rightOffset,level_height));
        }
        width.x_min = Math.min(width.x_min, leftWidth.x_min + leftOffset);
        width.x_max = Math.max(width.x_max, rightWidth.x_max + rightOffset);

        return width;
    }
    private void setFinalCoordinates(RedBlackNode node, int node_idx){
        if (node == null)
            return;
        if (node_idx > 1){
            XYPair parentCoordinates = node_coordinates.get(node_idx/2);
            XYPair nodeCoordinates = node_coordinates.get(node_idx);
            nodeCoordinates.x += parentCoordinates.x;
            nodeCoordinates.y += parentCoordinates.y;
        }
        setFinalCoordinates(node.leftChild(), 2*node_idx);
        setFinalCoordinates(node.rightChild(), 2*node_idx + 1);
    }
    public void render(String svg_filename){
        nodes_by_index.clear();
        node_coordinates.clear();
        gatherNodes(T.getRoot(), 1);
        node_coordinates.put(1,new XYPair(0,0));
        setChildInitialCoordinates(T.getRoot(),1);
        setFinalCoordinates(T.getRoot(),1);

        PrintStream output_file = null;
        try{
            output_file = new PrintStream(svg_filename);
        }catch(java.io.FileNotFoundException e){
            System.err.println("  Error: Unable to open "+svg_filename);
            return;
        }

        double x_min = 0, x_max = 0;
        double y_min = 0, y_max = 0;
        for (Map.Entry<Integer, XYPair> entry : node_coordinates.entrySet()){
            x_min = Math.min(x_min, entry.getValue().x);
            x_max = Math.max(x_max, entry.getValue().x);
            y_min = Math.min(y_min, entry.getValue().y);
            y_max = Math.max(y_max, entry.getValue().y);
        }
        x_min -= node_size;
        y_min -= node_size;
        x_max += node_size;
        y_max += node_size;
        output_file.printf("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"%d\" height=\"%d\">\n",(int)(x_max-x_min), (int)(y_max-y_min));
        output_file.printf("<g transform=\"translate(%d %d)\">\n",(int)-x_min,(int)-y_min);
        for (Map.Entry<Integer, RedBlackNode> entry : nodes_by_index.entrySet()){
            int node_idx = entry.getKey();
            RedBlackNode node = entry.getValue();
            XYPair coordinates = node_coordinates.get(node_idx);
            if (node.leftChild() != null){
                XYPair childCoordinates = node_coordinates.get(node_idx*2);
                output_file.printf("<line x1=\"%f\" y1=\"%f\" x2=\"%f\" y2=\"%f\" stroke=\"black\" stroke-width=\"2\"/>\n", coordinates.x, coordinates.y, childCoordinates.x, childCoordinates.y);
            }
            if (node.rightChild() != null){
                XYPair childCoordinates = node_coordinates.get(node_idx*2+1);
                output_file.printf("<line x1=\"%f\" y1=\"%f\" x2=\"%f\" y2=\"%f\" stroke=\"black\" stroke-width=\"2\"/>\n", coordinates.x, coordinates.y, childCoordinates.x, childCoordinates.y);
            }
            if (node.isRed())
                output_file.printf("<circle cx=\"%f\" cy=\"%f\" r=\"%d\" fill=\"#ffffff\" stroke=\"#ff3333\" stroke-width=\"5\"/>\n", coordinates.x, coordinates.y, node_size/2);
            else
                output_file.printf("<circle cx=\"%f\" cy=\"%f\" r=\"%d\" fill=\"#ffffff\" stroke=\"#000000\" stroke-width=\"5\"/>\n", coordinates.x, coordinates.y, node_size/2);
            output_file.printf("<text x=\"%f\" y=\"%f\" text-anchor=\"middle\" alignment-baseline=\"central\" dominant-baseline=\"central\">%s</text>\n",coordinates.x, coordinates.y, Integer.toString(node.key()));
        }
        output_file.println("</g>");
        output_file.println("</svg>");

        output_file.close();


    }
    

    private RedBlackTree T;
    // TreeMaps are important here (instead of HashMap) since we rely on the ability
    // to iterate through keys in ascending order.
    private TreeMap<Integer, RedBlackNode> nodes_by_index;
    private TreeMap<Integer, XYPair> node_coordinates;
}