package services.attendees.starPlayers;


import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Creator;
import scala.concurrent.duration.Duration;
import akka.japi.Function;
import scala.concurrent.duration.Duration;
import akka.japi.Function;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import static akka.dispatch.Futures.future;
import static java.util.concurrent.TimeUnit.SECONDS;
import static services.attendees.starPlayers.SoccerInfoMessageProtocol.*;

public class SoccerAttendeesInfoActor extends UntypedActor {
	
	LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	
	private AttendeesDB attendeesDB = new AttendeesDB();
	final HashSet<ActorRef> watchers = new HashSet<ActorRef>();
	

	@Override
	public void onReceive(Object arg0) throws Throwable {
		
		if (arg0 instanceof CountMessage) {
			Integer countFromDB = attendeesDB.attendeeCount();
			getSender().tell(countFromDB, getSelf());
		}
		else
	    if (arg0 instanceof GetAllMessage) {
	    	List<String> allPlayers = attendeesDB.getAll();
	    	getSender().tell(allPlayers,getSelf());
	    }
	    else
	    if (arg0 instanceof SoccerInfoMessageProtocol.AFanOf) {
	    	
	    	SoccerInfoMessageProtocol.AFanOf m = (SoccerInfoMessageProtocol.AFanOf) arg0;
	    	String status = this.attendeesDB.getCurrentVoteStatus(m.lname);
	    	getSender().tell(new SoccerInfoMessageProtocol.VoteStatus(status), self());
	    	watchers.forEach(w -> {
	    		w.tell(new SoccerInfoMessageProtocol.VoteStatus(status), self());
	    	});
	    	watchers.add(getSender());
	    }
	    else
	    if (arg0 instanceof SoccerInfoMessageProtocol.NoMoreAFanOf) {
	    	
	    	watchers.remove(getSender());
	    }
	    else 
	    if (arg0 instanceof SoccerInfoMessageProtocol.VoteFor) {
	    	
	    	SoccerInfoMessageProtocol.VoteFor v = (SoccerInfoMessageProtocol.VoteFor) arg0;
	    	this.attendeesDB.addVoteFor(v.lname);
	    	String status = this.attendeesDB.getCurrentVoteStatus(v.lname);
	    	watchers.forEach(w -> {
	    		w.tell(new SoccerInfoMessageProtocol.VoteStatus(status), self());
	    	});
	    	getSender().tell(status, self());
	    }
		
	}
	
	public static Props props = Props.create(SoccerAttendeesInfoActor.class);

}