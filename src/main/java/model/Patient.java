package model;

import java.time.LocalDateTime;

public class Patient {
	private int ticketNumber;
	private String name;
	private String entryTime;
	private String exitTime;
	private Sector currentSector;
	
	
	public Patient(int ticketNumber, String name, Sector currentSector) {
		this.ticketNumber = ticketNumber;
		this.name = name;
		this.currentSector = currentSector;
		this.entryTime = LocalDateTime.now().toString();
		this.exitTime = null;
	}
	
	public int getTicketNumber() {
		return ticketNumber;
	}
	
	public String getName() {
		return name;
	}
	
	public Sector getCurrentSector() {
		return currentSector;
	}
	
	public void setSector(Sector newSector) {
		this.currentSector = newSector;
	}
	
	public void setExitTime(String time) {
		this.exitTime = time;
	}
	
	@Override
	public String toString() {
		if(this.exitTime != null) {
			return "Password: " + ticketNumber + "  | Patient: " + name + "  |  Sector: " + currentSector + "  |  Entry time: " + entryTime + "  |  Exit time: " + exitTime;
		}
		
		return "Password: " + ticketNumber + "  | Patient: " + name + "  |  Sector: " + currentSector + "  |  Entry time: " + entryTime;
	}
}

