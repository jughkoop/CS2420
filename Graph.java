package assign07;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * This class implements a directed and sparse graph data structure
 * with a HashMap.
 * 
 * @author - Daniel Lee and Mi Zeng
 * @version 3-3-2025
 * @param <Type> - the type of elements contained in the graph
 */
public class Graph<Type> {
	private HashMap<Type, Vertex> graph;
	
	/**
	 * Creates a new graph.
	 */
	public Graph() {
		graph = new HashMap<Type, Vertex>();
	}
	
	/**
	 * Adds a directed edge from this source element to the
	 * given destination element.
	 * 
	 * @param source - element where the directed edge comes from
	 * @param destination - element where the directed edge points to
	 */
	public void addEdge(Type source, Type destination) {
		Vertex sourceVertex;
		// checks if source is already in the graph or not
		if(graph.containsKey(source))
			sourceVertex = graph.get(source);
		else {
			sourceVertex = new Vertex(source);
			graph.put(source, sourceVertex);
		}
		
		Vertex destinationVertex;
		// checks if destination is already in the graph or not
		if(graph.containsKey(destination))
			destinationVertex = graph.get(destination);
		else {
			destinationVertex = new Vertex(destination);
			graph.put(destination, destinationVertex);
		}
		
		// creates a directed edge pointing from source to destination
		sourceVertex.addEdge(destinationVertex);
	}
	
	/**
	 * Determines if the graph contains a path from the given source
	 * to the given destination.
	 * 
	 * @param source - the starting element of the path
	 * @param destination - the element to find a path to
	 * @return true or false if a path is found or not, respectively
	 */
	public boolean depthFirstSearch(Type source, Type destination) {
		// if source is not in the graph return false
		if(!graph.containsKey(source))
			return false;
		
		return dfsRecursive(source, destination);
	}
	
	/**
	 * Recursively goes through the graph to determine if a
	 * path exists from the given source element to the given 
	 * destination element.
	 * 
	 * @param source - the starting element of the path
	 * @param destination - the element to find a path to
	 * @return true or false if a path is found or not, respectively
	 */
	private boolean dfsRecursive(Type source, Type destination) {
		// if the current element is the destination return true
		if(source.equals(destination))
			return true;
		// mark current element as visited
		graph.get(source).isVisited = true;
		
		Vertex sourceVertex = graph.get(source);
		// gets each adjacent vertex of the current vertex
		for(Vertex adjacentVertex : sourceVertex.adjacent) {
			// if the adjacent vertex has not been visited yet,
			// recursively call and check if this adjacent vertex is the destination
			if(!adjacentVertex.isVisited)
				return dfsRecursive(adjacentVertex.data, destination);
		}
		// if no path can be found to the destination element
		return false;
	}
	
	/**
	 * Finds and returns the shortest path from the given source element 
	 * to the given destination element.
	 * 
	 * @param source - the starting element of the path
	 * @param destination - the element to make the shortest path to
	 * @return a list containing the elements of the shortest path possible from
	 * 		   source to destination, or empty if no path is found
	 */
	public List<Type> breadthFirstSearch(Type source, Type destination) {
		Queue<Type> queue = new LinkedList<Type>();
		List<Type> path = new ArrayList<Type>();
		Type current;
		Vertex currentVertex;
		
		// if source is not in the graph return an empty list
		if(!graph.containsKey(source))
			return path;
		queue.offer(source);
		// loop until queue is empty
		while(!queue.isEmpty()) {
			// poll and get the vertex associated with the current element
			current = queue.poll();
			currentVertex = graph.get(current);
			// for each adjacent vertex of the current vertex
			for(Vertex adjacentVertex : currentVertex.adjacent) {
				// if not visited add to back of queue and set to visited
				if(!adjacentVertex.isVisited) {
					queue.offer(adjacentVertex.data);
					adjacentVertex.isVisited = true;
					// note where adjacent vertex came from
					adjacentVertex.cameFrom = currentVertex;
					// if adjacent vertex equals destination break out of the loop
					if(adjacentVertex.data.equals(destination))
						break;
				}
			}
		}
		
		Vertex vertex = graph.get(destination);
		// add to list the path from destination to source
		// if path was found
		if(vertex != null)
			while(true) {
				path.add(vertex.data);
				// if vertex came from nothing or vertex points to itself, break
				if(vertex.cameFrom == null || vertex.cameFrom.equals(vertex))
					break;
				vertex = vertex.cameFrom;
			}
		
		// reverses list so path is from source to destination
		for(int i = 0; i < path.size() / 2; i++) {
			Type temp = path.get(i);
			path.set(i, path.get(path.size() - (i + 1)));
			path.set(path.size() - (i + 1), temp);
		}
		
		return path;
	}
	
	/**
	 * Sorts the graph and returns a list in a order where if vertex i
	 * points to vertex j, the i is before j (topological) in the list.
	 * Note that there may be multiple valid topological orderings, this 
	 * method will return one of the orderings.
	 * 
	 * @return a list of the graph topologically sorted
	 */
	public List<Type> topoSort() {
		List<Type> sorted = new ArrayList<Type>();
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		Queue<Vertex> queue = new LinkedList<Vertex>();
		int indegree;	// how many edges point to a vertex
		
		// adds all vertices of the graph into an ArrayList
		vertices.addAll(graph.values());
		
		for(Vertex vertex : vertices) {
			indegree = vertex.indegree;
			// if the vertex starts with an indegree of 0 add to queue and
			// sorted list
			if(indegree == 0) {
				queue.offer(vertex);
				sorted.add(vertex.data);
			}
			
			while (!queue.isEmpty()) {
				Vertex currentVertex = queue.poll();
				// for each adjacent vertex of the current vertex
				for(Vertex adjacentVertex : currentVertex.adjacent) {
					// get its indegree and decrement by 1
					indegree = adjacentVertex.indegree;
					indegree--;
					// if indegree is now zero add to poll and sorted list
					if(indegree == 0) {
						queue.offer(adjacentVertex);
						sorted.add(adjacentVertex.data);
					}
				}
			}	
		}
		
		return sorted;
	}
	
	/**
	 * Gets the number of vertices in the graph.
	 * 
	 * @return the number of vertices in the graph
	 */
	public int size() {
		return graph.size();
	}
	
	/**
	 * Private class that implements a vertex of the graph,
	 * mainly consisting of the data it holds and a list of vertices it
	 * points to.
	 * Uses the adjacency list method to be more efficient with sparse graphs.
	 */
	private class Vertex {
		public Type data;
		public List<Vertex> adjacent;
		public int indegree;
		// these two are mainly important for DFS and BFS
		public boolean isVisited;
		public Vertex cameFrom;
		
		/**
		 * Constructs a new Vertex.
		 * 
		 * @param data - the data this Vertex will hold
		 */
		public Vertex(Type data) {
			this.data = data;
			adjacent = new ArrayList<Vertex>();
			isVisited = false;
			cameFrom = null;
			indegree = 0;
		}
		
		/**
		 * Adds the given other vertex to the list of
		 * what vertices this vertex points to, adds one to the
		 * other vertex's indegree.
		 * 
		 * @param other the vertex to add to the adjacency list to
		 */
		public void addEdge(Vertex other) {
			adjacent.add(other);
			other.indegree++;
		}
	}
}
