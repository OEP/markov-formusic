package org.oep.markov.test;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiFileFormat;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import org.oep.markov.MarkovTrack;

public class TrackTest {
	public static final byte[] notes = {0x3C, 0x3E, 0x40, 0x41, 0x43};
	
	private static void makeSong(String filename)
	throws InvalidMidiDataException, MidiUnavailableException, IOException {
		Sequence s = new Sequence(Sequence.PPQ, 96);
		Track t = s.createTrack();
		Receiver rcr = MidiSystem.getReceiver();
		
		long ticker = 200;
		
		for(int i = 0; i < notes.length; i++) {
			ShortMessage playMsg = new ShortMessage();
			ShortMessage stopMsg = new ShortMessage();
			playMsg.setMessage(ShortMessage.NOTE_ON, 0, notes[i], 0x40);
			stopMsg.setMessage(ShortMessage.NOTE_OFF, 0, notes[i], 0x64);
			
			rcr.send(playMsg, i * ticker);
			rcr.send(stopMsg, (i+1) * ticker);
			
			t.add(new MidiEvent(playMsg, i * ticker));
			t.add(new MidiEvent(stopMsg, (i+1) * ticker));
		}
		
		MidiSystem.write(s, 1, new File(filename));
	}
	
	public static void main(String [] args) {
		String input = "input.mid";
		String other = "smbtheme.mid";
		
		try {
			makeSong(input);
		} catch (InvalidMidiDataException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 30; i >= 1; i--) {
			MarkovTrack track = new MarkovTrack(i);
			File f = new File(other);
			try {
				Sequence s = MidiSystem.getSequence(f);
				Track[] tracks = s.getTracks();
//				track.learnSequence(s);
				MidiFileFormat fmt = MidiSystem.getMidiFileFormat(f);
				track.learnTrack(tracks[1], fmt);
				
				System.out.printf("Writing output-%d.mid...\n", i);
				System.out.println("Resolution: " + fmt.getResolution());
				System.out.println("Type: " + fmt.getType());
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
