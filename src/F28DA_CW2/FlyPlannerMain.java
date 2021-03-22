package F28DA_CW2;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FlyPlannerMain {

	public static void main(String[] args) {

		// Your implementation should be in FlyPlannerImpl.java, this class is only to
		// run the user interface of your programme.

		FlyPlannerImpl fi;
		fi = new FlyPlannerImpl();
		try {
			fi.populate(new FlightsReader());

			System.out.println("Departure airport code?");
			Scanner scan = new Scanner(System.in);
			
			String deptCode = scan.next();
			
			if (fi.airport(deptCode) == null) {
				System.err.println("That airport doesn't exist!");
				scan.close();
				return;
			}
			
			System.out.println("Arrival airport code?");
			String destCode = scan.next();
			
			if (fi.airport(destCode) == null) {
				System.err.println("That airport doesn't exist!");
				scan.close();
				return;
			}
			
			List<String> excluded = getExcluded(scan);
			
			AirportImpl depart = fi.airport(deptCode);
			AirportImpl dest = fi.airport(destCode);
			
			TripImpl trip = fi.leastCost(deptCode, destCode, excluded);
			
			System.out.println("Trip for " + depart.getName() + " (" + depart.getCode() + ") to " + dest.getName() + " (" + dest.getCode() + ")");
			System.out.println(trip);
			
			scan.close();

		} catch (FileNotFoundException | FlyPlannerException e) {
			e.printStackTrace();
		}

	}
	
	private static List<String> getExcluded(Scanner scan) {
		List<String> excluded = new ArrayList<>();
		
		System.out.println("Please enter a list of airports to exclude (enter finish to stop):");
		while (true) {
			String exc = scan.next();
			if (exc.equals("finish")) break;
			
			excluded.add(exc);
		}
		
		return excluded;
	}

}
