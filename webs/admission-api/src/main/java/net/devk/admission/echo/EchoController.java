package net.devk.admission.echo;

import java.util.Collections;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("echo")
public class EchoController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response createResponse(JsonObject jsonObject) {
		return Response.ok(Collections.singletonMap("echo message", jsonObject.get("message"))).build();
	}

}
