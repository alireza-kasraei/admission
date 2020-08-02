package net.devk.admission.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "PATIENT")
@NamedQueries({ @NamedQuery(name = "Patient.findAll", query = "SELECT e FROM Patient e"),
		@NamedQuery(name = "Patient.findByName", query = "SELECT e FROM Patient e where e.name = :name") })

public class Patient implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(length = 40)
	private String name;

	public Patient() {
	}

	public Patient(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
