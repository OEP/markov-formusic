package org.oep.markov.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import org.oep.markov.MarkovChain;

public class WordTest {
	public static void main(String [] args) {
		String url = "http://pk-fire.com/etc/wtf/words.txt";
		
		MarkovChain<Character> chain = new MarkovChain<Character>(2);
		
		try {
			URL uri = new URL(url);
			InputStream is = uri.openStream();
			Scanner s = new Scanner(is);
			
			while(s.hasNext()) {
				String str = s.next();
				ArrayList<Character> foo = new ArrayList<Character>();
				
				for(int i = 0; i < str.length(); i++) {
					foo.add(str.charAt(i));
				}
				
				chain.addPhrase(foo);
			}
			
			System.out.println("Some random words:");
			for(int i = 0; i < 10; i++) {
				String word = chain.makePhrase().toString().replaceAll("[\\[\\] ,]", "");
				System.out.println(word);
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
