package com.luca.exercise;

import java.util.List;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;

/**
 * Utility type that defines binary features that are used to calculate the
 * conditional probability of a single word to belong to a given class.
 * 
 * The features are implemented as functional interfaces in order to be able to
 * use them in lambda expression whenever we require stream processing of the
 * tokens.
 * 
 * @author luca
 *
 */

public class Features {

	/**
	 * Feature #5
	 * 
	 * Checks if a word starts with a lowercase letter
	 * 
	 * @return int
	 */
	public static ToIntFunction<String> startsWithLowercase() {
		return s -> (Character.isLowerCase(s.charAt(0))) ? 1 : 0;
	}

	/**
	 * Feature #6
	 * 
	 * Checks if a word starts with a capital letter
	 * 
	 * @return int
	 */
	public static ToIntFunction<String> startsWithCapital() {
		return s -> (Character.isUpperCase(s.charAt(0))) ? 1 : 0;
	}

	/**
	 * Feature #4
	 * 
	 * Checks if a word contains an apostrophe
	 * 
	 * @return int
	 */
	public static ToIntFunction<String> containsApostrophe() {
		return s -> (s.contains("'")) ? 1 : 0;
	}

	/**
	 * Feature #1
	 * 
	 * Checks if the previous word start with a capital letter
	 * 
	 * @return int
	 */
	public static ToIntBiFunction<String, String> previousStartsWithCapital() {
		return (a, b) -> (Character.isUpperCase(a.charAt(0)) && Character
				.isUpperCase(b.charAt(0))) ? 1 : 0;
	}

	/**
	 * Feature #2
	 * 
	 * Checks if the word id precede by an English salutation
	 * 
	 * @return int
	 */
	public static ToIntBiFunction<String, String> isPrecededBySalutation() {
		return (a, b) -> (a.replace(".", "").toUpperCase()
				.matches("MR|MS|MISS|MRS|DR")) ? 1 : 0;
	}

	/**
	 * Feature #3
	 * 
	 * Checks if the word is preceded by single capilat letter followed by a dot
	 * 
	 * @return int
	 */
	public static ToIntBiFunction<String, String> isPrecededBySingleCapLetter() {
		return (a, b) -> (a.matches("[A-Z]\\.")) ? 1 : 0;
	}

	/**
	 * Feature #6
	 * 
	 * Checks if a word contains a Dash
	 * 
	 * @return int
	 */
	public static ToIntFunction<String> containsDash() {
		return s -> (s.contains("-")) ? 1 : 0;
	}

	/**
	 * Feature #7
	 * 
	 * Checks if a word is part of the list of known names
	 * 
	 * @return int
	 */
	public static ToIntFunction<String> isAKnownName(List<String> knownNames) {
		return s -> (knownNames.contains(s.toLowerCase())) ? 1 : 0;
	}

	/**
	 * Feature #8
	 * 
	 * Checks if a token is followed by an English postfix
	 * 
	 * @return int
	 */
	public static ToIntBiFunction<String, String> isFollowedByPostfix() {
		return (a, b) -> (b.toUpperCase().matches("JR|SR|MD")) ? 1 : 0;
	}

	/**
	 * Feature #9
	 * 
	 * Check if a token ends in "ing".
	 * 
	 * 
	 * @return int
	 */
	public static ToIntFunction<String> endsInIng() {
		return s -> (s.toUpperCase().matches(".*?ING$")) ? 1 : 0;
	}

	/**
	 * Feature #10
	 * 
	 * Checks if a token is a single letter
	 * 
	 * @return int
	 */
	public static ToIntFunction<String> isSingleLetter() {
		return s -> (s.matches("[A-Z]")) ? 1 : 0;
	}

	/**
	 * Feature #11
	 * 
	 * Check if a token is preceded by a name that is on the list of known names
	 * 
	 * @return int
	 */
	public static ToIntBiFunction<String, String> isPrecededByKnownName(
			List<String> knownNames) {
		return (a, b) -> (isAKnownName(knownNames).applyAsInt(a) == 1) ? 1 : 0;
	}

	/**
	 * Feature #12
	 * 
	 * Checks if a token is an English name postfix
	 * 
	 * @return int
	 */
	public static ToIntFunction<String> isEnglishNAMEPostfix() {
		return (a) -> (a.toUpperCase().matches("JR|SR|MD")) ? 1 : 0;
	}

}
