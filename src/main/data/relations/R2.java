package main.data.relations;

import main.data.annotation.PrimaryKey;

public class R2 implements Fact {
    @PrimaryKey
    public String y;
    public String v;
    public String w;

    public R2() {
    }

    @Override
    public String getPrimaryKey() {
        return y;
    }

    @Override
    public String toString() {
        return "R2{" +
                "y='" + y + '\'' +
                ", v='" + v + '\'' +
                ", w='" + w + '\'' +
                '}';
    }
}
