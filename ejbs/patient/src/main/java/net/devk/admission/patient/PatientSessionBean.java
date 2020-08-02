package net.devk.admission.patient;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import net.devk.admission.model.Patient;

@Stateless
public class PatientSessionBean implements PatientLocal, PatientRemote {

	private final PatientRepository patientRepository;

	@Inject
	public PatientSessionBean(PatientRepository patientRepository) {
		this.patientRepository = patientRepository;
	}

	@Override
	public Optional<Patient> findById(Long id) {
		return Optional.of(patientRepository.findBy(id));
	}

	@Override
	public Long addPatient(String name) {
		return patientRepository.save(new Patient(name)).getId();
	}

	@Override
	public List<Patient> findAll() {
		return patientRepository.findAll();
	}

	@Override
	public Optional<Patient> findByName(String name) {
		return Optional.of(patientRepository.findByName(name));
	}

}
