package Task1.VehicleWash;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import Task1.Vehicles.Vehicle;

//the vehicle logger can read and write to the file 
//using printwriter and scanner
//whenever some vehicle ask it
public class VehicleLogger {//HEN BERTI 201381407 && ELIRAN BALAISH 207598467
    private PrintWriter pw;
    private Scanner s;


    public VehicleLogger(){
        File file = new File("log.txt");
        
        try {
            pw = new PrintWriter(file);
            s = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    public void closeWrite(){
        pw.close();
    }
    public void closeRead(){
        s.close();
    }
    //write to file
    public void write(Vehicle vehicle, long time, int status){
        switch(status){
            case 1:
                pw.println(time+"ms Arraived: "+vehicle);
                break;
            case 2:
                pw.println(time+"ms In wash: "+vehicle);
                break;
            case 3:
                pw.println(time+"ms Finish: "+vehicle);
                break;
        }
    }
    //read from the file
    public String read(){
        StringBuffer sb = new StringBuffer();
        while(s.hasNextLine()){
          sb.append(s.nextLine());
        }
        return sb.toString();
    }

    

}
