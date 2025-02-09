package main.data.facts;

import main.data.annotation.PrimaryKey;

public class R6 implements Fact {

    @PrimaryKey
    public String y;
    public String z;

    public R6() {
    }

    public void setZ(String z) {
        this.z = z;
    }

    @Override
    public String getPrimaryKey() {
        return y;
    }

    @Override
    public String toString() {
        return "R6{" +
                "y='" + y + '\'' +
                ", z='" + z + '\'' +
                '}';
    }

}
