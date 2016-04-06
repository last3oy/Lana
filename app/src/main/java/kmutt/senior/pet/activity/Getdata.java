package kmutt.senior.pet.activity;

/**
 * Created by book on 3/19/2016.
 */
public class Getdata {
    String Time;
    String Name;
    String Date;
    Integer Pulse;
    public void sName(String vName){
        this.Name = vName;
    }
    public void sDate(String vDate){
        this.Name = vDate;
    }
    public void sTime(String vTime){
        this.Time = vTime;
    }
    public void sPulse(Integer vPulse){

        this.Pulse = vPulse;
    }


    // Get Value
    public String gName(){
        return Name;
    }
    public String gDate(){
        return Date;
    }
    public String gTime(){
        return Time;
    }

    public Integer gPulse(){
        return Pulse;
    }

}
