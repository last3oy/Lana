package kmutt.senior.pet.activity;

/**
 * Created by book on 3/19/2016.
 */
public class Getdata {
    String Time;
    Integer Pulse;
    public void sTime(String vTime){
        this.Time = vTime;
    }
    public void sPulse(Integer vPulse){

        this.Pulse = vPulse;
    }


    // Get Value
    public String gTime(){
        return Time;
    }
    public Integer gPulse(){
        return Pulse;
    }

}
