package main.data.relations;

import main.data.annotation.PrimaryKey;

public class R2_2 implements Fact {
    @PrimaryKey
    public String v;
    public String u;
    public String d;

    public R2_2() {
    }

    @Override
    public String getPrimaryKey() {
        return v;
    }

    @Override
    public String toString() {
        return "R2_2{" +
                "v='" + v + '\'' +
                ", u='" + u + '\'' +
                ", d='" + d + '\'' +
                '}';
    }
}
