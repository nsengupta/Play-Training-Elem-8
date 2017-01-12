package services.attendees.starPlayers;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class AttendeesDB {
	
	public final Map<String,SoccerAttendeeRecord> attendees= new HashMap<String,SoccerAttendeeRecord>();
	
	public AttendeesDB () {
		
		attendees.put("Ronaldo",     new SoccerAttendeeRecord("Ronaldo","Christiano"));
		attendees.put("Messi",       new SoccerAttendeeRecord("Messi","Lionel"));
		/*attendees.put("Ozil",        new SoccerAttendeeRecord("Ozil","Mesit"));
		attendees.put("Sterling",    new SoccerAttendeeRecord("Sterling","Rehim"));
		attendees.put("Griezeman",   new SoccerAttendeeRecord("Griezeman","Antoine"));
		attendees.put("Lewandowski", new SoccerAttendeeRecord("Lewandowski","Robert"));
		attendees.put("Agueiro",     new SoccerAttendeeRecord("Agueiro","Sergio"));
		attendees.put("Keane",       new SoccerAttendeeRecord("Keane","Robby"));
		attendees.put("Gyan",        new SoccerAttendeeRecord("Gyan","Asamoa"));
		attendees.put("Speed",       new SoccerAttendeeRecord("Speed","Gary"));
		attendees.put("Kelly",       new SoccerAttendeeRecord("Kelly","Gary"));*/
	}
	
	public List<String> getAll() {
		
		List<String> players = new ArrayList<String>();
		
		for (Entry<String, SoccerAttendeeRecord> s: this.attendees.entrySet()) 
			players.add(s.getValue().toString());
		
		return (players);
	}
	
	public String getBySurname(String sname) {
		
		SoccerAttendeeRecord soccerAttendee = attendees.get(sname);
		if (soccerAttendee == null)
			return ("No player with " + sname+ ", found");
		else 
			return (soccerAttendee.toString());
	}
	
	public int attendeeCount() {
		return (attendees.size());
	}
	
	public Integer addNewAttendee(SoccerAttendeeRecord sa) {
		attendees.put(sa.getLastName(), sa);
		return (attendeeCount());
	}
	
	public String getCurrentVoteStatus(String lname) {
		
		String status = this.attendees.get(lname).toString();
		return (status);
	}
	
	
	// TODO [Make this functionality available to the external world]
	public int addVoteFor(String lname) {
		
		SoccerAttendeeRecord r = this.attendees.get(lname);
		return(r.earnsAVote());	
	}

}
