package golfGame;


import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class DrivingRangeApp extends Thread{
    
        private static BallStash sharedStash; //link to shared stash
	private static Range sharedField; //link to shared field
        //
        
        public DrivingRangeApp(){
            (new Thread(new Golfer(sharedStash, sharedField, false, false))).start();
        }
        public static void startBollie(){
            (new Thread(new Bollie(sharedStash, sharedField, false ))).start();
        }

	public static void main(String[] args) throws InterruptedException {
		AtomicBoolean done  = new AtomicBoolean(false);

		//read these in as command line arguments instead of hard coding
		int noGolfers =3;
		int sizeStash=20;
		int sizeBucket=5;
		BallStash.setSizeStash(sizeStash);
		BallStash.setSizeBucket(sizeBucket);
		Golfer.setBallsPerBucket(sizeBucket);
		
		//initialize shared variables
		
		System.out.println("=======   River Club Driving Range Open  ========");
		System.out.println("======= Golfers:"+noGolfers+" balls: "+sizeStash+ " bucketSize:"+sizeBucket+"  ======");
                
                //create threads and set them running
                for (int i = 0; i< noGolfers ;i++){ //creates the golfer threads
                    new DrivingRangeApp();
                }
                startBollie();
                
		//for testing, just run for a bit
		Thread.sleep(10000);// this is an arbitrary value - you may want to make it random
		Golfer.done.set(true);
                Bollie.done.set(true);
		System.out.println("=======  River Club Driving Range Closing ========");
                
                
	}

}
