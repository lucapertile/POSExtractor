package com.luca.exercise;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestFeatures {
	
	@BeforeClass
	public static void setUp() throws IOException {
		
        DataLoader.loadNameCorpus("NAMES.txt");
    }
	

	@Test
	public void testStartsWithCapital() {
		assertEquals(1, Features.startsWithCapital().applyAsInt("Luca"));
		assertEquals(0, Features.startsWithCapital().applyAsInt("luca"));
	}

	@Test
	public void testPreviousWordStartsWithCapital() {

		assertEquals(
				1,
				Features.previousStartsWithCapital().applyAsInt("Testname",
						"TestSurname"));

		assertEquals(
				0,
				Features.previousStartsWithCapital().applyAsInt("luca",
						"Pertile"));
	}

	@Test
	public void testIsPrecededBySalutation() {

		assertEquals(1,
				Features.isPrecededBySalutation().applyAsInt("Mr", "test"));

		assertEquals(1,
				Features.isPrecededBySalutation().applyAsInt("mr", "test"));

		assertEquals(0,
				Features.isPrecededBySalutation().applyAsInt("Md", "test"));
	}

	@Test
	public void testIsPrecededBySingleCapLetter() {

		assertEquals(1,
				Features.isPrecededBySingleCapLetter().applyAsInt("J.", "test"));

		assertEquals(1,
				Features.isPrecededBySingleCapLetter().applyAsInt("D.", "test"));

		assertEquals(0,
				Features.isPrecededBySingleCapLetter().applyAsInt("kk", "test"));
	}
	
	@Test
	public void testEndInIng() {

		assertEquals(1,
				Features.endsInIng().applyAsInt("flying"));

		assertEquals(0,
				Features.endsInIng().applyAsInt("ingenieur"));

		assertEquals(0,
				Features.endsInIng().applyAsInt("test"));
	}
	
	@Test
	public void testIsEnglishNamePostfix() {

		assertEquals(0,
				Features.isEnglishNAMEPostfix().applyAsInt("test"));

		assertEquals(1,
				Features.isEnglishNAMEPostfix().applyAsInt("JR"));

		
	}
	
	@Test
	public void testIsPrecededByKnownName() {

		assertEquals(1,
				Features.isPrecededByKnownName(DataLoader.getNamesCorpus()).applyAsInt("charles","boudelaire"));
		
		assertEquals(0,
				Features.isPrecededByKnownName(DataLoader.getNamesCorpus()).applyAsInt("test","boudelaire"));

	
	}


}
