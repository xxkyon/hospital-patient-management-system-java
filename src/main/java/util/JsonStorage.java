package util;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import state.DailyState;

public class JsonStorage implements StateStorage{
	
	private static final String FILE_PATH = "daily_state.json";
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	@Override
	public DailyState loadToday() {
		
		try(FileReader reader = new FileReader(FILE_PATH)){
			
			DailyState savedState = gson.fromJson(reader, DailyState.class);
			
			if(savedState == null) {
				return new DailyState();
			}
			
			if(!savedState.getDate().equals(LocalDate.now())) {
				return new DailyState();
			}
			
			return savedState;
			
		} catch(IOException e) {
			return new DailyState();
		}
	}
	
	@Override
	public void saveToday(DailyState state) {
		
		try(FileWriter writer = new FileWriter(FILE_PATH)){
			gson.toJson(state, writer);
		} catch(IOException e) {
			System.out.println("Error saving daily state.");
		}
	}
	
	
}
