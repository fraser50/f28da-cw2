package F28DA_CW2;

import java.util.Iterator;
import java.util.List;

public class TripImpl implements TripA<AirportImpl, FlightImpl>, TripB<AirportImpl, FlightImpl> {
	private static final String FORMAT_STRING = "%1$-3s %2$-20s %3$-4s %4$-6s %5$-20s %6$-4s";
	private static final int DAY_IN_MINUTES = 24 * 60;
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
		int total = 0;
		
		Iterator<String> iter = flights.iterator();
		if (!iter.hasNext()) {
			return total;
		}
		
		FlightImpl flight = fi.flight(iter.next());
		int startTime = timeStrToMinutes(flight.getToGMTime());
		
		while (iter.hasNext()) {
			flight = fi.flight(iter.next());
			int endTime = timeStrToMinutes(flight.getFromGMTime());
			
			int diff = timeDiffInMinutes(startTime, endTime);
			startTime = timeStrToMinutes(flight.getToGMTime());
			total += diff;
		}
		
		return total;
	}

	@Override
	public int totalTime() {
		return airTime() + connectingTime();
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
		int total = 0;
		
		for (String flightCode : flights) {
			FlightImpl flight = fi.flight(flightCode);
			String startStr = flight.getFromGMTime();
			String endStr = flight.getToGMTime();
			
			int start = timeStrToMinutes(startStr);
			int end = timeStrToMinutes(endStr);
			
			int diff = timeDiffInMinutes(start, end);
			
			total += diff;
		}
		
		return total;
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
			
			curr++;
		}
		
		s += "\nTotal Trip Cost = £" + totalCost();
		s += "\nTotal Time in the Air = " + airTime();
		
		return s;
	}
	
	private int timeStrToMinutes(String s) {
		if (s.length() != 4) {
			throw new IllegalArgumentException("Invalid time string provided!");
		}
		
		String hourStr = s.substring(0, 2);
		String minStr = s.substring(2, 4);
		
		int hours = Integer.parseInt(hourStr);
		int minutes = Integer.parseInt(minStr);
		
		return (hours*60) + minutes;
	}
	
	private int timeDiffInMinutes(int start, int end) {
		int result = end - start;
		
		if (result <= 0) {
			result += DAY_IN_MINUTES;
		}
		
		return result;
	}

}
