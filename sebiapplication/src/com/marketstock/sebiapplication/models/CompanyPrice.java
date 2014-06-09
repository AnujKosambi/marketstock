package com.marketstock.sebiapplication.models;

public class CompanyPrice {
	String company;
	float price;
	
	public CompanyPrice(String com, float p){
		this.company = com;
		this.price = p;
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
