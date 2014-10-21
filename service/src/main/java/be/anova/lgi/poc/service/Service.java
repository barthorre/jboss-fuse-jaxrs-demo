package be.anova.lgi.poc.service;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/eventservice")
public class Service {

    private static final Logger LOG = LoggerFactory.getLogger(Service.class);

    @GET
    @Path("/events")
    @Produces("application/json")
    public List<Event> getEvents(){
        Event event1 = new Event("Event 1");
        Event event2 = new Event("Event 2");
        List<Event> events = new ArrayList<Event>();
        events.add(event1);
        events.add(event2);
        LOG.info(String.format("Returning %s events", events.size()));
        return events;
    }


}
