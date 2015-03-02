package com.tfidf;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import com.cluster.Cluster;
import com.cluster.Document;
import com.cluster.SoftClustering;
import com.utilities.*;

public class TfidfHelper {

	
	DatabaseHelper dbHelp = new DatabaseHelper();

	TreeMap<String, Integer> docTermsList = new TreeMap<String, Integer>();
	TreeMap<String, Integer> docTermIndexes = new TreeMap<String, Integer>();
    int targetIndex;
    String selectedHotel;
	ArrayList<Hotel> hotelsArray,originalHotels;
	ArrayList<double[]> tfidf = new ArrayList<double[]>();
	static double soft[][];
	
	private void printTFIDF()
	{
		System.out.println("Printing TF-IDF MATRIX ----");
		
		Set<String> keys = docTermsList.keySet();
		for (Iterator<String> i = keys.iterator(); i.hasNext();) {
			String key = (String) i.next();
			System.out.print(key + " ");
			
		}
		System.out.println("");
		for(int i=0;i<tfidf.size();i++)
		{
			for(int j=0;j<tfidf.get(i).length;j++)
			{
				System.out.print(String.format("%.2f", tfidf.get(i)[j]) + " ");
			}
			System.out.println();
		}
	}
	
	private void createTFIDFArray()
	{
		for(int i=0;i<hotelsArray.size();i++)
		{
			double tf[] = new double[docTermIndexes.size()];
			Set<String> keysInOneHotel = hotelsArray.get(i).termsList.keySet();
			
			for (Iterator<String> key = keysInOneHotel.iterator(); key.hasNext();) {
			String keyFound = (String) key.next();
			int docIndexOfKey = docTermIndexes.get(keyFound);
			int docFreqOfKey = docTermsList.get(keyFound);
			int hotelFreqOfKey = hotelsArray.get(i).termsList.get(keyFound);
			int totalDocs = hotelsArray.size();
			
			if(docFreqOfKey != totalDocs)
			{
				double idf = Math.log10(totalDocs/docFreqOfKey);
				
				double tfOfKey = hotelFreqOfKey;
				tf[docIndexOfKey] = tfOfKey*idf;
			}
			//tf[docIndexOfKey] = hotelFreqOfKey;
			}
			tfidf.add(tf);
		}
	}
	
	/*
	 * Fills the Term Index Map from the Document Term List Map
	 */
	private void fillTfidfTermIndexes() {
		Set<String> keys = docTermsList.keySet();
		int indexOfKey = 0;
		for (Iterator<String> i = keys.iterator(); i.hasNext();) {
			String key = (String) i.next();
			docTermIndexes.put(key, indexOfKey);
			indexOfKey++;
		}
	}

	/*
	 * Update the Document-Term-List because this is a new word for this Hotel
	 * Summary
	 */
	private void addTokenToDocTermList(String tok) {
		if (!docTermsList.containsKey(tok)) {
			docTermsList.put(tok, 1);
		} else {
			int i = docTermsList.get(tok);
			docTermsList.put(tok, i + 1);
		}
	}

	/*
	 * Add the tokens of the sentence to the given Hotel dictionary
	 */
	private void addStringToHotelDictionary(String sen, Hotel h) {
		StringTokenizer stk = new StringTokenizer(sen, " .");
		while (stk.hasMoreElements()) {
			String tok = (String) stk.nextElement();
			if (tok.endsWith(".")) {
				tok = tok.substring(0, tok.length() - 1);
			}
			tok = tok.trim();

			if (!h.termsList.containsKey(tok)) {
				h.termsList.put(tok, 1);
				this.addTokenToDocTermList(tok);
			} else {
				int i = h.termsList.get(tok);
				h.termsList.put(tok, i + 1);
			}

		}
	}

	private void fillDataStructures() {
		for (int i = 0; i < hotelsArray.size(); i++) {
			this.addStringToHotelDictionary(hotelsArray.get(i).getProSummary(),
					hotelsArray.get(i));
			// HotelUtility.printDictionary(hotelsArray.get(i));
		}
	}
	
public void replaceSynonyms()
{
	Synonyms syn = new Synonyms();
	for(int i=0;i<this.hotelsArray.size();i++)
	{
		String summary = this.hotelsArray.get(i).getRawSummary();
		String newSummary = "";
		String word;
		StringTokenizer st = new StringTokenizer(summary," .");  
	     while (st.hasMoreTokens()) {  
	    	 word = syn.getSynonyms(st.nextToken()); 
	        newSummary = newSummary + word + " ";
	     }  
	     this.hotelsArray.get(i).setRawSummary(newSummary);
	}
}
	public void calculatetfidf(String cities[]) {
		this.hotelsArray = dbHelp.getHotelByCity(cities);
		this.originalHotels = new ArrayList<Hotel>(); 
		this.originalHotels = (ArrayList<Hotel>) this.hotelsArray;
		this.replaceSynonyms();
		this.fillDataStructures();
		// HotelUtility.printHotelCityName(hArr);
		// HotelUtility.printTreeMap(docTermsList, "Document Term List");
		this.fillTfidfTermIndexes();
		this.createTFIDFArray();
		this.printTFIDF();
	}
	
	public ArrayList<Hotel> run(String cities[],String selectedHotel)
	{
		this.selectedHotel = selectedHotel;
		calculatetfidf(cities);
		ArrayList<Document> all = new ArrayList<Document>();
		for(double ifidf[]:this.tfidf)
		{	
			all.add(new Document(ifidf));
		}
		int index=0,in=0;
		for(Hotel h:hotelsArray)
		{
			if(h.getName().equals(this.selectedHotel))
		    {
				index = in;
				break;
		    }
			in++;
		}
		SoftClustering scluster = new SoftClustering();
		scluster.run(all);
		soft = new double[all.size()][scluster.getClusters().size()];
		for(int i=0;i<all.size();i++)
		{
			int j=0;
			for(Cluster c:scluster.getClusters())
			{
				//System.out.println(c.getCentroid().tfidf[0]);
				soft[i][j++] = c.calWeight_doc(scluster.getClusters(),3,all.get(i));
				System.out.println("D:"+i+" W:"+soft[i][j-1]);
			}
		}
		
		double hw=0;
		int clusterIndex=0;
		for(int i=0;i<scluster.getClusters().size();i++)
		{
			System.out.println("I:"+i+"SIZE OF CLUS:"+scluster.getClusters().get(i).getDocuments().size());
			if(soft[index][i]>hw)
			{
				hw=soft[index][i];
				clusterIndex=i;
			}
		}
		System.out.println("SELECTED DOC:"+index);
		System.out.println("CLUSTER INDEX:"+clusterIndex);
		ArrayList<Hotel> recoSummary = new ArrayList<Hotel>();
		for(Document d:scluster.getClusters().get(clusterIndex).getDocuments())
		{
			int index_reco = all.indexOf(d);
			recoSummary.add(originalHotels.get(index_reco));
			//System.out.println(hotelsArray.get(index).getRawSummary());
		}
		return recoSummary;
	}
	public static void main(String[] args)
	{
		TfidfHelper tfidf = new TfidfHelper();
		String arr[] = { "Delhi", "Beijing" };
		tfidf.calculatetfidf(arr);
		ArrayList<Document> all = new ArrayList<Document>();
		for(double ifidf[]:tfidf.tfidf)
		{	
			all.add(new Document(ifidf));
		}
		Document ref = all.get(0);
		
		SoftClustering scluster = new SoftClustering();
		scluster.run(all);
		System.out.println("TOTAL DOC:"+all.size());
		System.out.println("TOTAL CULS:"+scluster.getClusters().size());
		Cluster target = null;
		double we=0;
	
		soft = new double[all.size()][scluster.getClusters().size()];
		for(int i=0;i<all.size();i++)
		{
			int j=0;
			for(Cluster c:scluster.getClusters())
			{
				System.out.println(c.getCentroid().tfidf[0]);
				soft[i][j++] = c.calWeight_doc(scluster.getClusters(), 1,all.get(i));
				System.out.println("D:"+i+" W:"+soft[i][j-1]);
			}
		}
		
		double hw=0;
		int clusterIndex=0;
		for(int i=0;i<scluster.getClusters().size();i++)
		{
			System.out.println("I:"+i+"SIZE OF CLUS:"+scluster.getClusters().get(i).getDocuments().size());
			if(soft[0][i]>hw)
			{
				hw=soft[0][i];
				clusterIndex=i;
			}
		}
		System.out.println("index:"+clusterIndex);
		/*for(Cluster c:scluster.getClusters())
		{
			int i=0;
			System.out.println("I:"+i+"SIZE OF CLUS:"+c.getDocuments().size());
			ArrayList<Double> w = c.getWeight();
			for(Document d:c.getDocuments())
			{
				//System.out.println("I:"+i+"weight:"+c.getWeight().size());
				if(d==ref)
				{
					target = c;
					
					we=w.get(i);
					System.out.println("found");
					break;
				}
				System.out.println("w:"+w.get(i));
				i++;
			}
		}*/
		System.out.println(tfidf.hotelsArray.get(0).getRawSummary());
		System.out.println("ret res"+scluster.getClusters().get(clusterIndex).getDocuments().size());
		
		for(Document d:scluster.getClusters().get(clusterIndex).getDocuments())
		{
			int index = all.indexOf(d);
			System.out.println(tfidf.hotelsArray.get(index).getRawSummary());
		}
	}
}
