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
        // conditional variable to tell golfers when they can swing again ie. notify
        private static boolean cartJustLeft = false;
        
	private int myID;
	
	public static ArrayBlockingQueue<golfBall> golferBucket;
	private BallStash sharedStash; //link to shared stash
	private Range sharedField; //link to shared field
	private Random swingTime;
	
	
	
	Golfer(BallStash stash,Range field, Boolean cartFlag, Boolean doneFlag) {
		sharedStash = stash; //shared 
		sharedField = field; //shared               
		cartOnField.set(cartFlag); //shared
		done.set(doneFlag);
		golferBucket = new ArrayBlockingQueue<golfBall>(ballsPerBucket); //new bucket of golfballs for each golfer
                ///
                ///
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
            sharedStash.getBucketBalls(ballsPerBucket);
            while (done.get()!=true) {
                 
                            System.out.println(">>> Golfer #"+ myID + " trying to fill bucket with "+getBallsPerBucket()+" balls.");
                            
                            
                            //sharedStash.getBallsInStash();
                            System.out.println("<<< Golfer #"+ myID + " filled bucket with          "+getBallsPerBucket()+" balls"+" (remaining stash="+sharedStash.getBallsInStash()+")");

                      
                    for (int b=0;b<ballsPerBucket;b++)
                    { //for every ball in bucket
                        if (cartOnField.get()==true){ 
                            try {
                                // ie. If the cart is on the field
                                //System.out.println("This thread must wait");
                                synchronized(this){
                                    this.wait();
                                }                           
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Golfer.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        else{
                            try {
                                        sleep(swingTime.nextInt(2000));
                                        if (golferBucket.size()>0){ // ie. if there are any balls in  the bucket
                                            sharedField.hitBallOntoField(golferBucket.peek()); //hits ball onto field
                                            
                                        }
                                        else{ //
                                            //wait();
                                        }

                                        System.out.println("Golfer #"+ myID + " hit ball #"+golferBucket.peek().getID()+" onto field");	
                                        golferBucket.remove(golferBucket.peek()); //now the golf ball has to be removed from the bucket

                                } catch (InterruptedException e) {
                                        e.printStackTrace();
                                } //      swing
                            //!!wait for cart if necessary if cart there
                            if  (cartOnField.equals(true)){
                                //waiting();
                            }
                        }
                    }
            }
	}
        
        synchronized public void waiting(){
            try {
                this.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Golfer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //
        
        public void notifyThis(){
            synchronized(this){
                notify();
            }
        }
}
