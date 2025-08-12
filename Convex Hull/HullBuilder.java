/* HullBuilder.java
   CSC 225 - Summer 2025

   Starter code for Convex Hull Builder. Do not change the signatures
   of any of the methods below (you may add other methods as needed).

   B. Bird - 2025-05-12
   Gobindpreet Makkar / V01075365 / 2025-06-15
*/

import java.util.Collections;
import java.util.LinkedList;

public class HullBuilder{
    // Points sorted by smallest x then smallest y for all Point lists
    private LinkedList<Point2d> allPointsSorted;
    // structure of hull from extreme negative X to extreme positive Y to extreme positive X
    private LinkedList<Point2d> upperHull;
    // structure of hull from extreme negative X to extreme negative Y to extreme positive X
    private LinkedList<Point2d> lowerHull;
    private Point2d extremePositiveY;
    private Point2d extremeNegativeY;
    private Point2d extremePositiveX;
    private Point2d extremeNegativeX;
   

    public HullBuilder(){
        allPointsSorted = new LinkedList<Point2d>();
        upperHull = new LinkedList<Point2d>();
        lowerHull = new LinkedList<Point2d>();
        extremePositiveY = null;
        extremeNegativeY = null;
        extremePositiveX = null;
        extremeNegativeX = null;
    }
    
    /* addPoint(P)
       Add the point P to the internal point set for this object.
       Note that there is no facility to delete points (other than
       destroying the HullBuilder and creating a new one). 
    */
    public void addPoint(Point2d P){
         if (isInsideHull(P))
            return;

        if (!AddPointToArraySortedByXThenY(allPointsSorted, P))
            return; // duplicate point, no action

        upperHull.clear();
        lowerHull.clear();

        // remove collinear point if there are 3 points
        if (allPointsSorted.size() == 3 && Point2d.chirality(allPointsSorted.get(0), allPointsSorted.get(1), allPointsSorted.get(2)) == 0){
            allPointsSorted.remove(1);
        }
        
        if (allPointsSorted.size() > 3){
            buildUpperHull();
            buildLowerHull();
        }
        
        UpdateExtremePointIfRequired(P);
    }

    private void buildUpperHull(){
        for (Point2d p : allPointsSorted){
            while (upperHull.size() >= 2 && !makesRightTurn(p)) {
                upperHull.removeLast();
            }
            upperHull.addLast(p);
        }
    }

    private void buildLowerHull(){
        for (Point2d p : allPointsSorted){
            while (lowerHull.size() >= 2 && !makesLeftTurn(p)) {
                lowerHull.removeLast();
            }
            lowerHull.addLast(p);
        }
    }

    private boolean makesRightTurn(Point2d p){
        return Point2d.chirality(upperHull.get(upperHull.size()-2), upperHull.getLast(), p) == 1;
    }

    private boolean makesLeftTurn(Point2d p){
        return Point2d.chirality(lowerHull.get(lowerHull.size()-2), lowerHull.getLast(), p) == -1;
    }

    /*  
    * Add point p to the array hull sorted by smallest x then smallest y 
    * Returns true if the point was added, false otherwise
    */
    private boolean AddPointToArraySortedByXThenY(LinkedList<Point2d> hull, Point2d p) {
        // makes use of comparator defined in Point2d
        int index = Collections.binarySearch(hull, p);
        // index is negative if the point is not in the list
        if (index < 0) {
            hull.add(-(index + 1), p);
            return true;
        }
        // duplicate point ignored
        return false;
    }

    /* getHull()
       Return a java.util.LinkedList object containing the points
       in the convex hull, in order (such that iterating over the list
       will produce the same ordering of vertices as walking around the 
       polygon).
    */
    public LinkedList<Point2d> getHull(){
        if (allPointsSorted.size() <= 3){
            return new LinkedList<Point2d>(allPointsSorted);
        }
        
        var hull = new LinkedList<Point2d>(upperHull);
        // reverse order ignoring the first and last point
        for (int i = lowerHull.size() - 2; i > 0; i--) {
            hull.add(lowerHull.get(i));
        }
        return hull;
    }

    /* isInsideHull(P)
       Given a point P, return true if P lies inside the convex hull
       of the current point set (note that P may not be part of the
       current point set). Return false otherwise.
     */
    public boolean isInsideHull(Point2d P){
        if (allPointsSorted.size() < 3)
            return allPointsSorted.contains(P);
        
        if (P.y < extremeNegativeY.y || P.y > extremePositiveY.y || P.x < extremeNegativeX.x || P.x > extremePositiveX.x){
            return false;
        }
        
        var hull = getHull();
        for (int i = 0; i < hull.size(); i++){ 
            if (i == hull.size() - 1 && Point2d.chirality(hull.get(i), hull.get(0), P) == -1){
                return false;
            }
            else if (i != hull.size() - 1 && Point2d.chirality(hull.get(i), hull.get(i + 1), P) == -1){
                return false;
            }
        }

        return true;
    }

    private void UpdateExtremePointIfRequired(Point2d P){
        if (extremePositiveY == null || P.y > extremePositiveY.y){
            extremePositiveY = P;
        }
        if (extremeNegativeY == null || P.y < extremeNegativeY.y){
            extremeNegativeY = P;
        }
        if (extremePositiveX == null || P.x > extremePositiveX.x){
            extremePositiveX = P;
        }
        if (extremeNegativeX == null || P.x < extremeNegativeX.x){
            extremeNegativeX = P;
        }
    }
}