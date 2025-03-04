package assign07;

import java.util.List;

/**
 * Class that holds three generic static methods 
 * regarding a graph data-structure's function, including 
 * depth-first search, breadth-first search, and topological sort.
 * 
 * @author Daniel Lee and Mi Zeng
 * @verstion 3-3-2025
 */
public class GraphUtility {
	/**
	 * Determines if a graph created from the given lists of source elements
	 * and destination elements has a path from srcData to dstData.
	 * 
	 * @param <Type> - the type of elements the graph contains
	 * @param sources - the list of source elements
	 * @param destinations - the list of destination elements the sources point to
	 * @param srcData - the element where the path starts
	 * @param dstData - the element to determine if there is a path to from srcData
	 * @return true or false if a path exists between srcData and dstData, respectively
	 */
	public static <Type> boolean areConnected(List<Type> sources, List<Type> destinations, Type srcData, Type dstData) {
		Graph<Type> graph = constructGraph(sources, destinations);
		
		return graph.depthFirstSearch(srcData, dstData);
	}
	
	/**
	 * Finds the shortest path possible in a graph created from
	 * the given lists of source elements and destination elements
	 * from srcData to dstData.
	 * If a path is not found, an IllegalArgumentException is thrown.
	 * 
	 * @param <Type> - the type of elements the graph contains
	 * @param sources - the list of source elements
	 * @param destinations - the list of destination elements the sources point to
	 * @param srcData - the element where the path starts
	 * @param dstData - the element to find the shortest path to from srcData
	 * @return a list containing the shortest path from srcData to dstData
	 * @throws IllegalArgumentException if a path is not found
	 */
	public static <Type> List<Type> shortestPath(List<Type> sources, List<Type> destinations, Type srcData, Type dstData) throws IllegalArgumentException {
		Graph<Type> graph = constructGraph(sources, destinations);
		
		List<Type> path = graph.breadthFirstSearch(srcData, dstData);
		// if path not found, list size is 0
		if(path.size() == 0)
			throw new IllegalArgumentException();
		
		return path;
	}
	
	/**
	 * Sorts a graph created from the given lists of
	 * source elements and destination elements topologically.
	 * If the graph contains a cycle, an IllegalArgumentException is thrown.
	 * 
	 * @param <Type> - the type of elements the graph contains
	 * @param sources - the list of source elements
	 * @param destinations - the list of destination elements the sources point to
	 * @return a list of the graph created from the lists of sources and destinations
	 * 		   sorted topologically
	 * @throws IllegalArgumentException if the graph contains a cycle
	 */
	public static <Type> List<Type> sort(List<Type> sources, List<Type> destinations) throws IllegalArgumentException{
		Graph<Type> graph = constructGraph(sources, destinations);
		
		List<Type> sorted = graph.topoSort();
		// if the size of the sorted list is less than
		// the number of vertices in the graph, the graph contains a cycle
		if(sorted.size() < graph.size())
			throw new IllegalArgumentException();
		
		return sorted;
	}
	
	/**
	 * Helper method that constructs a digraph from the given lists of
	 * sources and destinations.
	 * If a source or destination cannot be matched up, an
	 * IllegalArgumentException is thrown.
	 * 
	 * @param <Type> - the type of elements the graph will contain
	 * @param sources - the list of source elements
	 * @param destinations - the list of destination elements the sources point to
	 * @return a graph created from the lists of sources and destinations
	 * @throws IllegalArgumentException if a source or destination cannot be matched up
	 */
	private static <Type> Graph<Type> constructGraph(List<Type> sources, List<Type> destinations) {
		// unequal sizes between the lists means something cannot be matched up
		if(sources.size() != destinations.size())
			throw new IllegalArgumentException();
		
		Graph<Type> graph = new Graph<Type>();
		// for each source a directed edge is pointed to its corresponding destination
		for(int i = 0; i < sources.size(); i++)
			graph.addEdge(sources.get(i), destinations.get(i));
		return graph;
	}
}
