package F28DA_CW2;

import java.util.Iterator;
import java.util.Scanner;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class HelloFlyPlanner {

	public static void main(String[] args) {
        Graph<String, DefaultEdge> g = new SimpleDirectedWeightedGraph<>(DefaultEdge.class);
        
        String c1, c2, c3, c4, c5;
        
        c1 = "Edinburgh";
        c2 = "Heathrow";
        c3 = "Dubai";
        c4 = "Sydney";
        c5 = "Kuala Lumpur";
        
        g.addVertex(c1);
        g.addVertex(c2);
        g.addVertex(c3);
        g.addVertex(c4);
        g.addVertex(c5);
        
        addDoubleEdge(g, c1, c2, 80);
        addDoubleEdge(g, c2, c3, 130);
        addDoubleEdge(g, c2, c4, 570);
        addDoubleEdge(g, c3, c5, 170);
        addDoubleEdge(g, c3, c1, 190);
        addDoubleEdge(g, c5, c4, 150);
        
        System.out.println("The following airports are available:");
        for (String s : g.vertexSet()) {
        	System.out.println(" " + s);
        }
        
        System.out.println("Please enter a starting airport:");
        Scanner scan = new Scanner(System.in);
        
        String startNode = scan.nextLine();
        if (!g.containsVertex(startNode)) {
        	System.err.println("Airport doesn't exist!");
        	scan.close();
        	return;
        }
        
        System.out.println("Please enter the destination airport:");
        
        String endNode = scan.nextLine();
        if (!g.containsVertex(endNode)) {
        	System.err.println("Airport doesn't exist!");
        	scan.close();
        	return;
        }
        
        scan.close();
        DijkstraShortestPath<String, DefaultEdge> cheapestPath = new DijkstraShortestPath<>(g);
        GraphPath<String, DefaultEdge> path = cheapestPath.getPath(startNode, endNode);
        
        System.out.println("Printing shortest path:");
        Iterator<String> iter = path.getVertexList().iterator();
        
        String curr = iter.next();
        
        while (iter.hasNext()) {
        	String end = iter.next();
        	
        	System.out.println(" " + curr + " -> " + end);
        	curr = end;
        }
        
        System.out.println("Total cost: £" + (int)path.getWeight());
        
	}
	
	private static void addDoubleEdge(Graph<String, DefaultEdge> g, String v1, String v2, int weight) {
		DefaultEdge e1 = g.addEdge(v1, v2);
		DefaultEdge e2 = g.addEdge(v2, v1);
		
		g.setEdgeWeight(e1, weight);
		g.setEdgeWeight(e2, weight);
	}

}
