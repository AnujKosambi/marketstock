package com.marketstock.adapter;

public class CardItemData
{
	private String tips;
	

	public CardItemData(String text1)
	{
		tips = text1.replaceAll("//", "'");
		
	}

	public String getText1()
	{
		return tips;
	}
	
}
