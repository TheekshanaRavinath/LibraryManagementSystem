import java.util.ArrayList;
import java.util.List;

public class BorrowBookControl {
	
	private BorrowBookUI ui;
	
	private Library library; //changed class name first letter into capital
	private Member member; //changed class name first letter into capital
	private enum CONTROL_STATE { INITIALISED, READY, RESTRICTED, SCANNING, IDENTIFIED, FINALISING, COMPLETED, CANCELLED };
	private CONTROL_STATE state;
	
	private List<book> PENDING;
	private List<loan> COMPLETED;
	private book book; //meaningful variable name
	
	
	public BorrowBookControl() {
		this.library = library.INSTANCE(); //Above changed variable used as a parameter
		state = CONTROL_STATE.INITIALISED;
	}
	

	public void setUI(BorrowBookUI ui) {
		if (!state.equals(CONTROL_STATE.INITIALISED)) 
			throw new RuntimeException("BorrowBookControl: cannot call setUI except in INITIALISED state");
			
		this.ui = ui;
		ui.setState(BorrowBookUI.UI_STATE.READY);
		state = CONTROL_STATE.READY;		
	}

		
	public void swiped(int memberId) { //change method name into lowerCamelcase
		if (!state.equals(CONTROL_STATE.READY)) 
			throw new RuntimeException("BorrowBookControl: cannot call cardSwiped except in READY state");
			
		member = library.getMember(memberId); //Above changed variable used as a parameter
		if (member == null) { //Above changed variable used as a parameter
			ui.display("Invalid memberId");
			return;
		}
		if (library.memberCanBorrow(member)) { //Above changed variable used as a parameter
			PENDING = new ArrayList<>();
			ui.setState(BorrowBookUI.UI_STATE.SCANNING);
			state = CONTROL_STATE.SCANNING; }
		else 
		{
			ui.display("Member cannot borrow at this time");
			ui.setState(BorrowBookUI.UI_STATE.RESTRICTED); }}
	
	
	public void scanned(int bookId) { //change method name into lowerCamelcase
		book = null; //Above changed variable used as a parameter
		if (!state.equals(CONTROL_STATE.SCANNING)) {
			throw new RuntimeException("BorrowBookControl: cannot call bookScanned except in SCANNING state");
		}	
		book = library.Book(bookId); //Above changed variable used as a parameter
		if (book == null) { //Above changed variable used as a parameter
			ui.display("Invalid bookId");
			return;
		}
		if (!book.Available()) { //Above changed variable used as a parameter
			ui.display("Book cannot be borrowed");
			return;
		}
		PENDING.add(book); //Above changed variable used as a parameter
		for (book book : PENDING) { //Above changed variable used as a parameter
			ui.display(book.toString()); //Above changed variable used as a parameter
		}
		if (library.loansRemainingForMember(member) - PENDING.size() == 0) {
			ui.display("Loan limit reached");
			complete();
		}
	}
	
	
	public void complete() { //change method name into lowerCamelcase
		if (PENDING.size() == 0) {
			cancel();
		}
		else {
			ui.display("\nFinal Borrowing List");
			for (book book : PENDING) { //Above changed variable used as a parameter
				ui.display(book.toString());
			}
			COMPLETED = new ArrayList<loan>();
			ui.setState(BorrowBookUI.UI_STATE.FINALISING);
			state = CONTROL_STATE.FINALISING;
		}
	}


	public void commitLoans() {
		if (!state.equals(CONTROL_STATE.FINALISING)) {
			throw new RuntimeException("BorrowBookControl: cannot call commitLoans except in FINALISING state");
		}	
		for (book book : PENDING) {
			loan loan = library.issueLoan(book, member);
			COMPLETED.add(loan);			
		}
		ui.display("Completed Loan Slip");
		for (loan loan : COMPLETED) {
			ui.display(loan.toString());
		}
		ui.setState(BorrowBookUI.UI_STATE.COMPLETED);
		state = CONTROL_STATE.COMPLETED;
	}

	
	public void cancel() {
		ui.setState(BorrowBookUI.UI_STATE.CANCELLED);
		state = CONTROL_STATE.CANCELLED;
	}
	
	
}
