package be.anova.lgi.poc.client;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EventsRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("timer://foo?fixedRate=true&period=30s")
                .bean(ClientBean.class, "getEvents");
    }

    public static final class ClientBean {

        private static final Logger LOG = LoggerFactory.getLogger(ClientBean.class);
        public void getEvents(Exchange exchange){
          /*  Client client = ClientBuilder.newClient();
            List<Event> events = null;
            try{
                events = client.target("http://localhost:8181/cxf/lgi/eventservice/events")
                        .path("all")
                        .request(MediaType.APPLICATION_JSON)
                        .get(new GenericType<List<Event>>(){
                        });
                if (events == null) {
                    LOG.info("Returned events null.");
                } else {
                    LOG.info("Events have been returned.");
                }
            }catch(WebApplicationException ex) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }
          */

            try {

                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet getRequest = new HttpGet(
                        "http://localhost:8181/cxf/lgi/eventservice/events");
                getRequest.addHeader("accept", "application/json");

                HttpResponse response = httpClient.execute(getRequest);

                if (response.getStatusLine().getStatusCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + response.getStatusLine().getStatusCode());
                }

                BufferedReader br = new BufferedReader(
                        new InputStreamReader((response.getEntity().getContent())));

                String output;
               LOG.info("Output from Server .... \n");
                while ((output = br.readLine()) != null) {
                    LOG.info(output);
                }
                httpClient.getConnectionManager().shutdown();


            } catch (ClientProtocolException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
}
