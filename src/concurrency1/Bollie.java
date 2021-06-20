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
				sleep(waitTime.nextInt(1000));
                                //Golfer.wait();
                                Golfer.cartOnField.set(true);
				System.out.println("*********** Bollie collecting balls   ************");	
				ballsCollected = new ArrayList<golfBall>();
                                ballsCollection = new ArrayList<golfBall>();
                                //collect balls, no golfers allowed to swing while this is happening
                                ballsCollection = sharedField.collectAllBallsFromField(ballsCollected);
				
                                noCollected = ballsCollection.size();
                                System.out.println("Number of balls collected from field: "+noCollected);
				sleep(1000);
				System.out.println("*********** Bollie adding balls to stash ************");	
                                
                                sharedStash.addBallsToStash(ballsCollected,noCollected);
                                System.out.println("Number of balls collected were "+noCollected);
                                //                     
                                Golfer.cartOnField.set(false);
                                notifyAll();
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		    		}
		}	
}
