package typingTutor;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

import typingTutor.FallingWord;

public class HungryWordMover extends Thread {
	private FallingWord myWord;
	private AtomicBoolean done;
	private AtomicBoolean pause; 
	private Score score;
	CountDownLatch startLatch; //so all can start at once
	
	HungryWordMover( FallingWord word) {
		myWord = word;
	}
	
	HungryWordMover( FallingWord word,WordDictionary dict, Score score,
			CountDownLatch startLatch, AtomicBoolean d, AtomicBoolean p) {
		this(word);
		this.startLatch = startLatch;
		this.score=score;
		this.done=d;
		this.pause=p;
	}

	public void pause() {
		int ms = 1000*10*1;
		try {
			sleep(ms);
			System.out.println("Paused for " + ms + " milliseconds");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};	
	}
	
	public void run() {

		//System.out.println(myWord.getWord() + " falling speed = " + myWord.getSpeed());
		
		try {
			System.out.println(myWord.getWord() + " waiting to start (hungryword)" );
			startLatch.await();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} //wait for other threads to start

		System.out.println(myWord.getWord() + " started (hungryword)" );
		String myWordString = myWord.getWord();
		while (!done.get()) {				
			//animate the word
			while (!myWord.drifted() && !done.get()) {
				    myWord.drift(10);
					try {
						sleep(myWord.getSpeed());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};		
					while(pause.get()&&!done.get()) {};
					// if (!myWordString.equals(myWord.getWord())) {
					// 	pause();
					// 	myWordString = myWord.getWord();
					// }
			}
			
			if (!done.get() && myWord.drifted()) {
				score.missedWord();
				myWord.resetWord();
			}
			myWord.resetWord();
		}
	}
	
}
