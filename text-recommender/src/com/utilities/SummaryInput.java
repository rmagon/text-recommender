package com.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.AbstractList;
import java.util.Collections;
import java.util.StringTokenizer;

import com.textrecommender.slist;

/**
 * @author Rachit
 * Process Flow
 * 1. main() calls calculateSummary() - Will input the data from two files
 * 2. calculateSummary() will call fillSentences(string,string)
 * 3. fillSentences() will fill SimilarityMatrix for Raw/Proc sentences 
 * 4. fillSentences() also fills the HashMap for the document through addToDictionary()
 * 5. calculateSummary() calls createSentenceVectors()
 */
public class SummaryInput {

	SimilarityMatrix document = new SimilarityMatrix();

	/*
	 * Will create vectors for all Sentences according to the HashMap
	 * CAUTION: document.dictionary should be built before calling this function
	 * DOUBLE CAUTION: do not change document.dictionary after calling this function
	 */
	private void createSentenceVectors()
	{
		//document.printDictionary();
		int noOfUniqueWords = document.getDictionary().size();
		int noOfSentences = document.getArrSen().size();
		for(int i=0;i<noOfSentences;i++)
		{
			Sentence sentObj = document.getArrSen().get(i);
			sentObj.initVector(noOfUniqueWords);
			
			String sen = sentObj.getProSen();
			StringTokenizer stk = new StringTokenizer(sen, " ");
			
			while (stk.hasMoreElements()) {
				String tok = (String) stk.nextElement();
				if(tok.endsWith("."))
				{
					tok = tok.substring(0, tok.length()-1);
				}
				tok = tok.trim();
				if (document.getDictionary().contains(tok))
				{
					int indexOfToken = document.getDictionary().indexOf(tok);
					int presentValue = sentObj.getVector().get(indexOfToken);
					sentObj.getVector().set(indexOfToken, presentValue+1);
				}
			}
			sentObj.printVector();
			
		}
	}
	
	/*
	 * This function is called whenever a new Processed Sentence is encountered
	 */
	private void addToDictionary(String sen) {
		StringTokenizer stk = new StringTokenizer(sen, " ");
		while (stk.hasMoreElements()) {
			String tok = (String) stk.nextElement();
			if(tok.endsWith("."))
			{
				tok = tok.substring(0, tok.length()-1);
			}
			tok = tok.trim();
			
			if (!document.getDictionary().contains(tok) && tok.length() >= 3) {
				document.getDictionary().add(tok);
			} 
		}
	}

	/*
	 * Fill all sentences in the SimilarityMatrix object called document Fills
	 * Raw as well as Processed at the same index
	 */
	private void fillSentences(String input1, String input2) {
		int fs1 = 0, fs2 = 0; // fs2 is the fseek counter
		int nx = 0; // gets the index of full stop '.'

		// while index of '.' is less than the length of the string
		while (nx < (input1.length() - 1)) {
			nx = input1.indexOf(".", fs2); // find the new index
			if (nx == -1) // if new index -1 that means done
				break;
			else if (nx == input1.lastIndexOf(".")) { // if this is the last '.'
														// then all the rest is
														// a sentence
				String str = (input1.substring(fs1, nx + 1)).toLowerCase();
				// System.out.println("RAW NEW SENTENCE LASTINDEX: " + str);
				document.addRawSentence(str);
				fs2 = nx + 1;
				fs1 = fs2;
				break;
			} else if (input1.charAt(nx + 1) == ' '
					|| input1.charAt(nx + 1) == '\r'
					|| input1.charAt(nx + 1) == '\n'
					|| input1.charAt(nx + 1) == '\t') {
				String str = (input1.substring(fs1, nx + 1).toLowerCase())
						.trim();
				// System.out.println("RAW NEW SENTENCE ENLSEIF: " + str);
				document.addRawSentence(str);
				
				fs2 = nx + 1;
				fs1 = fs2;
			} else
				// nothing interesting found move forward
				fs2 = nx + 1;
		}

		fs1 = 0;
		fs2 = 0;
		nx = 0;
		int index = 0;
		while (nx < (input2.length() - 1)) {
			nx = input2.indexOf(".", fs2); // find the new index
			if (nx == -1) // if new index -1 that means done
				break;
			else if (nx == input2.lastIndexOf(".")) { // if this is the last '.'
														// then all the rest is
														// a sentence
				String str = (input2.substring(fs1, nx + 1)).toLowerCase();
				// System.out.println("PRO NEW SENTENCE LASTINDEX: " + str);
				document.addProcSentence(index++, str);
				this.addToDictionary(str);
				fs2 = nx + 1;
				fs1 = fs2;
				break;
			} else if (input2.charAt(nx + 1) == ' '
					|| input2.charAt(nx + 1) == '\r'
					|| input2.charAt(nx + 1) == '\n'
					|| input1.charAt(nx + 1) == '\t') { // I don't understand
														// this but it is
														// working
				String str = (input2.substring(fs1, nx + 1).toLowerCase())
						.trim();
				// System.out.println("PRO NEW SENTENCE ELSEIF: " + str);
				document.addProcSentence(index++, str);
				this.addToDictionary(str);
				fs2 = nx + 1;
				fs1 = fs2;
			} else
				// nothing interesting found move forward
				fs2 = nx + 1;
		}
		
		
		
	}

	/*
	 * Basically the most important function which will do all the work The main
	 * function will call this function with two file names
	 */
	private void calculateSummary(String rawFileName, String preProcessedFileName,String file) {
		int sz;
		byte bt[];
		String rawContent = "", procContent = "";

		// first input the raw file
		try {
			File fp1 = new File(rawFileName);
			FileInputStream fis = new FileInputStream(fp1);
			sz = (int) fp1.length();
			bt = new byte[sz];
			fis.read(bt);
			rawContent = new String(bt);
		} catch (IOException ex) {
			System.out.println("--ERROR READING RAWFILE--\n" + ex);
		}

		// now get the processed file
		try {
			File fp2 = new File(preProcessedFileName);
			FileInputStream fis = new FileInputStream(fp2);
			sz = (int) fp2.length();
			bt = new byte[sz];
			fis.read(bt);
			procContent = new String(bt);
		} catch (IOException ex) {
			System.out.println("--ERROR READING PREPROCESSED FILE--\n" + ex);
		}

		this.fillSentences(rawContent, procContent);
		
		document.printBothSentences();
		Collections.sort(document.getDictionary()); //Sort the Bag of Words
		document.printDictionary();
		
		this.createSentenceVectors(); //create vectors for every sentence
		
		document.initSimilarity(); //initialize the similarity matrix to all 0s
		CosineSimilarity.findCosineSimilairty(document);  //fidn the similarity matrix
		document.printSimilarity();
		
		int noOfSentencesNeeded = 4;
		int aa[][] = CosineSimilarity.getTopSentences(noOfSentencesNeeded, document);
		
		String summaryRAW = "";
		String summaryPROC = "";
		for(int i=0;i<noOfSentencesNeeded;i++)
		{
			System.out.println(aa[i][0] + ": "+ document.getArrSen().get(aa[i][0]).getRawSen());
			summaryRAW = summaryRAW + document.getArrSen().get(aa[i][0]).getRawSen();
			summaryPROC = summaryPROC + document.getArrSen().get(aa[i][0]).getProSen();
		}
		
		DatabaseHelper dbHelp = new DatabaseHelper();
		dbHelp.insertHotelReview(file, summaryRAW, summaryPROC);
	}

	public static void main(String[] args) {
		SummaryInput sum = new SummaryInput();
		sum.calculateSummary("G://hotels//beijing//china_beijing_ascott_beijing",
				"G://hotels//beijing//china_beijing_ascott_beijing_preprocessed.txt","china_beijing_ascott_beijing");

	}

}
