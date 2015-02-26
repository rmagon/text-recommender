package com.utilities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

public class HotelUtility {

	public static void printHotelCityName(ArrayList<Hotel> h)
	{
		for(int i=0;i<h.size();i++)
		{
			System.out.println(h.get(i).getCity() + "\t" + h.get(i).getName());
		}
	}
	
	public static void printDictionary(Hotel h)
	{
		System.out.println("------- Printing list for " + h.getName());
		Set<String> keys = h.termsList.keySet();
		   for (Iterator<String> i = keys.iterator(); i.hasNext();) 
		   {
		       String key = (String) i.next();
		       Integer value = (Integer) h.termsList.get(key);
		       System.out.println(key + " = " + value);
		   }
	}
	
	public static void printTreeMap(TreeMap<String,Integer> h,String treeMapName)
	{
		System.out.println("------- Printing TreeMap -" + treeMapName);
		Set<String> keys = h.keySet();
		   for (Iterator<String> i = keys.iterator(); i.hasNext();) 
		   {
		       String key = (String) i.next();
		       Integer value = (Integer) h.get(key);
		       System.out.println(key + " = " + value);
		   }
	}
}
