package F28DA_CW2;

import java.util.Set;

public class AirportImpl implements AirportA, AirportB {
	private String code;
	private String name;
	
	public AirportImpl(String code, String name) {
		this.code = code;
		this.name = name;
	}

	@Override
	public Set<AirportImpl> getDicrectlyConnected() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDicrectlyConnected(Set<AirportImpl> dicrectlyConnected) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDicrectlyConnectedOrder(int order) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getDirectlyConnectedOrder() {
		// TODO Auto-generated method stub
		return 0;
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
