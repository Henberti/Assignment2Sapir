package Task1.VehicleWash;
import java.io.File;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import Task1.ADT.Node;
import Task1.ADT.Queue;
import Task1.Vehicles.Vehicle;

@SuppressWarnings("unchecked")
public class VehicleWasher {//HEN BERTI 201381407 && ELIRAN BALAISH 207598467
    enum TYPE {Car, SUV, MiniBus, Truck}
    Queue<Vehicle> inLine, wash, after_wash[];
    Semaphore empty,full;
    Lock lock_inline, lock_wash, lock_after_wash, time_lock;
    long washing_time, line_time;
    VehicleLogger vLog;
    long startTime;
    AtomicInteger lineSize;
    long avgTime;

   


    //constractor
    public VehicleWasher(int lineSize,int machine_size, long washing_time, long line_time, File file){
        inLine = new Queue<>();
        wash = new Queue<>();
        
        after_wash = new Queue[4];
        for(int i=0; i<4; i++)
            after_wash[i] = new Queue<>();
        
        empty = new Semaphore(machine_size, true);
        full = new Semaphore(50,true);

        //locks for evry shaerd data
        lock_inline = new ReentrantLock();
        lock_wash = new ReentrantLock();
        lock_after_wash = new ReentrantLock();
        time_lock = new ReentrantLock();

        //
        this.washing_time = washing_time;
        this.line_time = line_time;
        vLog = new VehicleLogger();
        startTime = System.currentTimeMillis();
        this.lineSize = new AtomicInteger(lineSize);
        
    }
    //the method get's vehicle and push it to the line queue
    //using eche specipic lock to syncronized shared data
    //and print the vehicle propery and log it to the file
    public void inline(Vehicle vehicle) throws InterruptedException{
       
        lock_inline.lock();
            inLine.enQueue(vehicle);
        lock_inline.unlock();

        Thread.sleep(line_time);

        synchronized(vLog){
            System.out.println(vehicle+" Arrived");
            long time = getCurrentTime();
            vLog.write(vehicle, time, 1);
        }
        // System.out.println("line: "+inLine);
        
    }
    //thread that enter the method dequeue vehicle
    //only if there is washer machine that free
    //if not the thread will wait antil some wash maschine
    public void inWash() throws InterruptedException {
        empty.acquire();
        
        lock_inline.lock();
        Vehicle temp = inLine.deQueue();
        lock_inline.unlock();

        lock_wash.lock();
        wash.enQueue(temp);
        
        synchronized(vLog){
            System.out.println(temp+" in wash");
            long time = getCurrentTime();
            vLog.write(temp, time, 2);
        }
       //sleeping in wash
        lock_wash.unlock();
        Thread.sleep(washing_time);
      
    }
    //the method finish the process of washing
    //geting out from the queue and enqueue to the after wash queue
    //the last thread that left will sum all the average time of each type
    //of vihecle and close the log file
    public void Finish(){
        lock_wash.lock();
        Vehicle temp =wash.deQueue();
        synchronized(vLog){
            System.out.println(temp+" finish");
            long time = getCurrentTime();
            temp.setFinishTime(time);
            vLog.write(temp, time, 3);
        }
        lock_wash.unlock();
        empty.release();

        lock_after_wash.lock();
        after_wash[TYPE.valueOf(temp.getClass().getSimpleName()).ordinal()].enQueue(temp);
        lock_after_wash.unlock();

        int check = lineSize.decrementAndGet();
        if(check == 0){
            synchronized(vLog){
                vLog.closeWrite();
                for(int i=0; i<after_wash.length; i++){
                    avgTime = 0;
                    Integer counter = 0;
                    Node<Vehicle> first = after_wash[i].getFirst();
                    while(first.hasNext()){
                        counter++;
                        avgTime += first.getData().getFinishTime();
                        first = first.getNext();
                    }
                    String avgStr = after_wash[i].getFirst().getData().getClass().getSimpleName()+" Average Time: "+ avgTime/counter;
                    System.out.println("\n"+avgStr);
                }
            }
        } 
    }
    //returns the current time
    public long getCurrentTime(){
        long time;
        try{
        time_lock.lock();
        time = System.currentTimeMillis() - startTime;
        }finally{
        time_lock.unlock();
        }
        return time;
    }

}
