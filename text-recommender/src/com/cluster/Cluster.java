package com.cluster;

import java.util.ArrayList;

public class Cluster {
	
  private ArrayList<Document> list;
  private Document centroid;
  private ArrayList<Double> weight;
  public Cluster()
  {
	  list = new ArrayList<Document>();
      weight = new ArrayList<Double>();
	 // System.out.print("call");
  }
  public ArrayList<Double> getWeight()
  {
	  return weight;
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
	 // System.out.println("size:"+list.size());
	  if(list.size()==0)
		  return;
	  if(centroid==null)
	  {
		  centroid = new Document(list.get(0).tfidf);
	  } 
      for(int i=0;i<centroid.tfidf.length;i++)
      {
    	  double temp=0,lower=0;
    	  for(int j=0;j<list.size();j++)
    	  {
    		//  System.out.println("w:"+weight.get(j)+" tfidf"+list.get(j).tfidf[i]);
    		 // temp =  temp + weight.get(j)*list.get(j).tfidf[i];
    		  temp =  temp + list.get(j).tfidf[i];
    		  lower=lower+weight.get(j);
    		//  System.out.println("I:"+i+" j:"+j+"cen:"+ centroid.tfidf[i]);
    	  }
    	  centroid.tfidf[i] =  temp/lower;
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
  public double calWeight(ArrayList<Cluster> clusters,int m)
  {
	  for(int i=0;i<list.size();i++)
	  {
		  double upper=cosineSimilarity(list.get(i).tfidf,this.getCentroid().tfidf);
		  double lower=0;
		  for(int j=0;j<clusters.size();j++)
		  {
			  double temp = cosineSimilarity(list.get(i).tfidf,clusters.get(j).getCentroid().tfidf);
			  for(int k=0;k<m;k++)
			  {
				  temp=temp+temp;
			  }
			  lower = lower + temp;
		  }
		  
		  for(int j=0;j<m;j++)
		  {
			  upper=upper+upper;
			  
		  }
		 
			  //System.out.print("UPPER:"+upper+" Lower:"+lower);
		      double avg= upper/lower;
		      return avg;
		   // weight.set(i, avg);
		  
	  }
	return 0.0;
  }
  public double calWeight_doc(ArrayList<Cluster> clusters,int m,Document d)
  {
	 
	
		  double upper=cosineSimilarity(d.tfidf,this.getCentroid().tfidf);
		  double lower=0;
		  for(int j=0;j<clusters.size();j++)
		  {
			  double temp = cosineSimilarity(d.tfidf,clusters.get(j).getCentroid().tfidf);
			  for(int k=0;k<m;k++)
			  {
				  temp=temp+temp;
			  }
			  lower = lower + temp;
		  }
		  
		  for(int j=0;j<m;j++)
		  {
			  upper=upper+upper;
			  
		  }
		 
			  //System.out.print("UPPER:"+upper+" Lower:"+lower);
		      double avg= upper/lower;
		      return avg;
		   // weight.set(i, avg);
		  
	 

  }
  public ArrayList<Document> getDocuments()
  {
	  return list;
  }
  
  public void setDocument(Document d)
  {
	  list.add(d);
	  weight.add(1.0);
  }
  public void removeDocument(Document d)
  {
	  int index = list.indexOf(d);
	  weight.remove(index);
	  list.remove(index);
  }
  public Document getCentroid()
  {
	  return centroid;
  }
  
}
