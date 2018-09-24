package com.acme.anvil.service.localstorage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.ejb.Schedule;
import javax.ejb.Singleton;

@Singleton
public class LocalStorageService {

	@Schedule(second = "*/1", minute = "*", hour = "*", persistent = false)
	public void writeToFile() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\somewindowspath\\timer.txt"));) {
			writer.write("Hello");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
