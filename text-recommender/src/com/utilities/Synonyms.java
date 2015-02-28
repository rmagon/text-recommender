/* Daniel Shiffman               */
/* Programming from A to Z       */
/* Spring 2008                   */
/* http://www.shiffman.net       */
/* daniel.shiffman@nyu.edu       */

/* Picks a random noun and lists synonyms
 * Example directly adapted from Daniel Howe's
 * http://www.rednoise.org/rita/examples/RWN_Syns_Ex1/RWN_Syns_Ex1.pde
 */

package com.utilities;

import java.io.IOException;
import java.util.Arrays;

import edu.smu.tspell.wordnet.NounSynset;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.WordNetDatabase;
import edu.smu.tspell.wordnet.impl.file.ReferenceSynset;

//import rita.wordnet.*;
//import rita.*;

public class Synonyms {

	WordNetDatabase database;
	public Synonyms()
	{
		System.setProperty("wordnet.database.dir", "C:\\WordNet\\2.1\\dict");
		database = WordNetDatabase.getFileInstance();
	}
	public String getSynonyms(String word)
	{
		NounSynset nounSynset;
		Synset[] synsets = database.getSynsets(word, SynsetType.NOUN);
		if(synsets.length==0)
			return word;
		else
		{
			nounSynset = (NounSynset)(synsets[0]);
			String s[] = nounSynset.getWordForms();
			if(s!=null)
			return s[0];
			else
				return word;
			
		}
		
		
	}
	
	
	public static void main(String[] args) throws IOException {
		
		System.setProperty("wordnet.database.dir", "C:\\WordNet\\2.1\\dict");
		//ReferenceSynset r = new ReferenceSynset(SynsetType.NOUN,"fly",null,null,null,10,10);
		NounSynset nounSynset; 
		NounSynset[] hyponyms; 

		WordNetDatabase database = WordNetDatabase.getFileInstance(); 
		Synset[] synsets = database.getSynsets("gain", SynsetType.NOUN); 
		for (int i = 0; i < 1; i++) { 
			System.out.print(synsets.length);
		    nounSynset = (NounSynset)(synsets[i]); 
		    for(String s:nounSynset.getWordForms())
		    {
		    	System.out.println(s);
		    }
		    
		  //  hyponyms = nounSynset.getHyponyms(); 
		    //System.err.println(nounSynset.getWordForms()[0] + 
		      //      ": " + nounSynset.getDefinition() + ") has " + hyponyms.length + " hyponyms"); 
		}
	}
}


