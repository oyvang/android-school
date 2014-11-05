package org.oyvang.friendlist;

import java.io.Serializable;

/**
 * Created by GeirMorten on 22.09.2014.
 */
public class Person implements Serializable {
    private String name;
    private Integer day, month, year;


    public Person(String name, Integer day, Integer month, Integer year) {

        this.name = name;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getDate() {
        return getDay() + "/" + getMonth() + "/" + getYear();
    }

    @Override
    public String toString() {
        return getName() + "," + getDate();
    }
}
