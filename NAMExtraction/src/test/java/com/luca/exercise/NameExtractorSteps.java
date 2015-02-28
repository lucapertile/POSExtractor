package com.luca.exercise;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

/**
 * 
 * Draft of a simple BDD integration test using JBehave
 * 
 * @author luca
 *
 */

public class NameExtractorSteps {

	
	TrainingModel tm ;
	
	POSModel model;
	
	DataLoader test;
	
	
	
	Map<String, ArrayList<Double>> result ;

	 @Given("an HTML page loaded from disk")
	 public void anHTMLpage() throws Exception {
			
		     tm = new TrainingModel(new StochasticGradientDescentTraining());

			 model = new POSModel();
			 
			 //test =  new DataLoader.Builder().fromFile("OracleBoard.html").withNamesCorpus("NAMES.txt").build();
	         	
			 test =  new DataLoader.Builder().fromFile("Mirror.html").withNamesCorpus("NAMES.txt").build();
         	
		}

		@When("we apply the POSModel to classify parts of speech")
		public void theWhiteSpaceTokeniserIsAppliedToAString() {
			
			result = model.getClassProbabilities(
					 model.getFeaturesWeights(tm), test.getTokenisedWords(),test.getNamesCorpus());
		}

		@Then("we get back a list of names")
		public void eGetBackTheExpecedTokens() {
			
		    List<String> resultsToAssert = new ArrayList<String>();
		    
			boolean isPartOfName = false;

			for (Map.Entry<String, ArrayList<Double>> entry : result.entrySet()) {
				
				//classify as NAME every datum with conditional prob > 0.9		
				if (entry.getValue().get(0) > 0.90){
					
					resultsToAssert.add(entry.getKey());
					isPartOfName = true;
					
				}
			    
				
			    
			}

			
			assert resultsToAssert.contains("Gabriel Paulista");
			assert resultsToAssert.contains("Lewis Hamilton");
			assert resultsToAssert.contains("Rebecca Ferguson's");
			assert resultsToAssert.contains("Gillian Taylforth");
		   
		}
	

}

