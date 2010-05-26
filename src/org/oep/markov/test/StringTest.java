package org.oep.markov.test;

import java.util.ArrayList;

import org.oep.markov.MarkovChain;

public class StringTest {
	public static void main(String [] args) {
		String phrase1[] = "a b c d e f g h i j k l m n o p q r s t u v w x y z".split(" ");
		String phrase2[] = "m n c d".split(" ");
		
		MarkovChain<String> chain = new MarkovChain<String>(2);
		chain.addPhrase(phrase1);
		chain.addPhrase(phrase2);
//		chain.addPhrase(phrase3);
//		chain.addPhrase(phrase4);
		
		long start = System.currentTimeMillis();
		ArrayList<String> phrase = chain.makePhrase();
		long dt = System.currentTimeMillis() - start;
		
		for(String word : phrase) {
			System.out.print(word + " ");
		}
		System.out.println();
		
		System.out.println(dt + " ms");
	}
}
