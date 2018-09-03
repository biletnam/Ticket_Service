import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Set;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class MyTicketService implements TicketService{

	private MovieTheater myMovieTheater;
	private int currentHoldTicket;
	private ArrayList<SeatHold> seatsHold;
	
	public MyTicketService(MovieTheater inputMovieTheater) {
		currentHoldTicket = 0;
		myMovieTheater = inputMovieTheater;
		seatsHold = new ArrayList<SeatHold>();
	}
	
	/**
	 * Return the number of seats available in the movie theater
	 */
	public int numSeatsAvailable() {
		return myMovieTheater.getNumAvailableSeats();
	}

	/**
	 * Find and return SeatHold object holding the best seats in the theater for the given number of seats 
	 * requested. 
	 * If there are enough seats in a row that would fit the given party, those consecutive seats will 
	 * be assigned, otherwise, if there are enough seats in the theater but not enough consecutive seats for the entire
	 * party, party will be split, but will try and find the next best consecutive seats for the party. 
	 * If there are simply not enough seats in the theater, SeatHold object will be null. No seats will be held. 
	 */
	public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
		MyTicketService me = this;
		SeatHold mySeatHoldObj;
		ArrayList<String> mySeats = new ArrayList<String>();
		int seatsNeed = numSeats;
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        ValueComparator bvc = new ValueComparator(map);
        TreeMap<Integer, Integer> sorted_map = new TreeMap<Integer, Integer>(bvc);
		
		//if one or more seats are requested and there are enough seats available in the theater
		if (numSeats > 0 && numSeatsAvailable() >= numSeats) {
			
			//set SeatHoldID
			currentHoldTicket++;
			
			//find best seats
			for (int row = 0; row < myMovieTheater.getRows(); row++) {
				
				//there are enough together seats in this row
				if (myMovieTheater.seatsTogetherInRow(row).size() >= numSeats) {
					
					//add selected seats into mySeats
					for (int i = 0; i < numSeats; i++) {
						mySeats.add(myMovieTheater.seatsTogetherInRow(row).get(i));
					}
					
					//Set selected seats as unavailable in movie theater
					myMovieTheater.markSeatsUnavailable(mySeats);
					
					//Create new SeatHold Object with selected seats
					mySeatHoldObj = new SeatHold(me, currentHoldTicket, mySeats);
					
					//Add SeatHold Object into seatsHold arrayList
					seatsHold.add(mySeatHoldObj);
					
					System.out.println("Your seat hold ID is #" + mySeatHoldObj.getSeatHoldID());
					mySeatHoldObj.printSeats();
					
					return mySeatHoldObj;

				}
				
				map.put(row, myMovieTheater.seatsTogetherInRow(row).size());
			}
			
			//find next best seats
			
			//sort rows by number of consecutive seats
			sorted_map.putAll(map);
		
		    while (seatsNeed > 0) {
		    	//Grab the row with the most consecutive seats
		    	int row = sorted_map.pollFirstEntry().getKey();
		    	int maxSeatsInRow = myMovieTheater.seatsTogetherInRow(row).size();
		    	if (seatsNeed > maxSeatsInRow) {
		    		mySeats.addAll(myMovieTheater.seatsTogetherInRow(row));
		    		seatsNeed -= maxSeatsInRow;
		    	} else {
		    		int temp = seatsNeed;
		    		for (int x = 0; x < temp; x++) {
		    			mySeats.add(myMovieTheater.availableSeatsInRow(row).get(x));
		    			seatsNeed--;
		    		}
		    	}
		    }

		    //Set selected seats as unavailable in movie theater
			myMovieTheater.markSeatsUnavailable(mySeats);
		    
		    //Create new SeatHold Object with selected seats
		    mySeatHoldObj = new SeatHold(me, currentHoldTicket, mySeats);
		    
		    //Add SeatHold Object into seatsHold arrayList
			seatsHold.add(mySeatHoldObj);
			
			System.out.println("Your seat hold ID is #" + mySeatHoldObj.getSeatHoldID());
			mySeatHoldObj.printSeats();
			
			return mySeatHoldObj;
		    
		}
		System.out.println("Your reservation cannot be placed due to lack of seats.");
		System.out.println("Available Seats: "  + numSeatsAvailable());
		return null;
	}

	/**
	 * Reserve the seats held in the given seatHold object, marking the seats unavailable permanently.
	 * Return a reservation number when a reservation is successfully placed. 
	 */
	public String reserveSeats(int seatHoldId, String customerEmail) {
		SeatHold mySeatHold = null;
		Long reservationNum = Long.MIN_VALUE;
		
		//find seatHoldObj in the seatsHold
		for (SeatHold seatHoldObj: seatsHold){
			
			//found seatHoldObj
			if (seatHoldObj.getSeatHoldID() == seatHoldId) {
				mySeatHold = seatHoldObj;
				//mark those seats unavailable
				myMovieTheater.markSeatsUnavailable(seatHoldObj.getSeats());
				//Generate a random Long
				Random rand = new Random();
				reservationNum = Math.abs(rand.nextLong());
				System.out.println("Your seats have been reserved.");
				System.out.println("Reservation #R" + reservationNum);
			}
		}
		
		if (mySeatHold != null) {
			seatsHold.remove(mySeatHold);
		}else {
			//SeatHold object cannot be found. Either timed out, or it never existed.
			System.out.println("Your seats cannot be reserved. Please hold your seats again!");
		}
		
		return "R" + String.valueOf(reservationNum);
	}

	public ArrayList<SeatHold> getSeatsHold() {
		return seatsHold;
	}

	public void setSeatsHold(ArrayList<SeatHold> seatsHold) {
		this.seatsHold = seatsHold;
	}

	public MovieTheater getMovieTheater() {
		return myMovieTheater;
	} 

	/**
	 * Cancel a SeatHold, marking seats available again. 
	 * @param seatHoldId
	 * @return True if cancellation on SeatHold is successful.
	 */
	public boolean cancelSeatHold(int seatHoldId){
		SeatHold mySeatHold = null;
		//find seatHoldObj in the seatsHold
		for (SeatHold seatHoldObj: seatsHold){
			//found seatHoldObj
			if (seatHoldObj.getSeatHoldID() == seatHoldId) {
				mySeatHold = seatHoldObj;
				//Mark those seats available again
				myMovieTheater.markSeatsAvailable(seatHoldObj.getSeats());
			}
		}
		
		if (mySeatHold != null) {
			return seatsHold.remove(mySeatHold);
		}
		
		return false;
	}
	
	class ValueComparator implements Comparator<Integer> {
	    Map<Integer, Integer> base;

	    public ValueComparator(Map<Integer, Integer> base) {
	        this.base = base;
	    }

	    // Note: this comparator imposes orderings that are inconsistent with
	    // equals.
	    public int compare(Integer a, Integer b) {
	        if (base.get(a) >= base.get(b)) {
	            return -1;
	        } else {
	            return 1;
	        }
	    }
	}
}
