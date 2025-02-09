package main.data.facts;

import main.data.annotation.PrimaryKey;

public class R1_2 implements Fact {

    @PrimaryKey
    public String z;
    public String x;
    public String d;

    public R1_2() {
    }

    public void setX(String x) {
        this.x = x;
    }

    @Override
    public String getPrimaryKey() {
        return z;
    }

    @Override
    public String toString() {
        return "R1_2{" +
                "z='" + z + '\'' +
                ", x='" + x + '\'' +
                ", d='" + d + '\'' +
                '}';
    }

}
