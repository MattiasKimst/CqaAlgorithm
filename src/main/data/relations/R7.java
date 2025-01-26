package main.data.relations;

import main.data.annotation.PrimaryKey;

public class R7 implements Fact {
    @PrimaryKey
    public String z;
    public String x;

    public R7() {
    }

    public void setX(String x) {
        this.x = x;
    }

    @Override
    public String getPrimaryKey() {
        return z;
    }

    @Override
    public String toString() {
        return "R7{" +
                "z='" + z + '\'' +
                ", x='" + x + '\'' +
                '}';
    }
}
