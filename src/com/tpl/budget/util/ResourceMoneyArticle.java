package com.tpl.budget.util;

public class ResourceMoneyArticle
{
	private String _id;
	private String _title;
	private String _date;
	private String _des;
	private String _link;
	private String _author;
	public ResourceMoneyArticle()
	{
		_id="";
		_title ="";
		_date ="";
		_des ="";
		_link="";
		_author="";
	}
	public ResourceMoneyArticle(String title, String date,String des,String link)
	{
		this._title = title;
		this._date 	= date;
		this._des = des;
		this._link = link;
		
	}
	public String getId()
	{
		return _id;
	}

	public void setId(String id)
	{
		this._id = id;
	}
	public String getTitle()
	{
		return _title;
	}

	public void setTitle(String title)
	{
		this._title = title;
	}
	public String getDate()
	{
		return _date;
	}
	public void setDate(String date)
	{
		this._date = date;
	}	
	public void setDes(String des)
	{
		this._des = des;
	}
	public String getDes()
	{
		return _des;
	}	
	public void setLink(String link)
	{
		this._link = link;
	}
	public String getLink()
	{
		return _link;
	}	
	
	public String getAuthor()
	{
		return _author;
	}	
	public void setAuthor(String author)
	{
		this._author = author;
	}	
}
