package main.data.facts;

import main.data.annotation.PrimaryKey;

public class R1 implements Fact {

    @PrimaryKey
    public String x;
    public String y;
    public String z;

    public R1() {
    }

    public void setY(String y) {
        this.y = y;
    }

    public void setX(String x) {
        this.x = x;
    }

    @Override
    public String getPrimaryKey() {
        return x;
    }

    @Override
    public String toString() {
        return "R1{" +
                "x='" + x + '\'' +
                ", y='" + y + '\'' +
                ", z='" + z + '\'' +
                '}';
    }

}
