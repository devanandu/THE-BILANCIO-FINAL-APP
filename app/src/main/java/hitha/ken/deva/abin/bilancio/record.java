package hitha.ken.deva.abin.bilancio;

/**
 * Created by deva on 01/05/17.
 */

public class record {
    String cat;
    String note;
    String amt;
    String type;
    String time;
    public record() {}

    public record(String cat,String note,String amt,String type, String time){
        this.amt= amt;
        this.cat=cat;
        this.note=note;
        this.type=type;
        this.time=time;
    }
}
