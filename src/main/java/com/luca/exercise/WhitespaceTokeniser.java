package com.luca.exercise;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WhitespaceTokeniser extends Tokeniser {

	@Override
	protected List<String> tokenise(String str) {
		
		return Arrays.asList(str.split("\\s")).stream().map(s->s.trim()).collect(Collectors.toList()); 
	}

}
