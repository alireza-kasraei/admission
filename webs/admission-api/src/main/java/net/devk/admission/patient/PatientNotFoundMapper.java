package net.devk.admission.patient;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class PatientNotFoundMapper implements ExceptionMapper<PatientNotFoundException> {

	@Override
	public Response toResponse(PatientNotFoundException exception) {
		return Response.status(Response.Status.NOT_FOUND).build();
	}

}
