package jysk_shared;

public class DataCollection {

	private String sendFrom;
	private String sendTo; 
	private long timeTaken; 
	
	public DataCollection(String sendFrom, String sendTo, long timeTaken) {
		this.sendFrom = sendFrom; 
		this.sendTo = sendTo; 
		this.timeTaken = timeTaken; 
	}
	
	public String getSendFrom() {
		return sendFrom;
	}
	
	public String getSendTo() {
		return sendTo;
	}
	
	public long getTimeTaken() {
		return timeTaken;
	}
}
