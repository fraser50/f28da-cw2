package F28DA_CW2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
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
		// TODO Auto-generated method stub
		return null;
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
