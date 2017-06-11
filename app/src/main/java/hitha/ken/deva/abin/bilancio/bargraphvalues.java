package hitha.ken.deva.abin.bilancio;

/**
 * Created by deva on 03/05/17.
 */

public class bargraphvalues {
    int inc;
    int exp;
    String month;
    public bargraphvalues() {}

    public bargraphvalues(int msg,int sender,String time) {
        this.inc = msg;
        this.exp = sender;
        this.month = time;
    }
}