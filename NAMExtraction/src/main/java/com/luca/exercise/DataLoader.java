package com.luca.exercise;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Utility type to retrieve external resources
 * 
 * @author luca
 */

public class DataLoader {
	
	public static class Builder {
		
	     private  String filePath=null;
		
	     private  String URL=null;
		
		 private  String namesURL=null;
		
		public Builder fromFile(String filePath){
			this.filePath=filePath;
			return this;
			
		}
		
        public Builder fromURL(String URL){
        	this.URL=URL;
			return this;
			
		}
        
       public Builder withNamesCorpus(String namesURL){
        	this.namesURL=namesURL;
    	        	   
			return this ;
			
		}
		
       public Builder(){
   
       }
       
       public DataLoader build() throws Exception {
           return new DataLoader(this);
       }

	}

	List<String> loadedWords;

	private static List<String> namesCorpus = new LinkedList<String>();
	
	public static List<String> getNamesCorpus(){
		
		return namesCorpus;
		
	}
	
	
	/**
	 * Loads a list of known names from a file.
	 * 
	 * @param filename
	 * @throws IOException
	 */
	
	public static void loadNameCorpus(String filename) throws IOException {

		BufferedReader in = new BufferedReader(new FileReader(filename));
		String line = "";
		while ((line = in.readLine()) != null) {
			namesCorpus.add(line);
		}
		in.close();

	}
	
	/**
	 * Loads an HTML document from a URL using Jsoup and applies the specified tokeniser to the body of the HTML document.
	 * 
	 * @param url
	 * @param tokeniserType
	 * @throws IOException
	 */
	public void loadDocumentFromURL(String url,TokeniserType tokeniserType) throws IOException{
		
		Document doc = Jsoup.connect(url).timeout(100000).get();
		
		Tokeniser tk = TokeniserFactory.getTokeniser(tokeniserType);
		
		this.loadedWords=tk.tokenise(doc.body().text());
		
	}
	
   /**
    * Loads an HTML document form a file using Jsoup and applies the specified tokeniser to the body of the HTML document.
    * 
    * @param uri
    * @param tokeniserType
    * @throws IOException
    */
   public void loadDocumentFromURI(String uri,TokeniserType tokeniserType) throws IOException{
	   
	    File inputFile = new File(uri);
	 
	    Document doc = Jsoup.parse(inputFile, "UTF-8");
	    
	    Tokeniser tk = TokeniserFactory.getTokeniser(tokeniserType);
	        
		this.loadedWords=tk.tokenise(doc.body().text());
 
		
	}
	
   /**
    * Returns the tokenised words.
    * 
    * @return
    */
	
	public List<String> getTokenisedWords(){
		
		return this.loadedWords;
		
	}
	
	

    /*
     * Private constructor to implement the builder pattern
     * 
     */
	private DataLoader(Builder loaderBuilder) throws Exception {
		
		
		//simple check to avoid an invalid object to be built
		if (loaderBuilder.filePath !=null && loaderBuilder.URL !=null) throw new Exception("Please specify either a file path or a http url!");
		
		
		loadNameCorpus(loaderBuilder.namesURL);
		
		//fixed whitespace tokeniser for now
		if (loaderBuilder.filePath !=null){
			loadDocumentFromURI(loaderBuilder.filePath, TokeniserType.WHITESPACE);
		}
		
		//fixed whitespace tokeniser for now
		if (loaderBuilder.URL !=null){
			loadDocumentFromURL(loaderBuilder.URL,TokeniserType.WHITESPACE);
		}
		
	
		
	}

}
