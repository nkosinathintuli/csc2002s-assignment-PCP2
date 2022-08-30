package typingTutor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.awt.FontMetrics;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
		private AtomicBoolean done ; //REMOVE
		private AtomicBoolean started ; //REMOVE
		private AtomicBoolean won ; //REMOVE

		private FallingWord[] words;
		private int noWords;
		private final static int borderWidth=25; //appearance - border
		private int maxY;

		GamePanel(FallingWord[] words, int maxY,	//maxY seem irrelevant *
				 AtomicBoolean d, AtomicBoolean s, AtomicBoolean w) {
			this.words=words; //shared word list
			this.maxY=maxY;
			noWords = words.length; //only need to do this once
			done=d; //REMOVE
			started=s; //REMOVE
			won=w; //REMOVE
		}
		
		public void paintComponent(Graphics g) {
		    int width = getWidth()-borderWidth*2;
		    int height = getHeight()-borderWidth*2;
		    g.clearRect(borderWidth,borderWidth,width,height);//the active space
		    g.setColor(Color.pink); //change colour of pen
		    g.fillRect(borderWidth,height,width,borderWidth); //draw danger zone

		    g.setColor(Color.black);
		    g.setFont(new Font("Arial", Font.PLAIN, 26));
		   //draw the words
		    if (!started.get()) {
		    	g.setFont(new Font("Arial", Font.BOLD, 26));
				g.drawString("Type all the words before they hit the red zone,press enter after each one.",borderWidth*2,height/2);	
		    	
		    }
		    else if (!done.get()) {
		    	for (int i=0;i<noWords-1;i++){	    	
		    		g.drawString(words[i].getWord(),words[i].getX()+borderWidth,words[i].getY());
					//if (i!=noWords-1) collide(words[i],i,g);
		    	
		    	}
				g.setColor(Color.green); //set the colour of the hungry word
		    	g.fillRect(borderWidth,height,0,borderWidth); 
				g.drawString(words[noWords-1].getWord(),words[noWords-1].getX()+borderWidth,words[noWords-1].getY());
		    	g.setColor(Color.lightGray); //change colour of pen
		    	g.fillRect(borderWidth,0,width,borderWidth);			
		   }
		   else { if (won.get()) {
			   g.setFont(new Font("Arial", Font.BOLD, 36));
			   g.drawString("Well done!",width/3,height/2);	
		   } else {
			   g.setFont(new Font("Arial", Font.BOLD, 36));
			   g.drawString("Game over!",width/2,height/2);	
		   }
		   }
		   
		}
		
		public int getValidXpos() {
			int width = getWidth()-borderWidth*4;
			int x= (int)(Math.random() * width);
			return x;
		}

		public int getValidYpos() {
			int height = getHeight()-borderWidth*4;
			int y= (int)(Math.random() * height);
			return y;
		}

		public void collide(FallingWord word, int pos,Graphics g) {
			FontMetrics fm = g.getFontMetrics(g.getFont());
			Rectangle2D bounds = fm.getStringBounds(word.getWord(), g);
			FallingWord theword = word;
			int deltaXa = (int)bounds.getWidth();
			int deltaYa = (int)bounds.getHeight();
			int Xa = word.getX();
			int Ya = word.getY();
			int Xa1 = Xa + deltaXa;
			int Ya1 = Ya + deltaYa;
			bounds = fm.getStringBounds(words[noWords-1].getWord(), g);
			int deltaXb  = (int)bounds.getWidth();
			int deltaYb = (int)bounds.getHeight();
			int Xb = words[noWords-1].getX();
			int Yb = words[noWords-1].getY();
			int Xb1 = Xb + deltaXb;
			int Yb1 = Yb + deltaYb;
			boolean collision = false;
			collision = (Xa1 >= Xb && Xa1 <= Xb1 && Ya1 >= Yb && Ya1 <= Yb1);
			collision = collision || (Xa >= Xb && Xa <= Xb1 && Ya1 >= Yb && Ya1 <= Yb1);
			collision = collision || (Xa1 >= Xb && Xa1 <= Xb1 && Ya >= Yb && Ya <= Yb1);
			collision = collision || (Xa >= Xb && Xa <= Xb1 && Ya >= Yb && Ya <= Yb1);
			
			//collision = (Xa1 >= Xb && Ya1 >= Yb || Xa <= Xb1 && Ya <= Yb1 || Xa1 >= Xb && Ya <= Yb1 || Xa <= Xb1 && Ya1 >= Yb);
			if(collision) {
				theword.setY(maxY+1);
				words[pos]= theword;
				//System.out.print(theword.getWord());
			}

		}
		
		public void run() {
			while (true) {
				repaint();
				try {
					Thread.sleep(10); 
				} catch (InterruptedException e) {
					e.printStackTrace();
				};
			}
		}

	}


