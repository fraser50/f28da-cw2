package F28DA_CW2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.BFSShortestPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DirectedAcyclicGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class FlyPlannerImpl implements FlyPlannerA<AirportImpl,FlightImpl>, FlyPlannerB<AirportImpl,FlightImpl> {
	private Graph<AirportImpl, FlightImpl> g;
	private Map<String, AirportImpl> airportFromCode;
	private Map<String, FlightImpl> flightFromCode;
	private DirectedAcyclicGraph<AirportImpl, FlightImpl> dag;
	
	public FlyPlannerImpl() {
		airportFromCode = new HashMap<>();
		flightFromCode = new HashMap<>();
		g = new SimpleDirectedWeightedGraph<>(FlightImpl.class);
		
	}

	@Override
	public Set<AirportImpl> directlyConnected(AirportImpl airport) {
		Set<AirportImpl> connectedSet = new HashSet<>();
		
		for (AirportImpl ap : g.vertexSet()) {
			if (ap == airport) continue; // Skip if the airport is the same
			
			if (g.containsEdge(airport, ap) && g.containsEdge(ap, airport)) {
				connectedSet.add(ap);
			}
		}
		
		return connectedSet;
	}

	@Override
	public int setDirectlyConnected() {
		int total = 0;
		
		for (AirportImpl airport : g.vertexSet()) {
			Set<AirportImpl> connected = directlyConnected(airport);
			airport.setDicrectlyConnected(connected);
			airport.setDicrectlyConnectedOrder(connected.size());
			// Store Directly connected set and size of set in the airport object
			total += connected.size();
		}
		
		return total;
	}

	@Override
	public int setDirectlyConnectedOrder() {
		dag = new DirectedAcyclicGraph<>(FlightImpl.class);
		for (AirportImpl ap : g.vertexSet()) {
			dag.addVertex(ap);
			// Add each airport to the DAG
		}
		
		for (AirportImpl ap : g.vertexSet()) {
			for (FlightImpl flight : g.outgoingEdgesOf(ap)) {
				AirportImpl newAP = flight.getTo();
				if (newAP.getDirectlyConnectedOrder() > ap.getDirectlyConnectedOrder()) {
					dag.addEdge(ap, newAP, flight);
					// Add all outgoing flights that are going to an airport with more direct connections
				}
			}
		}
		
		return dag.edgeSet().size();
	}

	@Override
	public Set<AirportImpl> getBetterConnectedInOrder(AirportImpl airport) {
		return dag.getDescendants(airport); // Every airport in the DAG that can be reached from the provided airport
	}

	@Override
	public String leastCostMeetUp(String at1, String at2) throws FlyPlannerException {
		AirportImpl AP1 = airport(at1);
		AirportImpl AP2 = airport(at2);
		DijkstraShortestPath<AirportImpl, FlightImpl> dsp = new DijkstraShortestPath<>(g);
		double lowestDistance = Integer.MAX_VALUE;
		String suitableAP = "";
		
		for (AirportImpl ap : g.vertexSet()) {
			if (ap == AP1 || ap == AP2) continue;
			double distance = dsp.getPathWeight(AP1, ap) + dsp.getPathWeight(AP2, ap);
			// Find the total distance travelled for both airports
			
			if (distance < lowestDistance) {
				lowestDistance = distance;
				suitableAP = ap.getCode();
				// If the distance is shorter than the previous shortest, update it
			}
		}
		
		if (suitableAP.equals("")) {
			throw new FlyPlannerException("No suitable meetup location!");
		}
		
		return suitableAP;
	}

	@Override
	public String leastHopMeetUp(String at1, String at2) throws FlyPlannerException {
		AirportImpl AP1 = airport(at1);
		AirportImpl AP2 = airport(at2);
		BFSShortestPath<AirportImpl, FlightImpl> dsp = new BFSShortestPath<>(g);
		
		double lowestDistance = Integer.MAX_VALUE; // Setting to MAX_VALUE so it is larger than any possible distance
		String suitableAP = ""; // Suitable airport code
		
		for (AirportImpl ap : g.vertexSet()) {
			if (ap == AP1 || ap == AP2) continue;
			double distance = dsp.getPathWeight(AP1, ap) + dsp.getPathWeight(AP2, ap);
			
			if (distance < lowestDistance) {
				lowestDistance = distance;
				suitableAP = ap.getCode();
			}
		}
		
		if (suitableAP.equals("")) {
			throw new FlyPlannerException("No suitable meetup location!");
		}
		
		return suitableAP;
	}

	@Override
	public String leastTimeMeetUp(String at1, String at2, String startTime) throws FlyPlannerException {
		AirportImpl AP1 = airport(at1);
		AirportImpl AP2 = airport(at2);
		BFSShortestPath<AirportImpl, FlightImpl> dsp = new BFSShortestPath<>(g);
		
		int lowestTime = Integer.MAX_VALUE; // Setting to MAX_VALUE so it is larger than any possible time
		String suitableAP = ""; // Suitable airport code
		
		for (AirportImpl ap : g.vertexSet()) {
			if (ap == AP1 || ap == AP2) continue;
			GraphPath<AirportImpl, FlightImpl> path1 = dsp.getPath(AP1, ap);
			GraphPath<AirportImpl, FlightImpl> path2 = dsp.getPath(AP2, ap);
			if (path1 == null || path2 == null) continue;
			
			TripImpl trip1 = new TripImpl(createVertexList(path1), createEdgeList(path1), this);
			TripImpl trip2 = new TripImpl(createVertexList(path2), createEdgeList(path2), this);
			
			int time = trip1.totalTime() + trip2.totalTime();
			
			if (time < lowestTime) {
				lowestTime = time;
				suitableAP = ap.getCode();
			}
		}
		
		if (suitableAP.equals("")) {
			throw new FlyPlannerException("No suitable meetup location!");
		}
		
		return suitableAP;
	}

	@Override
	public boolean populate(FlightsReader fr) {
		return populate(fr.getAirports(), fr.getFlights());
	}

	@Override
	public boolean populate(HashSet<String[]> airports, HashSet<String[]> flights) {
		for (String[] ap : airports) {
			AirportImpl ai = new AirportImpl(ap[0], ap[2]);
			airportFromCode.put(ai.getCode(), ai);
			g.addVertex(ai);
		}
		
		// UA0557,SDF,0513,DEN,0654,113
		// Flight Code, departure, dep time, dest, dest time, cost
		
		for (String[] fl : flights) {
			AirportImpl from = airport(fl[1]);
			AirportImpl to = airport(fl[3]);
			
			FlightImpl flight = new FlightImpl(fl[0], to, from, fl[2], fl[4], Integer.parseInt(fl[5]));
			g.addEdge(from, to, flight);
			
			g.setEdgeWeight(flight, flight.getCost());
			
			flightFromCode.put(flight.getFlightCode(), flight);
			// Record flight code in map to allow easy lookups
			
		}
		
		return true;
	}

	@Override
	public AirportImpl airport(String code) {
		return airportFromCode.get(code);
	}

	@Override
	public FlightImpl flight(String code) {
		return flightFromCode.get(code);
	}

	@Override
	public TripImpl leastCost(String from, String to) throws FlyPlannerException {
		AirportImpl fromAP = airport(from);
		AirportImpl toAP = airport(to);
		DijkstraShortestPath<AirportImpl, FlightImpl> dsp = new DijkstraShortestPath<>(g);
		GraphPath<AirportImpl, FlightImpl> fp = dsp.getPath(fromAP, toAP);
		
		if (fp == null) {
			throw new FlyPlannerException("No path available!");
		}
		
		List<String> vertexList = createVertexList(fp);
		List<String> edgeList = createEdgeList(fp);
		
		return new TripImpl(vertexList, edgeList, this);
	}

	@Override
	public TripImpl leastHop(String from, String to) throws FlyPlannerException {
		AirportImpl fromAP = airport(from);
		AirportImpl toAP = airport(to);
		
		BFSShortestPath<AirportImpl, FlightImpl> bsp = new BFSShortestPath<>(g);
		GraphPath<AirportImpl, FlightImpl> fp = bsp.getPath(fromAP, toAP);
		
		if (fp == null) {
			throw new FlyPlannerException("No path available!");
		}
		
		List<String> vertexList = createVertexList(fp);
		List<String> edgeList = createEdgeList(fp);
		
		return new TripImpl(vertexList, edgeList, this);
	}

	@Override
	public TripImpl leastCost(String from, String to, List<String> excluding) throws FlyPlannerException {
		Set<FlightImpl> removedEdges = removeAirportFlights(excluding);
		
		TripImpl trip = leastCost(from, to);
		
		addAirportFlights(removedEdges);
		
		return trip;
	}

	@Override
	public TripImpl leastHop(String from, String to, List<String> excluding) throws FlyPlannerException {
		Set<FlightImpl> removedEdges = removeAirportFlights(excluding);
		
		TripImpl trip = leastHop(from, to);
		
		addAirportFlights(removedEdges);
		
		return trip;
	}
	
	/**
	 * This method will remove all flights that were to or from an excluded airport and will return the removed flights
	 * @param excluding airports to exclude
	 * @return set of flights that were removed from the graph
	 */
	private Set<FlightImpl> removeAirportFlights(List<String> excluding) {
		Set<FlightImpl> removedEdges = new HashSet<>();
		
		for (String ex : excluding) {
			AirportImpl exAP = airport(ex);
			Iterator<FlightImpl> currEdges = g.edgesOf(exAP).iterator();
			while (currEdges.hasNext()) {
				removedEdges.add(currEdges.next());
			}
			
			g.removeAllEdges(g.edgesOf(exAP));
		}
		
		return removedEdges;
	}
	
	/**
	 * This method will re-add flights that were removed by the previous method.
	 * @param removedEdges
	 */
	private void addAirportFlights(Set<FlightImpl> removedEdges) {
		Iterator<FlightImpl> toAdd = removedEdges.iterator();
		
		while (toAdd.hasNext()) {
			FlightImpl flight = toAdd.next();
			g.addEdge(flight.getFrom(), flight.getTo(), flight);
			g.setEdgeWeight(flight, flight.getCost());
		}
	}
	
	/**
	 * This method will take a flight path and will return a String list containing the codes for all airports in the path.
	 * @param fp The flight path that should be used to generate a String list of vertices
	 * @return A String list of Vertices
	 */
	private List<String> createVertexList(GraphPath<AirportImpl, FlightImpl> fp) {
		List<String> vertexList = new ArrayList<>();
		
		for (AirportImpl v : fp.getVertexList()) {
			vertexList.add(v.getCode());
		}
		
		return vertexList;
	}
	
	/**
	 * This method will take a flight path and will return a String list containing the codes for all flights in the path.
	 * @param fp The flight path that should be used to generate a String list of edges
	 * @return A String list of Edges
	 */
	private List<String> createEdgeList(GraphPath<AirportImpl, FlightImpl> fp) {
		List<String> edgeList = new ArrayList<>();
		
		for (FlightImpl e : fp.getEdgeList()) {
			edgeList.add(e.getFlightCode());
		}
		
		return edgeList;
	}

}
