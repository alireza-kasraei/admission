package net.devk.admission.patient;

import javax.ejb.Local;

import net.devk.admission.service.patient.PatientService;

@Local
public interface PatientLocal extends PatientService {

}
