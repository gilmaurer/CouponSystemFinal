package com.gil.couponsys02.clr.primary;

public enum CustomerDummy {
	GIL("Gil", "Maurer", "gil@maurer.com", "gilmaupass"),
	MICHAELA("Michaela", "Sobelmann", "michaella@sobelmann.com", "michalasobpass"),
	EITAN("Eitan", "Arava", "eitan@arava.com", "eitanatapass"),
	ERIC("Eric", "Sobelmann", "eric@sobelmann.com", "ericsobpass"),
	BRANDA("Branda", "Shteinberg", "branda@sh.com", "brandashpass"),
	SVETLANA("Svetlana", "Rosenblatt", "svetlana@rose.com", "svetrosepass"),
	FLORIA("Floria", "Ginzburg", "floria@ginz.com", "floriapass"),
	OHAD("Ohad", "Maurer", "ohad@maurer.com", "ohadmaupass"),
	RONIT("Ronit", "Shabi", "ronit@shabi.com", "ronitshabipass"),
	DANA("Dana", "Sobelmann", "dana@sobelmann.com", "danasobelpass"),
	IDAN_S("Idan", "Sobelmann", "idan@sobelmann.com", "idansobelpass"),
	IDAN_M("Idan", "Maurer", "idan@sobelmann.com", "idanmaupass"),
	RAN("Ran", "Shabi", "ran@shabi.com", "ranshabipass");
	
	private String firsName;
	private String lastName;
	private String email;
	private String password;
	
	private CustomerDummy(String firsName, String lastName, String email, String password) {
		this.firsName = firsName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public String getFirsName() {
		return firsName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
}
