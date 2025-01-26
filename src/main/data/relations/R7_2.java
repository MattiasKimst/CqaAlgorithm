package main.data.relations;

import main.data.annotation.PrimaryKey;

public class R7_2 implements Fact {
    @PrimaryKey
    public String x;
    public String u;

    public R7_2() {
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
        return "R7_2{" +
                "x='" + x + '\'' +
                ", u='" + u + '\'' +
                '}';
    }
}
