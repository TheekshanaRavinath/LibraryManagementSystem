public class PayFineControl {
	
	private PayFineUserInterface ui;//changed class name into a meaningful name
	private enum CONTROL_STATE { INITIALISED, READY, PAYING, COMPLETED, CANCELLED };
	private CONTROL_STATE state;
	
	private Library library;//changed class name first letter into a capital letter
	private Member member;//deleted one semicolon and changed class name first letter into a capital letter


	public PayFineControl() {
		this.library = library.INSTANCE();
		state = CONTROL_STATE.INITIALISED;
	}
	
	
	public void setUI(PayFineUserInterface ui) { //changed class name into a meaningful name
		if (!state.equals(CONTROL_STATE.INITIALISED)) {
			throw new RuntimeException("PayFineControl: cannot call setUI except in INITIALISED state");
		}	
		this.ui = ui;
		ui.setState(PayFineUserInterface.UI_STATE.READY);//changed class name into a meaningful name
		state = CONTROL_STATE.READY;		
	}


	public void cardSwiped(int memberId) {
		if (!state.equals(CONTROL_STATE.READY)) {
			throw new RuntimeException("PayFineControl: cannot call cardSwiped except in READY state");
		}	
		member = library.getMember(memberId);
		
		if (member == null) {
			ui.display("Invalid Member Id");
			return;
		}
		ui.display(member.toString());
		ui.setState(PayFineUserInterface.UI_STATE.PAYING);//changed class name into a meaningful name
		state = CONTROL_STATE.PAYING;
	}
	
	
	public void cancel() {
		ui.setState(PayFineUserInterface.UI_STATE.CANCELLED);//changed class name into a meaningful name
		state = CONTROL_STATE.CANCELLED;
	}


	public double payFine(double amount) {
		if (!state.equals(CONTROL_STATE.PAYING)) {
			throw new RuntimeException("PayFineControl: cannot call payFine except in PAYING state");
		}	
		double change = member.payFine(amount);
		if (change > 0) {
			ui.display(String.format("Change: $%.2f", change));
		}
		ui.display(member.toString());
		ui.setState(PayFineUserInterface.UI_STATE.COMPLETED);//changed class name into a meaningful name
		state = CONTROL_STATE.COMPLETED;
		return change;
	}
	


}
