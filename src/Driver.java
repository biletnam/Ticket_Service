import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Timer;

public class Driver {

	private static MovieTheater myMovieTheater;
	private static MyTicketService myTicketService;
	
	public static void main(String[] args) {
		
		myMovieTheater = new MovieTheater(3, 4);
		myTicketService = new MyTicketService(myMovieTheater);
		
		try {
			File file = new File("Reservations");
			Scanner myScanner = new Scanner(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			
			while (myScanner.hasNext()) {
				String thisLine = myScanner.nextLine();
				String[] words = thisLine.split(" ");
				//Obtain reservation ID
				String reservationID = words[0];
				//Obtain number of seats wanted
				int numSeats = Integer.parseInt(words[1]);
				System.out.println();
				System.out.println("#" + reservationID);
				SeatHold myHold = myTicketService.findAndHoldSeats(numSeats, "abc@gmail.com");
				System.out.println("Seating Chart:");
				myMovieTheater.printTheater();
				if (myHold != null) {
					System.out.println("Would you like to commit to this reservation? (Yes or No)");
					if (reader.readLine().toUpperCase().equals("Yes".toUpperCase())) {
						myTicketService.reserveSeats(myHold.getSeatHoldID(), "abc@gmail.com");
					}else {
						if (myTicketService.cancelSeatHold(myHold.getSeatHoldID())){
							System.out.println("You have canceled to reserve seats.");
						}else {
							System.out.println("Your seats have been timed out, or seats cannot be found.");
						}
					}
				}

			}
			} catch (Exception e) {
			System.out.println(e);
		}

	}
}
