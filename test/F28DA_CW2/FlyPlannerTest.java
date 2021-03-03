package F28DA_CW2;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

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
	public void anotherTest() {
		fail("To be implemented");
	}

}
