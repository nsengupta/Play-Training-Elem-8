package services.attendees.starPlayers;

import java.io.Serializable;

public class SoccerInfoMessageProtocol {
	
	public static class VoteFor {
		public final String lname;
		public VoteFor(String lname) {
			this.lname = lname;
		}

		public String toString() {
			return ("VoteFor (" + lname + ")");
		}
	}

	public static class CountMessage { 
		public String toString() {
			return ("CountMessage");
		}
	}
	
	public static class GetAllMessage { 
		public String toString() {
			return ("GetAllMessage");
		}
	}
	
	public static class GetAllTournamentsMessage {
		public final String year;
		public GetAllTournamentsMessage(String year) {
			this.year = year;
		}
		public String toString() {
			return ("GetAllTournamentsMessage");
		}
	}
	
	public static class AFanOf {
		public final String lname;
		public AFanOf(String lname) {
			this.lname = lname;
		}
		public String toString() {
			return ("AFanOf (" + lname + ")");
		}
	}
	
	public static class NoMoreAFanOf {
		public final String lname;
		public NoMoreAFanOf(String lname) {
			this.lname = lname;
		}
		public String toString() {
			return ("NoMoreAFanOf (" + lname + ")");
		}
	}
	
	public  static class VoteStatus {

		public final String status;
		public VoteStatus(String status) {
			this.status = status;
		}
		public String toString() {
			return ("VoteStaus (" + status + ")");
		}
	}
	

}
