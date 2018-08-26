import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("serial")
public class Loan implements Serializable { //changed class name's first letter into upper case
	
	public static enum LOAN_STATE { CURRENT, OVER_DUE, DISCHARGED };
	
	private int loanID;
	private Book book;
	private Member member;
	private Date date;
	private LOAN_STATE loanState;
	//above private variable names are modified using meaningful variable names
	
	public loan(int loanId, book book, member member, Date dueDate) {
		this.loanID = loanId;
		this.book = book;
		this.member = member;
		this.date = dueDate;
		this.loanState = LOAN_STATE.CURRENT;
		//above modified variables are replaced in this method
	}

	
	public void checkOverDue() {
		if (state == LOAN_STATE.CURRENT &&
			Calendar.getInstance().Date().after(D)) {
			this.state = LOAN_STATE.OVER_DUE;			
		}
	}

	
	public boolean isOverDue() {
		return state == LOAN_STATE.OVER_DUE;
	}

	
	public Integer getId() {
		return loanID;
	}


	public Date getDueDate() {
		return date;
	}
	
	
	public String toString() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

		StringBuilder stringBuilder = new StringBuilder();
		sb.append("Loan:  ").append(loanID).append("\n")
		  .append("  Borrower ").append(Member.getId()).append(" : ")
		  .append(Member.getLastName()).append(", ").append(Member.getFirstName()).append("\n")
		  .append("  Book ").append(Book.bookID()).append(" : " )
		  .append(book.title()).append("\n")
		  .append("  DueDate: ").append(simpleDateFormat.format(date)).append("\n")
		  .append("  State: ").append(state);		
		return stringBuilder.toString();
	}


	public member Member() {
		return member;
	}


	public book Book() {
		return book;
	}


	public void Loan() {
		state = LOAN_STATE.DISCHARGED;		
	}

}
