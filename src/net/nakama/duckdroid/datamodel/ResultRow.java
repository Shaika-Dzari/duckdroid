/*
 * ResultRow.java
 * 
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. 
 * See http://sam.zoy.org/wtfpl/COPYING for more details. 
 * 
 * @since 2012-10-06
 */ 
package net.nakama.duckdroid.datamodel;

public class ResultRow {
	
	public String text;
	public String url;
	
	public ResultRow(String text, String url) {
		this.text = text;
		this.url = url;
	}

}
