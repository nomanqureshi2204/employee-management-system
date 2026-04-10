package com.noman.ems.util;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

@Component
public class IdGenerator {
	private static final Object employeeLock  = new Object();
	private static final Object projectLock  = new Object();
	private static final Object clientLock  = new Object();

	// Employee-ID
	public static String generateEmployeeId(String lastId) {
		synchronized(employeeLock) {
			if(lastId == null )lastId = "JTC-000";
			String numPart = lastId.split("-")[1].trim();
			int num = Integer.parseInt(numPart)+1;
			return String.format("JTC-%03d", num); // JTC-001,JTC-002
		}
	}
	
	// Project Id
	public static String generateProjectId(String lastId) {
		synchronized (projectLock) {
			if(lastId == null)lastId = "PROJECT-000";
			String numPart = lastId.split("-")[1].trim();
			int num = Integer.parseInt(numPart)+1;
			return String.format("PROJECT-%03d", num);
		}
	}

	// Project Id
		public static String generateClientId(String lastId) {
			synchronized (clientLock) {
				if(lastId == null)lastId = "CLIENT-000";
				String numPart = lastId.split("-")[1].trim();
				int num = Integer.parseInt(numPart)+1;
				return String.format("CLIENT-%03d", num);
			}
		}
}
