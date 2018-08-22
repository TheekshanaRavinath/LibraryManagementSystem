public class FixBookControl {
	
	private FixBookUI userInterface; //changed the veriable name into meaningful name 
	private enum CONTROL_STATE { INITIALISED, READY, FIXING };
	private CONTROL_STATE state;
	
	private Library library; //class name modified "Starting with uppercase latter"
	private Book currentBook; //class name modified "Starting with uppercase latter"


	public FixBookControl() {
		this.library = library.INSTANCE();
		state = CONTROL_STATE.INITIALISED;
	}
	
	
	public void setUI(FixBookUI userInterface) {//chaneged the parameter name 
		if (!state.equals(CONTROL_STATE.INITIALISED)) {
			throw new RuntimeException("FixBookControl: cannot call setUI except in INITIALISED state");
		}	
		this.userInterface = userInterface; //fixed the veriable name
		userInterface.setState(FixBookUI.UI_STATE.READY); //fixed the veriable name
		state = CONTROL_STATE.READY;		
	}


	public void bookScanned(int bookId) {
		if (!state.equals(CONTROL_STATE.READY)) {
			throw new RuntimeException("FixBookControl: cannot call bookScanned except in READY state");
		}	
		currentBook = library.Book(bookId);
		
		if (currentBook == null) {
			userInterface.display("Invalid bookId"); //fixed the veriable name
			return;
		}
		if (!currentBook.Damaged()) {
			userInterface.display("\"Book has not been damaged"); //fixed the veriable name
			return;
		}
		userInterface.display(currentBook.toString()); //fixed the veriable name
		userInterface.setState(FixBookUI.UI_STATE.FIXING); //fixed the veriable name
		state = CONTROL_STATE.FIXING;		
	}


	public void fixBook(boolean fix) {
		if (!state.equals(CONTROL_STATE.FIXING)) {
			throw new RuntimeException("FixBookControl: cannot call fixBook except in FIXING state");
		}	
		if (fix) {
			library.repairBook(currentBook);
		}
		currentBook = null;
		userInterface.setState(FixBookUI.UI_STATE.READY); //fixed the veriable name
		state = CONTROL_STATE.READY;		
	}

	
	public void scanningComplete() {
		if (!state.equals(CONTROL_STATE.READY)) {
			throw new RuntimeException("FixBookControl: cannot call scanningComplete except in READY state");
		}	
		userInterface.setState(FixBookUI.UI_STATE.COMPLETED);		
	}






}
