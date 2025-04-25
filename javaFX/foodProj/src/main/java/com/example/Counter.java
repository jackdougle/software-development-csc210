package com.example;

public class Counter {
    private int num = 0;

    public int getNum() {
        return this.num;
    }

    public void incr() {
        this.num += 1;
    }
    
    public String toString() {
        return String.valueOf(this.getNum());
    }
}
