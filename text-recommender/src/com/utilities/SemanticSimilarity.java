package com.utilities;

import java.util.List;

import edu.cmu.lti.jawjaw.pobj.POS;
import edu.cmu.lti.lexical_db.ILexicalDatabase;
import edu.cmu.lti.lexical_db.NictWordNet;
import edu.cmu.lti.lexical_db.data.Concept;
import edu.cmu.lti.ws4j.Relatedness;
import edu.cmu.lti.ws4j.RelatednessCalculator;
import edu.cmu.lti.ws4j.impl.HirstStOnge;
import edu.cmu.lti.ws4j.impl.JiangConrath;
import edu.cmu.lti.ws4j.impl.LeacockChodorow;
import edu.cmu.lti.ws4j.impl.Lesk;
import edu.cmu.lti.ws4j.impl.Lin;
import edu.cmu.lti.ws4j.impl.Path;
import edu.cmu.lti.ws4j.impl.Resnik;
import edu.cmu.lti.ws4j.impl.WuPalmer;
import edu.cmu.lti.ws4j.util.WS4JConfiguration;

public class SemanticSimilarity {

	private static ILexicalDatabase db = new NictWordNet();
    /*private static RelatednessCalculator[] rcs = {
                    new HirstStOnge(db), new LeacockChodorow(db), new Lesk(db),  new WuPalmer(db), 
                    new Resnik(db), new JiangConrath(db), new Lin(db), new Path(db)
                    };
    private static void run( String word1, String word2 ) {
            WS4JConfiguration.getInstance().setMFS(true);
            for ( RelatednessCalculator rc : rcs ) {
                    double s = rc.calcRelatednessOfWords(word1, word2);
                    System.out.println( rc.getClass().getName()+"\t"+s );
            }
    }
	
    private static double runMy( String word1, String word2 ) {
        WS4JConfiguration.getInstance().setMFS(true);
        RelatednessCalculator rc = new Lesk(db);
                double s = rc.calcRelatednessOfWords(word1, word2);
                return s;
        
}*/

    /*
     * This gets the maximum of ALL POS PAIRS
     * http://stackoverflow.com/questions/17750234/ws4j-returns-infinity-for-similarity-measures-that-should-return-1
     */
    private static double calcMAXScore(String word1,String word2)
    {
    	ILexicalDatabase db = new NictWordNet();
    	WS4JConfiguration.getInstance().setMFS(true);
    	RelatednessCalculator rc = new WuPalmer(db);
    	List<POS[]> posPairs = rc.getPOSPairs();
    	double maxScore = -1D;

    	for(POS[] posPair: posPairs) {
    	    List<Concept> synsets1 = (List<Concept>)db.getAllConcepts(word1, posPair[0].toString());
    	    List<Concept> synsets2 = (List<Concept>)db.getAllConcepts(word2, posPair[1].toString());

    	    for(Concept synset1: synsets1) {
    	        for (Concept synset2: synsets2) {
    	            Relatedness relatedness = rc.calcRelatednessOfSynset(synset1, synset2);
    	            double score = relatedness.getScore();
    	            if (score > maxScore) { 
    	                maxScore = score;
    	            }
    	        }
    	    }
    	}

    	if (maxScore == -1D) {
    	    maxScore = 0.0;
    	}
    	return maxScore;
    	//System.out.println("sim('" + word1 + "', '" + word2 + "') =  " + maxScore);
    }
    
    /*
     * Compares two sentences and calculates the semantic similarity
     */
	private static double runSingle(String[] sen1, String[] sen2)
	{
		double sum = 0;
		//System.out.println("PRINTING SEMANTIC SIMILARITY MATRIX---");
		for(int i=0;i<sen1.length;i++)
		{
			for(int j=0;j<sen2.length;j++)
			{
				double element = calcMAXScore(sen1[i],sen2[j]);
		//		System.out.print(String.format( "%.2f",element ) +" ");
				sum += element;
			}
		//	System.out.println();
		}
		//System.out.println("END OF SIMILARITY MATRIX---");
		return sum;
	}
	
    public static void main(String[] args) {
            long t0 = System.currentTimeMillis();
            //run("walk","trot");
           // System.out.println(calc( "walk","walk" ));
            runSingle(new String[]{"what","walk"},new String[]{"what","trot","walk"});
            long t1 = System.currentTimeMillis();
            System.out.println( "Done in "+(t1-t0)+" msec." );
    }
    
	public static void addSemanticSimilarity(SimilarityMatrix document)
	{
		int noOfSentences = document.getArrSen().size();
		double tempMat[][] = new double[noOfSentences][noOfSentences];
		double max=0,min=Double.MAX_VALUE;
		for(int i=0;i<noOfSentences;i++)
		{
			for(int j=i;j<noOfSentences;j++)
			{
				if(i!=j)
				{
					tempMat[i][j] = runSingle(document.getArrSen().get(i).getArrayRawSen(),document.getArrSen().get(j).getArrayRawSen());
					tempMat[j][i] = tempMat[i][j];
				}
				if(tempMat[j][i]>max)
				{
					max = tempMat[j][i];
				}
				//if(tempMat[j][i]<min)
				//{
				//	min = tempMat[j][i];
				//}
			}
		}
		CosineSimilarity.normalizeMatrix(tempMat, max, 0);
		//CosineSimilarity.printMatrix(tempMat, noOfSentences);
		CosineSimilarity.addMatrixToSimilarityAndNormalize(document.getSimilarity(), tempMat, noOfSentences);
	}
}
