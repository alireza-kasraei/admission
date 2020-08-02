package net.devk.admission.echo;

import java.util.Collections;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("echo")
public class EchoController {

	@GET
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createResponse(@PathParam("name") String name) {
		return Response.ok(Collections.singletonMap("echo", name)).build();
	}

}
