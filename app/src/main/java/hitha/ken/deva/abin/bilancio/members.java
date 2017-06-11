package hitha.ken.deva.abin.bilancio;

/**
 * Created by deva on 27/04/17.
 */

public class members {
    public String name;
    public String phno;
    public String status;
    public members() {}

    public members(String msg,String sender,String time){
        this.name= msg;
        this.phno=sender;
        this.status=time;
    }

}
