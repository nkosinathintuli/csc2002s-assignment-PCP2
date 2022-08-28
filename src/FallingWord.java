package typingTutor;

public class FallingWord {
	private String word; // the word
	private int x; //position - width
	private int y; // postion - height
	private int maxX; //max width *
	private int maxY; //maximum height
	private boolean dropped; //flag for if user does not manage to catch word in time
	private boolean drifted; //flag for if word has drifted off the screen to the right *
	private boolean isHungry;

	private int fallingSpeed; //how fast this word is
	private static int maxWait=1000;
	private static int minWait=100;

	public static WordDictionary dict;
	
	FallingWord() { //constructor with defaults
		word="computer"; // a default - not used
		x=0;
		y=0;	
		maxY=300;
		maxX=500;
		dropped=false;
		drifted=false;
		isHungry=false;
		fallingSpeed=(int)(Math.random() * (maxWait-minWait)+minWait); 
	}
	
	FallingWord(String text) { 
		this();
		this.word=text;
	}
	
	FallingWord(String text,int x, int maxY) { //most commonly used constructor - sets it all.
		this(text);
		this.x=x; //only need to set x, word is at top of screen at start
		this.maxY=maxY;
	}

	FallingWord(String text, int x, int maxY, int y, int maxX, boolean isHungry){ // exetended constructor *
		this(text,x,maxY);
		this.y=y;
		this.maxX=maxX;
		this.isHungry=isHungry;
	}
	
	public static void increaseSpeed( ) {
		minWait+=50;
		maxWait+=50;
	}
	
	public static void resetSpeed( ) {
		maxWait=1000;
		minWait=100;
	}
	

// all getters and setters must be synchronized
	public synchronized  void setY(int y) {
		if (y>maxY) {
			y=maxY;
			dropped=true; //user did not manage to catch this word
		}
		this.y=y;
	}
	
	public synchronized  void setX(int x) {
		if (x>maxX) {
			x=maxX;
			drifted=true; //word has drifted off the screen to the right
		}
		this.x=x;
	}
	
	public synchronized  void setWord(String text) {
		this.word=text;
	}

	public synchronized  String getWord() {
		return word;
	}
	
	public synchronized  int getX() {
		return x;
	}	
	
	public synchronized  int getY() {
		return y;
	}
	
	public synchronized  int getSpeed() {
		return fallingSpeed;
	}

	public synchronized void setPos(int x, int y) {
		setY(y);
		setX(x);
	}
	public synchronized void resetPos() {
		if (isHungry) {
			setX(0);
		} else {
			setY(0);
		}
	}

	public synchronized void resetWord() {
		resetPos();
		word=dict.getNewWord();
		dropped=false;
		drifted=false;
		fallingSpeed=(int)(Math.random() * (maxWait-minWait)+minWait); 
		//System.out.println(getWord() + " falling speed = " + getSpeed());
	}
	
	public synchronized boolean matchWord(String typedText) {
		//System.out.println("Matching against: "+text);
		if (typedText.equals(this.word)) {
			// resetWord(); //we don't want to reset the word here, we want to check for a duplicate first in the CatchWord class
			return true;
		}
		else
			return false;
	}

	public synchronized  void drop(int inc) {
		setY(y+inc);
	}

	public synchronized  void drift(int inc) { // move word to the right *
		setX(x+inc);
	}
	
	public synchronized  boolean dropped() {
		return dropped;
	}

	public synchronized  boolean drifted() { // check if word has drifted off the screen to the right *
		return drifted; 
	}

}
