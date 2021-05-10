package com.gil.couponsys02.clr.primary;

public enum CompanyDummy {
	APPLE("apple@apple.com", "hailstevejobs"),
	NIKE("nike@niee.com", "justdoit"),
	FACEBOOK("facebook@fb.com", "markistheman123"),
	AMAZON("amazon@amazon.uk", "sbezo$$$"),
	ALIEXPRESS("ali@express.ch", "qawsed"),
	ASOS("asos@asos.uk", "q1w2e3r4"),
	EMI("emi@music.uk", "fab4four"),
	HP("hp@techhp.com", "h123456p"),
	KFC("kfc@kfc.com", "colonelt"),
	EBAY("ebay@ebay.com", "2ndhandstuff");
	
	private String email;
	private String password;
	
	CompanyDummy(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
}
