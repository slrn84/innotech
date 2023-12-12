package ru.courses2.reflection;

public class Fraction implements Fractionable {
    private int num;
    private int denum;

    public int testMethodIsCached = 0;

    public Fraction(int num, int denum) {
        this.num = num;
        this.denum = denum;
    }

    @Override
    @Mutator
    public void setNum(int num) {
        this.num = num;
    }

    @Override
    @Mutator
    public void setDenum(int denum) {
        this.denum = denum;
    }

    @Override
    @Cache
    public double doubleValue() {
        testMethodIsCached++;
        System.out.println("invoke double value");
        return (double) num / denum;
    }

    @Override
    public String toString() {
        return "Fraction {" + num + ";" + denum + '}';
    }
}
