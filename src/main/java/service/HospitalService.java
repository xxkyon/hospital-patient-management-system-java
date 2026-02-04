package service;

import java.time.LocalDateTime;

import model.Patient;
import model.Sector;
import state.DailyState;
import util.InputValidator;
import util.StateStorage;

public class HospitalService {
	
	private DailyState state;
	private StateStorage storage;
	
	public HospitalService(StateStorage storage) {
		this.storage = storage;
		this.state = storage.loadToday();
		
	}
	
	public RegistrationResult registerPatient(Patient patient) {
		
		int password = patient.getTicketNumber();
		String name = patient.getName();
		
		if(!InputValidator.isValidPassword(password)) {
			return RegistrationResult.INVALID_PASSWORD;
		}
		
		if(!InputValidator.isValidName(name)) {
			return RegistrationResult.INVALID_NAME;
		}
		
		if(state.getUsedPasswords().contains(password)) {
			return RegistrationResult.DUPLICATE_PASSWORD;
		}
		
		state.getUsedPasswords().add(password);
		state.getReceptionQueue().add(patient);
		
		storage.saveToday(state);
		
		return RegistrationResult.SUCCESS;
	}
	
	
	public Patient callNextFromReception() {
		
		if(state.getReceptionQueue().isEmpty()) {
			return null;
		}
		
		Patient nextPatient = state.getReceptionQueue().callNext();
		nextPatient.setSector(Sector.MEDICAL);
		state.getMedicalQueue().add(nextPatient);
		
		storage.saveToday(state);
		
		return nextPatient;	
	}
	
	public Patient callNextFromMedical() {
		
		if(state.getMedicalQueue().isEmpty()) {
			return null;
		}
		
		Patient nextPatient = state.getMedicalQueue().callNext();
		nextPatient.setSector(Sector.MEDICATION);
		state.getMedicationQueue().add(nextPatient);
		
		storage.saveToday(state);
		
		return nextPatient;
	}
	
	public Patient callNextFromMedication() {
		
		if(state.getMedicationQueue().isEmpty()) {
			return null;
		}
		
		Patient nextPatient = state.getMedicationQueue().callNext();
		nextPatient.setSector(Sector.SEEN);
		nextPatient.setExitTime(LocalDateTime.now().toString());
		
		storage.saveToday(state);
		
		return nextPatient;
	}
	
	public DailyState getState() {
		return state;
	}
	
	public void printDailyState() {
		System.out.println("\n*** DAILY STATE ***");
		System.out.println("Date: " + state.getDate());
		
		System.out.println("\nReception queue:");
		state.getReceptionQueue().printAll();
		
		System.out.println("\nMedical queue:");
		state.getMedicalQueue().printAll();
		
		System.out.println("\nMedication queue:");
		state.getMedicationQueue().printAll();
	}
	
	public boolean isReceptionEmpty() {
		return state.getReceptionQueue().isEmpty();
	}
	
	public boolean isMedicalEmpty() {
		return state.getMedicalQueue().isEmpty();
	}
	
	public boolean isMedicationEmpty() {
		return state.getMedicationQueue().isEmpty();
	}
	
	public boolean isPasswordAlreadyUsed(int password) {
		return state.getUsedPasswords().contains(password);
	}
}
