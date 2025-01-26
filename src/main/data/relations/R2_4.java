package main.data.relations;

import main.data.annotation.PrimaryKey;

public class R2_4 implements Fact {
    @PrimaryKey
    public String y;
    public String x;
    public String w;

    public R2_4() {
    }

    @Override
    public String getPrimaryKey() {
        return y;
    }

    @Override
    public String toString() {
        return "R2_4{" +
                "y='" + y + '\'' +
                ", x='" + x + '\'' +
                ", w='" + w + '\'' +
                '}';
    }
}
