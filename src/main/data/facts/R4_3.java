package main.data.facts;

import main.data.annotation.PrimaryKey;

public class R4_3 implements Fact {

    @PrimaryKey
    public String x;
    @PrimaryKey
    public String u;
    public String v;

    public R4_3() {
    }

    public void setX(String x) {
        this.x = x;
    }

    // In case of a composite primary key we simply concatenate them, when we look for the blocks
    // sharing primary keys, the concatenated primary keys can successfully be compared
    @Override
    public String getPrimaryKey() {
        return x + u;
    }

    @Override
    public String toString() {
        return "R4_3{" +
                "x='" + x + '\'' +
                ", u='" + u + '\'' +
                ", v='" + v + '\'' +
                '}';
    }

}
