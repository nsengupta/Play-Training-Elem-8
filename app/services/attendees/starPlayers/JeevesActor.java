package services.attendees.starPlayers;

import java.util.HashSet;

import play.db.Database;
import play.mvc.WebSocket;
import play.mvc.WebSocket.Out;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.Creator;

public class JeevesActor extends UntypedActor {
	
	private Out<String> wsOut;

	@Override
	public void onReceive(Object arg0) throws Throwable {
	
		
		

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
	
	public static Props props = Props.create(JeevesActor.class);

}
