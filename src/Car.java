public class Car extends  Thread{

    int carId;

    int direction;

    Bridge bridge;

    public Car(int carId, int direction, Bridge bridge){

        this.carId = carId;
        this.direction = direction;
        this.bridge = bridge;
    }


    @Override
    public void run() {
        bridge.arriveBridge(this.direction,this.carId);
        int i=0;
        while(i< 100000){
            i++;
        }

        bridge.leaveBridge(this.direction,this.carId);
    }
}
