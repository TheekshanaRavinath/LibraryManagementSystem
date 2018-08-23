public class ReturnBookControl {

	private ReturnBookUI userInterface;//changed the veriable name into meaningful name 
	private enum CONTROL_STATE { INITIALISED, READY, INSPECTING };
	private CONTROL_STATE state;
	
	private Library library;//class name modified "Starting with uppercase latter"
	private Loan currentLoan;//class name modified "Starting with uppercase latter"
	

	public ReturnBookControl() {
		this.library = library.INSTANCE();
		state = CONTROL_STATE.INITIALISED;
	}
	
	
	public void setUI(ReturnBookUI userInterface) {
		if (!state.equals(CONTROL_STATE.INITIALISED)) {
			throw new RuntimeException("ReturnBookControl: cannot call setUI except in INITIALISED state");
		}	
		this.userInterface = userInterface;
		userInterface.setState(ReturnBookUI.UI_STATE.READY);//fixed the veriable name
		state = CONTROL_STATE.READY;		
	}


	public void bookScanned(int bookId) {
		if (!state.equals(CONTROL_STATE.READY)) {
			throw new RuntimeException("ReturnBookControl: cannot call bookScanned except in READY state");
		}	
		book currentBook = library.Book(bookId);
		
		if (currentBook == null) {
			userInterface.display("Invalid Book Id");
			return;
		}
		if (!currentBook.On_loan()) {
			userInterface.display("Book has not been borrowed");//fixed the veriable name
			return;
		}		
		currentLoan = library.getLoanByBookId(bookId);	
		double overDueFine = 0.0;
		if (currentLoan.isOverDue()) {
			overDueFine = library.calculateOverDueFine(currentLoan);
		}
		userInterface.display("Inspecting");//fixed the veriable name
		userInterface.display(currentBook.toString());//fixed the veriable name
		userInterface.display(currentLoan.toString());//fixed the veriable name
		
		if (currentLoan.isOverDue()) {
			userInterface.display(String.format("\nOverdue fine : $%.2f", overDueFine));
		}
		userInterface.setState(ReturnBookUI.UI_STATE.INSPECTING);//fixed the veriable name
		state = CONTROL_STATE.INSPECTING;		
	}


	public void scanningComplete() {
		if (!state.equals(CONTROL_STATE.READY)) {
			throw new RuntimeException("ReturnBookControl: cannot call scanningComplete except in READY state");
		}	
		userInterface.setState(ReturnBookUI.UI_STATE.COMPLETED);	//fixed the veriable name	
	}


	public void dischargeLoan(boolean isDamaged) {
		if (!state.equals(CONTROL_STATE.INSPECTING)) {
			throw new RuntimeException("ReturnBookControl: cannot call dischargeLoan except in INSPECTING state");
		}	
		library.dischargeLoan(currentLoan, isDamaged);
		currentLoan = null;
		userInterface.setState(ReturnBookUI.UI_STATE.READY);
		state = CONTROL_STATE.READY;				
	}


}
