package main.data.relations;

import main.data.annotation.PrimaryKey;

public class R4 implements Fact {
    @PrimaryKey
    public String y;
    @PrimaryKey
    public String v;
    public String w;

    public R4() {
    }

    //in case of a composite primary key we simply concatenate them, when we find the blocks sharing primary keys, we can
    //detect that the composite primary keys are the same this way
    @Override
    public String getPrimaryKey() {
        return y + v;
    }

    @Override
    public String toString() {
        return "R4{" +
                "y='" + y + '\'' +
                ", v='" + v + '\'' +
                ", w='" + w + '\'' +
                '}';
    }
}
