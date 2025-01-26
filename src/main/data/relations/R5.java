package main.data.relations;

import main.data.annotation.PrimaryKey;

public class R5 implements Fact {
    @PrimaryKey
    public String x;
    public String y;
    public String d;

    public R5() {
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
        return "R5{" +
                "x='" + x + '\'' +
                ", y='" + y + '\'' +
                ", d='" + d + '\'' +
                '}';
    }
}
