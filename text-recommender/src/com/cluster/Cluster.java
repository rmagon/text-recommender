package com.cluster;

import java.util.ArrayList;

public class Cluster {
	
  private ArrayList<Document> list;
  private Document centroid;
  private ArrayList<Double> weight;
  public Cluster()
  {
	  list = new ArrayList<Document>();
	  System.out.print("call");
  }
  public double size()
  {
	  double s=0;
	  for(int i=0;i<list.size();i++)
	  {
		  s=s+weight.get(i);
	  }
	  return s;
  }
  public void calCentroid()
  {
	  
      for(int i=0;i<list.size();i++)
      {
    	  for(int j=0;j<centroid.tfidf.length;j++)
    	  {
    		  centroid.tfidf[j] = weight.get(j)*list.get(i).tfidf[j];
    	  }
      }
	  
  }
  public double cosineSimilarity(int index)
  {
	  double upper=0,one_sqr_sum=0,sec_sqr_sum=0;
	  double one[] = list.get(index).tfidf;
	  double sec[] = centroid.tfidf;
	  for(int i=0;i<list.get(index).tfidf.length;i++)
	  {
		  upper = upper+(one[i]*sec[i]);
		  one_sqr_sum = one_sqr_sum + one[i]*one[i];
		  sec_sqr_sum = sec_sqr_sum + sec[i]*sec[i];
	  }
	  
	return upper/(Math.sqrt(one_sqr_sum)*Math.sqrt(sec_sqr_sum));
	  
  }
  public double cosineSimilarity(double[] one,double[] sec)
  {
	  double upper=0,one_sqr_sum=0,sec_sqr_sum=0;
	  
	  for(int i=0;i<one.length;i++)
	  {
		  upper = upper+(one[i]*sec[i]);
		  one_sqr_sum = one_sqr_sum + one[i]*one[i];
		  sec_sqr_sum = sec_sqr_sum + sec[i]*sec[i];
	  }
	  
	return upper/(Math.sqrt(one_sqr_sum)*Math.sqrt(sec_sqr_sum));
	  
  }
  public void calWeight(ArrayList<Cluster> clusters,int m)
  {
	  for(int i=0;i<list.size();i++)
	  {
		  double upper=cosineSimilarity(list.get(i).tfidf,centroid.tfidf);
		  double lower=0;
		  for(int j=0;j<clusters.size();j++)
		  {
			  double temp = cosineSimilarity(list.get(i).tfidf,clusters.get(j).centroid.tfidf);
			  for(int k=0;k<m;k++)
			  {
				  temp=temp*temp;
			  }
			  lower = lower + temp;
		  }
		  for(int j=0;j<m;j++)
		  {
			  upper=upper*upper;
			  
		  }
		  weight.set(i, upper/lower);
		  
	  }
  }
  
  public ArrayList<Document> getDocuments()
  {
	  return list;
  }
  
  public void setDocument(Document d)
  {
	  list.add(d);
	  calCentroid();
  }
  
  public Document getCentroid()
  {
	  return centroid;
  }
  
}
