package net.devk.admission.secured;

import static org.keycloak.test.TestsHelper.createClient;
import static org.keycloak.test.builders.ClientBuilder.AccessType.BEARER_ONLY;

import java.io.File;
import java.io.IOException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.keycloak.test.FluentTestsHelper;
import org.keycloak.test.TestsHelper;
import org.keycloak.test.builders.ClientBuilder;

import net.devk.admission.AdmissionApiApplication;

//@RunWith(Arquillian.class)
public class SecuredResourceTestss {

	private static FluentTestsHelper testsHelper;

	static {
		testsHelper = new FluentTestsHelper();
		try {
			testsHelper.importTestRealm(SecuredResourceTestss.class.getResourceAsStream("/quickstart-realm.json"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
//		try {
//			TestsHelper.appName = "test-demo";
//			TestsHelper.baseUrl = "http://localhost:18080/test-demo";
//			importTestRealm("admin", "admin", "/quickstart-realm.json");
//			createDirectGrantClient();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	@Deployment
	public static Archive<?> createTestArchive() throws IOException {
		return ShrinkWrap.create(WebArchive.class, "test-demo.war")
				.addPackages(true, Filters.exclude(".*Test.*"), AdmissionApiApplication.class.getPackage())
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsWebInfResource(new StringAsset(createClient(
						ClientBuilder.create("test-demo").baseUrl(TestsHelper.baseUrl).accessType(BEARER_ONLY))),
						"keycloak.json")
				.setWebXML(new File("src/main/webapp", "WEB-INF/web.xml"));

	}

	@AfterClass
	public static void cleanUp() throws IOException {
//		TestsHelper.deleteRealm("admin", "admin", TestsHelper.testRealm);
		testsHelper.deleteRealm("quickstart");
	}

	@Test
	public void testSecuredEndpoint() {
		try {
			Assert.assertTrue(TestsHelper.returnsForbidden("api/secured"));
		} catch (IOException e) {
			Assert.fail();
		}
	}

	@Test
	public void testAdminEndpoint() {
		try {
			Assert.assertTrue(TestsHelper.returnsForbidden("api/admin"));
		} catch (IOException e) {
			Assert.fail();
		}
	}

	@Test
	public void testPublicEndpoint() {
		try {
			Assert.assertFalse(TestsHelper.returnsForbidden("api/public"));
		} catch (IOException e) {
			Assert.fail();
		}
	}

	@Test
	public void testSecuredEndpointWithAuth() {
		try {
			Assert.assertTrue(TestsHelper.testGetWithAuth("api/secured",
					TestsHelper.getToken("alice", "password", TestsHelper.testRealm)));
		} catch (IOException e) {
			Assert.fail();
		}
	}

	@Test
	public void testAdminEndpointWithAuthButNoRole() {
		try {
			Assert.assertFalse(TestsHelper.testGetWithAuth("api/admin",
					TestsHelper.getToken("alice", "password", TestsHelper.testRealm)));
		} catch (IOException e) {
			Assert.fail();
		}
	}

	@Test
	public void testAdminEndpointWithAuthAndRole() {
		try {
			Assert.assertTrue(TestsHelper.testGetWithAuth("api/admin",
					TestsHelper.getToken("test-admin", "password", TestsHelper.testRealm)));
		} catch (IOException e) {
			Assert.fail();
		}
	}
}