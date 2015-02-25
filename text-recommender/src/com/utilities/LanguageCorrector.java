package com.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * @author Rachit
 *
 */
public class LanguageCorrector {

	private static HashMap<String, String> dictionary;

	// Will Load the dictionary.txt into the main memory HashMap called
	// dictionary
	public LanguageCorrector() {
		if (dictionary == null) {
			dictionary = new HashMap<String, String>();
			Scanner scanner = null;
			try {
				scanner = new Scanner(new File("G://sourcefiles//dictionary.txt"));
			} catch (FileNotFoundException e) {
				System.out
						.println("WARNING - DID NOT FIND FILE DICTIONARY.TXT");
				e.printStackTrace();
			}
			while (scanner.hasNextLine()) {
				String[] temp = scanner.nextLine().split(":");
				dictionary.put(temp[0], temp[1]);
			}
			scanner.close();
		}
	}

	private String checkInDictionary(String s1) {
		if (dictionary.get(s1) != null) {
			return dictionary.get(s1);
		} else {
			return s1;
		}
	}
	
	private String[] getTokens(String sen) {
		int sz = 0, cnt = 0;
		String words[] = null;
		StringTokenizer stk = new StringTokenizer(sen);
		sz = stk.countTokens();
		words = new String[sz];
		while (stk.hasMoreTokens()) {
			words[cnt] = new String(stk.nextToken());
			cnt++;
		}
		return words;
	}
	
	/*
	 * gr8 -> great
	 * Will preserve full stop
	 */
	public String correctString(String sen) {
		String words[] = getTokens(sen);
		String stemmedSen = "";
		for (int j = 0; j < words.length; j++) {
				stemmedSen = stemmedSen + checkInDictionary(words[j]) + " ";
		}

		return stemmedSen;
	}

}
