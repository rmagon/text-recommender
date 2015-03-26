/**
 * 
 */
package com.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * @author Jemin-PC
 *
 */
public class preprocess {

	public  void listFilesForFolder(final File folder) {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	        	doPreprocess(fileEntry.getAbsolutePath());
	            
	        }
	    }
	}
	
	public void doPreprocess(String fileName)
	{
		Stopword sp=new Stopword();
	    Special spe=new Special();
	    Porter stemmer = new Porter();
	    LanguageCorrector corrector = new LanguageCorrector();
	    FileInputStream fis_task=null; 
        String str_task;
        InputStreamReader isr_task;
        BufferedReader br_task;
        StringTokenizer st_task;
    
      try 
      {   	
    	  fis_task = new FileInputStream(fileName);
    	  isr_task = new InputStreamReader(fis_task) ;
    	  br_task = new BufferedReader(isr_task);
    	  File f3 = new File(fileName+"_preprocessed.txt");
          FileWriter writer = new FileWriter(f3);
          BufferedWriter output = new BufferedWriter(writer);
    	  while((str_task = br_task.readLine())!=null)
    	  {
         // System.out.print(str_task);
           str_task = sp.remove(str_task);
        //  str_task=spe.remove(str_task);
          str_task = stemmer.stemString(str_task);
           str_task = corrector.correctString(str_task);
          //System.out.println(str_task);
          output.write(str_task+"\n");
    	  }
    	  //System.out.print(corrector.correctString("i actively participated in the music clubs it was a gr8 night."));
          output.close();
      }
      catch (FileNotFoundException e) 
      {
				// TODO Auto-generated catch block
				e.printStackTrace();
      } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        //System.out.println(sp.remove("a hi my name is slim  shady"));
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		preprocess p = new preprocess();
		final File folder = new File("G:\\hotels");
		p.listFilesForFolder(folder);
		

	}

}
