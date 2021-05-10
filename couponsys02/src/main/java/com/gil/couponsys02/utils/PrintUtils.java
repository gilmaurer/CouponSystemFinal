package com.gil.couponsys02.utils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.gil.couponsys02.beans.Company;
import com.gil.couponsys02.beans.Coupon;

@Component
public class PrintUtils {
	private  CommandLineTable printTable = new CommandLineTable();

	public  <T> void printTable(List<T> list, Class cls) throws IllegalArgumentException, IllegalAccessException {
		tableTitle(cls.getSimpleName());
		Field[] fields = cls.getDeclaredFields();
		printTable.setHeaders(getHeaders(cls, fields));
		for (int i = 0; i < list.size(); i++) {
			printTable.addRow(getValues(fields, list.get(i)));
		}
		printTable.print();
		System.out.println();
	}

	private  String[] getHeaders(Class cls, Field[] fields) {
		String[] headers = new String[fields.length];
		for (int i = 0; i < headers.length; i++) {
			headers[i] = fields[i].getName();
		}
		return headers;
	}

	private  <T> String[] getValues(Field[] fields, T obj) throws IllegalArgumentException, IllegalAccessException {
		String[] values = new String[fields.length];
		for (int i = 0; i < values.length; i++) {
			fields[i].setAccessible(true);
			Object value = fields[i].get(obj);
			if(value instanceof List) {
				value = ((List<Coupon>) value).stream().mapToInt(Coupon::getId).boxed().collect(Collectors.toList());
			} else if(value instanceof Company) {
				value = ((Company) value).getId();
			}
			values[i] = value.toString();
		}

		return values;
	}
	
	public void printArt(String title) {
		System.out.println(title);
	}
	
	private void tableTitle(String tableTitle) {
		if(tableTitle.endsWith("y")) tableTitle = tableTitle.replace("y", "ies");
		else tableTitle = tableTitle + "s";
		System.out.println("************ "+ tableTitle + " ************");
	}
	
	public void testTitle(String testTitle, boolean testSuccess, int testNumber) {
		String type = testSuccess ? "Succsees" : "Exception";
		System.out.println("------------------------------------------------------------------------------------");
		System.out.println("         	 Test #" + testNumber + ": " + testTitle + " (" + type + ")                  ");
		System.out.println("------------------------------------------------------------------------------------");
	}
	
	public void result(String test) {
		System.out.println(test);
		System.out.println();
	}
	
	public void exception(String exception) {
		System.err.println(exception);
		System.out.println();
	}

}
