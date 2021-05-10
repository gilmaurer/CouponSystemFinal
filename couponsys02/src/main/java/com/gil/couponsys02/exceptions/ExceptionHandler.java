package com.gil.couponsys02.exceptions;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.gil.couponsys02.utils.DateUtils;
import com.gil.couponsys02.utils.PrintUtils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExceptionHandler {
	
	private final PrintUtils print;
	
	public void handle(Exception e) {
		StringBuilder sb = new StringBuilder();
		sb.append(DateUtils.dateFormat.format(new Date()));
		sb.append(": ");
		sb.append(e.getClass().getSimpleName());
		sb.append(" - ");
		sb.append(e.getMessage());
		try {
			Thread.sleep(800);
		} catch (InterruptedException e1) {
			System.err.println(e1.getMessage());
		}
		print.exception(sb.toString());
	}
}
