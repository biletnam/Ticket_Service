import java.util.ArrayList;


public class MovieTheater {

	private int rows;
	private int seatsPerRow;
	private int numAvailableSeats;
	private Seat[][] seats;

	public MovieTheater(int inputRows, int inputSeatsPerRow){
		rows = inputRows;
		seatsPerRow = inputSeatsPerRow;
		seats = new Seat[inputRows][inputSeatsPerRow];

		for (int x = 0; x < rows; x++){
			for (int y = 0; y < seatsPerRow; y++){
				seats[x][y] = new Seat(x, y);
			}
		}
		numAvailableSeats = inputRows * inputSeatsPerRow;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getSeatsPerRow() {
		return seatsPerRow;
	}

	public void setSeatsPerRow(int seatsPerRow) {
		this.seatsPerRow = seatsPerRow;
	}
	
	public Seat getSeat(int row, int col) {
		return seats[row][col];
	}

	/**
	 * mark given seats unavailable, if one of the seats is already unavailable, this returns false
	 * @param seats
	 * @return true if all of the seats given could be mark unavailable
	 */
	public boolean markSeatsUnavailable(ArrayList<String> seats){
		for (String mySeat: seats){
			int row = getRowFromSeatID(mySeat);
			int col = getColFromSeatID(mySeat);
			
			if (!getSeat(row, col).isAvailable()){
				return false;
			}
			getSeat(row, col).setAvailable(false);
		}
		
		numAvailableSeats -= seats.size();
		return true;
	}
	
	/**
	 * Mark given seats available
	 * @param seats
	 * @return true if all seats can be marked available
	 */
	public boolean markSeatsAvailable(ArrayList<String> seats){
		for (String mySeat: seats){
			int row = getRowFromSeatID(mySeat);
			int col = getColFromSeatID(mySeat);
			
			if (getSeat(row, col).isAvailable()){
				return false;
			}
			getSeat(row, col).setAvailable(true);
		}
		
		numAvailableSeats += seats.size();
		return true;
	}
	
	public int getRowFromSeatID(String seatID) {
		return "ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(seatID.charAt(0));
	}
	
	public int getColFromSeatID(String seatID) {
		return Integer.parseInt(seatID.substring(1)) - 1;
	}

	public int getNumAvailableSeats() {
		return numAvailableSeats;
	}
	
	/**
	 * Show the number of available seats in the requested row
	 * @param row
	 * @return available seats in row
	 */
	public int numAvailableSeatsInRow(int row) {
		int count = 0;
		for (int seat = 0; seat < seatsPerRow; seat++) {
			if (seats[row][seat].isAvailable()) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Check if seats can be together in this row
	 * @param row, numSeats
	 * @return true if this row allows seats to be together
	 */
	public ArrayList<String> seatsTogetherInRow(int row) {
		ArrayList<String> mySeats = new ArrayList<String>();

			for (int seat = 0; seat < seatsPerRow; seat++) {
				if (getSeat(row, seat).isAvailable()) {
					mySeats.add(getSeat(row, seat).getSeatID());
				}else {
					mySeats.clear();
				}	
			}
		return mySeats;
	}
	
	/**
	 * all the available seats in this given row
	 * @param row
	 * @return a list of all available seats 
	 */
	public ArrayList<String> availableSeatsInRow(int row){
		ArrayList<String> availableSeats = new ArrayList<String>();
		for (int seat = 0; seat < seatsPerRow; seat++) {
			if (getSeat(row, seat).isAvailable()) {
				availableSeats.add(getSeat(row, seat).getSeatID());
			}
		}
		return availableSeats;
	}
	
	/**
	 * Print seating chart
	 */
	public void printTheater() {
		for (int row = 0; row < rows; row++) {
			System.out.print("ABCDEFGHIJKLMNOPQRSTUVWXYZ".charAt(row) + " ");
			for (int seat = 0; seat < seatsPerRow; seat++) {
				if (seats[row][seat].isAvailable()) {
					System.out.print("O ");
				}
				else {
					System.out.print("X ");
				}
				
			}
			System.out.println();
		}
		
	}
}
