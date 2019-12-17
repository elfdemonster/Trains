package com.tw.trains;

import com.tw.trains.exception.TrainsException;

public class Main {

	public static void main(String[] args) {
		
		Maps.init();
		
		actTestInput();
		
	}
	
	
	public static void actTestInput()
	{
		/*
		The distance of the route A-B-C.
		The distance of the route A-D.
		The distance of the route A-D-C.
		The distance of the route A-E-B-C-D.
		The distance of the route A-E-D.
		The number of trips starting at C and ending at C with a maximum of 3 stops.  
		In the sample data below, there are two such trips: C-D-C (2 stops). and C-E-B-C (3 stops).
		
		The number of trips starting at A and ending at C with exactly 4 stops.  
		In the sample data below, there are three such trips: A to C (via B,C,D); A to C (via D,C,D); and A to C (via D,E,B).
		
		The length of the shortest route (in terms of distance to travel) from A to C.
		The length of the shortest route (in terms of distance to travel) from B to B.
		The number of different routes from C to C with a distance of less than 30.  
		In the sample data, the trips are: CDC, CEBC, CEBCDC, CDCEBC, CDEBC, CEBCEBC, CEBCEBCEBC.
		
		//Graph: AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7
		*/
		
		Route route1 = new Route(new Town("A", "A"), new Town("C", "C"), "A-B-C");
		
		try {
			System.out.println("The distance of the route A-B-C is " + Maps.getDistance(route1));
		} catch (TrainsException e) {
			System.out.println(e.getMessage());
		}
		
		Route route2 = new Route(new Town("A", "A"), new Town("D", "D"), "A-D");
		try {
			System.out.println("The distance of the route A-D is " + Maps.getDistance(route2));
		} catch (TrainsException e) {
			System.out.println(e.getMessage());
		}
		
		Route route3 = new Route(new Town("A", "A"), new Town("C", "C"), "A-D-C");
		try {
			System.out.println("The distance of the route A-D-C is " + Maps.getDistance(route3));
			//System.out.println("The distance of the route A-D-C is " + Maps.getDistance(route3));
		} catch (TrainsException e) {
			System.out.println(e.getMessage());
		}
		
		Route route4 = new Route(new Town("A", "A"), new Town("D", "D"), "A-E-B-C-D");
		try {
			System.out.println("The distance of the route A-E-B-C-D is " + Maps.getDistance(route4));
		} catch (TrainsException e) {
			System.out.println(e.getMessage());
		}
		
		Route route5 = new Route(new Town("A", "A"), new Town("D", "D"), "A-E-D");
		try {
			System.out.println("The distance of the route A-E-D is " + Maps.getDistance(route5));
		} catch (TrainsException e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		Route route6 = new Route(new Town("C", "C"), new Town("C", "C"));
		System.out.println(Maps.getRoutesCount(route6, 3, true));
		
		Route route7 = new Route(new Town("A", "A"), new Town("C", "C"));
		System.out.println(Maps.getRoutesCount(route7, 5, false));
		
		Route route8 = new Route(new Town("A", "A"), new Town("C", "C"));
		System.out.println(Maps.getShortestDistance(route8));
		
		Route route9 = new Route(new Town("B", "B"), new Town("B", "B"));
		System.out.println(Maps.getShortestDistance(route9));
		
		Route route10 = new Route(new Town("C", "C"), new Town("C", "C"));
		System.out.println(Maps.getMaxDistanceRoutesCount(route10, 30));
	}
}
