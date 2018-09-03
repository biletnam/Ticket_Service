
public class Seat {
	
	private String seatID;
	private int row;
	private int column;
	private boolean available;
	
	public Seat(int inputRow, int inputCol) {
		int col = inputCol + 1;
		seatID = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".substring(inputRow, inputRow + 1) + 
				String.valueOf(col);
		available = true;
	}
	
	public Seat(String inputSeatID){
		seatID = inputSeatID;
		available = true;
	}
	
	public String getSeatID() {
		return seatID;
	}
	public void setSeatID(String seatID) {
		this.seatID = seatID;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	
	
}
