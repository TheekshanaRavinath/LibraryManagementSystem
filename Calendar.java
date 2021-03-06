import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Calendar {
	
	private static Calendar self;
	private static java.util.Calendar calendar; //changed the veriable name into meaningfull name 
	
	
	private Calendar() {
		calendar = java.util.Calendar.getInstance(); //changed the veriable name into meaningfull name 
	}
	
	public static Calendar getInstance() {
		if (self == null) {
			self = new Calendar();
		}
		return self;
	}
	
	public void incrementDate(int days) {
		calendar.add(java.util.Calendar.DATE, days); //fix the veriable name		
	}
	
	public synchronized void setDate(Date date) {
		try {
			calendar.setTime(date); //fix the veriable name
	        calendar.set(java.util.Calendar.HOUR_OF_DAY, 0); //fix the veriable name  
	        calendar.set(java.util.Calendar.MINUTE, 0); //fix the veriable name 
	        calendar.set(java.util.Calendar.SECOND, 0); //fix the veriable name  
	        calendar.set(java.util.Calendar.MILLISECOND, 0); //fix the veriable name
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}	
	}
	public synchronized Date Date() {
		try {
	        calendar.set(java.util.Calendar.HOUR_OF_DAY, 0);   //fix the veriable name
	        calendar.set(java.util.Calendar.MINUTE, 0);   //fix the veriable name
	        calendar.set(java.util.Calendar.SECOND, 0);  //fix the veriable name 
	        cal.set(java.util.Calendar.MILLISECOND, 0);  //fix the veriable name
			return calendar.getTime();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}	
	}

	public synchronized Date getDueDate(int loanPeriod) {
		Date now = Date();
		calendar.add(java.util.Calendar.DATE, loanPeriod); //fix the veriable name
		Date dueDate = cal.getTime();
		calendar.setTime(now); //fix the veriable name
		return dueDate;
	}
	
	public synchronized long getDaysDifference(Date targetDate) {
		long diffMillis = Date().getTime() - targetDate.getTime();
	    long diffDays = TimeUnit.DAYS.convert(diffMillis, TimeUnit.MILLISECONDS);
	    return diffDays;
	}

}
