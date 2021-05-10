package com.gil.couponsys02.clr.primary;

public enum CouponDummy {
	//COUPON(category_id, title, year, month, day, amount, price)
		COUPON_1( 0, 0, 2021, 4, 19, 11, 53.99),
		COUPON_2( 1, 4, 2021, 2, 2, 23, 10.99),
		COUPON_3( 3, 3, 2020, 7, 4, 4, 15.99),
		COUPON_4( 4, 0, 2019, 3, 3, 18, 20.99),
		COUPON_5( 1, 2, 2021, 1, 1, 42, 30.99),
		COUPON_6( 2, 4, 2018, 9, 3, 21, 5.99),
		COUPON_7( 4, 1, 2020, 3, 15, 4, 39.99),
		COUPON_8( 0, 3, 2021, 12, 31, 0, 100.99),
		COUPON_9( 3, 3, 2020, 10, 5, 25, 42.99),
		COUPON_10( 4, 1, 2019, 8, 30, 6, 12.99),
		
		COUPON_TEST(4, 1, 2021, 5, 12, 1, 45.99);
		
		private int categoryId;
		private int title;
		private int year;
		private int month;
		private int day;
		private int amount;
		private double price;
		
		CouponDummy(int categoryId, int title, int year, int month, int day, int amount, double price) {
			this.categoryId = categoryId;
			this.title = title;
			this.year = year;
			this.month = month;
			this.day = day;
			this.amount = amount;
			this.price = price;
		}

		public int getCategoryId() {
			return categoryId;
		}

		public int getTitle() {
			return title;
		}

		public int getYear() {
			return year;
		}

		public int getMonth() {
			return month;
		}

		public int getDay() {
			return day;
		}

		public int getAmount() {
			return amount;
		}

		public double getPrice() {
			return price;
		}
}
