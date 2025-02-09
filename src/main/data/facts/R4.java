package main.data.facts;

import main.data.annotation.PrimaryKey;

public class R4 implements Fact {

    @PrimaryKey
    public String y;
    @PrimaryKey
    public String v;
    public String w;

    public R4() {
    }

    // In case of a composite primary key we simply concatenate them, when we look for the blocks
    // sharing primary keys, the concatenated primary keys can successfully be compared
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
