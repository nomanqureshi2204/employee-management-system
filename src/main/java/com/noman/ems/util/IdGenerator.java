package com.noman.ems.util;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
	private static AtomicInteger employeeCounter = new AtomicInteger(0);
	private static AtomicInteger projectCounter = new AtomicInteger(0);
	private static AtomicInteger clientCounter = new AtomicInteger(0);

	public static int getNextEmployee() {
		return employeeCounter.incrementAndGet();
	}

	public static int getNextProjectId() {
		return projectCounter.incrementAndGet();
	}

	public static int getNextClientId() {
		return clientCounter.incrementAndGet();
	}
}
