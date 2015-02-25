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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Stopword sp=new Stopword();
	    Special spe=new Special();
	    FileInputStream fis_task=null; 
        String str_task;
        InputStreamReader isr_task;
        BufferedReader br_task;
        StringTokenizer st_task;
    
      try 
      {
    	  fis_task = new FileInputStream("C:\\Users\\Jemin-PC\\Desktop\\Hotel Car Dataset\\hotels\\beijing\\china_beijing_aloft_beijing_haidian");
    	  isr_task = new InputStreamReader(fis_task) ;
    	  br_task = new BufferedReader(isr_task)      ;
    	  File f3 = new File("C:\\Users\\Jemin-PC\\Desktop\\Hotel Car Dataset\\hotels\\beijing\\"+"preprocessed.txt");
          FileWriter writer = new FileWriter(f3);
          BufferedWriter output = new BufferedWriter(writer);
    	  while((str_task = br_task.readLine())!=null)
    	  {
          System.out.print(str_task);
          str_task=sp.remove(str_task);
          str_task=spe.remove(str_task);
          System.out.println(str_task);
          output.write(str_task+"\n");
    	  }
    	  System.out.print(spe.remove("a hello you are mine! of% about  at  are ( murugan )kannan."));
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
        System.out.println(sp.remove("a hello you are mine of about  at  are  murugan kannan"));

	}

}
