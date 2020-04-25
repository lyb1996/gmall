package com.steven.gmall.item;

public class Obj implements Cloneable {
    private int a = 0;

    public int getAInt() {
        return a;
    }

    public void setInt(int int1) {
        a = int1;
    }

    public void changeInt() {
        this.a = 1;
    }

    public Object clone() {
        Object o = null;
        try {
            o = (Obj) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }

    public static void main(String[] args) {
        Obj a = new Obj();
//        Obj b = a;
        Obj b = (Obj) a.clone();
        b.changeInt();
        System.out.println("a：" + a.getAInt());
        System.out.println("b：" + b.getAInt());
    }
}
