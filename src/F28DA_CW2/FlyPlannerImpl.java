package F28DA_CW2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public class FlyPlannerImpl implements FlyPlannerA<AirportImpl,FlightImpl>, FlyPlannerB<AirportImpl,FlightImpl> {
	private Graph<AirportImpl, FlightImpl> g;
	private Map<String, AirportImpl> airportFromCode;
	
	public FlyPlannerImpl() {
		airportFromCode = new HashMap<>();
		g = new SimpleDirectedWeightedGraph<>(FlightImpl.class);
		
	}

	@Override
	public Set<AirportImpl> directlyConnected(AirportImpl airport) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int setDirectlyConnected() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int setDirectlyConnectedOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<AirportImpl> getBetterConnectedInOrder(AirportImpl airport) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String leastCostMeetUp(String at1, String at2) throws FlyPlannerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String leastHopMeetUp(String at1, String at2) throws FlyPlannerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String leastTimeMeetUp(String at1, String at2, String startTime) throws FlyPlannerException {
		// TODO Auto-generated method stub
		return null;
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
			
		}
		
		return true;
	}

	@Override
	public AirportImpl airport(String code) {
		return airportFromCode.get(code);
	}

	@Override
	public FlightImpl flight(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TripImpl leastCost(String from, String to) throws FlyPlannerException {
		AirportImpl fromAP = airport(from);
		AirportImpl toAP = airport(to);
		DijkstraShortestPath<AirportImpl, FlightImpl> dsp = new DijkstraShortestPath<>(g);
		GraphPath<AirportImpl, FlightImpl> fp = dsp.getPath(fromAP, toAP);
		
		List<String> vertexList = new ArrayList<>();
		List<String> edgeList = new ArrayList<>();
		
		for (AirportImpl v : fp.getVertexList()) {
			vertexList.add(v.getCode());
		}
		
		for (FlightImpl e : fp.getEdgeList()) {
			edgeList.add(e.getFlightCode());
		}
		
		return new TripImpl(vertexList, edgeList, (int) fp.getWeight());
	}

	@Override
	public TripImpl leastHop(String from, String to) throws FlyPlannerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TripImpl leastCost(String from, String to, List<String> excluding) throws FlyPlannerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TripImpl leastHop(String from, String to, List<String> excluding) throws FlyPlannerException {
		// TODO Auto-generated method stub
		return null;
	}

}
