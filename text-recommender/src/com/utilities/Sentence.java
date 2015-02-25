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

	/*
	 * Set both the sentences at the time of object creation
	 */
	public Sentence(String rawSen2) {
		rawSen = rawSen2;
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

	public void setVector(ArrayList<Integer> vector) {
		this.vector = vector;
	}

	public void initVector(int n) {
		vector = new ArrayList<Integer>(n);
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
