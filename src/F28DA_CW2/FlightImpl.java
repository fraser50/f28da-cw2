package F28DA_CW2;

import org.jgrapht.graph.DefaultWeightedEdge;

@SuppressWarnings("serial")
public class FlightImpl extends DefaultWeightedEdge implements Flight {
	private String flightCode;
	private AirportImpl to;
	private AirportImpl from;
	private String fromTime;
	private String toTime;
	private int cost;
	
	public FlightImpl(String flightCode, AirportImpl to, AirportImpl from, String fromTime, String toTime, int cost) {
		this.flightCode = flightCode;
		this.to = to;
		this.from = from;
		this.fromTime = fromTime;
		this.toTime = toTime;
		this.cost = cost;
	}

	@Override
	public String getFlightCode() {
		return flightCode;
	}

	@Override
	public AirportImpl getTo() {
		return to;
	}

	@Override
	public AirportImpl getFrom() {
		return from;
	}

	@Override
	public String getFromGMTime() {
		return fromTime;
	}

	@Override
	public String getToGMTime() {
		return toTime;
	}

	@Override
	public int getCost() {
		return cost;
	}

}
