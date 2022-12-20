package Task1.Vehicles;
import Task1.VehicleWash.VehicleWasher;

public abstract class Vehicle implements Runnable {//HEN BERTI 201381407 && ELIRAN BALAISH 207598467
    private final int LICENCE;
    private final VehicleWasher WASHER;
    long finishTime;


    public Vehicle(int LICENCE_number, VehicleWasher vehicleWasher){
        this.LICENCE = LICENCE_number;
        this.WASHER = vehicleWasher;
    }

    @Override
    public String toString() {
        return "TYPE: "+this.getClass().getSimpleName()+ " LICENCE: "+LICENCE;
    }

    //the run method start all 3 method from the vehicle washer 
    //getting in line washing and finishing
    @Override
    public void run() {
       try {
        WASHER.inline(this);
        WASHER.inWash();
        WASHER.Finish();
        
    } catch (InterruptedException e) {
        
        e.printStackTrace();
    }
        
    }
    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }
    public long getFinishTime() {
        return finishTime;
    }
  




    
}
