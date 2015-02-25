package com.textrecommender;
import java.io.*;
import java.util.StringTokenizer;

public class Stopword 
{       
   
   String stwd[];
   public Stopword ()
    {
        int cnt=0,sz=0;
        char bt[]=null;
       try {
         File fp=new File("C:\\Workspace\\text-recommender\\src\\com\\textrecommender\\"+"stopwords.txt");	
         FileReader fis=new FileReader(fp);
         sz=(int)fp.length();
         bt=new  char [sz];
         fis.read(bt);
         fis.close();	  		
          }
      catch(IOException ex) {}       
      stwd=getTokens(new String(bt));		        	
   }

public void display () 
 {
            for (int i=0;i<stwd.length;i++)
	System.out.println(stwd[i]);	
 }

 public boolean  isStopword( String word) 
  {
         boolean flag=false;        
          for (int i =0;i<stwd.length;i++)  {
             if(stwd[i].equalsIgnoreCase(word) ) {
	  flag=true;
	   break;	
               }
            }
          return flag;	
   }

  public String[]  getTokens(String sen) 
  {
	 int sz=0,cnt=0;String words[]=null;
	StringTokenizer  stk=new StringTokenizer(sen) ;
	sz=stk.countTokens();
    words=new String[sz];	        	
    while ( stk.hasMoreTokens()) 
	{
           	words[cnt]=new String(stk.nextToken());	     				
           	cnt++;
       	}		
	return words;	        
    }
  
  public String remove(String sen) 
  {
     String  dsen="";
     String  words[]=getTokens(sen);	
      for (int j=0;j<words.length; j++) 
          { 	
               if ( ! isStopword(words[j] )  )  	
	dsen = dsen +words[j] +" ";              		
          }		
         return dsen;	         		
    }    

  public static void main(String str[])
   {
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