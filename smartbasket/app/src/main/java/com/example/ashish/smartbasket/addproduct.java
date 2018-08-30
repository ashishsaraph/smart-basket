package com.example.ashish.smartbasket;

/**
 * Created by ashish on 17-Mar-18.
 */

public class addproduct {
    String pname,des;
    int price;
    long pid;

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }





    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }



    public addproduct(long pid, String pname, String des, int price) {

        this.pid = pid;
        this.pname = pname;
        this.des = des;

        this.price = price;
    }
}
