package com.luca.exercise;

public abstract class TokeniserFactory {

	public static Tokeniser getTokeniser(TokeniserType tokeniser) {
        Tokeniser tk = null;
        switch (tokeniser) {
        case WHITESPACE:
            tk = new WhitespaceTokeniser();
            break;
 
        default:
            // TODO:throw some unsupported tokeniser exception
            break;
        }
        return tk;
    }

}


