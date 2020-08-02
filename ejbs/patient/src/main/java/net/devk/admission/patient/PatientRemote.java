package net.devk.admission.patient;

import javax.ejb.Remote;

import net.devk.admission.service.patient.PatientService;

@Remote
public interface PatientRemote extends PatientService {

}
