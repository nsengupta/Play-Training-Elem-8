package models.attendees.StarPlayers;

public class SoccerAttendeeVoteCarrier {
	
	private String lname;
	private int    vote;
	public SoccerAttendeeVoteCarrier(String lname, int vote) {
		super();
		this.lname = lname;
		this.vote = vote;
	}
	public String getLname() {
		return lname;
	}
	public int getVote() {
		return vote;
	}
	
	

}
