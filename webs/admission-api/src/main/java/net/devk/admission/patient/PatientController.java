package net.devk.admission.patient;

import java.util.Collections;
import java.util.Optional;

import javax.ejb.EJB;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.devk.admission.model.Patient;

@Path("patients")
public class PatientController {

	private PatientLocal patientLocal;

	@EJB(lookup = "java:app/patient-ejb/PatientSessionBean!net.devk.admission.patient.PatientLocal")
	public void setPatientLocal(PatientLocal patientLocal) {
		this.patientLocal = patientLocal;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		return Response.ok(patientLocal.findAll()).build();
	}

	@Path("/{name}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findByName(@PathParam("name") String name) {
		Optional<Patient> optional = patientLocal.findByName(name);
		Patient patient = optional.orElseThrow(PatientNotFoundException::new);
		return Response.ok(patient).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPatient(JsonObject jsonObject) {
		final String patientName = jsonObject.getString("name");
		final Long patientId = patientLocal.addPatient(patientName);
		return Response.ok(Collections.singletonMap(patientId, patientName)).build();
	}

}
