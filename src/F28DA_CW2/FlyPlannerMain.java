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
			
			System.out.println("Welcome to the Flight Planner, please select an option:");
			System.out.println(" 1. Find route between 2 airports");
			System.out.println(" 2. Find a suitable meetup location");
			
			Scanner scan = new Scanner(System.in);
			
			try {
				int choice = scan.nextInt();
				switch (choice) {
					case 1:
						partA(scan, fi);
						break;
						
					case 2:
						partB(scan, fi);
						break;
						
					default:
						System.err.println("That is not a valid choice! Please run the program again.");
				}
			} catch (InputMismatchException e) {
				System.err.println("That is not a valid choice! Please run the program again.");
			}
			
			scan.close();

		} catch (FileNotFoundException | FlyPlannerException e) {
			e.printStackTrace();
		}

	}
	
	private static void partA(Scanner scan, FlyPlannerImpl fi) throws FlyPlannerException {
		System.out.println("Departure airport code?");
		
		String deptCode = scan.next().toUpperCase();
		
		if (fi.airport(deptCode) == null) {
			System.err.println("That airport doesn't exist!");
			return;
		}
		
		System.out.println("Arrival airport code?");
		String destCode = scan.next().toUpperCase();
		
		if (fi.airport(destCode) == null) {
			System.err.println("That airport doesn't exist!");
			return;
		}
		
		// Check departure and arrival airports are different
		if (deptCode.equals(destCode)) {
			System.err.println("You can't pick the same airport twice!");
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
	}
	
	private static void partB(Scanner scan, FlyPlannerImpl fi) throws FlyPlannerException {
		System.out.println("First airport?");
		
		String ap1Code = scan.next().toUpperCase();
		AirportImpl ap1 = fi.airport(ap1Code);
		if (ap1 == null) {
			System.err.println("Invalid airport!");
			return;
		}
		
		System.out.println("Second airport?");
		
		String ap2Code = scan.next().toUpperCase();
		AirportImpl ap2 = fi.airport(ap2Code);
		if (ap2 == null) {
			System.err.println("Invalid airport!");
			return;
		}
		
		if (ap1 == ap2) {
			System.err.println("You can't pick the same airport twice!");
			return;
		}
		
		System.out.println("Please pick a method to find a meetup location:");
		System.out.println(" 1. Least cost");
		System.out.println(" 2. Least hop");
		
		int choice = 0;
		
		try {
			choice = scan.nextInt();
			
		} catch (InputMismatchException e) {
			System.err.println("Invalid meetup option! (Run the program again)");
			return;
		}
		
		String ap;
		
		switch (choice) {
			case 1: // Least cost
				ap = fi.leastCostMeetUp(ap1Code, ap2Code);
				break;
				
			case 2: // Least hop
				ap = fi.leastHopMeetUp(ap1Code, ap2Code);
				break;
				
			default:
				System.err.println("Invalid meetup option! (Run the program again)");
				return;
		}
		
		System.out.println("Here is a suitable location: " + fi.airport(ap).getName() + " (" + ap + ")");
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
