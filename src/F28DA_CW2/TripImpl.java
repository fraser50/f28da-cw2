package F28DA_CW2;

import java.util.Iterator;
import java.util.List;

public class TripImpl implements TripA<AirportImpl, FlightImpl>, TripB<AirportImpl, FlightImpl> {
	private static final String FORMAT_STRING = "%1$-3s %2$-20s %3$-4s %4$-6s %5$-20s %6$-4s";
	private List<String> stops;
	private List<String> flights;
	private int cost;
	private FlyPlannerImpl fi;
	
	public TripImpl(List<String> stops, List<String> flights, int cost, FlyPlannerImpl fi) {
		this.stops = stops;
		this.flights = flights;
		this.cost = cost;
		this.fi = fi;
	}

	@Override
	public int connectingTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int totalTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<String> getStops() {
		return stops;
	}

	@Override
	public List<String> getFlights() {
		return flights;
	}

	@Override
	public int totalHop() {
		return flights.size();
	}

	@Override
	public int totalCost() {
		return cost;
	}

	@Override
	public int airTime() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public String toString() {
		String s = String.format(FORMAT_STRING, "Leg", "Leave", "At", "On", "Arrive", "At");
		
		int curr = 1;
		
		Iterator<String> iter = getFlights().iterator();
		
		while (iter.hasNext()) {
			String flightCode = iter.next();
			FlightImpl flight = fi.flight(flightCode);
			AirportImpl fromAP = flight.getFrom();
			AirportImpl toAP = flight.getTo();
			s += String.format("\n"+FORMAT_STRING, curr, fromAP.getName() + " (" + fromAP.getCode() + ")", flight.getFromGMTime(), flight.getFlightCode(),
					toAP.getName() + " (" + toAP.getCode() + ")", flight.getToGMTime());
			
			s += "\nTotal trip cost = £" + totalCost();
			
			curr++;
		}
		
		return s;
	}

}
