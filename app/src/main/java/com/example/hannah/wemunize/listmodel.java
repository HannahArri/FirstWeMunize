package com.example.hannah.wemunize;

/**
 * Created by hannah on 3/2/17.
 */

public class listmodel {
    private String childname;
    private String carename ;
    private long num1 ;
    private long num2;
    public void setChildname(String childname){
        this.childname = childname;
    }
    public void setCarename(String carename){
        this.carename = carename;
    }
    public void setNum1(long num1){
        this.num1 = num1;
    }
    public void setNum2(long num2){
        this.num2 =num2;
    }
    public String getChildname(){
        return this.childname;
    }
    public String getCarename(){
        return this.carename;
    }
    public long getNum1(){
        return this.num1;
    }
    public long getNum2(){
        return this.num2;
    }
}
