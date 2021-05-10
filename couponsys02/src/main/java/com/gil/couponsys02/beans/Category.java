package com.gil.couponsys02.beans;

public enum Category {
	FOOD ("Fast Food", "Chef", "Asian", "Home", "American"), 
	ELECTRICITY ("Lighting", "AC", "Computers", "Smartphones", "Televisions"), 
	SPORT("Basketball", "Football", "Jym", "Olympic", "Diving"), 
	VACATION ("Hotels", "Flights", "Air BnB","Spa", "Baths"),
	ENTERTAINMENT("Concerts", "Movies", "Museums", "Parks", "Tours");
	
	private String[] titles;

	private Category(String... titles) {
		this.titles = titles;
	}
	
	public String getTitle(int index) {
		return titles[index];
	}
	
	public int titlesSize() {
		return titles.length;
	}
	
	
	
	
}
