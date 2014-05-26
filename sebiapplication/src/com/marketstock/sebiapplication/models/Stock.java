package com.marketstock.sebiapplication.models;

import java.sql.Date;

public class Stock {
	
	int id;
	Date date;
	Double openPrice;
	Double closePrice;
	Double highPrice;
	Double lowPrice;
	Double volume;

	public Stock() {

	}

	public Stock(Date date, Double openPrice, Double closePrice,
			Double highPrice, Double lowPrice, Double volume) {
				
		this.date = date;
		this.openPrice = openPrice;
		this.closePrice = closePrice;
		this.highPrice = highPrice;
		this.lowPrice = lowPrice;
		this.volume = volume;
	}

	public Date getDate() {
		return this.date;
	}

	public Double getOpenPrice() {
		return this.openPrice;
	}

	public Double getClosePrice() {
		return this.closePrice;
	}

	public Double getHighPrice() {
		return this.highPrice;
	}

	public Double getLowPrice() {
		return this.lowPrice;
	}

	public Double getVolume() {
		return this.volume;
	}
}
