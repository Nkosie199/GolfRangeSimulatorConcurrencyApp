package golfGame;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Range {
	private static int sizeStash=20;
        //
        private static BallStash stash;
        private static int noOfBalls; 

	//ADD variable: ballsOnField collection;
        private static BlockingQueue <golfBall> ballsOnField = new ArrayBlockingQueue <golfBall> (sizeStash);
        
	//ADD method: collectAllBallsFromField(golfBall [] ballsCollected)
        synchronized public static ArrayList<golfBall> collectAllBallsFromField(ArrayList<golfBall> ballsCollected){
            //cartOnField.set(true);
            //int i = 0;
            for (golfBall ball : ballsOnField){ //for each ball in balls collected, remove the ball from bellsOnField
                ballsCollected.add(ball);
                ballsOnField.remove(ball);
                //i++;
            }
            return ballsCollected;
            //cartOnField.set(false);
        }
        
        public static int getNoBallsOnField(){
            return ballsOnField.size();
        }

	//ADD method: hitBallOntoField(golfBall ball)
        synchronized public static void hitBallOntoField(golfBall ball){
            ballsOnField.add(ball);
            //noOfBalls = stash.getSizeBucket();         
        }
        //
        
}
