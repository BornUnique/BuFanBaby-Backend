package com.bufanbaby.backend.rest;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Test;

public class TestSystemTime {

	@Test
	public void test() {
		System.currentTimeMillis();
		long time = Instant.now().toEpochMilli();

		ZonedDateTime zdt = ZonedDateTime.ofInstant(Instant.ofEpochMilli(time),
				ZoneId.systemDefault());

		System.out.println(zdt);

		ZonedDateTime zdt1 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(time),
				ZoneId.of("Asia/Tokyo"));

		System.out.println(zdt1);

		ZonedDateTime zdt2 = ZonedDateTime.ofInstant(Instant.ofEpochMilli(time),
				ZoneId.of("Asia/Chongqing"));

		System.out.println(zdt2);
	}

}
