package com.luca.exercise;

import static org.junit.Assert.*;


import java.util.List;

import org.junit.Test;


public class TestTokenisers {
	
	Tokeniser tk;
	
	List<String> tokens;

	@Test
	public void testWhitespaceTokeniser() {
		
		 Tokeniser tk = TokeniserFactory.getTokeniser(TokeniserType.WHITESPACE);
		 
		 tokens=tk.tokenise("a b cd e fg");
		 
		 assertEquals(5,tokens.size());
	}
	


}
