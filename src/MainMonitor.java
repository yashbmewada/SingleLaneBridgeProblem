import java.util.Scanner;


public class MainMonitor {

	public static void main(String[] args) throws InterruptedException {


        final MonitorBridge bridge = new MonitorBridge();

        System.out.println("Enter the values with id of car followed by the direction ( 0 or 1) :  ");

        Scanner scanner = new Scanner(System.in);

        Car[] cars = new Car[500];


        int carId;
        int direction;
        int numCars = 0;

        while (scanner.hasNextInt()) {
            carId = scanner.nextInt();
            direction = scanner.nextInt();
            cars[numCars] = new Car(carId, direction, bridge);
            numCars++;
        }

        for (Car newCar:cars) {
            if (newCar != null) {
                newCar.start();

            }
        }

        for( Car newCar:cars){
            if(newCar != null && newCar.isAlive()){
                newCar.join();
            }
        }

		
	}

}
