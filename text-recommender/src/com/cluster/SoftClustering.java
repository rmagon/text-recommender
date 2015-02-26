package com.cluster;

import java.util.ArrayList;
import java.util.Random;

public class SoftClustering {

	
	private static int noofcluster=0;

	
	public double fuzzyCritria(ArrayList<Cluster> clusters)
	{
		double value=0;
		for(int i=0;i<clusters.size();i++)
		{
			for(int j=0;j<clusters.get(i).getDocuments().size();j++)
			{
				//value = value + clusters.get(i)clusters
			}
		}
		return 0;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Document> input = new ArrayList<Document>();
	//	input.add(new Document());
		//input.add(new Document());
		//input.add(new Document());
		
		
		ArrayList<Cluster> clusters = new ArrayList<Cluster>();
		Cluster c[] = new Cluster[2];
		c[0]=new Cluster();
		c[1]=new Cluster();
		
		
		for(Document d:input)
		{
			Random rand = new Random();

		    // nextInt is normally exclusive of the top value,
		    // so add 1 to make it inclusive
		    int randomNum = rand.nextInt((1 - 0) + 1) + 0;
		    System.out.print(randomNum);
		    c[randomNum].setDocument(d);
		}
		clusters.add(c[0]);
		noofcluster++;
		clusters.add(c[1]);
		noofcluster++;
		while(true)
		{
			Cluster div;
			if(c[0].getDocuments().size()>c[1].getDocuments().size())
			{
				div=c[0];
			}
			else
			{
				div=c[1];
			}
			
		}
	}

}
