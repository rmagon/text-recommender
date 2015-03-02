package com.cluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

public class SoftClustering {

	ArrayList<Cluster> clusters = new ArrayList<Cluster>();
	private static int noofcluster=0;
	private static int m=3;
	
	ArrayList<ArrayList<Integer>> weight ;
	
	
	public ArrayList<Cluster> getClusters()
	{
		return clusters;
	}
	public void run(ArrayList<Document> input)
	{
		ArrayList<Document> localInput=input;//intially all are input.
		weight = new ArrayList<ArrayList<Integer>>();
		
		Cluster temp = null;
		while(true)
		{
			Cluster c[] = new Cluster[2];
			c[0]=new Cluster();
			c[1]=new Cluster();
			
			for(Document d:localInput)
			{
				Random rand = new Random();

			    // nextInt is normally exclusive of the top value,
			    // so add 1 to make it inclusive
			    int randomNum = rand.nextInt((1 - 0) + 1) + 0;
			    System.out.print(randomNum);
			    c[randomNum].setDocument(d);
			}
			if(c[0].getDocuments().size()<.2*input.size())
			{
				clusters.add(temp);
				break;
			}
			else if(c[1].getDocuments().size()<.2*input.size())
			{
				clusters.add(temp);
				break;
			}
			if(c[0].getDocuments().size()> 0)
			{
				c[0].calCentroid();
				//c[0].calWeight(clusters, m);
			}
			if(c[1].getDocuments().size()> 0)
			{
				c[1].calCentroid();
				//c[1].calWeight(clusters, m);
			}
			clusters.add(c[0]);
			clusters.add(c[1]);
			c=refineClusters(c);
	
			clusters.set(clusters.indexOf(c[0]),c[0]);
			clusters.set(clusters.indexOf(c[1]),c[1]);
			//c[0].calWeight(clusters, m);
			//c[1].calWeight(clusters, m);
			
			int size=0,index=0,i=0;
			for(Cluster clocal: clusters)
			{
				if(size<clocal.getDocuments().size())
				{
					size = clocal.getDocuments().size();
					index=i;
				}
				i++;
			}
			//if(c[0].getDocuments().size()>c[1].getDocuments().size())
			{
				localInput = clusters.get(index).getDocuments();
				temp = clusters.get(index);
				clusters.remove(temp);
			}
			//else
			{
				//localInput=c[1].getDocuments();
				//temp=c[1];
				//clusters.remove(c[1]);
			}
			
		}
	}
	
	
	public Cluster[] refineClusters(Cluster[] c)
	{
		Cluster[] goodC=c;
		ArrayList<Cluster> localClusters = clusters;
		//System.out.println("intialLocalsize:"+localClusters.size());
		double intialFuzzy = fuzzyCriteria_new(clusters);
		ArrayList<Document> input=c[0].getDocuments();
		
		HashMap<Document,Integer> totalDocument=new HashMap<Document,Integer>();
		
		for(Document d: input)
		{
			totalDocument.put(d,0);
		}
		input=c[1].getDocuments();

		for(Document d: input)
		{
			totalDocument.put(d,1);
		}
		
	  
	    int clusterIndex,indexFirst,indexSecond;
		for(Document d: totalDocument.keySet())
		{
		    clusterIndex = totalDocument.get(d);
		    if(clusterIndex==1)
		    {
		    	indexFirst = 1;
		    	indexSecond = 0;
		    }
		    else
		    {
		    	indexFirst = 0;
		    	indexSecond = 1;
		    }
			c[indexFirst].removeDocument(d);
			c[indexSecond].setDocument(d);
			c[indexFirst].calCentroid();
			c[indexSecond].calCentroid();
		//	System.out.println("localsize"+localClusters.size());
		//	localClusters.remove(c[indexFirst]);
			//System.out.println(localClusters.get(0));
			//System.out.println(localClusters.get(1));
			//System.out.println("index:"+c[0]);
			//System.out.println("index:"+c[1]);
			localClusters.set(localClusters.indexOf(c[indexFirst]), c[indexFirst]);
			localClusters.set(localClusters.indexOf(c[indexSecond]), c[indexSecond]);
			
			//c[indexFirst].calWeight(localClusters, m);
		//	c[indexSecond].calWeight(localClusters, m);
			
			double newFuzzy = fuzzyCriteria_new(localClusters);
			if(newFuzzy>intialFuzzy)
			{
				goodC=c;
				intialFuzzy = newFuzzy;
			}
			else
			{
				c=goodC;
			}
		}
	
		return goodC;
	}
	
	public double fuzzyCriteria(ArrayList<Cluster> clusters)
	{
		double value=0;
		for(int i=0;i<clusters.size();i++)
		{
			Cluster c=clusters.get(i);
			for(int j=0;j<clusters.get(i).getDocuments().size();j++)
			{
				value = value + c.cosineSimilarity(j);
			}
		}
		return value;
	}
	
	public double fuzzyCriteria_new(ArrayList<Cluster> clusters)
	{
		double value=0;
		double lower=0,upper=0;
		for(int i=0;i<clusters.size();i++)
		{
			Cluster c=clusters.get(i);
			lower=0;
			upper=0;
			for(int j=0;j<c.getDocuments().size();j++)
			{
				//System.out.println("csize:"+c.getDocuments().size());
				lower = lower + c.getWeight().get(j);
				for(int k=0;k<c.getDocuments().size();k++)
				{
					//System.out.println("j:"+j+" k:"+k);
					upper = upper + this.cosineSimilarity(c.getDocuments().get(j),c.getDocuments().get(k));
				}
			}
			value = value +(upper/(lower*lower));
			
		}
		return value/lower;
	}
	
	public double cosineSimilarity(Document d1,Document d2)
	{
		double upper=0,one_sqr_sum=0,sec_sqr_sum=0;
		  double one[] = d1.tfidf;
		  double sec[] = d2.tfidf;
		  for(int i=0;i<d1.tfidf.length;i++)
		  {
			  upper = upper+(one[i]*sec[i]);
			  one_sqr_sum = one_sqr_sum + one[i]*one[i];
			  sec_sqr_sum = sec_sqr_sum + sec[i]*sec[i];
		  }
		  
		return upper/(Math.sqrt(one_sqr_sum)*Math.sqrt(sec_sqr_sum));
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
	   SoftClustering sclu = new SoftClustering();
	   //sclu.run(input);
		
	}

}
