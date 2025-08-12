/* PixelVertex.java
   CSC 225 - Summer 2025


   B. Bird - 2025-07-07
   Gobindpreet Makkar - V01075365 - 2025-07-28
*/


public class PixelVertex{
    
    private int x;
    private int y;
    private boolean visited;
    int degree;
    private PixelVertex[] neighbours;
    
    public PixelVertex(int x, int y) {
        this.x = x;
        this.y = y;
        this.neighbours = new PixelVertex[4];
        this.visited = false;
        this.degree = 0;
    }

    /* getX()
       Return the x-coordinate of the pixel corresponding to this vertex.
    */
    public int getX(){
        return x;
    }
    
    /* getY()
       Return the y-coordinate of the pixel corresponding to this vertex.
    */
    public int getY(){
        return y;
    }
    
    /* getNeighbours()
       Return an array containing references to all neighbours of this vertex.
       The size of the array must be equal to the degree of this vertex (and
       the array may therefore contain no duplicates).
    */
    public PixelVertex[] getNeighbours(){
        PixelVertex[] result = new PixelVertex[degree];
        int index = 0;
        for (PixelVertex neighbour : neighbours) {
            if (neighbour != null) {
                result[index] = neighbour;
                index++;
            }
        }
        return result;
    }
    
    /* addNeighbour(newNeighbour)
       Add the provided vertex as a neighbour of this vertex.
    */
    public void addNeighbour(PixelVertex newNeighbour){
      for (int i = 0; i < neighbours.length; i++) {
        if (neighbours[i] == null) {
          neighbours[i] = newNeighbour;
          degree++;
          return;
        }
      }
    }
    
    /* removeNeighbour(neighbour)
       If the provided vertex object is a neighbour of this vertex,
       remove it from the list of neighbours.
    */
    public void removeNeighbour(PixelVertex neighbour){
      for (int i = 0; i < neighbours.length; i++) {
        if (neighbours[i] == neighbour) {
          neighbours[i] = null;
          degree--;
          return;
        }
      }
    }
    
    /* getDegree()
       Return the degree of this vertex. Since the graph is simple, 
       the degree is equal to the number of distinct neighbours of this vertex.
    */
    public int getDegree(){
      return degree;
    }
    
    /* isNeighbour(otherVertex)
       Return true if the provided PixelVertex object is a neighbour of this
       vertex and false otherwise.
    */
    public boolean isNeighbour(PixelVertex otherVertex){
      for (PixelVertex neighbour : neighbours) {
        if (neighbour == otherVertex) {
          return true;
        }
      }
      return false;
    }

    /*
     * setVisited()
     * Set the visited flag of this vertex to the provided value.
     */
    public void setVisited(boolean b) {
      visited = b;
    }

    /*
     * isVisited()
     * Return the visited flag of this vertex.
     */
    public boolean isVisited() {
      return visited;
    }
}