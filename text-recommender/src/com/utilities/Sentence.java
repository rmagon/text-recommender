package com.utilities;

import java.util.ArrayList;

/*
 * rawSen: is the rawSentence which is the direct input from the file
 * proSen: is the pre-processed Sentence with stop words removed, stemming done etc
 * vector: this is a vector which should be of the size of the Bag-of-Words dictionary in the SimilarityMatrix Class
 */
public class Sentence {
	private String rawSen = "", proSen = "";
	private ArrayList<Integer> vector;

	public String[] getArrayRawSen()
	{
		return rawSen.split("[[ ]*|[,]*|[\\.]*|[:]*|[/]*|[!]*|[?]*|[+]*]+");
	}
	/*
	 * Set both the sentences at the time of object creation
	 */
	public Sentence(String rawSen2) {
		rawSen = rawSen2;
	}
	
	/*
	 * Helper Function to Calculate Vector Sum
	 */
	public int vectorSum()
	{
		int sum =0;
		for(int i=0;i<vector.size();i++)
		{
			sum += vector.get(i);
		}
		return sum;
	}
	
	/*
	 * Helper Function to calculate square sum root
	 */
	public double vectorSquareSumRoot()
	{
		double sum =0;
		for(int i=0;i<vector.size();i++)
		{
			sum += (vector.get(i)*vector.get(i));
		}
		return Math.sqrt(sum);
	}
	
	/*
	 * Only return the vector if it is not null
	 * To use the vector first call initVector and initialize it
	 */
	public ArrayList<Integer> getVector() {
		if (vector != null) {
			return vector;
		} else {
			System.out
					.println("Error accessing vector in Sentence class for rawSen= '"
							+ rawSen + "'\n");
			return null;
		}
	}
	
	public void printVector()
	{
		for(int i=0;i<vector.size();i++){
			System.out.print(vector.get(i) + "\t");
		}
		System.out.println();
	}
	public void setVector(ArrayList<Integer> vector) {
		this.vector = vector;
	}

	/*
	 * Initialize the vector with 0s
	 */
	public void initVector(int n) {
		vector = new ArrayList<Integer>(n);
		for(int i=0;i<n;i++)
		vector.add(0);
	}

	public String getRawSen() {
		return rawSen;
	}

	public void setRawSen(String rawSen) {
		this.rawSen = rawSen;
	}

	public String getProSen() {
		return proSen;
	}

	public void setProSen(String proSen) {
		this.proSen = proSen;
	}

}
