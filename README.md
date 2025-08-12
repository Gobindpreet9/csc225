# CSC 225 - Data Structures and Algorithms Programming Assignments

This repository contains programming assignments completed for CSC 225 - Data Structures and Algorithms at the University of Victoria.

## Table of Contents

1. [Convex Hull](#convex-hull)
2. [Red Black Trees](#red-black-trees)
3. [Images and Graphs](#images-and-graphs)

## Convex Hull

Implementation of convex hull algorithms to compute the smallest convex polygon that contains all given points.

### Files
- `HullBuilder.java` - Main implementation of convex hull algorithms
- `HullViewer225.java` - Visualization tool for convex hulls
- `Point2d.java` - 2D point data structure
- `PrintHull.java` - Utility for printing hull results
- `TestContainment.java` - Testing containment of points within hull
- Various test files (`.txt`) for different test cases

### Test Cases
- Simple shapes (Diamond, Square, Triangle)
- Complex polygons (Hexagon)
- Floating point coordinates
- Random point sets of varying sizes
- Edge cases (identical points, collinear points)

### Performance Notes

The current implementation of the convex hull algorithm has known inefficiencies due to accessing elements by index from a linked list data structure. This results in O(n) time complexity for each index-based access, leading to suboptimal overall performance for large datasets.

## Red Black Trees

Implementation of a Red-Black Tree data structure with insertion, deletion, and validation functionality.

### Files
- `RedBlackTree.java` - Main Red-Black Tree implementation
- `RedBlackNode.java` - Node structure for the tree
- `RedBlackTester.java` - Testing framework
- `RedBlackValidator.java` - Validation of Red-Black tree properties
- `RedBlackSVGRenderer.java` - Visualization of trees as SVG
- `RedBlackTrege.java` - Additional tree functionality

## Images and Graphs

Graph algorithms applied to image processing, including pathfinding and connectivity analysis.

### Files
- `A3Algorithms.java` - Implementation of graph algorithms
- `ImageViewer225.java` - Image viewing and processing application
- `PixelGraph.java` - Graph representation of images
- `PixelVertex.java` - Vertex representation for pixels
- `PixelWriter.java` - Utility for writing pixel data
- Sample images For Testing (`heart.png`, `rainbow.png`)

### Algorithms
- Connected component analysis
- Shortest path algorithms
- Image segmentation techniques

## Usage

Each assignment can be compiled and run independently:

```bash
# Navigate to the specific assignment directory
# Compile Java files
javac *.java

# Run specific programs (examples)
java HullViewer225
java RedBlackTester
java ImageViewer225
```

## License

This project is for educational purposes only and should not be used without permission.
