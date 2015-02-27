package com.utilities;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class CosineSimilarity {

	public static void findCosineSimilairty(SimilarityMatrix document)
	{
		int noOfSentences = document.getArrSen().size();
		double max=0,min=Double.MAX_VALUE;
		for(int i=0;i<noOfSentences;i++)
		{
			for(int j=i;j<noOfSentences;j++)
			{
				if(i!=j)
				{
					Sentence sen1 = document.getArrSen().get(i);
					Sentence sen2 = document.getArrSen().get(j);
					double num1 = sen1.vectorSum();
					double num2 = sen2.vectorSum();
					double den1 = sen1.vectorSquareSumRoot();
					double den2 = sen2.vectorSquareSumRoot();
					document.getSimilarity()[i][j] = (num1*num2)/(den1*den2);
					document.getSimilarity()[j][i] = document.getSimilarity()[i][j];
					if(document.getSimilarity()[i][j]>max)
					{
						max = document.getSimilarity()[i][j];
					}
					//if(document.getSimilarity()[i][j]<min)
					//{
					//	min = document.getSimilarity()[i][j];
					//}
				}
			}
		}
		//document.printSimilarity();
		normalizeMatrix(document.getSimilarity(),max,0);
		
	}
	
	public static void normalizeMatrix(double[][] toNormalize,double max,double min)
	{
		
		for(int i=0;i<toNormalize.length;i++)
		{
			for(int j=0;j<toNormalize.length;j++)
			{
				toNormalize[i][j] = (toNormalize[i][j] - min)/(max - min);
			}
		}
	}
	public static int getIndexOfMaxRow(SimilarityMatrix document)
	{
		int index=-1;
		int noOfSentences = document.getArrSen().size();
		double max = 0;
		for(int i=0;i<noOfSentences;i++)
		{
			double count =0;
			for(int j=0;j<noOfSentences;j++)
			{
				count += document.getSimilarity()[i][j];
			}
			if(max<count)
			{
				max = count;
				index = i;
			}
		}
		return index;
	}
	
	/*
	 * Returns a 2 dimensional int howManyX2 array:
	 * index_of_sentence,count_of_similarity_total(nearest integer)
	 * index_of_sentence,count_of_similarity_total(nearest integer)
	 * ..upto howMany
	 */
	public static int[][] getTopSentences(int howMany,SimilarityMatrix document)
	{
		int index=-1;
		int noOfSentences = document.getArrSen().size();
		int topCounter = 0;
		double sumOfSimilarity[][] = new double[noOfSentences][2];
		int result[][] = new int[howMany][2];
		for(int i=0;i<noOfSentences;i++)
		{
			double count =0;
			for(int j=0;j<noOfSentences;j++)
			{
				count += document.getSimilarity()[i][j];
			}
			sumOfSimilarity[i][0] = i;
			sumOfSimilarity[i][1] = count;
		}
		
		for(int i=0;i<howMany;i++)
		{
			//int max0 = sumOfSimilarity[i][0];
			//int max1 = sumOfSimilarity[i][1];
			int maxIndex = i;
			for(int j=i+1;j<noOfSentences;j++)
			{
				if(sumOfSimilarity[maxIndex][1]<sumOfSimilarity[j][1])
				{
					maxIndex = j;
				}
			}
			if(maxIndex!=i)
			{
				double temp0 = sumOfSimilarity[maxIndex][0];
				double temp1 = sumOfSimilarity[maxIndex][1];
				
				sumOfSimilarity[maxIndex][0] = sumOfSimilarity[i][0];
				sumOfSimilarity[maxIndex][1] = sumOfSimilarity[i][1];
				
				sumOfSimilarity[i][0] = temp0;
				sumOfSimilarity[i][1] = temp1;
			}
		}
		
		for(int i=0;i<howMany;i++)
		{
			result[i][0] = (int)sumOfSimilarity[i][0];
			result[i][1] = (int)sumOfSimilarity[i][1];
			//System.out.println(sumOfSimilarity[i][0] + " " + sumOfSimilarity[i][1]);
		}
		return result;
	}
	
	
	public static void addMatrixToSimilarityAndNormalize(double[][] Similarity,double[][] matToAdd,int len)
	{
		double max=0,min=Double.MAX_VALUE;
		for(int i=0;i<len;i++)
		{
			for(int j=0;j<len;j++)
			{
				Similarity[i][j] += matToAdd[i][j];
				if(Similarity[i][j]>max)
				{
					max = Similarity[i][j];
				}
				if(Similarity[i][j]<min)
				{
					min = Similarity[i][j];
				}
			}
		}
		normalizeMatrix(Similarity,max,min);
	}
	
	public static void printMatrix(double[][] mat, int len)
	{
		for(int i=0;i<len;i++)
		{
			for(int j=0;j<len;j++)
			{
				System.out.print(String.format( "%.2f", mat[i][j] )  + " ");
			}
			System.out.println();
		}
		
	}
}
