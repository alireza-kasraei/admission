package net.devk.admission.patient;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import javax.inject.Inject;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import net.devk.admission.model.Patient;
import net.devk.admission.repository.EntityManagerProducer;

@RunWith(Arquillian.class)
public class PatientRepositoryTest {

	@Inject
	private PatientRepository patientRepository;

	@Inject
	private UserTransaction utx;

	/**
	 * Arquillian specific method for creating a file which can be deployed while
	 * executing the test.
	 *
	 * @return a war file
	 */
	@Deployment
	public static WebArchive deployment() {

		File[] files = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeDependencies().resolve()
				.withTransitivity().asFile();

		return ShrinkWrap.create(WebArchive.class).addPackage(Patient.class.getPackage())
				.addClass(EntityManagerProducer.class).addClass(PatientRepository.class).addAsLibraries(files)
				.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
				.addAsResource("META-INF/apache-deltaspike.properties").addAsResource("META-INF/beans.xml")
				.addAsResource("META-INF/load.sql");

	}

	/**
	 * Test of withdraw method, of class AccountSessionBean.
	 */
	@Test
	public void testFindPatientByName() {
		Patient patient = patientRepository.findByName("Penny");
		assertNotNull(patient);
	}

	@Test
	public void testAddPatinet() throws Exception {
		utx.begin();
		Patient patient = new Patient("koorosh01");
		Patient savedPatient = patientRepository.save(patient);
		assertNotNull(savedPatient);
		utx.commit();

	}

}