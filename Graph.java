package assign07;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Graph<Type> {
	private HashMap<Type, Vertex> graph;
	
	public Graph() {
		graph = new HashMap<Type, Vertex>();
	}
	
	public void addEdge(Type source, Type destination) {
		Vertex sourceVertex;
		if(graph.containsKey(source))
			sourceVertex = graph.get(source);
		else {
			sourceVertex = new Vertex(source);
			graph.put(source, sourceVertex);
		}
		
		Vertex destinationVertex;
		if(graph.containsKey(destination))
			destinationVertex = graph.get(destination);
		else {
			destinationVertex = new Vertex(destination);
			graph.put(destination, destinationVertex);
		}
		
		sourceVertex.addEdge(destinationVertex);
	}
	
	public boolean depthFirstSearch(Type source, Type destination) {
		if(graph.get(source) == null || graph.get(destination) == null)
			return false;
		if(source.equals(destination))
			return true;
		graph.get(source).isVisited = true;
		
		Vertex sourceVertex = graph.get(source);
		for(int i = 0; i < sourceVertex.adjacent.size(); i++) {
			Vertex adjacentVertex = sourceVertex.adjacent.get(i);
			if(!adjacentVertex.isVisited) {
				depthFirstSearch(adjacentVertex.data, destination);
				return true;
			}
		}
		return false;
	}
	
	public List<Type> breadthFirstSearch(Type source, Type destination) {
		Queue<Type> queue = new LinkedList<Type>();
		List<Type> path = new ArrayList<Type>();
		Type current;
		Vertex currentVertex;
		
		if(!graph.containsKey(source))
			return path;
		queue.offer(source);
		while(!queue.isEmpty()) {
			current = queue.poll();
			currentVertex = graph.get(current);
			for(Vertex adjacent : currentVertex.adjacent) {
				if(!adjacent.isVisited) {
					queue.offer(adjacent.data);
					adjacent.isVisited = true;
					adjacent.cameFrom = currentVertex;
					if(adjacent.data.equals(destination))
						break;
				}
			}
		}
		
		Vertex vertex = graph.get(destination);
		while(vertex != null) {
			path.add(vertex.data);
			if(vertex.cameFrom == null || vertex.cameFrom.equals(vertex))
				break;
			vertex = vertex.cameFrom;
		}
		
		for(int i = 0; i < path.size() / 2; i++) {
			Type temp = path.get(i);
			path.set(i, path.get(path.size() - (i + 1)));
			path.set(path.size() - (i + 1), temp);
		}
		
		return path;
	}
	
	public List<Type> topoSort() {
		List<Type> sorted = new ArrayList<Type>();
		Queue<Vertex> queue = new LinkedList<Vertex>();
		int indegree;
		
		ArrayList<Vertex> vertices = (ArrayList<Graph<Type>.Vertex>)graph.values();
		for(Vertex vertex : vertices) {
			indegree = vertex.indegree;
			if(indegree == 0) {
				queue.offer(vertex);
				sorted.add(vertex.data);
			}
			while (!queue.isEmpty()) {
				Vertex next = queue.poll();
				for(Vertex adjacentVertex : next.adjacent) {
					indegree = adjacentVertex.indegree;
					indegree--;
					if(indegree == 0) {
						queue.offer(adjacentVertex);
						sorted.add(adjacentVertex.data);
					}
				}
			}	
		}
		
		return sorted;
	}
	
	private class Vertex {
		public Type data;
		public List<Vertex> adjacent;
		public boolean isVisited;
		public Vertex cameFrom;
		public int indegree;
		
		public Vertex(Type data) {
			this.data= data;
			adjacent = new ArrayList<Vertex>();
			isVisited = false;
			cameFrom = null;
			indegree = 0;
		}
				
		public void addEdge(Vertex other) {
			adjacent.add(other);
			other.indegree++;
		}
		
		public String toString() {
			String str = data.toString() + " is adjacent to ";
			for(int i = 0; i < adjacent.size(); i++)
				str += adjacent.get(i).data.toString() + " ";
			return str;
		}
	}
}
