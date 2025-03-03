package assign07;

import java.util.List;

public class GraphUtility {
	public static <Type> boolean areConnected(List<Type> sources, List<Type> destinations, Type srcData, Type dstData) {
		if(sources.size() != destinations.size())
			throw new IllegalArgumentException();
		Graph<Type> graph = constructGraph(sources, destinations);
		
		return graph.depthFirstSearch(srcData, dstData);
	}
	
	public static <Type> List<Type> shortestPath(List<Type> sources, List<Type> destinations, Type srcData, Type dstData) {
		if(sources.size() != destinations.size())
			throw new IllegalArgumentException();
		Graph<Type> graph = constructGraph(sources, destinations);
		List<Type> path = graph.breadthFirstSearch(srcData, dstData);
		if(path.size() == 0)
			throw new IllegalArgumentException();
		
		return path;
	}
	
	public static <Type> List<Type> sort(List<Type> sources, List<Type> destinations) {
		if(sources.size() != destinations.size())
			throw new IllegalArgumentException();
		Graph<Type> graph = constructGraph(sources, destinations);
		List<Type> sorted = graph.topoSort();
		if(sorted.size() != graph.size())
			throw new IllegalArgumentException();
		
		return sorted;
	}
	
	private static <Type> Graph<Type> constructGraph(List<Type> sources, List<Type> destinations) {
		Graph<Type> graph = new Graph<Type>();
		for(int i = 0; i < sources.size(); i++)
			graph.addEdge(sources.get(i), destinations.get(i));
		return graph;
	}
}
