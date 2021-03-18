package F28DA_CW2;

import java.util.List;

public class TripImpl implements TripA<AirportImpl, FlightImpl>, TripB<AirportImpl, FlightImpl> {
	private List<String> stops;
	private List<String> flights;
	private int cost;
	
	public TripImpl(List<String> stops, List<String> flights, int cost) {
		this.stops = stops;
		this.flights = flights;
		this.cost = cost;
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

}
