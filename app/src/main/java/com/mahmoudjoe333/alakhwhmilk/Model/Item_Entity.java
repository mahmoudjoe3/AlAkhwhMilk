package com.mahmoudjoe333.alakhwhmilk.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

@Entity(tableName = "item")
public class Item_Entity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String name;
    private String address;
    private String number;
    private int regain;
    private boolean Done;
    private int time;
    private String day;
    private float quantity;

    public Item_Entity() {

    }

    public Item_Entity(String name, String address, String number, int regain, boolean done, int time, String day, float quantity) {
        this.name = name;
        this.address = address;
        this.number = number;
        this.regain = regain;
        Done = done;
        this.time = time;
        this.day = day;
        this.quantity = quantity;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setRegain(int regain) {
        this.regain = regain;
    }

    public void setDone(boolean done) {
        Done = done;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getNumber() {
        return number;
    }

    public int getRegain() {
        return regain;
    }

    public boolean isDone() {
        return Done;
    }

    public int getTime() {
        return time;
    }

    public String getDay() {
        return day;
    }

    public float getQuantity() {
        return quantity;
    }
}
