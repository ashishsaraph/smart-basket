package com.example.ashish.customer;

public class Movie {

    private String title, genre, year;
    private String pid,pname,price;
    public Movie() {
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Movie(String pid, String pname, String price) {
        this.pid = pid;
        this.pname = pname;
        this.price ="price ₹"+ price;
    }


//    public Movie(String title, String genre, String year) {
//        this.title = title;
//        this.genre = genre;
//        this.year = "price ₹"+year;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String name) {
//        this.title = name;
//    }
//
//    public String getYear() {
//        return year;
//    }
//
//    public void setYear(String year) {
//        this.year = year;
//    }
//
//    public String getGenre() {
//        return genre;
//    }
//
//    public void setGenre(String genre) {
//        this.genre = genre;
//    }
}
