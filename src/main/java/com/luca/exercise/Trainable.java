package com.luca.exercise;

import java.util.Map;

/**
 * 
 * Interface that defines a model that can be trained in order to find the optimal feature weights.
 * 
 * @author luca
 *
 */

public interface Trainable {
   
	
	public  Map<POSClasses,Double[]> getFeaturesWeights(TrainingModel model);
	
}
