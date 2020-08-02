package net.devk.admission.patient;

import java.util.Collections;

import javax.ejb.EJB;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("patients")
public class PatientController {

	@EJB(lookup = "java:app/patient-ejb/PatientSessionBean!net.devk.admission.patient.PatientLocal")
	private PatientLocal patientLocal;

//		try {
//			patientLocal = InitialContext
//					.doLookup("java:app/net.devk-patient-ejb-1.0-SNAPSHOT/PatientSessionBean!net.devk.admission.patient.PatientLocal");
//		} catch (NamingException e) {
//			e.printStackTrace();
//		}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		return Response.ok(patientLocal.findAll()).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPatient(JsonObject jsonObject) {
		final String patientName = jsonObject.getString("name");
		final Long patientId = patientLocal.addPatient(patientName);
		return Response.ok(Collections.singletonMap(patientId, patientName)).build();
	}

}
