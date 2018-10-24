import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorBridge implements Bridge {
	
	 private static int totalEvents;
	 private static int[] waitingCars;
	 private static int[] activeCars;
	 private Lock bridgeLock = new ReentrantLock(true);
	 private Condition[] waitingCondition;



	 public MonitorBridge(){
	     this.totalEvents = 0;
	     waitingCars = new int[2];
	     activeCars = new int[2];
	     waitingCondition = new Condition[]{bridgeLock.newCondition(),bridgeLock.newCondition()};

     }




	@Override
	public synchronized void arriveBridge(int direction) {

	     bridgeLock.lock();


	     try{
	         while(activeCars[1-direction] > 0  || waitingCars[1-direction] != 0){
	             waitingCars[direction]++;
	             waitingCondition[direction].await();
	             waitingCars[direction]--;

             }

             activeCars[direction]++;
         }catch (InterruptedException e){
             System.out.println("Threads interrupted");
         }
         finally {
	         bridgeLock.unlock();
         }


		
	}

	@Override
	public synchronized void leaveBridge(int direction) {
            bridgeLock.lock();

            try{
                activeCars[direction]--;
                if(activeCars[direction]==0){
                    if(waitingCars[1-direction] != 0){
                        waitingCondition[1-direction].signal();
                    }
                }

            }finally {
                bridgeLock.unlock();
            }


	}


	public synchronized  void addCarToBridge(){
	     totalEvents++;
    }




}
