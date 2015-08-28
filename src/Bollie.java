package golfGame;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Bollie extends Thread{ //Bollie is a thread

	public static AtomicBoolean done = new AtomicBoolean();  // flag to indicate when threads should stop
        //
        private static int noCollected;
        public static ArrayList<golfBall> ballsCollected;
        public static ArrayList<golfBall> ballsCollection;
	//
	private BallStash sharedStash; //link to shared stash
	private Range sharedField; //link to shared field
	private Random waitTime;

	//link to shared field
	Bollie(BallStash stash,Range field,Boolean doneFlag) {
		sharedStash = stash; //shared
		sharedField = field; //shared
		waitTime = new Random();
                done.set(doneFlag);
	}
	
	
	public void run() {
		//while True
		while (done.get()!=true) {
			try { //
				//sleep(waitTime.nextInt(10));
                                Golfer.cartOnField.set(true);
                                for (Thread t: DrivingRangeApp.golfers){
                                    t.wait();
                                }
				System.out.println("*********** Bollie collecting balls   ************");
				ballsCollected = new ArrayList<golfBall>(); //empty list
                                ballsCollection = new ArrayList<golfBall>(); 
                                //collect balls, no golfers allowed to swing while this is happening
                                ballsCollection = sharedField.collectAllBallsFromField(ballsCollected); //method of Bollie collecting balls from Range
				
                                noCollected = ballsCollection.size();
                                sharedStash.addBallsToStash(ballsCollection,noCollected); //method of Bollie adding balls to BallStash
				System.out.println("*********** Bollie adding "+noCollected +" balls to stash ************");	                             
                                //                     
                                Golfer.cartOnField.set(false);
                                for (Thread t: DrivingRangeApp.golfers){
                                    if (t.getState() == Thread.State.WAITING || t.getState() == Thread.State.TIMED_WAITING){
                                        t.notify();
                                        //break;
                                    }
                                }
                                //Golfer.notifyAll();
				
			} 
                        catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			} 
		    		}
		}	
}
