import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SeatHold {
	
	private int seatHoldID;
	private ArrayList<String> seats;
	private MyTicketService myTicketService;
	private Timer myTimer;
	
	public SeatHold(MyTicketService inputTicketService, int inputSeatHoldID, ArrayList<String> inputSeats){
		
		seatHoldID = inputSeatHoldID;
		myTicketService = inputTicketService;
		SeatHold me = this;
		
		//Start timer, kills itself (remove itself from seatsHold arrayList in its Ticket Service) 
		//after 30 seconds
		myTimer = new Timer();
		
		myTimer.schedule(new TimerTask() {
			@Override
            public void run() {
                //Revert seats to available
                if (myTicketService.cancelSeatHold(seatHoldID)) {
                	System.out.println("Your seat hold #" + seatHoldID + " has been timed out.");
                }
                
            }
			
		}, 30 * 1000);
		
		//add requested seats into the seats need to be held
		seats = inputSeats;
	}

	public int getSeatHoldID() {
		return seatHoldID;
	}

	public void setSeatHoldID(int seatHoldID) {
		this.seatHoldID = seatHoldID;
	}

	public ArrayList<String> getSeats() {
		return seats;
	}

	public void setSeats(ArrayList<String> seats) {
		this.seats = seats;
	}
	
	/**
	 * Print a list of seats in this SeatHold object
	 */
	public void printSeats(){
		System.out.println("Your seats: ");
		for (String seat: seats) {
			System.out.print(seat + " ");
		}
		System.out.println();
	}
	
}
