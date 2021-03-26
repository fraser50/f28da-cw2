package F28DA_CW2;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
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
			
			String deptCode = scan.next().toUpperCase();
			
			if (fi.airport(deptCode) == null) {
				System.err.println("That airport doesn't exist!");
				scan.close();
				return;
			}
			
			System.out.println("Arrival airport code?");
			String destCode = scan.next().toUpperCase();
			
			if (fi.airport(destCode) == null) {
				System.err.println("That airport doesn't exist!");
				scan.close();
				return;
			}
			
			List<String> excluded = getExcluded(scan, fi);
			
			AirportImpl depart = fi.airport(deptCode);
			AirportImpl dest = fi.airport(destCode);
			
			System.out.println("Please pick one of the following path types:");
			System.out.println(" 1. Least cost path");
			System.out.println(" 2. Least hops path");
			
			int chosenPath;
			
			try {
				chosenPath = scan.nextInt();
				
			} catch (InputMismatchException e) {
				System.err.println("Incorrect path chosen! Please run the program again");
				return;
			}
			
			TripImpl trip;
			
			switch (chosenPath) {
				case 1: // Least cost path
					trip = fi.leastCost(deptCode, destCode, excluded);
					break;
					
				case 2: // Least hops path
					trip = fi.leastHop(deptCode, destCode, excluded);
					break;
					
				default:
					System.err.println("Incorrect path chosen! Please run the program again");
					return;
			}
			
			System.out.println("Trip for " + depart.getName() + " (" + depart.getCode() + ") to " + dest.getName() + " (" + dest.getCode() + ")");
			System.out.println(trip);
			
			scan.close();

		} catch (FileNotFoundException | FlyPlannerException e) {
			e.printStackTrace();
		}

	}
	
	private static List<String> getExcluded(Scanner scan, FlyPlannerImpl fi) {
		List<String> excluded = new ArrayList<>();
		
		System.out.println("Please enter a list of airports to exclude (enter finish to stop):");
		while (true) {
			String exc = scan.next();
			if (exc.equalsIgnoreCase("finish")) break;
			if (fi.airport(exc.toUpperCase()) == null) {
				System.err.println("Invalid airport! If you want to stop entering airports, type finish");
				continue;
			}
			
			excluded.add(exc.toUpperCase());
		}
		
		return excluded;
	}

}
