package com.marketstock.sebiapplication.models;

public class CompanyPrice {
	String company;
	float price;
	float weight;
	float percentChange;
	public CompanyPrice(String com, float p,float w,float pc){
		this.company = com;
		this.price = p;
		this.weight = w;
		this.percentChange = pc;
	}
	
	public String getCompany(){
		return this.company;
	}
	
	public float getPrice(){
		return this.price;
	}
	
	public void setPrice(float f){
		this.price = f;
	}
	
	public void setCompany(String c){
		this.company = c;
	}
}
