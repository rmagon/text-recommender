package com.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.AbstractList;
import java.util.StringTokenizer;

import com.textrecommender.slist;

/**
 * @author Rachit
 * Process Flow
 * 1. main() calls inputData() - Will input the data from two files
 * 2. inputData() will call fillSentences(string,string)
 * 3. fillSentences() will fill SimilarityMatrix for Raw/Proc sentences 
 * 4. fillSentences() also fills the HashMap for the document through addToDictionary()
 * 5. inputData() calls createSentenceVectors()
 */
public class SummaryInput {

	SimilarityMatrix document = new SimilarityMatrix();

	/*
	 * Will create vectors for all Sentences according to the HashMap
	 * CAUTION: the HashMap document.dictionary should be built before calling this function
	 */
	private void createSentenceVectors()
	{
		int noOfSen = document.getArrSen().size();
		for(int i=0;i<noOfSen;i++)
		{
			document.getArrSen().get(i).initVector(noOfSen);
			String sen = document.getArrSen().get(i).getProSen();
			StringTokenizer stk = new StringTokenizer(sen, " ");
			while (stk.hasMoreElements()) {
				String tok = (String) stk.nextElement();
				if(tok.endsWith("."))
				{
					tok = tok.substring(0, tok.length()-1);
				}
				tok = tok.trim();
				if (document.getDictionary().containsKey(tok))
				{
					
				}
			}
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
			
			if (!document.getDictionary().containsKey(tok) && tok.length() >= 3) {
				document.getDictionary().put(tok, 1);
			} else if (document.getDictionary().containsKey(tok)) {
				Integer wc = document.getDictionary().get(tok);
				document.getDictionary().put(tok, wc+1);
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
					|| input1.charAt(nx + 1) == '\n') {
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
					|| input2.charAt(nx + 1) == '\n') { // I don't understand
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
		//document.printBothSentences();
		document.printDictionary();
	}

	/*
	 * Basically the most important function which will do all the work The main
	 * function will call this function with two file names
	 */
	private void inputData(String rawFileName, String preProcessedFileName) {
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
		
	}

	public static void main(String[] args) {
		SummaryInput sum = new SummaryInput();
		sum.inputData("G://sourcefiles//1.txt",
				"G://sourcefiles//1_preprocessed.txt");

	}

}
