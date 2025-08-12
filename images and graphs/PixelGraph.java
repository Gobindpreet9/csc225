/* PixelGraph.java
   CSC 225 - Summer 2025

   B. Bird - 2025-07-07
   Gobindpreet Makkar - V01075365 - 2025-07-28
*/ 

import java.awt.Color;

public class PixelGraph{
   private PixelVertex[][] vertices;

    /* PixelGraph constructor
       Given a 2d array of colour values (where element [x][y] is the colour 
       of the pixel at position (x,y) in the image), initialize the data
       structure to contain the pixel graph of the image. 
    */
    public PixelGraph(Color[][] imagePixels){
        vertices = new PixelVertex[imagePixels.length][imagePixels[0].length];
        for (int i = 0; i < imagePixels.length; i++) {
            for (int j = 0; j < imagePixels[i].length; j++) {
                vertices[i][j] = new PixelVertex(i, j);
            }
        }
        addEdges(imagePixels);
    }

    private void addEdges(Color[][] imagePixels){
        for (int i = 0; i < imagePixels.length; i++) {
            for (int j = 0; j < imagePixels[i].length; j++) {
                Color currentColor = imagePixels[i][j];
                if (i > 0 && imagePixels[i - 1][j].equals(currentColor)) {
                    vertices[i][j].addNeighbour(vertices[i - 1][j]);
                }
                if (i < imagePixels.length - 1 && imagePixels[i + 1][j].equals(currentColor)) {
                    vertices[i][j].addNeighbour(vertices[i + 1][j]);
                }
                if (j > 0 && imagePixels[i][j - 1].equals(currentColor)) {
                    vertices[i][j].addNeighbour(vertices[i][j - 1]);
                }
                if (j < imagePixels[i].length - 1 && imagePixels[i][j + 1].equals(currentColor)) {
                    vertices[i][j].addNeighbour(vertices[i][j + 1]);
                }
            }
        }
    }
    
    /* getPixelVertex(x,y)
       Given an (x,y) coordinate pair, return the PixelVertex object 
       corresponding to the pixel at the provided coordinates.
       This method is not required to perform any error checking (and you may
       assume that the provided (x,y) pair is always a valid point in the 
       image).
    */
    public PixelVertex getPixelVertex(int x, int y){
      return vertices[x][y];
    }
    
    /* getWidth()
       Return the width of the image corresponding to this PixelGraph 
       object.
    */
    public int getWidth(){
        return vertices.length;
    }
    
    /* getHeight()
       Return the height of the image corresponding to this PixelGraph 
       object.
    */
    public int getHeight(){
        return vertices[0].length;
    }
    
}