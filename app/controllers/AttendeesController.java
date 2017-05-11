package controllers;

import static akka.pattern.Patterns.ask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import models.attendees.StarPlayers.SoccerAttendeeDataCarrier;
import models.attendees.StarPlayers.SoccerAttendeeVoteCarrier;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Akka;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import play.mvc.Controller;
import play.mvc.LegacyWebSocket;
import play.mvc.Result;
import play.mvc.WebSocket;
import scala.compat.java8.FutureConverters;
import services.attendees.starPlayers.JeevesActor;
import services.attendees.starPlayers.SoccerAttendeesInfoActor;
import services.attendees.starPlayers.SoccerInfoMessageProtocol;
import views.html.index;
import views.html.attendees.list;
import views.html.attendees.count;
import views.html.attendees.external;
import views.html.attendees.voteStatus;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class AttendeesController extends Controller {
	
    private final ActorSystem actorSystem;
	
	@Inject
	FormFactory formFactory;
	
	private final ActorRef soccerAttendeesInfoActor;
	@Inject
    public AttendeesController(ActorSystem actorSystem) {
		this.actorSystem = actorSystem;
       this.soccerAttendeesInfoActor = actorSystem.actorOf(SoccerAttendeesInfoActor.props,"Soccer-Players-Actor");
    }
    public Result index() {
        return ok(index.render("Attendees Application is being readied."));
    }
    
	public CompletionStage<Result> getAll() {
	    	
	    	Function<Object,List<String>> fn = (r) -> {
	    		List<String> s = (ArrayList<String>)r;
	    		return (s);
	    	};
	    	
	        return( FutureConverters.toJava(ask(soccerAttendeesInfoActor,
				    new SoccerInfoMessageProtocol.GetAllMessage(), 
					1000))
	                .thenApply(fn)
	                .thenApply(nameList -> ok(list.render(nameList))));
	    }
    
    @SuppressWarnings("deprecation")
	public LegacyWebSocket<String> ws() {
        return WebSocket.whenReady((in, out) -> {
        	final String[] outComponents = out.toString().split("@");
        	// Create a Jeeves for this client
            final ActorRef jeeves = actorSystem
            		.actorOf(
            				JeevesActor.props(out),
            				"JeevesActor" + outComponents[1]
            		);
            // Remember to send all WebSocket outbound message to the JeevesActor
            in.onMessage(lastName -> {            
                this.soccerAttendeesInfoActor
                .tell(
                		new SoccerInfoMessageProtocol.AFanOf(lastName), 
                		jeeves);
            });

            // on close, tell the JeevesActor to go away
            in.onClose(() -> {
            	
            	this.soccerAttendeesInfoActor
                .tell(
                       new SoccerInfoMessageProtocol.NoMoreAFanOf(""), jeeves);
                actorSystem.stop(jeeves);
            });
        });
    }
    
    
    
    public CompletionStage<Result> voteFor(String lname) {
        	
        	Function<Object,String> fn = (r) -> {
        		String s = (String) r;
        		return (s);
        	};
        	
            return( FutureConverters.toJava(ask(soccerAttendeesInfoActor,
    			    new SoccerInfoMessageProtocol.VoteFor(lname), 
    				1000))
                    .thenApply(fn)
                    .thenApply(vStatus -> ok(voteStatus.render(vStatus))));
    }
    
    
    public Result getBySurname(String surname) {
    	
    	return TODO;
    }
    
    public Result count() {
    	int attendeeCountAtPresent = 0; // this.attendeesManager.attendeeCount();
    	return TODO;
    }
    
    public Result addAttendee(String surname,String firstname) {
    	return TODO;
    }
    
    public Result voteForSoccerAttendeeThruForm() {
    	Form<SoccerAttendeeVoteCarrier> attendeeForm = formFactory.form(SoccerAttendeeVoteCarrier.class);
    	attendeeForm.fill(new SoccerAttendeeVoteCarrier("LastName here",0));
    	return TODO;
    }
    
    
    public Result addSoccerAttendeeThruForm() {
    	Form<SoccerAttendeeDataCarrier> attendeeForm = formFactory.form(SoccerAttendeeDataCarrier.class);
    	attendeeForm.fill(new SoccerAttendeeDataCarrier("LastName here","Firstname here"));
    	return TODO;
    	
    }
    
    public Result saveSoccerAttendeeThruForm() {
    	Form<SoccerAttendeeDataCarrier> attendeeForm = formFactory.form(SoccerAttendeeDataCarrier.class).bindFromRequest();
    	/*this.attendeesManager
    	    .addNewAttendee(
    	    			attendeeForm.apply("surname").value(), 
    	    			attendeeForm.apply("firstname").value()
    	    		);*/
    	ok(String
    			.format("New Soccer Player %s,%s added", 
    					 attendeeForm.apply("firstName").value(),
    					 attendeeForm.apply("lastName").value()
    				   )
    			 );
    	
    	return TODO;
    }

}
