package com.marketstock.adapter;

public class CardItemTitleNData
{
	private String title,data,learning;
	

	public CardItemTitleNData(String title,String data,String learning)
	{
		this.title=title;
		this.data=data;
		this.learning=learning;
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
	
	
}
