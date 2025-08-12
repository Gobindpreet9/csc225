/* A3Algorithms.java
   CSC 225 - Summer 2025


   B. Bird - 2025-07-07
   Gobindpreet Makkar - V01075365 - 2025-07-28
*/ 

import java.awt.Color;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.List;

public class A3Algorithms{

    /* FloodFillDFS(v, writer, fillColour)
       Traverse the component the vertex v using DFS and set the colour 
       of the pixels corresponding to all vertices encountered during the 
       traversal to fillColour.
       
       To change the colour of a pixel at position (x,y) in the image to a 
       colour c, use
            writer.setPixel(x,y,c);
    */
    public static void FloodFillDFS(PixelVertex v, PixelWriter writer, Color fillColour){
        List<PixelVertex> visitedVertices = new ArrayList<>();
        FloodFillDFSRecursive(v, writer, fillColour, visitedVertices);
        resetVisitedVertices(visitedVertices);
    }
    
    public static void FloodFillDFSRecursive(PixelVertex v, PixelWriter writer, Color fillColour, List<PixelVertex> visitedVertices){
        v.setVisited(true);
        visitedVertices.add(v);
        writer.setPixel(v.getX(), v.getY(), fillColour);
        PixelVertex[] neighbors = v.getNeighbours();
        for (PixelVertex neighbor : neighbors) {
            if (!neighbor.isVisited()) {
                FloodFillDFSRecursive(neighbor, writer, fillColour, visitedVertices);
            }
        }
    }
    
    /* FloodFillBFS(v, writer, fillColour)
       Traverse the component the vertex v using BFS and set the colour 
       of the pixels corresponding to all vertices encountered during the 
       traversal to fillColour.
       
       To change the colour of a pixel at position (x,y) in the image to a 
       colour c, use
            writer.setPixel(x,y,c);
    */
    public static void FloodFillBFS(PixelVertex v, PixelWriter writer, Color fillColour){
        List<PixelVertex> visitedVertices = new ArrayList<>();
        v.setVisited(true);
        visitedVertices.add(v);
        writer.setPixel(v.getX(), v.getY(), fillColour);
        Queue<PixelVertex> queue = new LinkedList<>();
        queue.add(v);
        while (!queue.isEmpty()){
            PixelVertex current = queue.remove();
            PixelVertex[] neighbors = current.getNeighbours();
            for (PixelVertex neighbor : neighbors) {
                if (!neighbor.isVisited()) {
                    neighbor.setVisited(true);
                    visitedVertices.add(neighbor);
                    writer.setPixel(neighbor.getX(), neighbor.getY(), fillColour);
                    queue.add(neighbor);
                }
            }
        }
        resetVisitedVertices(visitedVertices);
    }
    
    /* OutlineRegionDFS(v, writer, outlineColour)
       Traverse the component the vertex v using DFS and set the colour 
       of the pixels corresponding to all vertices with degree less than 4
       encountered during the traversal to outlineColour.
       
       To change the colour of a pixel at position (x,y) in the image to a 
       colour c, use
            writer.setPixel(x,y,c);
    */
    public static void OutlineRegionDFS(PixelVertex v, PixelWriter writer, Color outlineColour){
        List<PixelVertex> visitedVertices = new ArrayList<>();
        OutlineRegionDFSRecursive(v, writer, outlineColour, visitedVertices);
        resetVisitedVertices(visitedVertices);
    }
    
    public static void OutlineRegionDFSRecursive(PixelVertex v, PixelWriter writer, Color outlineColour, List<PixelVertex> visitedVertices){
        v.setVisited(true);
        visitedVertices.add(v);
        if (v.getDegree() < 4) {
            writer.setPixel(v.getX(), v.getY(), outlineColour);
        }
        PixelVertex[] neighbors = v.getNeighbours();
        for (PixelVertex neighbor : neighbors) {
            if (!neighbor.isVisited()) {
                OutlineRegionDFSRecursive(neighbor, writer, outlineColour, visitedVertices);
            }
        }
    }
    
    /* OutlineRegionBFS(v, writer, outlineColour)
       Traverse the component the vertex v using BFS and set the colour 
       of the pixels corresponding to all vertices with degree less than 4
       encountered during the traversal to outlineColour.
       
       To change the colour of a pixel at position (x,y) in the image to a 
       colour c, use
            writer.setPixel(x,y,c);
    */
    public static void OutlineRegionBFS(PixelVertex v, PixelWriter writer, Color outlineColour){
        List<PixelVertex> visitedVertices = new ArrayList<>();
        v.setVisited(true);
        visitedVertices.add(v);
        if (v.getDegree() < 4) {
            writer.setPixel(v.getX(), v.getY(), outlineColour);
        }
        Queue<PixelVertex> queue = new LinkedList<>();
        queue.add(v);
        while (!queue.isEmpty()) {
            PixelVertex current = queue.remove();
            PixelVertex[] neighbors = current.getNeighbours();
            for (PixelVertex neighbor : neighbors) {
                if (!neighbor.isVisited()) {
                    neighbor.setVisited(true);
                    visitedVertices.add(neighbor);
                    if (neighbor.getDegree() < 4) {
                        writer.setPixel(neighbor.getX(), neighbor.getY(), outlineColour);
                    }
                    queue.add(neighbor);
                }
            }
        }
        resetVisitedVertices(visitedVertices);
    }

    /* CountComponents(G)
       Count the number of connected components in the provided PixelGraph 
       object.
    */
    public static int CountComponents(PixelGraph G){
        int width = G.getWidth();
        int height = G.getHeight();
        int count = 0;
        List<PixelVertex> allVisitedVertices = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (!G.getPixelVertex(x, y).isVisited()) {
                    RunBFS(G.getPixelVertex(x, y), allVisitedVertices);
                    count++;
                }
            }
        }
        resetVisitedVertices(allVisitedVertices);
        return count;
    }
    
    private static void RunBFS(PixelVertex v, List<PixelVertex> allVisitedVertices){
        v.setVisited(true);
        allVisitedVertices.add(v);
        Queue<PixelVertex> queue = new LinkedList<>();
        queue.add(v);
        while (!queue.isEmpty()) {
            PixelVertex current = queue.remove();
            PixelVertex[] neighbors = current.getNeighbours();
            for (PixelVertex neighbor : neighbors) {
                if (!neighbor.isVisited()) {
                    neighbor.setVisited(true);
                    allVisitedVertices.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
    }

    private static void resetVisitedVertices(java.util.List<PixelVertex> visitedVertices){
        for (PixelVertex vertex : visitedVertices) {
            vertex.setVisited(false);
        }
    }
}