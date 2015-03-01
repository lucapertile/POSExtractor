package com.luca.exercise;

import java.util.Map;

/**
 * 
 * Each model that classifies parts of speech can be trained using different optimisation algorithms.
 * To allow this flexibility we use a simple strategy pattern that can create multiple TrainingModels based 
 * on different concrete algorithm implementations. In this exercise the idea was to implement a simple gradient descent
 * method to find the set of weight that minimise the conditional likelihood -log(p(POSClas|word,weights) .
 * 
 * 
 * @author luca
 *
 */

public class TrainingModel {
	
	TrainingStrategy optimisationStrategy;
	
	public TrainingModel(TrainingStrategy optimisationStrategy){
		
		this.optimisationStrategy=optimisationStrategy;
		
	}
	
	/**
	 * 
	 * Trains the model using the optimisationStrategy defined in the constructor on the tagged data, then returns the "optimal" set of weights for each part of speech class.
	 * 
	 * @param taggedCorpus tagged data to train the model
	 * @return
	 */
	
	public Map<POSClasses,Double[]> executeTraining( Map<String,POSClasses> taggedCorpus){
        return this.optimisationStrategy.trainClassWeights(taggedCorpus);
    }
	

}
