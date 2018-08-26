import java.util.Scanner;


public class BorrowBookUI {
	
	public static enum UI_STATE { INITIALISED, READY, RESTRICTED, SCANNING, IDENTIFIED, FINALISING, COMPLETED, CANCELLED };

	private BorrowBookControl control;
	private Scanner input;
	private UI_STATE state;

	
	public BorrowBookUI(BorrowBookControl control) {
		this.control = control;
		input = new Scanner(System.in);
		state = UI_STATE.INITIALISED;
		control.setUI(this);
	}

	
	private String input(String prompt) {
		System.out.print(prompt);
		return input.nextLine();
	}	
		
		
	private void output(Object object) {
		System.out.println(object);
	}
	
			
	public void setState(UI_STATE state) {
		this.state = state;
	}

	
	public void run() {
		output("Borrow Book Use Case UI\n");
		
		while (true) {
			
			switch (state) {			
			
			case CANCELLED:
				output("Borrowing Cancelled");
				return;

				
			case READY:
				String memberStatus = input("Swipe member card (press <enter> to cancel): "); //changed variable name into memberStatus
				if (memberStatus.length() == 0) { //changed the parameter
					control.cancel();
					break;
				}
				try {
					int memberId = Integer.valueOf(memberStatus).intValue(); //changed parameter
					control.Swiped(memberId);
				}
				catch (NumberFormatException error) {
					output("Invalid Member Id" + error); 
				}
				break;

				
			case RESTRICTED:
				input("Press <any key> to cancel");
				control.cancel();
				break;
			
				
			case SCANNING:
				String bookStatus = input("Scan Book (<enter> completes): ");//changed variable name into bookStatus
				if (bookStatus.length() == 0) { //changed the parameter
					control.complete(); 
					break;
				}
				try {
					int bookId = Integer.valueOf(bookStatus).intValue();
					control.Scanned(bookId);
					
				} catch (NumberFormatException error) {
					output("Invalid Book Id" + error); //output shows the error
				} 
				break;
					
				
			case FINALISING:
				String answer = input("Commit loans? (Y/N): "); //changed variable name into answer
				if (answer.toUpperCase().equals("N")) { //changed the parameter according to variable name
					control.cancel();
					
				} else {
					control.commitLoans();
					input("Press <any key> to complete ");
				}
				break;
				
				
			case COMPLETED:
				output("Borrowing Completed");
				return;
	
				
			default:
				output("Unhandled state");
				throw new RuntimeException("BorrowBookUI : unhandled state :" + state);			
			}
		}		
	}


	public void display(Object object) {
		output(object);		
	}


}
