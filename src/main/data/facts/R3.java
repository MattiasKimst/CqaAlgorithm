package main.data.facts;

import main.data.annotation.PrimaryKey;

public class R3 implements Fact {

    @PrimaryKey
    public String y;
    public String v;

    public R3() {
    }

    public void setV(String v){
        this.v = v;
    }

    @Override
    public String getPrimaryKey() {
        return y;
    }

    @Override
    public String toString() {
        return "R3{" +
                "y='" + y + '\'' +
                ", v='" + v + '\'' +
                '}';
    }

}
