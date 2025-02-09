package main.data.facts;

import main.data.annotation.PrimaryKey;

public class R2_3 implements Fact {

    @PrimaryKey
    public String xPrim;
    public String y;
    public String w;

    public R2_3() {
    }

    @Override
    public String getPrimaryKey() {
        return xPrim;
    }

    @Override
    public String toString() {
        return "R2_3{" +
                "x'='" + xPrim + '\'' +
                ", y='" + y + '\'' +
                ", w='" + w + '\'' +
                '}';
    }

}
