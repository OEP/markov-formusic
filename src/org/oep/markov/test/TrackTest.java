package org.oep.markov.test;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiFileFormat;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

import org.oep.markov.MarkovTrack;

public class TrackTest {
	public static void main(String [] args) {
		
		for(int i = 15; i <= 90; i++) {
			MarkovTrack track = new MarkovTrack(5);
			File f = new File("smbtheme.mid");
			try {
				Sequence s = MidiSystem.getSequence(f);
				Track[] tracks = s.getTracks();
//				track.learnSequence(s)
				track.learnTrack(tracks[1]);
				MidiFileFormat fmt = MidiSystem.getMidiFileFormat(f);
				
				System.out.printf("Writing output-%d.mid...\n", i);
				track.exportTrack(String.format("output-%d.mid",i), fmt.getDivisionType(), fmt.getResolution(), fmt.getType());
			} catch (InvalidMidiDataException e) {
			} catch (IOException e) {
			} catch (OutOfMemoryError e) {
				System.err.println("\tFAIL");
				e.printStackTrace();
			}	
		}
		
	}
}
