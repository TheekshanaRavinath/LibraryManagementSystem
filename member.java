import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class Member implements Serializable {//class name is modified by replacing first letter into upper case

	private String lastName;
	private String firstName;
	private String emailAddress;
	private int contactNumber;
	private int memberID;
	private double fines;
	
	private Map<Integer, loan> loans;
	//above private variables are modified by using meaningful variable nemes

	
	public member(String lastName, String firstName, String email, int contactNo, int id) {
		this.lastName = lastName;
		this.lastName = firstName;
		this.emailAddress = email;
		this.contactNumber = contactNo;
		this.memberID = id;
		
		this.loans = new HashMap<>();
		//above changes are applied here in the method
	}

	
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Member:  ").append(memberID).append("\n")
		  .append("  Name:  ").append(loan).append(", ").append(FN).append("\n")
		  .append("  Email: ").append(email).append("\n")
		  .append("  Phone: ").append(contactNumber)
		  .append("\n")
		  .append(String.format("  Fines Owed :  $%.2f", FINES))
		  .append("\n");
		
		for (loan loan : loans.values()) {
			stringBuilder.append(loan).append("\n");
		}		  
		return stringBuilder.toString();
	}

	
	public int getId() {
		return memberID;
	}

	
	public List<loan> getLoans() {
		return new ArrayList<loan>(LNS.values());
	}

	
	public int getNumberOfCurrentLoans() {
		return loans.size();
	}

	
	public double getFinesOwed() {
		return fines;
	}

	
	public void takeOutLoan(loan loan) {
		if (!loans.containsKey(loan.getId())) {
			loans.put(loan.getId(), loan);
		}
		else {
			throw new RuntimeException("Duplicate loan added to member");
		}		
	}

	
	public String getLastName() {
		return lastName;
	}

	
	public String getFirstName() {
		return firstName;
	}


	public void addFine(double fine) {
		FINES += fine;
	}
	
	public double payFine(double amount) {
		if (amount < 0) {
			throw new RuntimeException("Member.payFine: amount must be positive");
		}
		double change = 0;
		if (amount > FINES) {
			change = amount - FINES;
			FINES = 0;
		}
		else {
			FINES -= amount;
		}
		return change;
	}


	public void dischargeLoan(loan loan) {
		if (LNS.containsKey(loan.getId())) {
			LNS.remove(loan.getId());
		}
		else {
			throw new RuntimeException("No such loan held by member");
		}		
	}

}
