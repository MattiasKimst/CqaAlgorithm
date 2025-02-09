package main.data.facts;

import main.data.annotation.PrimaryKey;

public class R3_2 implements Fact {

    @PrimaryKey
    public String x;
    public String y;

    public R3_2() {
    }

    public void setY(String y) {
        this.y = y;
    }

    @Override
    public String getPrimaryKey() {
        return x;
    }

    @Override
    public String toString() {
        return "R3_2{" +
                "x='" + x + '\'' +
                ", y='" + y + '\'' +
                '}';
    }

}
