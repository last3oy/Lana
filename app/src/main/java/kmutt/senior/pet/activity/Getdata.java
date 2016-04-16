package kmutt.senior.pet.activity;

/**
 * Created by book on 3/19/2016.
 */
public class Getdata {
    public String Time;
    public String Name;
    public String Date;
    public String D;
    public Integer Pulse;

    public Getdata(){

    }
    public void sName(String vName){
        this.Name = vName;
    }
    public void sDate(String vDate){
        this.Date = vDate;
    }
    public void sD(String vD){
        this.D = vD;
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
    public String gD(){
        return D;
    }
    public String gTime(){
        return Time;
    }

    public Integer gPulse(){
        return Pulse;
    }

}
