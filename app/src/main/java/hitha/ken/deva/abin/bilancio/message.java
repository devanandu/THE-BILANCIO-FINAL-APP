package hitha.ken.deva.abin.bilancio;

/**
 * Created by deva on 28/04/17.
 */

public class message {
    public String msg;
    public String sender;
    public String time;
    public message() {}

    public message(String msg,String sender,String time){
        this.msg= msg;
        this.sender=sender;
        this.time=time;
    }
}
// adb connect 192.168.0.2
