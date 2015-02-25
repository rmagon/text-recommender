package com.utilities;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TreeMap;

import com.textrecommender.wlist;

/*
 * This class has
 * 1. An ArrayList of all the Sentence in the document
 * 2. A HashMap which is the Bag-Of-Words Dictionary for the document
 */
public class SimilarityMatrix {
	//this will store all the Sentences
	private ArrayList<Sentence> arrSen;
	
	//this is the Bag-Of-Words for the whole document
	private TreeMap<String, Integer> dictionary;
	
	//number of Sentences
	private Integer noOfSen;
	
	//A two dimensional similarity matrix indexed by arrSen Sentences
	private Double similarity[][];
	
	public SimilarityMatrix()
	{
		arrSen = new ArrayList<Sentence>();
		dictionary = new TreeMap<String, Integer>();
	}
	
	/*
	 * Add a new sentence to arrSen
	 */
	public void addRawSentence(String rawSen) {
		arrSen.add(new Sentence(rawSen));
	}
	
	/*
	 * CAUTION: index SHOULD EXISTS, OTHERWISE ERROR
	 */
	public void addProcSentence(int index,String procSen)
	{
		try
		{
			arrSen.get(index).setProSen(procSen);
		}
		catch(Exception ex)
		{
			System.out.println("--ERROR IN addProcSentence() in SimilarityMatrix --\n" + ex);
		}
	}
	
	/*
	 * Need to call this after every Sentence is added
	 * It will automatically initialize the similarity matrix to NxN
	 * where N is the number of sentences
	 */
	public void initSimilarity() {
		noOfSen = arrSen.size();
		similarity = new Double[noOfSen][noOfSen];
	}
	
	public ArrayList<Sentence> getArrSen() {
		return arrSen;
	}

	public void setArrSen(ArrayList<Sentence> arrSen) {
		this.arrSen = arrSen;
	}

	public TreeMap<String, Integer> getDictionary() {
		return dictionary;
	}

	public void setDictionary(TreeMap<String, Integer> dictionary) {
		this.dictionary = dictionary;
	}

	public Integer getNoOfSen() {
		return noOfSen;
	}

	public void setNoOfSen(Integer noOfSen) {
		this.noOfSen = noOfSen;
	}

	public Double[][] getSimilarity() {
		return similarity;
	}
	
	public void setSimilarity(Double[][] similarity) {
		this.similarity = similarity;
	}

	/*
	 * Print Utility
	 */
	public void printBothSentences()
	{
		for(int i=0;i<arrSen.size();i++)
		{
			System.out.println(i + " RAW " + arrSen.get(i).getRawSen());
			System.out.println(i + " PRC " + arrSen.get(i).getProSen());
		}
	}
	
	public void printDictionary()
	{
		Set<String> keys = dictionary.keySet();
	
		   for (Iterator<String> i = keys.iterator(); i.hasNext();) 
		   {
		       String key = (String) i.next();
		       Integer value = (Integer) dictionary.get(key);
		       System.out.println(key + " = " + value);
		   }
	}
	
}
