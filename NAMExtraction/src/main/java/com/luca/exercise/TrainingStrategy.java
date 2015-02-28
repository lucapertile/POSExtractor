package com.luca.exercise;

import java.util.Map;

/**
 * 
 * Interface that defines a generic training strategy
 * 
 * @author luca
 *
 */

public interface TrainingStrategy {
	
	Map<POSClasses, Double[]> trainClassWeights(Map<String,POSClasses> taggedCorpus);

}
