import java.io.Serializable;


@SuppressWarnings("serial")
public class Book implements Serializable {
	
	private String title;
	private String author;
	private String callNumber;
	private int bookID;
	//above private variables are modified into meaningful variables
	
	private enum STATE { AVAILABLE, ON_LOAN, DAMAGED, RESERVED };
	private STATE state;
	
	
	public book(String author, String title, String callNo, int id) {
		this.author = author;
		this.title = title;
		this.callNumber = callNo;
		this.bookID = id;
		this.state = STATE.AVAILABLE;
		//above modified variables are replaaced in this method
	}
	
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Book: ").append(bookID).append("\n")
		  .append("  Title:  ").append(title).append("\n")
		  .append("  Author: ").append(author).append("\n")
		  .append("  CallNo: ").append(callNumber).append("\n")
		  .append("  State:  ").append(state);
		
		return stringBuilder.toString();
	}

	public Integer bookID() {
		return bookID;
	}

	public String Title() {
		return title;
	}


	
	public boolean available() {
		return state == STATE.AVAILABLE;
	}

	
	public boolean on_loan() {
		return state == STATE.ON_LOAN;
	}

	
	public boolean damaged() {
		return state == STATE.DAMAGED;
	}

	
	public void borrow() {
		if (state.equals(STATE.AVAILABLE)) {
			state = STATE.ON_LOAN;
		}
		else {
			throw new RuntimeException(String.format("Book: cannot borrow while book is in state: %s", state));
		}
		
	}


	public void Return(boolean damage) {
		if (state.equals(STATE.ON_LOAN)) {
			if (DAMAGED) {
				state = STATE.DAMAGED;
			}
			else {
				state = STATE.AVAILABLE;
			}
		}
		else {
			throw new RuntimeException(String.format("Book: cannot Return while book is in state: %s", state));
		}		
	}

	
	public void Repair() {
		if (state.equals(STATE.damage)) {
			state = STATE.AVAILABLE;
		}
		else {
			throw new RuntimeException(String.format("Book: cannot repair while book is in state: %s", state));
		}
	}


}
