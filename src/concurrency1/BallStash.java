package golfGame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BallStash {
	//static variables
	private static int sizeStash=20;
	private static int sizeBucket=4;
        
	//ADD variables: a collection of golf balls, called stash
	private static ArrayList<golfBall> stash = new ArrayList <golfBall>();
	
	//ADD methods:
	//getBucketBalls
        public static void getBucketBalls(int sizeBucket){ //fills golfers' buckets with balls from the stash
            for (int i=0; i<sizeBucket; i++){
                try{
                   Golfer.golferBucket.add(stash.get(i)); 
                }
                catch(Exception e){
                    System.out.println("Error in adding to golfer bucket");
                }
                
            }
            for (int i=0; i<sizeBucket; i++){
                stash.remove(i);
            }
        }
        
	// addBallsToStash
        public static void addBallsToStash(ArrayList<golfBall> balls, int ballsCollected){
            for (int i=0; i<ballsCollected; i++){
                stash.add(balls.get(i));
            }           
        }
        public static void removeBallsFromStash(golfBall[] balls, int ballsCollected){
            
        }
        
	// getBallsInStash - return number of balls in the stash
        public static int getBallsInStash(){
            return stash.size();
        }
	
	
	//getters and setters for static variables - you need to edit these
	public static  void setSizeBucket (int noBalls) {
		sizeBucket=noBalls;
                
	}
	public static int getSizeBucket () {
		return sizeBucket;
	}
	public static void setSizeStash (int noBalls) {
		sizeStash=noBalls;
                for (int i=0; i<noBalls; i++){
                    stash.add(new golfBall()); //creates golfBalls in the stash
                }               
	}
	public static int getSizeStash () {
		return sizeStash;
	}
	
	
}
