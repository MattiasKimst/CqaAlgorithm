package main.data.relations;

import main.data.annotation.PrimaryKey;

public class R1 implements Fact {
    @PrimaryKey
    public String x;
    public String y;
    public String z;

    public R1() {
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
