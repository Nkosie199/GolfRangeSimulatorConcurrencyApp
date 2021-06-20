package golfGame;


public class golfBall {
	//add mechanisms for thread saftey
	private static int noBalls;
	private int myID;
	
	golfBall() {
		myID=noBalls;
		incID();
	}
	
	synchronized public int getID() {
		return myID;		
	}
	
	synchronized private static void  incID() {
		noBalls++;
	}
	
}
