package F28DA_CW2;

import java.util.Set;

public class AirportImpl implements AirportA, AirportB {
	private String code;
	private String name;
	private int order;
	private Set<AirportImpl> directlyConnected;
	
	public AirportImpl(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@Override
	public Set<AirportImpl> getDicrectlyConnected() {
		return directlyConnected;
	}

	@Override
	public void setDicrectlyConnected(Set<AirportImpl> dicrectlyConnected) {
		this.directlyConnected = dicrectlyConnected;
		
	}

	@Override
	public void setDicrectlyConnectedOrder(int order) {
		this.order = order;
		
	}

	@Override
	public int getDirectlyConnectedOrder() {
		return order;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getName() {
		return name;
	}

}
