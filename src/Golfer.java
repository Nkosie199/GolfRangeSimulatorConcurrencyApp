package golfGame;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Golfer extends Thread {
	//remeber to ensure thread saftey
	
	public static AtomicBoolean done = new AtomicBoolean(false); 
	public static AtomicBoolean cartOnField = new AtomicBoolean(false);
	
	public static int noGolfers; //shared amoungst threads
	public static int ballsPerBucket=5; //shared amoungst threads
	//
        
	private int myID;
	
	public static ArrayList<golfBall> golferBucket;
	private BallStash sharedStash; //link to shared stash
	private Range sharedField; //link to shared field
	private Random swingTime;
	
	
	
	Golfer(BallStash stash,Range field, Boolean cartFlag, Boolean doneFlag) {
		sharedStash = stash; //shared 
		sharedField = field; //shared               
		cartOnField.set(cartFlag); //shared
		done.set(doneFlag);
		golferBucket = new ArrayList<golfBall>(); //new bucket of golfballs for each golfer
		swingTime = new Random(); //when a golfer swings
		myID=newGolfID();                    
	}

	public static int newGolfID() { 
		noGolfers++;
		return noGolfers;
	}	
	public static void setBallsPerBucket (int noBalls) {
		ballsPerBucket=noBalls;
	}
	public static int getBallsPerBucket () {
		return ballsPerBucket;
	}
	public void run() {      
            while (done.get()!=true) {             
                
                try {
                    synchronized(this){
                        if (sharedStash.getBallsInStash()>5){//if the stash has at least 5 balls
                            System.out.println(">>> Golfer #"+ myID + " trying to fill bucket with "+getBallsPerBucket()+" balls.");
                            synchronized(this){
                                sharedStash.getBucketBalls(getBallsPerBucket());
                            }              
                            System.out.println("<<< Golfer #"+ myID + " filled bucket with          "+getBallsPerBucket()+" balls"+" (remaining stash="+sharedStash.getBallsInStash()+")");
                        }   
                        else{
                            //sleep((long) 100);
                        }
                    }
                    
                } catch (InterruptedException ex) {
                    Logger.getLogger(Golfer.class.getName()).log(Level.SEVERE, null, ex);
                }             
                     //for every ball in bucket
                        if (cartOnField.equals(true)){ 
                            try {
                                // ie. If the cart is on the field
                                //System.out.println("This thread must wait");                     
                                 wait();                                                         
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Golfer.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        else{
                            
                            for (golfBall g: golferBucket){
                                try {
                                    sleep(swingTime.nextInt(2000));
                                    golfBall ball = g;
                                    sharedField.hitBallOntoField(ball); //hits ball onto field
                                    System.out.println("Golfer #"+ myID + " hit ball #"+ball.getID()+" onto field");	
                                    golferBucket.remove(ball); //now the golf ball has to be removed from the bucket
                                    //
                                    //System.out.println("");

                                } catch (InterruptedException e) {
                                        e.printStackTrace();
                                } //      swing
                            //!!wait for cart if necessary if cart there
                            }
                        }
                    
            }
	}
}
