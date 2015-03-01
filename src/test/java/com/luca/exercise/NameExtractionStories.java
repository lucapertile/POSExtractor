package com.luca.exercise;




import org.jbehave.core.configuration.Configuration;
import static org.jbehave.core.reporters.Format.CONSOLE;  
import static org.jbehave.core.reporters.Format.TXT;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;

public class NameExtractionStories extends JUnitStory {
     

	 @Override  
	 public Configuration configuration() {
	        return  super.configuration()  
	         .useStoryReporterBuilder(  
	                 new StoryReporterBuilder()  
	                     .withDefaultFormats()  
	                     .withFormats(CONSOLE, TXT));
	    }
	 
	 
	 @Override public InjectableStepsFactory stepsFactory() {
	        return new InstanceStepsFactory(configuration(),
	                new NameExtractorSteps());
	    }
	


}
