package com.luca.exercise;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Implementation of a simple Maximum Entropy classifier to classify the words. I am using a standard log-linear model applied on a set of binary features:
 * 
 * p(class|word,weights)=1/normalisation * exp[sum[weight(class)[i]*feature[i],{i,1,number_of_features}]]
 * 
 * where 
 * 
 * normalisation=sum[ exp[sum[weight(class)[i]*feature[i],{i,1,number_of_features}) ]],{all classification tags}]
 * 
 * In order to obtain the maximum entropy solution we should:
 * a) find constraint for the feature weights and 
 * b) solve the optimisation problem by finding the maximum likelihoo estimation of the parameters, that in this case will give also the Maximum entropy model
 * 
 * Unfortunately in the timeframe provided I have not been able to implement the full solution that included the optimisation of the weights from training data.
 * However I have managed to write the skeleton of a strategy pattern that allows to implement different optimisation algorithms to solve the maximum likelihood problem.
 * Without an optimal set of weights I have temporarily set the weights manually using some trial and error heuristics. This of course impacts negatively on the quality of the results provided by this program as it is now.
 * 
 * The design behind this application has been focused in building an extensible framework that allows to add different tokenisers(via a factory on TokeniserFactory.java)
 * and also that allows different training strategies (via strategy pattern on TrainingStrategy/TrainingModel.java). 
 * On top of this it is possible to ad additional features by adding functional interfaces to Features.java. 
 * 
 * If we wanted to tag other parts of speech EMAIL, VERB etc, it would be as simple as adding an additional entry in the POSClasses enum, the required new features on the Features class, and modifying the expLinearCombination method on this class to reflect the new features.
 * Via reflections (Features.class.getMethods()) we could also make the method expLinearCombination of this class totally generic so that changing the enum POSClasses and Features would be enough to extend the classifier.
 * 
 * 
 * @author Luca Pertile
 * 
 */

public class POSModel implements Trainable {

	
    /**
     * Returns a set of features. The weights should be fitted on a training dataset of labelled data and then validated on a test sample.
     * 
     * @return Map<PartOfSpeechTag, Double[]>
     */
	@Override
	public Map<POSClasses, Double[]> getFeaturesWeights(TrainingModel tm) {

		Map<POSClasses, Double[]> featuresWeights = new HashMap<POSClasses, Double[]>();
        

        
        //Here we return the fitted set of weights obtained via Strategy pattern with one of the optimisation strategies.
        
        //featuresWeights=tm.executeTraining(taggedCorpus);
        
		// add name weights (manual tuning for now..)
		featuresWeights.put(POSClasses.NAME, new Double[] { 0.0, 1.0, 0.02, 0.04,
				0.02, 0.0, 2.0, 1.0, 0.0,1.0,1.9 });
		// add weight for all the remaining words
		featuresWeights.put(POSClasses.OTHER, new Double[] { 0.0, 0.0, -0.80, 0.5,
				-1.0, -1.0, 0.0, 0.0, 2.0, 0.0, 0.0 });

		return featuresWeights;

	}
	
	/**
	 * Softmax function 
	 * 
	 * 
	 * @param words
	 * @param featuresWeights
	 * @param speechTag
	 * @param knownNames
	 * @param index
	 * @return
	 */
	
	
	private  static Double expLinearCombination(List<String> words, Map<POSClasses, Double[]> featuresWeights,POSClasses speechTag, List<String> knownNames,int index){
	
		
		
	return Math.exp(featuresWeights.get(speechTag)[0]
			* Features.previousStartsWithCapital().applyAsInt(
					words.get(index - 1), words.get(index))
			+ featuresWeights.get(speechTag)[1]
			* Features.isPrecededBySalutation().applyAsInt(
					words.get(index - 1), words.get(index))
			+ featuresWeights.get(speechTag)[2]
			* Features.containsApostrophe().applyAsInt(words.get(index))
			+ featuresWeights.get(speechTag)[3]
			* Features.containsDash().applyAsInt(words.get(index))
			+ featuresWeights.get(speechTag)[4]
			* Features.isPrecededBySingleCapLetter().applyAsInt(
					words.get(index - 1), words.get(index))
			+ featuresWeights.get(speechTag)[5]
			* Features.startsWithCapital().applyAsInt(words.get(index))
			+ featuresWeights.get(speechTag)[6]
			* Features.isAKnownName(knownNames).applyAsInt(words.get(index))
			+ featuresWeights.get(speechTag)[7]
			* Features.isFollowedByPostfix().applyAsInt(words.get(index),
					words.get(index + 1))
			+ featuresWeights.get(speechTag)[8]
			* Features.startsWithLowercase().applyAsInt(words.get(index))
			+ featuresWeights.get(speechTag)[9]
					* Features.isSingleLetter().applyAsInt(words.get(index))
		    + featuresWeights.get(speechTag)[10]
							* Features.isPrecededByKnownName(knownNames).applyAsInt(words.get(index-1),
									words.get(index)));
	
	}
	
	
	
	/**
	 * This method returns a Map containing the word and an ArrayList with the probability assigned to each class, conditioned to the data. 
	 * In this particular case we are just considering the class of type NAME and the class of every other part of speech OTHER.
	 * 
	 * 
	 * @param tag
	 * @param featuresWeights
	 * @param words
	 * @return
	 */

	public Map<String, ArrayList<Double>> getClassProbabilities(
		 Map<POSClasses, Double[]> featuresWeights,
			List<String> words,List<String> knownNames) {
		
		//to preserver the order of words we need a linked hash map
		Map<String, ArrayList<Double>> taggedNames = new LinkedHashMap<String, ArrayList<Double>>(
				words.size());

		double  normalisation = 0;

		for (int i = 1; i < words.size() - 1; i++) {
		//debug bundle to check the features that are activated for each word	
		//String bundle =""+ Features.previousStartsWithCapital().applyAsInt(words.get(i - 1), words.get(i))+Features.isPrecededBySalutation().applyAsInt(
		//		words.get(i - 1), words.get(i))+Features.containsApostrophe().applyAsInt(words.get(i))+ Features.startsWithCapital().applyAsInt(words.get(i));
				
		//Here we normalise for a situation where we have multiple POS tags, so that it is more general	
		 for(POSClasses  speechTag : POSClasses.values()){
               normalisation+= expLinearCombination(words,featuresWeights, speechTag,knownNames,i); 
	      }
		 
		 //Here we store the class conditional probabilities
		 ArrayList<Double> classConditionalProbabilities = new ArrayList<Double>(
					POSClasses.values().length);
					    
		 for(POSClasses  speechTag : POSClasses.values()){
			 
		    //calculates the conditional probability  for each word
			classConditionalProbabilities.add(expLinearCombination(words,featuresWeights, speechTag,knownNames,i) /normalisation );
		 }
		    //resets normalisation for each word
		    normalisation=0; 
            //we are now classify the words according to the highest probability stored in the ArrayList
			taggedNames.put(words.get(i), classConditionalProbabilities);

		}

		return taggedNames;

	}

	

	
	

}
