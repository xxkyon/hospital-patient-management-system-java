package app;

import service.HospitalService;
import service.RegistrationResult;

import java.util.Scanner;

import util.FileStorage;
import util.InputValidator;
import util.JsonStorage;
import util.StateStorage;

import model.Patient;
import model.Sector;

public class Main {
	
	public static Patient confirmPatientData(Scanner sc) {
		
		while(true) {
			System.out.println("Patient's password: ");
			String passwordInput = sc.nextLine();
			
			if(!InputValidator.isValidPassword(passwordInput)){
				System.out.println("Invalid password. It must contain exactly 5 digits.\n");
				continue;
			}
			
			int password = Integer.parseInt(passwordInput);
			
			System.out.println("Patient's name: ");
			String name = sc.nextLine();
			
			if(!InputValidator.isValidName(name)) {
				System.out.println("Invalid name. Only letters and spaces are allowed.\n");
				continue;
			}
			
			System.out.println("\nPlease, check the patient's data before proceeding:\n");
			
	        System.out.println("Name: " + name);
	        System.out.println("Password: " + password);
	        System.out.print("Is this data correct? [Y/N]: ");

	        char confirm = sc.next().charAt(0);
	        sc.nextLine();
	        
	        if(confirm == 'Y' || confirm == 'y') {
	        	return new Patient(password, name, Sector.RECEPTION);
	        } else if(confirm == 'N' || confirm == 'n') {
	        	System.out.println("Let's try again.\n");
	        } else {
	        	System.out.println("Invalid option. Try again.\n");
	        }
		}
	}

	public static void main(String[] args) {
		
		StateStorage storage = new JsonStorage();
		
		HospitalService dailyHospitalService = new HospitalService(storage);
		
		Scanner sc = new Scanner(System.in);
		
		int option = -1;
		
		do {
			System.out.println("#################################################\n\n");
			System.out.println("Welcome to XXX Hospital Patient Management System!");
			System.out.println("1 - Register patient\n2 - Call next from reception\n3 - Call next from medical\n4 - Call next from medication\n5 - Check Daily State \n0 - Exit");
			System.out.println("\nYour option: ");
			
			String optionInput = sc.nextLine();
			
			if(!optionInput.matches("[0-5]")) {
				System.out.println("Invalid option.\n");
				continue;
			}
			
			option = Integer.parseInt(optionInput);		
			
			switch(option) {
			case 1:
				System.out.println("\nYou chose: 1 - Register patient\n");
				
				Patient newPatient = confirmPatientData(sc);
				
				RegistrationResult result = dailyHospitalService.registerPatient(newPatient);
				
				switch(result) {
				case SUCCESS:
					System.out.println(newPatient.getName() + " has been registered.\n");
					System.out.println(newPatient.toString());
					break;
					
				case DUPLICATE_PASSWORD: System.out.println("This password is already in use.\n");
					break;
					
				case INVALID_PASSWORD:
					System.out.println("Password must have exactly 5 digits.\n");
					break;
					
				case INVALID_NAME:
					System.out.println("Invalid name.\n");
					break;
				}
				
				break;

			case 2:
				
				Patient nextReceptionPatient = dailyHospitalService.callNextFromReception();
				
				if(nextReceptionPatient == null) {
					System.out.println("Reception queue is empty.\n");
					break;
				} 
				
				System.out.println(nextReceptionPatient.getName() + " has been moved to the Medical section\n");
				
				System.out.println(nextReceptionPatient.toString());
				
				break;
				
			case 3:
				
				Patient nextMedicalPatient = dailyHospitalService.callNextFromMedical();
				
				if(nextMedicalPatient == null) {
					System.out.println("Medical queue is empty.\n");
					break;
				} 
				
				System.out.println(nextMedicalPatient.getName() + " has been moved to the Medication section\n");
				
				System.out.println(nextMedicalPatient.toString()); 
				
				break;
				
			case 4:
				
				Patient nextMedicationPatient = dailyHospitalService.callNextFromMedication();
				
				if(nextMedicationPatient == null) {
					System.out.println("Medication queue is empty.\n");
					break;
				} 
				
				System.out.println(nextMedicationPatient.getName() + " has been seen and treated.\n");
				
				System.out.println(nextMedicationPatient.toString());
				
				FileStorage.appendSeenPatient(nextMedicationPatient);
				
				break;
			
			case 5:
				dailyHospitalService.printDailyState();
				break;
			
			case 0:
				System.out.println("\nExiting system...\n");
				break;
			}
			
				
			
			System.out.println("\n\n#################################################");
		} while (option != 0);
		
		
		sc.close();
	}

}
