package net.devk.admission.patient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import net.devk.admission.model.Patient;

@RunWith(MockitoJUnitRunner.class)
public class PatientSessionBeanTest {

	@Mock
	private PatientRepository patientRepository;

	private static PatientSessionBean patientSessionBean;

	@Before
	public void inti() {
		patientSessionBean = new PatientSessionBean(patientRepository);
	}

	@Test
	public void testFindPatientByName() {
		Patient penny = new Patient("Penny");
		penny.setId(1L);
		when(patientRepository.findByName(Mockito.anyString())).thenReturn(penny);
		Optional<Patient> optional = patientSessionBean.findByName("Penny");
		assertTrue(optional.isPresent());
		assertEquals(optional.get().getId().longValue(), 1L);
	}

}