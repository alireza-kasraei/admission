package net.devk.admission.service.patient;

import java.util.List;
import java.util.Optional;

import net.devk.admission.model.Patient;

public interface PatientService {

	public Optional<Patient> findById(Long id);

	public Long addPatient(String name);

	public List<Patient> findAll();

	public Optional<Patient> findByName(String name);

}
