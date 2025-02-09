package main.data.facts;

import main.data.annotation.PrimaryKey;

public class R4_2 implements Fact {

    @PrimaryKey
    public String y;
    public String u;
    public String d;

    public R4_2() {
    }

    public void setY(String y) {
        this.y = y;
    }

    @Override
    public String getPrimaryKey() {
        return y;
    }

    @Override
    public String toString() {
        return "R4_2{" +
                "y='" + y + '\'' +
                ", u='" + u + '\'' +
                ", d='" + d + '\'' +
                '}';
    }

}
