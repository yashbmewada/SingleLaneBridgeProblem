import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorBridge implements Bridge {

    private static final long startTime = System.nanoTime();


    static int totalEvents;
	 static int[] waitingCars;
	 static int[] activeCars;
	 Lock bridgeLock = new ReentrantLock(true);
	 Condition[] waitingCondition;



	 public MonitorBridge(){
	     this.totalEvents = 0;
	     waitingCars = new int[2];
	     activeCars = new int[2];
	     waitingCondition = new Condition[]{bridgeLock.newCondition(),bridgeLock.newCondition()};

     }




	@Override
	public synchronized void arriveBridge(int direction,int carId) {

	     long enterTime;
	     bridgeLock.lock();


	     try{
	         while(activeCars[1-direction] > 0  || waitingCars[1-direction] != 0){
	             waitingCars[direction]++;
	             waitingCondition[direction].await();
	             waitingCars[direction]--;

             }


             activeCars[direction]++;
	         enterTime = System.nanoTime() - startTime;

             System.out.printf(" car with id %d from %d direction is entering. \n  " , carId,direction);
         }catch (InterruptedException e){
             System.out.println("Threads interrupted");
         }
         finally {
            // System.out.println("in finally");
	         bridgeLock.unlock();
             //System.out.println(" in finally after unlock lock");

         }


		
	}

	@Override
	public synchronized void leaveBridge(int direction,int carId) {
       // System.out.println(" before leave lock");
            bridgeLock.lock();
        //System.out.println(" after leave lock");


        try{
                activeCars[direction]--;
                if(activeCars[direction]==0){
                    if(waitingCars[1-direction] != 0){
                        waitingCondition[1-direction].signal();
                    }
                }
                System.out.printf(" car with id %d from %d direction is leaving. \n " , carId,direction );

            }finally {
                bridgeLock.unlock();
            }


	}


	public synchronized  void addCarToBridge(){
	     totalEvents++;
    }




}
