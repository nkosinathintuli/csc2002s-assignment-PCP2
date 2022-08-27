package typingTutor;

import java.util.concurrent.atomic.AtomicBoolean;

//Thread to monitor the word that has been typed.
public class CatchWord extends Thread {
	String target;
	static AtomicBoolean done ; //REMOVE
	static AtomicBoolean pause; //REMOVE
	
	private static  FallingWord[] words; //list of words
	private static int noWords; //how many
	private static Score score; //user score
	
	CatchWord(String typedWord) {
		target=typedWord;
	}
	
	public static void setWords(FallingWord[] wordList) {
		words=wordList;	
		noWords = words.length;
	}
	
	public static void setScore(Score sharedScore) {
		score=sharedScore;
	}
	
	public static void setFlags(AtomicBoolean d, AtomicBoolean p) {
		done=d;
		pause=p;
	}
	
	public void run() {
		int i=0;
		while (i<noWords) {		
			while(pause.get()) {};
			if (words[i].matchWord(target)) {
				FallingWord match = words[i]; //save the matched falling word and look for a duplicate
				while(i<noWords){
					if(words[i].matchWord(target)){ // continue to look for another match
						if(match.getY()<words[i].getY()){
							match=words[i]; //save the one that is lower on the screen
							//break; //stop looking for duplicates as soon as you find the first one
						}
					}
					i++;
				}
				match.resetWord(); // reseting the falling word here instead of in the matchWord method in the falling word class
				System.out.println( " score! '" + target); //for checking
				score.caughtWord(target.length());	
				//FallingWord.increaseSpeed();
				break;
			}
		   i++;
		}
		
	}	
}
