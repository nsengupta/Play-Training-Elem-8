package services.attendees.starPlayers;

import java.util.HashSet;

import play.db.Database;
import play.mvc.WebSocket;
import play.mvc.WebSocket.Out;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.Creator;

import services.attendees.starPlayers.SoccerInfoMessageProtocol.VoteStatus;

public class JeevesActor extends UntypedActor {
	
	private Out<String> wsOut;

	@Override
	public void onReceive(Object arg0) throws Throwable {
	
		if (arg0 instanceof SoccerInfoMessageProtocol.VoteStatus) {
			SoccerInfoMessageProtocol.VoteStatus v = (SoccerInfoMessageProtocol.VoteStatus) arg0;
			System.out.println("VoteStatus received (" + v.status + ")");
			wsOut.write(v.status);
		}
	}
	
	public void postStop() {
		System.out.println(getSelf().path() + " is going down!");
	}
	
	public static Props props(final WebSocket.Out<String> wsOut) {
	    return Props.create(new Creator<JeevesActor>() {
	      private static final long serialVersionUID = 1L;
	 
	      public JeevesActor create() throws Exception {
	        return new JeevesActor(wsOut);
	      }
	    });
	} 


	public JeevesActor(final WebSocket.Out<String> out) {
		this.wsOut = out;
	}
	

}
