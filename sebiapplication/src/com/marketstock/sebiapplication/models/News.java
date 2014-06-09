package com.marketstock.sebiapplication.models;

public class News {

	int id;
	String title;
	String desc;
	String learning;
	String effect;
	String fluctuation;
	int day;

	public News() {

	}

	public News(String t, String d, String l, String e, String f,int da) {
		this.title = t;
		this.desc = d;
		this.learning = l;
		this.effect = e;
		this.fluctuation = f;
		this.day = da;
	}

	public String getTitle() {
		return this.title;
	}

	public String getDesc() {
		return this.desc;
	}

	public String getLearning() {
		return this.learning;
	}

	public String getEffect() {
		return this.effect;
	}

	public String getFlucatuation() {
		return this.fluctuation;
	}
	public int getDay(){
		return this.day;
	}
}
