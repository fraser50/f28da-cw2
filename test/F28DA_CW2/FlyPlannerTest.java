package F28DA_CW2;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class FlyPlannerTest {

	FlyPlannerImpl fi;

	@Before
	public void initialize() {
		fi = new FlyPlannerImpl();
		try {
			fi.populate(new FlightsReader());
		} catch (FileNotFoundException | FlyPlannerException e) {
			e.printStackTrace();
		}
	}

	// Add your own tests here!
	//
	// You can get inspiration from the tests in FlyPlannerProvidedTest
	// that uses the provided data set but also from the
	// leastCostCustomTest test that uses a custom-made graph
	
	@Test
	public void testExclusion() throws FlyPlannerException {
		// Check exclusions work when there is a path that does not require excluded airports
		List<String> exclude = new ArrayList<>();
		exclude.add("AMS");
		TripImpl trip = fi.leastCost("NCL", "NTL", exclude);
		List<String> flights = trip.getFlights();
		assertEquals(5, trip.getFlights().size());
		
		assertEquals("BA2600", flights.get(0));
		assertEquals("BA9502", flights.get(1));
		assertEquals("LH6123", flights.get(2));
		assertEquals("CX7100", flights.get(3));
		assertEquals("QF0640", flights.get(4));
		
	}
	
	@Test
	public void testExclusionNotPermanent() throws FlyPlannerException {
		// Checks that excluded airports are not permanent for the lifetime of the FlyPlannerImpl
		List<String> exclude = new ArrayList<>();
		exclude.add("AMS");
		fi.leastCost("NCL", "NTL", exclude);
		
		TripImpl trip = fi.leastCost("NCL", "NTL");
		assertEquals(4, trip.getFlights().size());
		
	}

}
