package util;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import model.Patient;

public class FileStorage {

	private static final String FILE_PATH = "patients_seen.txt";
	
	public static void appendSeenPatient(Patient patient) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String timestamp = LocalDateTime.now().format(formatter);
		
		String line = timestamp + "  |  " + patient.getTicketNumber() + "  |  " + patient.getName() + "  |  " + patient.getCurrentSector();  
		
		try(FileWriter writer = new FileWriter(FILE_PATH, true)){
			writer.write(line + System.lineSeparator());
		} catch (IOException e) {
			System.out.println("Error writing patient history file.");
		}
	}
	
	
	
}
