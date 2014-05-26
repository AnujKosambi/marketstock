package com.example.sebiapplication.models;

public class News {

	String title;
	String desc;
	String learning;
	String effect;
	String fluctuation;

	public News() {

	}

	public News(String t, String d, String l, String e, String f) {
		this.title = t;
		this.desc = d;
		this.learning = l;
		this.effect = e;
		this.fluctuation = f;
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
}
