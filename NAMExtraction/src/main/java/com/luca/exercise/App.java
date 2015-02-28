package com.luca.exercise;

import java.util.ArrayList;
import java.util.Map;
import java.util.StringJoiner;

/**
 * Main application. This command line application accepts a single parameter that can be either a file or a url and returns
 * on the standard output a list of the possible tokens classified as names by the POSModel.
 * 
 *   
 * 
 * At the moment the weight of the POS mode are hard-coded with simple trial and error, therefore the accuracy is quite limited.
 * It manages to extract a few names on the pages but there is still a lot of noise data returned back.
 * My idea was to train the POSModel using a simple gradient descend on labeled data and get the weights from there, but unfortunately 
 * I couldn't do it within the 3 days deadline. Additional documentation on the file POSModel.java .
 * 
 * 
 * 
 * @author luca
 *
 */

public class App {


	public static void main(String args[]) throws Exception {

		//"http://www.oracle.com/us/corporate/press/BoardofDirectors/index.html";
		//"http://en.wikipedia.com";
		
		DataLoader test = null;
		
		int i=0;
		String flag,argument;
		//flag to print the conditional probabilities
		boolean showProbabilities=false;
		
		while (i < args.length && args[i].startsWith("-")) {
		   flag = args[i];
           argument  = args[++i];
           

            // simple check for the flag "file"
            if (flag.toLowerCase().equals("-file")) {
            	
            	test =  new DataLoader.Builder().fromFile(argument).withNamesCorpus("NAMES.txt").build();
            	
            }
            // simple check for the flag "url"
            if (flag.toLowerCase().equals("-url")) {
            	
            	 test =  new DataLoader.Builder().fromURL(argument).withNamesCorpus("NAMES.txt").build();
                 
            	
                
            }
            
            //displays probabilities
         /*if (flag.toLowerCase().equals("-debug")) {
            	
            	showProbabilities=true;
  	
               
           }*/
            
          
            
		}
		
		TrainingModel tm = new TrainingModel(new StochasticGradientDescentTraining());
		
		POSModel model = new POSModel();
		
		
		/*
		 * Here I join together words with a conditional probability > 90% to be names and split when I find a word with a probability that is less than that.
		 * It's a bit of a hack and can definitely be improved since for example this can join together different consecutive names in a single one.
		 * 
		 */
		StringJoiner joiner = new StringJoiner(" ");
		
		boolean isPartOfName = false;

		for (Map.Entry<String, ArrayList<Double>> entry : model.getClassProbabilities(
			 model.getFeaturesWeights(tm), test.getTokenisedWords(),test.getNamesCorpus()).entrySet()) {
			
			//classify as NAME every datum with conditional prob > 0.9		
			if (entry.getValue().get(0) > 0.90){
				if(showProbabilities) System.out.println(entry.getKey() + " -- p(NAME|d,l)= "+entry.getValue().get(0)+ " p(OTHER|d,l)= "+ entry.getValue().get(1));
				joiner.add(entry.getKey());
				isPartOfName = true;
				
			}else{
				//add a delimiter when once we find a word that is not a name.
				if(isPartOfName)joiner.add("#");
				isPartOfName=false;
			}
            
			
            
		}

		String[] results = joiner.toString().split("#");
		//prints the results to stdout
        for(String a:results){
        	System.out.println(a.trim());
        }
		
		
	}
}
