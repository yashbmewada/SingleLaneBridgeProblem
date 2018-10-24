import java.util.concurrent.Semaphore;

public class SemaphoreBridge implements Bridge{

	private static Semaphore serviceEnter;
	private static Semaphore serviceExit;
    private static Semaphore bridge;
    private static int[] waitingCars;
	private static int[] activeCars;
    
    public SemaphoreBridge(){
    	serviceEnter = new Semaphore(1, true);
    	serviceExit = new Semaphore(1, true);
    	bridge = new Semaphore(1, true);
    	waitingCars = new int[2];
	    activeCars = new int[2];
    }
	@Override
	public void arriveBridge(int direction, int carId) {
		// TODO Auto-generated method stub
        try {
			serviceEnter.acquire(1);
			waitingCars[direction]++;
	        if(activeCars[direction] ==0  || (activeCars[1-direction]> 0 &&waitingCars[1-direction] >0)){
	            bridge.acquire(1);
	        }
	        waitingCars[direction]--;
	        activeCars[direction]++;
	        System.out.printf(" car with id %d from %d direction is entering. \n  " , carId,direction);
	        serviceEnter.release(1);
	        
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void leaveBridge(int direction, int carId) {
		// TODO Auto-generated method stub
		try {
			serviceExit.acquire(1);
			activeCars[direction]--;
			if(activeCars[direction] == 0) {
	            bridge.release(1);
	        }
			System.out.printf(" car with id %d from %d direction is leaving. \n " , carId,direction );

	        serviceExit.release();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
