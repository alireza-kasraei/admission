package net.devk.admission.patient;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.core.SynchronousDispatcher;
import org.jboss.resteasy.core.SynchronousExecutionContext;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.devk.admission.model.Patient;

public class PatientControllerTest {

	private static Dispatcher dispatcher;
	private static PatientController patientController;
	private static PatientLocal patientLocal;

	private static ObjectMapper mapper;

	// This code here gets run before our tests begin
	@BeforeClass
	public static void setup() {
		dispatcher = MockDispatcherFactory.createDispatcher();
		patientLocal = mock(PatientLocal.class);
		patientController = new PatientController();
		patientController.setPatientLocal(patientLocal);
		dispatcher.getRegistry().addSingletonResource(patientController);
//		dispatcher.getRegistry().addSingletonResource(new PatientNotFoundMapper());
		dispatcher.getProviderFactory().getExceptionMappers().put(PatientNotFoundException.class, new PatientNotFoundMapper());
		mapper = new ObjectMapper();
	}

	@Test
	public void testFindAll() throws Exception {
		when(patientLocal.findAll()).thenReturn(Collections.singletonList(new Patient("hamid")));
		MockHttpRequest request = MockHttpRequest.get("/patients");
		request.accept(MediaType.APPLICATION_JSON);
		MockHttpResponse response = new MockHttpResponse();
		SynchronousExecutionContext synchronousExecutionContext = new SynchronousExecutionContext(
				(SynchronousDispatcher) dispatcher, request, response);
		request.setAsynchronousContext(synchronousExecutionContext);
		dispatcher.invoke(request, response);
		Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void testFindByName() throws Exception {
		when(patientLocal.findByName(Mockito.anyString())).thenReturn(Optional.empty());
		MockHttpRequest request = MockHttpRequest.get("/patients/koorosh01");
		request.accept(MediaType.APPLICATION_JSON);
		MockHttpResponse response = new MockHttpResponse();
		SynchronousExecutionContext synchronousExecutionContext = new SynchronousExecutionContext(
				(SynchronousDispatcher) dispatcher, request, response);
		request.setAsynchronousContext(synchronousExecutionContext);
		dispatcher.invoke(request, response);
		Assert.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

	@Test
	public void testAddPatient() throws Exception {
		when(patientLocal.addPatient(Mockito.anyString())).thenAnswer(invocation -> 12L);
		MockHttpRequest request = MockHttpRequest.post("/patients");
		request.accept(MediaType.APPLICATION_JSON);
		request.contentType(MediaType.APPLICATION_JSON_TYPE);
		request.content(mapper.writeValueAsBytes(Collections.singletonMap("name", "koorosh01")));
		MockHttpResponse response = new MockHttpResponse();
		SynchronousExecutionContext synchronousExecutionContext = new SynchronousExecutionContext(
				(SynchronousDispatcher) dispatcher, request, response);
		request.setAsynchronousContext(synchronousExecutionContext);
		dispatcher.invoke(request, response);
		Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
		Assert.assertEquals("koorosh01", mapper.readValue(response.getOutput(), Map.class).get("12"));
	}

}