package state;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import queue.ReceptionQueue;
import queue.MedicalQueue;
import queue.MedicationQueue;

public class DailyState{
	
	private String date;
	
	private ReceptionQueue receptionQueue;
	private MedicalQueue medicalQueue;
	private MedicationQueue medicationQueue;
	
	private Set<Integer> usedPasswords;
	
	public DailyState() {
		
		this.date = LocalDate.now().toString();
		this.receptionQueue = new ReceptionQueue();
		this.medicalQueue = new MedicalQueue();
		this.medicationQueue = new MedicationQueue();
		this.usedPasswords = new HashSet<>();

	}
	
	public static DailyState createNew() {
		return new DailyState();
	}
	
	public String getDate() {
		return date;
	}
	
	public ReceptionQueue getReceptionQueue() {
		return receptionQueue;
	}
	
	public MedicalQueue getMedicalQueue() {
		return medicalQueue;
	}
	
	public MedicationQueue getMedicationQueue() {
		return medicationQueue;
	}
	
	public Set<Integer> getUsedPasswords(){
		return usedPasswords;
	}
}