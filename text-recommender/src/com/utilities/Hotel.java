package com.utilities;

import java.util.TreeMap;

public class Hotel {
private String city = "";
private String name = "";
private String rawSummary = "";
private String proSummary = "";
public TreeMap<String,Integer> termsList = new TreeMap<String,Integer>();

public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getRawSummary() {
	return rawSummary;
}
public void setRawSummary(String rawSummary) {
	this.rawSummary = rawSummary;
}
public String getProSummary() {
	return proSummary;
}
public void setProSummary(String proSummary) {
	this.proSummary = proSummary;
}


}
