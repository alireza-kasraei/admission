package net.devk.admission.patient;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

import net.devk.admission.model.Patient;

@Repository
public interface PatientRepository extends EntityRepository<Patient, Long> {

	Patient findByName(String name);

}