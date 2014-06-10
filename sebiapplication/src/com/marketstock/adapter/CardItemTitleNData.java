package com.marketstock.adapter;

public class CardItemTitleNData
{
	private String title,data,learning;
	private int day;

	public CardItemTitleNData(int day,String title,String data,String learning)
	{
		this.title=title;
		this.data=data;
		this.learning=learning;
		this.day=day;
	}
	

	public String getData()
	{
		return data;
		
	}
	public String getTitle()
	{
		return title;
		
	}
	public String getLearning()
	{
		return learning;
		
	}
	public int getDay()
	{
		return day;
		
	}
	
	
}
