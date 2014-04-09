package com.example.homework311bjs0000;

public class ArticleItem {
	ArticleItem () {
		content = "";
		icon = "";
		title = "";
		date = "";
		
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		return "ArticleItem [content=" + content + ", icon=" + icon
				+ ", title=" + title + ", date=" + date + "]";
	}

	private String content;
	private String icon;
	private String title;
	private String date;

}
